package dronewar.server.game;

import choke3d.math.Vec3f;
import choke3d.network.BinaryPackage;

/**
 *
 * @author tocatoca
 */
public class Safezone extends BinaryPackage implements Sphere {
    public Vec3f position=new Vec3f(0,0,0);
    public float radius=512f;
    public float damage=20f;
    @Override
    public float getRadius() {return radius;}

    @Override
    public Vec3f getPosition() {return position;}
    
     public Safezone() {
        super();  
        putField("position","vec3f");   
        putField("radius","float");   
        putField("damage","float");   
    }
    @Override
    public void serialize() {
        putValue("position", position);  
        putValue("radius", radius); 
        putValue("damage", damage); 
    }
    @Override
    public void unserialize() {
        position=getVec3f("position"); 
        radius=getFloat("radius"); 
        damage=getFloat("damage"); 
    }

}
