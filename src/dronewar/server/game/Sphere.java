package dronewar.server.game;

import choke3d.math.Vec3f;

/**
 *
 * @author tocatoca
 */
public interface Sphere {
    public float getRadius();
    public Vec3f getPosition();
    default boolean collideWith(Sphere other) { 
        return Vec3f.distance(other.getPosition(),getPosition())<(getRadius()+other.getRadius());
    }
}
