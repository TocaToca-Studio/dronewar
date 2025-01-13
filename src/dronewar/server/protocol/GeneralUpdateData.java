package dronewar.server.protocol;

import choke3d.utils.BinaryPackage;
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
        /*
        putField("bullets","binary");*/
    }
    @Override
    public void serialize() { 
        try {
            putValue("safezone",safezone.packBytes()); 
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (Drone d : drones) outputStream.write(d.packBytes()); 
            byte[] buffdrones=outputStream.toByteArray();
            putValue("drones",buffdrones);
            System.out.println("buffer drones:"+drones.size());
            
            ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream(); 
            for (Bullet b : bullets) outputStream2.write(b.packBytes()); 
            putValue("bullets",outputStream2.toByteArray()); 
        } catch (IOException ex) {
            Logger.getLogger(GeneralUpdateData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void unserialize() {
       safezone.unpack(ByteBuffer.wrap(getBytes("safezone")));
        drones.clear();
       // System.out.println("buffer drones:"+ByteBuffer.wrap(getBytes("drones")).toString());
       ByteBuffer buff_drones=ByteBuffer.wrap(getBytes("drones")); 
       while(buff_drones.remaining()>0) {
           //System.out.println("lendo um novo drone " + buff_drones.position());
           Drone d=new Drone();
           d.unpack(buff_drones);
           drones.add(d);
       }  
       bullets.clear();
      /* ByteBuffer buff_bullets=ByteBuffer.wrap(getBytes("bullets"));
       while(buff_bullets.remaining()>0) {
           System.out.println("lendo um novo bullet");
           Bullet b=new Bullet();
           b.unpack(buff_bullets);
           bullets.add(b);
       }*/
    }
}