package dronewar.server;

import dronewar.server.game.Room;
import choke3d.engine.Timing;
import dronewar.server.game.Drone;
import javax.swing.JOptionPane;

/**
 *
 * @author tocatoca
 */
public class Server {
    Room room=new Room();
    boolean running=false;
    Timing time=new Timing(); 
    
    PacketHandler listenThread=new PacketHandler(this);
    public void run()  {
        time.init();
        room.init();  
        running=true;
        listenThread.start();
        while(running) {
            float delta=time.update();
            //System.out.println("delta:"+delta);
            room.update(delta);
            // limit server to 200FPS
            try { Thread.sleep(5); } 
            catch (InterruptedException ex) {running=false;}
        }
        room.destroy();    
        listenThread.interrupt();
    }
    public static void main(String[] args) {  
        Server server=(new Server());
        (new Thread(() -> {
            JOptionPane.showMessageDialog(null, "Clique para fechar");
            server.running=false;
            if(server.listenThread.socket!=null) {
                server.listenThread.socket.close();
            }
        })).start();
        server.run();   
    }
}
