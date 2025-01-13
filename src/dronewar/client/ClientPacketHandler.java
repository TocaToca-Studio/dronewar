package dronewar.client;

import choke3d.utils.BinaryPackage;
import choke3d.utils.ChecksumUtil;
import dronewar.server.Server;
import dronewar.server.protocol.ErrorData;
import dronewar.server.protocol.LoginData;
import dronewar.server.protocol.Protocol;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tocatoca
 */
public class ClientPacketHandler extends Thread {  
    public boolean connect(String name,String ip,int port) {
        try {
            // Criação do socket UDP
            if (socket==null) socket = new DatagramSocket();
            // Endereço e porta do servidor
            InetAddress address = InetAddress.getByName(ip); 
            
            socket.connect(address, port);
            
            LoginData loginData = new LoginData(); 
            loginData.name=name;
             
            // Criação do pacote UDP 
            // Enviar o pacote
            socket.send(
                    Protocol.preparePacket(loginData, Protocol.LOGIN_REQUEST, address, port)
            );
            System.out.println("Pacote enviado para o servidor " );
            byte[] receiveBuffer = new byte[1024]; 
            // Cria o socket para receber pacotes
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            
            socket.receive(receivePacket);
            ByteBuffer buffer=ByteBuffer.wrap(receivePacket.getData());
            int message_code=buffer.getInt();
            System.out.println("Resposta do servidor: " + message_code);
            if(message_code==Protocol.FAIL) {
                ErrorData error=new ErrorData();
                error.decode(buffer);
                System.out.println("Erro enviado pelo servidor: " + error.message);
                
            }
            start();
            return message_code==Protocol.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
            return false;
        }  
    }
    ClientPacketHandler(DroneWarClient aThis) {
        client=aThis;
    }
    @Override
    public void run() {
        // Código a ser executado na nova thread
        listen();
    }
    int port = 25532; 
    public DatagramSocket socket; 
    public DroneWarClient client = null;
    public InetAddress serverAddress= null;
    public int serverPort= 25532;
    public void send_message(int message_code,BinaryPackage pack) throws IOException {
       if(pack==null) {
           socket.send(Protocol.preparePacket(message_code, serverAddress, serverPort));
       } else {
           socket.send(Protocol.preparePacket(pack, message_code, serverAddress, serverPort));
       }
    }
    public void handle_message(ByteBuffer buffer,int message_code) { 
        try {
            if(message_code==Protocol.GENERAL_UPDATE) {
                client.last_update.unpack(buffer);
                /*System.out.println("RAIO DA SAFE:" +
                client.last_update.safezone.getRadius() );*/
            } else if(message_code==Protocol.FAIL) {
                System.out.println("Servidor não reconheceu pacote.");
            }  else {
                send_message(Protocol.FAIL,new ErrorData());
                System.out.println("Mensagem não reconhecida");

            }
        } catch (IOException ex) {

            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void handle_packet(DatagramPacket packet) {
        // Processa a mensagem recebida
        serverAddress = packet.getAddress();
        serverPort = packet.getPort();
        //String connection=serverAddress + ":" + serverPort;
        //System.out.println("Pacote recebido o servidor " + connection + " ("+packet.getLength()+" bytes)");
        
        ByteBuffer buffer=ByteBuffer.wrap(packet.getData());
       /* if(!ChecksumUtil.verifyChecksum(buffer)) {
            System.out.println("PACOTE CORROMPIDO CHECKSUM INVALIDO!");
            return;
        }*/
        int message_code=buffer.getInt();
        handle_message(buffer,message_code);  
    }
    public void listen()  {  
        byte[] buffer = new byte[1024];
         
        // Cria o socket para receber pacotes
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        System.out.println("Cliente aguardando pacotes na porta " + port);
  
        while (client.running && !isInterrupted()) {
            try {
                // pede atualização
                send_message(Protocol.GENERAL_UPDATE,null);
                // Recebe o pacote
                socket.receive(packet);
                handle_packet(packet); 
            } catch (IOException ex) {
                System.out.println("erro ao receber ou enviar pacote: "+ ex.getLocalizedMessage());
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientPacketHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
}