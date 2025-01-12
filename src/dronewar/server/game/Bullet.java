package dronewar.server.game;

import choke3d.math.Vec3f;

/**
 *
 * @author tocatoca
 */
public class Bullet implements Sphere {
    Vec3f direction=new Vec3f(0,0,0);
    Vec3f position=new Vec3f(0,0,0);
    float velocity=10;
    float energy=10;
    int player=0;
    
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
}
