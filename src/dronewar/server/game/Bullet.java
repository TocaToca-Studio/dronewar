package dronewar.server.game;

import choke3d.math.Vec3f;
import choke3d.utils.BinaryPackage;

/**
 *
 * @author tocatoca
 */
public class Bullet extends BinaryPackage implements Sphere {
    public Vec3f direction=new Vec3f(0,0,0);
    public Vec3f position=new Vec3f(0,0,0);
    public float velocity=10;
    public float energy=10;
    public int player=0;
    
    void update(float delta) {
        energy-=delta;
        position=position.add(
            direction.normalized().mul(velocity*delta)
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
        putField("direction","vec3f");   
        putField("position","vec3f");   
        putField("velocity","float");   
        putField("energy","float");   
        putField("player","int");   
    }
    @Override
    public void serialize() {
        putValue("direction",direction);   
        putValue("position",position);   
        putValue("velocity",velocity);   
        putValue("energy",energy);   
        putValue("player",player);   
    }
    @Override
    public void unserialize() {
        direction=getVec3f("direction");   
        position=getVec3f("position");   
        velocity=getFloat("velocity");   
        energy=getFloat("energy");   
        player=getInt("player");   
    }
}
