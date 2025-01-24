package dronewar.server.protocol;

import choke3d.math.Vec2f;
import choke3d.math.Vec3f;
import choke3d.network.BinaryFormat;
import choke3d.network.BinaryPackage;

/**
 *
 * @author tocatoca
 */ 
public class ControlData extends BinaryPackage { 
    public Vec2f movement=new Vec2f();
    public boolean fire=false;
    public ControlData() {
        super();
        putField("movement", "vec2f"); 
        putField("fire", "boolean"); 
    } 
    @Override
    public void serialize() {
        putValue("movement", movement);
        putValue("fire", fire); 
    }
    @Override
    public void unserialize() {
        movement=getVec2f("movement");
        fire=getBool("fire");
    }
}