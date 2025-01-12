package dronewar.client;

import choke3d.engine.App;
import choke3d.utils.BinaryPackage;
import dronewar.server.protocol.Protocol;
import dronewar.server.protocol.LoginData;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 *
 * @author tocatoca
 */
public class DroneWarClient extends App {
    GameMap map=new GameMap();
    HashMap<String,Drone> drones=new HashMap<>();
    
    DatagramSocket socket = null;
    
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
            byte[] responseBytes = new byte[1024]; 
            // Cria o socket para receber pacotes
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
            
            socket.receive(responsePacket);
            System.out.println("Resposta recebida do servidor " );
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
            return false;
        }  
    }
    
    @Override
    public void update() {
        super.update();
        map.update();
        
        camera.update(time.delta);
    }
    
    void draw() { 
        map.draw();  
        for(String id : drones.keySet()) {
            drones.get(id).draw();
        }
    }
    public void run() {
        init();  
        camera=new DroneCamera();
        camera.setup3D(60); 
        map.init();
        Drone drone=new Drone();
        ((DroneCamera)camera).target=drone;
        drones.put("1",drone);
        while(running) {
           update(); 
           draw();
        }
        destroy();
    }
    public static void main(String args[]) {
        (new DroneWarClient()).run();
    }
}
