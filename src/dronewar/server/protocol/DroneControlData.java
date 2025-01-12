package dronewar.server.protocol;

import choke3d.math.Vec2f;
import choke3d.utils.BinaryFormat;
import choke3d.utils.BinaryPackage;

/**
 *
 * @author tocatoca
 */ 
public class DroneControlData extends BinaryPackage { 
    Vec2f movement=new Vec2f();
    boolean fire=false;
    public DroneControlData() {
        super();
        putField("movement.y", "float");
        putField("movement.x", "float"); 
        putField("fire", "boolean"); 
    } 
    @Override
    public void serialize() {
        putValue("movement.y", movement.x);
        putValue("movement.x", movement.y); 
        putValue("fire", fire); 
    }
    @Override
    public void unserialize() {
        movement.x=getFloat("movement.x");
        movement.y=getFloat("movement.y");
        fire=getBool("fire");
    }
}