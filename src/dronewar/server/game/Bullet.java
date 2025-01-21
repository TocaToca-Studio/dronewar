package dronewar.server.game;

import choke3d.math.Vec3f;
import choke3d.utils.BinaryPackage;

/**
 *
 * @author tocatoca
 */
public class Bullet extends BinaryPackage implements Sphere {
    public Vec3f velocity=new Vec3f(0,0,0);
    public Vec3f position=new Vec3f(0,0,0); 
    public float energy=2;
    public int player=0; 
    
    void update(double delta) { 
        energy-=delta;
        position=position.add(
            velocity.mul((float)delta)
        );
    }

    @Override
    public float getRadius() {
        return energy/10f;
    }

    @Override
    public Vec3f getPosition() {
        return position;
    }
    
    public Bullet() {
        super();  
        putField("velocity","vec3f");   
        putField("position","vec3f");    
        putField("energy","float");   
        putField("player","int");   
    }
    @Override
    public void serialize() {   
        putValue("velocity",velocity); 
        putValue("position",position);   
        putValue("energy",energy);   
        putValue("player",player);   
    }
    @Override
    public void unserialize() {
        velocity=getVec3f("velocity");   
        position=getVec3f("position");   
        energy=getFloat("energy");   
        player=getInt("player");   
    }
}
