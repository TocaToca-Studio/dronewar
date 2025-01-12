package dronewar.server;

import dronewar.server.game.Room;
import choke3d.engine.Timing;
import dronewar.server.protocol.LoginData;
import dronewar.server.protocol.Protocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tocatoca
 */
public class Server {
    DatagramSocket socket ; 
    int port = 25532; 
    Room room=new Room();
    boolean running=false;
    Timing time=new Timing(); 
    public void handle_package(DatagramPacket packet) {
        // Processa a mensagem recebida
         InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();
        String connection=clientAddress + ":" + clientPort;
        System.out.println("Pacote recebido de " + connection + " ("+packet.getLength()+" bytes)");
            
        try {
            ByteBuffer buff=ByteBuffer.wrap(packet.getData());
            int message_code=buff.getInt();
            if(message_code==Protocol.LOGIN_REQUEST) {
                LoginData loginData=new LoginData();
                loginData.decode(buff);
                System.out.println("Player " + loginData.name + " tentou logar");
            } else {
                System.out.println("Mensagem não reconhecida");
            }
             
            
            // Envia resposta ao cliente
            String responseMessage = "Resposta para o cliente";
            byte[] responseBuffer = responseMessage.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, clientAddress, clientPort);
            socket.send(responsePacket);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void listen()  { 
        try {
            byte[] buffer = new byte[1024];
            socket = new DatagramSocket(port);
            // Cria o socket para receber pacotes
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Servidor aguardando pacotes na porta " + port);

            while (running) {
                // Recebe o pacote
                socket.receive(packet); 
                handle_package(packet);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    Thread listenThread=new Thread(() ->{ listen(); });
    public void run()  {
        time.init();
        room.init();  
        running=true;
        listenThread.start();
        while(running) {
            float delta=time.update();
            room.update(delta);
            // limit server to 200FPS
            try { Thread.sleep(5); } 
            catch (InterruptedException ex) {running=false;}
        }
        room.destroy();    
        listenThread.interrupt();
    }
    public static void main(String[] args) {
        (new Server()).run();  
    }
}
