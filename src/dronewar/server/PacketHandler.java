package dronewar.server;

import choke3d.utils.BinaryPackage;
import choke3d.utils.UDPCompression;
import dronewar.server.game.Player;
import dronewar.server.protocol.ControlData;
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
        String connection=clientAddress + ":" + clientPort;
        try {
            if(message_code==Protocol.LOGIN_REQUEST) {
                LoginData loginData=new LoginData();
                loginData.decode(buffer);
                System.out.println("Player " + loginData.name + " tentou logar");
                
                Player player=server.room.register_player(connection,loginData.name);
                System.out.println("Player " + loginData.name + " logou");
                send_response(Protocol.LOGIN_REQUEST,
                        player
                        ,clientAddress,clientPort);
            } else if(message_code==Protocol.GENERAL_UPDATE) {  
                int player=0;
                try {
                    player=server.room.get_player_id(connection);
                } catch (Exception ex) {
                    Logger.getLogger(PacketHandler.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                } 
                synchronized(server.room.player_controls) {
                    if(!server.room.player_controls.containsKey(player)) {
                        ControlData control=new ControlData();
                        control.unpack(buffer);
                        server.room.player_controls.put(player,control);
                    }else {
                        server.room.player_controls.get(player).unpack(buffer);
                    }
                }
                
                GeneralUpdateData data=new GeneralUpdateData();
                
                synchronized(server.room.bullets) {
                    data.bullets.clear();
                    data.bullets.addAll(server.room.bullets);
                }
                synchronized(server.room.drones) {
                        data.drones.clear();
                        data.drones.addAll(server.room.drones.values());
                }
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
        try {
            // Processa a mensagem recebida
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            //String connection=clientAddress + ":" + clientPort;
            //System.out.println("Pacote recebido de " + connection + " ("+packet.getLength()+" bytes)");
            
            ByteBuffer buffer=ByteBuffer.wrap(UDPCompression.decompress(packet.getData()));
            /*if(!ChecksumUtil.verifyChecksum(buffer)) {
            System.out.println("PACOTE CORROMPIDO CHECKSUM INVALIDO!");
            return;
            }*/
            int message_code=buffer.getInt();
            handle_message(buffer,message_code,clientAddress,clientPort);  
        } catch (IOException ex) {
            Logger.getLogger(PacketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    byte[] buffer = new byte[65535];
    public void listen()  {  
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
