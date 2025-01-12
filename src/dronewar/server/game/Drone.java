package dronewar.server.game;

import choke3d.math.Mat4f;
import choke3d.math.Quat;
import choke3d.math.Vec3f;

/**
 *
 * @author tocatoca
 */
public class Drone implements Sphere {
    float energy=100;
    Vec3f position=new Vec3f(0,0,0);
    float velocity=0;
    Quat rotation=new Quat();
    int player=0;
    
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
}
