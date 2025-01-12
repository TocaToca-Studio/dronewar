package dronewar.server.game;

import choke3d.math.Vec3f;

/**
 *
 * @author tocatoca
 */
public class Safezone implements Sphere {
    Vec3f position=new Vec3f(0,0,0);
    float radius=512f;
    float damage=20f;
    @Override
    public float getRadius() {return radius;}

    @Override
    public Vec3f getPosition() {return position;}

}
