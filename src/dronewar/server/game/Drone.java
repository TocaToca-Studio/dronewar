package dronewar.server.game;

import choke3d.math.Color4f;
import choke3d.math.Mat4f;
import choke3d.math.Quat;
import choke3d.math.Vec2f;
import choke3d.math.Vec3f;
import choke3d.network.BinaryPackage;
import java.util.Random;

/**
 *
 * @author tocatoca
 */
public class Drone extends BinaryPackage implements Sphere {
    public Vec3f position=new Vec3f(0,10,0);
    public Vec2f angles=new Vec2f();
    public float energy=100;
    public float velocity=2f;
    public int player=0;
    final float FIRERATE=1f/2f; // dois tiros por segundo
    public float fire_timeout=0.1f;
    
    public Quat get_rotation() {
        Quat rot=Quat.IDENTITY();
        rot.rotate(angles.y, new Vec3f(1f, 0, 0));
        rot.rotate(angles.x, new Vec3f(0f,1f,0));
        return rot;
    }
    
    @Override
    public float getRadius() {
        return 2.5f;//energy/50f;
    } 
   
    void update(double delta) {
        if(fire_timeout>0) fire_timeout-=delta;
        Vec3f direction=
                Mat4f.IDENTITY().translated(Vec3f.FORWARD())
                        .rotated(get_rotation()).translation();
        position=position.add(
            direction.normalized().mul(velocity*(float)delta)
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
        putField("angles","vec2f");  
        putField("velocity","float");   
        putField("energy","float");   
        putField("fire_timeout","float");   
        putField("velocity","float");   
        putField("player","int");     
    }
    @Override
    public void serialize() { 
        putValue("position",position);   
        putValue("angles",angles);  
        putValue("energy",energy);   
        putValue("fire_timeout",fire_timeout);   
        putValue("velocity",velocity);   
        putValue("player",player);   
    }
    @Override
    public void unserialize() {
        position=getVec3f("position");   
        angles=getVec2f("angles");  
        energy=getFloat("energy");    
        fire_timeout=getFloat("fire_timeout");    
        velocity=getFloat("velocity");   
        player=getInt("player");   
    }

    boolean canFire() {
        return energy>50 && fire_timeout<=0;
    }
}
