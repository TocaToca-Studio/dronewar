package choke3d.math;

/**
 *
 * @author tocatoca
 */

public class Vec2f {
    public float x, y;

    public Vec2f() {
        x = y = 0;
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2f(Vec3f v) {
        this.x = v.x;
        this.y = v.y;
    }

    // Overload operators as methods
    public Vec2f add(Vec2f v) {
        return new Vec2f(x + v.x, y + v.y);
    }

    public Vec2f subtract(Vec2f v) {
        return new Vec2f(x - v.x, y - v.y);
    }

    public Vec2f multiply(Vec2f v) {
        return new Vec2f(x * v.x, y * v.y);
    }
    public Vec2f mul(Vec2f v) {
        return new Vec2f(x * v.x, y * v.y);
    }

    public Vec2f divide(Vec2f v) {
        return new Vec2f(x / v.x, y / v.y);
    }

    public Vec2f add(float s) {
        return new Vec2f(x + s, y + s);
    }

    public Vec2f subtract(float s) {
        return new Vec2f(x - s, y - s);
    }

    public Vec2f multiply(float s) {
        return new Vec2f(x * s, y * s);
    }
    public Vec2f mul(float s) {
        return new Vec2f(x * s, y * s);
    }


    public Vec2f divide(float s) {
        return new Vec2f(x / s, y / s);
    }

    public Vec2f negate() {
        return new Vec2f(-x, -y);
    }

    public boolean equals(Vec2f v) {
        return Float.compare(x, v.x) == 0 && Float.compare(y, v.y) == 0;
    }

    public boolean notEquals(Vec2f v) {
        return !equals(v);
    }

    public Vec2f flipped() {
        return new Vec2f(y, x);
    }

    public Vec2f opposite() {
        return new Vec2f(-x, -y);
    }

    public Vec2f inverse() {
        return new Vec2f(1 / x, 1 / y);
    }

    public float dot() {
        return x * x + y * y;
    }

    public float dot(Vec2f v) {
        return x * v.x + y * v.y;
    }

    public float length() {
        return (float) Math.sqrt(dot());
    }

    public float magnitude() {
        return length();
    }

    public Vec2f normalize() {
        return divide(magnitude());
    }

    public Vec3f toVec3(float z) {
        return new Vec3f(x, y, z);
    }

    public Vec2f abs() {
        return new Vec2f(Math.abs(x), Math.abs(y));
    }

    public static float distance(Vec2f a, Vec2f b) {
        return a.subtract(b).magnitude();
    }

    public static Vec2f ZERO() {
        return new Vec2f(0, 0);
    }

    public static Vec2f UNIT() {
        return new Vec2f(1, 1);
    }

    public static Vec2f X() {
        return new Vec2f(1, 0);
    }

    public static Vec2f Y() {
        return new Vec2f(0, 1);
    }

    public static Vec2f UP() {
        return new Vec2f(0, 1);
    }

    public static Vec2f DOWN() {
        return new Vec2f(0, -1);
    }

    public static Vec2f LEFT() {
        return new Vec2f(-1, 0);
    }

    public static Vec2f RIGHT() {
        return new Vec2f(1, 0);
    }
}