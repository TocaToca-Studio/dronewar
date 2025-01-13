package choke3d.math;

import static choke3d.math.MathUtils.LERP;
import java.nio.ByteBuffer;

/**
 *
 * @author tocatoca
 */
public class Vec3f {
    public float x, y, z;

    public Vec3f() {
        x = y = z = 0;
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public static Vec3f lerp(Vec3f a,Vec3f b, float factor) {
       return new Vec3f(
               LERP(a.x,b.x,factor),
               LERP(a.y,b.y,factor),
               LERP(a.z,b.z,factor)
       );
    }
    // Overload operators as methods
    public Vec3f add(Vec3f v) {
        return new Vec3f(x + v.x, y + v.y, z + v.z);
    }

    public Vec3f subtract(Vec3f v) {
        return new Vec3f(x - v.x, y - v.y, z - v.z);
    }

    public Vec3f multiply(Vec3f v) {
        return new Vec3f(x * v.x, y * v.y, z * v.z);
    }
    public Vec3f mul(Vec3f v) {
        return multiply(v);
    }
    public Vec3f mul(float v) {
        return new Vec3f(x * v, y * v, z * v);
    }
    public Vec3f divide(Vec3f v) {
        return new Vec3f(x / v.x, y / v.y, z / v.z);
    }

    public Vec3f add(float s) {
        return new Vec3f(x + s, y + s, z + s);
    }

    public Vec3f subtract(float s) {
        return new Vec3f(x - s, y - s, z - s);
    }

    public Vec3f multiply(float s) {
        return new Vec3f(x * s, y * s, z * s);
    }

    public Vec3f divide(float s) {
        return new Vec3f(x / s, y / s, z / s);
    }

    public Vec3f negate() {
        return new Vec3f(-x, -y, -z);
    }

    public boolean equals(Vec3f v) {
        return Float.compare(x, v.x) == 0 && Float.compare(y, v.y) == 0 && Float.compare(z, v.z) == 0;
    }

    public boolean notEquals(Vec3f v) {
        return !equals(v);
    }

    public Vec3f opposite() {
        return new Vec3f(-x, -y, -z);
    }

    public Vec3f inverse() {
        return new Vec3f(-x, -y, -z);
    }

    public float dot() {
        return x * x + y * y + z * z;
    }

    public float dot(Vec3f v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public float length() {
        return (float) Math.sqrt(dot());
    }
    

    public float magnitude() {
        return length();
    }

    public Vec3f normalized() {
        return divide(magnitude());
    }

    public Vec3f abs() {
        return new Vec3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Color4f toColor() {
        return new Color4f(x, y, z, 1.0f);
    }

    public Vec3f project(Vec3f b) {
        return project(this, b);
    }

    public static Vec3f cross(Vec3f a, Vec3f b) {
        return new Vec3f(
                a.y * b.z - a.z * b.y,
                a.z * b.x - a.x * b.z,
                a.x * b.y - a.y * b.x
        );
    }

    public static float dot(Vec3f a, Vec3f b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static Vec3f project(Vec3f a, Vec3f b) {
        return a.multiply(dot(a, b) / a.dot());
    }

    public static Vec3f ZERO() {
        return new Vec3f(0, 0, 0);
    }

    public static Vec3f UNIT() {
        return new Vec3f(1, 1, 1);
    }

    public static Vec3f X() {
        return new Vec3f(1, 0, 0);
    }

    public static Vec3f Y() {
        return new Vec3f(0, 1, 0);
    }

    public static Vec3f Z() {
        return new Vec3f(0, 0, 1);
    }

    public static Vec3f UP() {
        return new Vec3f(0, 1, 0);
    }

    public static Vec3f DOWN() {
        return new Vec3f(0, -1, 0);
    }

    public static Vec3f LEFT() {
        return new Vec3f(-1, 0, 0);
    }

    public static Vec3f RIGHT() {
        return new Vec3f(1, 0, 0);
    }

    public static Vec3f FORWARD() {
        return new Vec3f(0, 0, -1);
    }

    public static Vec3f BACK() {
        return new Vec3f(0, 0, 1);
    }

    public static float distance(Vec3f a, Vec3f b) {
        return a.subtract(b).magnitude();
    }

    public static Vec3f barycentric(Vec3f p, Vec3f a, Vec3f b, Vec3f c) {
        Vec3f v0 = b.subtract(a);
        Vec3f v1 = c.subtract(a);
        Vec3f v2 = p.subtract(a);

        float d00 = v0.dot(v0);
        float d01 = v0.dot(v1);
        float d11 = v1.dot(v1);
        float d20 = v2.dot(v0);
        float d21 = v2.dot(v1);

        float denom = d00 * d11 - d01 * d01;

        float v = (d11 * d20 - d01 * d21) / denom;
        float w = (d00 * d21 - d01 * d20) / denom;
        float u = 1.0f - v - w;

        return new Vec3f(u, v, w);
    }
    public ByteBuffer pack() {
        ByteBuffer ret=ByteBuffer.allocate(Float.SIZE*3);
        ret.putFloat(x); ret.putFloat(y);  ret.putFloat(z);
        ret.flip();
        return ret;
    }
    public static Vec3f unpack(ByteBuffer buff) {
        return new Vec3f(buff.getFloat(),buff.getFloat(),buff.getFloat());
    }
}
