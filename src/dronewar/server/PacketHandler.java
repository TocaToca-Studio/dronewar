package dronewar.server;

import choke3d.utils.BinaryPackage;
import choke3d.utils.ChecksumUtil;
import dronewar.server.protocol.ErrorData;
import dronewar.server.protocol.GeneralUpdateData;
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
public class PacketHandler extends Thread {  

    PacketHandler(Server aThis) {
        server=aThis;
    }
    @Override
    public void run() {
        // Código a ser executado na nova thread
        listen();
    }
    int port = 25532; 
    public DatagramSocket socket ; 
    public Server server = null;
    public void send_response(int message_code,BinaryPackage pack,InetAddress address,int port) throws IOException {
       if(pack==null) {
           socket.send(Protocol.preparePacket(message_code, address, port));
       } else {
           socket.send(Protocol.preparePacket(pack, message_code, address, port));
       }
    }
    public void handle_message(ByteBuffer buffer,int message_code,InetAddress clientAddress,int clientPort) { 
        
        try {
            if(message_code==Protocol.LOGIN_REQUEST) {
                LoginData loginData=new LoginData();
                loginData.decode(buffer);
                System.out.println("Player " + loginData.name + " tentou logar");
                String connection=clientAddress + ":" + clientPort;
                int player_id=server.room.register_player(connection,loginData.name);
                System.out.println("Player " + loginData.name + " logou");
                send_response(Protocol.SUCCESS,null,clientAddress,clientPort);
            } else if(message_code==Protocol.GENERAL_UPDATE) {
                GeneralUpdateData data=new GeneralUpdateData();
                data.bullets.clear();
                data.bullets.addAll(server.room.bullets);
                data.drones.clear();
                data.drones.addAll(server.room.drones.values());
                data.safezone=server.room.safezone;
                send_response(Protocol.GENERAL_UPDATE,data,clientAddress,clientPort);
            }  else if(message_code==Protocol.FAIL) {
                System.out.println("Servidor não reconheceu pacote.");
            }  else {
                send_response(Protocol.FAIL,new ErrorData(),clientAddress,clientPort);
                System.out.println("Mensagem não reconhecida");

            }
        } catch (IOException ex) {

            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void handle_packet(DatagramPacket packet) {
        // Processa a mensagem recebida
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();
        //String connection=clientAddress + ":" + clientPort;
        //System.out.println("Pacote recebido de " + connection + " ("+packet.getLength()+" bytes)");

        ByteBuffer buffer=ByteBuffer.wrap(packet.getData());
       /*if(!ChecksumUtil.verifyChecksum(buffer)) {
            System.out.println("PACOTE CORROMPIDO CHECKSUM INVALIDO!");
            return;
        }*/
        int message_code=buffer.getInt();
        handle_message(buffer,message_code,clientAddress,clientPort);  
    }
    public void listen()  {  
        byte[] buffer = new byte[1024];
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            System.out.println("ERRO AO ABRIR, POSSIVELMENTE PORTA JA EM USO!.");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        // Cria o socket para receber pacotes
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        System.out.println("Servidor aguardando pacotes na porta " + port);

        while (server.running && !isInterrupted()) {
            try {
                // Recebe o pacote
                socket.receive(packet);
                handle_packet(packet);
            } catch (IOException ex) {
                System.out.println("erro ao receber pacote: "+ ex.getLocalizedMessage());
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
}
