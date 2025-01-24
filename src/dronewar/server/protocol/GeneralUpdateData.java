package dronewar.server.protocol;

import choke3d.network.BinaryPackage;
import dronewar.server.game.Bullet;
import dronewar.server.game.Drone;
import dronewar.server.game.Safezone;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tocatoca
 */
public class GeneralUpdateData extends BinaryPackage {
    public Safezone safezone=new Safezone();
    public final List<Drone> drones= Collections.synchronizedList(new ArrayList<>());
    public final List<Bullet> bullets= Collections.synchronizedList(new ArrayList<>());
    
    public GeneralUpdateData() {
        super();   
        putField("safezone","binary");
        putField("drones","binary");
        putField("bullets","binary");
        
    }
    @Override
    public void serialize() { 
        try {
            putValue("safezone",safezone.packBytes());  
          
            putValue("drones",packList(drones));  
            putValue("bullets",packList(bullets)); 
        } catch (IOException ex) {
            Logger.getLogger(GeneralUpdateData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void unserialize() {
       
       safezone.unpack(ByteBuffer.wrap(getBytes("safezone")));
       synchronized(drones) {
           drones.clear();
           unpackToList(drones,getBytes("drones"),Drone.class); 
       }
       synchronized(drones) {
            bullets.clear();
            unpackToList(bullets,getBytes("bullets"),Bullet.class);
       }
    }
}