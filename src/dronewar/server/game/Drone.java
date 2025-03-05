package dronewar.server.game;

import choke3d.math.Color4f;
import choke3d.math.Mat4f;
import choke3d.math.Quat;
import choke3d.math.Vec3f;
import choke3d.network.BinaryPackage; 

/**
 *
 * @author tocatoca
 */
public class Drone extends BinaryPackage implements Sphere {
    public static final float MAX_VELOCITY=15f; 
    public static final float MAX_ANG_VELOCITY=1.6f; 
    public static final float FIRERATE=1f/2f; // dois tiros por segundo
    public Vec3f position=new Vec3f(0,10,0);
    public float angle=0;
    public float energy=100;
    public Vec3f velocity=new Vec3f();
    public float angular_velocity=0;
    public int player=0;
    public float fire_timeout=0.1f;
    
    public Quat get_rotation() {
        Quat rot=Quat.IDENTITY();
        //rot.rotate(angle.y, new Vec3f(1f, 0, 0));
        rot.rotate(angle, new Vec3f(0f,1f,0));
        return rot;
    }
    
    @Override
    public float getRadius() {
        return 2.5f;//energy/50f;
    } 
    public Vec3f getDirection() {
        return Mat4f.IDENTITY().translated(Vec3f.FORWARD())
                        .rotated(get_rotation()).translation().normalized();
    }
   
    void update(double delta) {
        if(fire_timeout>0) fire_timeout-=delta;       
        position=position.add(
            getDirection().mul(velocity.z*(float) delta)
        );
    }
    public Color4f get_color() { 
        return PlayerColorGenerator.getColor(player,energy/100.0f);
    }
    @Override
    public Vec3f getPosition() {
        return position;
    }
    public Drone() {
        super();  
        putField("position","vec3f");   
        putField("angle","float");    
        putField("angular_velocity","float");    
        putField("energy","float");   
        putField("fire_timeout","float");   
        putField("velocity","vec3f");   
        putField("player","int");     
    }
    @Override
    public void serialize() { 
        putValue("position",position);   
        putValue("angular_velocity",angular_velocity);  
        putValue("angle",angle);  
        putValue("energy",energy);   
        putValue("fire_timeout",fire_timeout);   
        putValue("velocity",velocity);   
        putValue("player",player);   
    }
    @Override
    public void unserialize() {
        position=getVec3f("position");   
        angular_velocity=getFloat("angular_velocity");  
        angle=getFloat("angle");  
        energy=getFloat("energy");    
        fire_timeout=getFloat("fire_timeout");    
        velocity=getVec3f("velocity");   
        player=getInt("player");   
    }

    boolean canFire() {
        return energy>50 && fire_timeout<=0;
    }
}
