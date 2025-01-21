package dronewar.server.protocol;

import choke3d.math.Vec2f;
import choke3d.math.Vec3f;
import choke3d.utils.BinaryFormat;
import choke3d.utils.BinaryPackage;

/**
 *
 * @author tocatoca
 */ 
public class ControlData extends BinaryPackage { 
    public Vec3f movement=new Vec3f();
    public boolean fire=false;
    public ControlData() {
        super();
        putField("movement", "vec3f"); 
        putField("fire", "boolean"); 
    } 
    @Override
    public void serialize() {
        putValue("movement", movement);
        putValue("fire", fire); 
    }
    @Override
    public void unserialize() {
        movement=getVec3f("movement");
        fire=getBool("fire");
    }
}