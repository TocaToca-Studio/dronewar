package dronewar.client;

import choke3d.engine.App;
import choke3d.engine.FPSCamera; 
import dronewar.server.game.Drone; 
import dronewar.server.protocol.GeneralUpdateData; 
/**
 *
 * @author tocatoca
 */
public class DroneWarClient extends App {
    GameMap map=new GameMap(); 
    
    GeneralUpdateData last_update=new GeneralUpdateData(); 
    
    ClientPacketHandler packetHandler=new ClientPacketHandler(this);
    
    
    @Override
    public void update() {
        super.update();
        map.update();
        
        camera.update(time.delta);
    }
    
    void draw() { 
        map.draw();  
        synchronized(last_update.drones) {
        for(Drone d : last_update.drones) {
            Drawer.draw_drone(d);
        }
        }
    }
    public void run() {
        init();  
        camera=new FPSCamera();
        camera.setup3D(60); 
        map.init(); 
        //((DroneCamera)camera).target=drone; 
        while(running) {
           update(); 
           draw();
        }
        destroy();
        packetHandler.socket.close();
    }
    public static void main(String args[]) {
        (new DroneWarClient()).run();
    }
}
