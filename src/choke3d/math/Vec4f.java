package choke3d.math;

/**
 *
 * @author tocatoca
 */
public class Vec4f {
    public float x, y, z, w;

    // Constructors
    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1;
    }

    public Vec4f(Vec3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    // Convert to Vec3f
    public Vec3f toVec3() {
        return new Vec3f(x, y, z);
    }

    // Convert to Vec2f
    public Vec2f toVec2() {
        return new Vec2f(x, y);
    }

    // Normalize the vector
    public Vec3f normalize() {
        return new Vec3f(x / w, y / w, z / w);
    }

    // Project the vector
    public Vec2f project() {
        Vec3f n = normalize();
        return new Vec2f(n.x / n.z, n.y / n.z);
    }
}
