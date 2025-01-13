package dronewar.server.game;

import choke3d.math.Mat4f;
import choke3d.math.Quat;
import choke3d.math.Vec3f;
import choke3d.utils.BinaryPackage;

/**
 *
 * @author tocatoca
 */
public class Drone extends BinaryPackage implements Sphere {
    public Vec3f position=new Vec3f(0,0,0);
    public Quat rotation=new Quat();
    public float energy=100;
    public float velocity=0.5f;
    public int player=0;
    
    @Override
    public float getRadius() {
        return energy/10f;
    } 
    void update(float delta) {
        Vec3f direction=
                Mat4f.IDENTITY().translated(Vec3f.FORWARD())
                        .rotated(rotation).translation();
        position=position.add(
            direction.normalized().mul(velocity*delta)
        );
    }

    @Override
    public Vec3f getPosition() {
        return position;
    }
    public Drone() {
        super();  
        putField("position","vec3f");   
        putField("rotation","quat");  
        putField("velocity","float");   
        putField("energy","float");   
        putField("player","int");     
    }
    @Override
    public void serialize() { 
        putValue("position",position);   
        putValue("rotation",rotation);  
        putValue("energy",energy);   
        putValue("velocity",velocity);   
        putValue("player",player);   
    }
    @Override
    public void unserialize() {
        position=getVec3f("position");   
        rotation=getQuat("rotation");  
        energy=getFloat("energy");    
        velocity=getFloat("velocity");   
        player=getInt("player");   
    }
}
