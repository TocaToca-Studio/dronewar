package choke3d.math;

/**
 *
 * @author tocatoca
 */

public class Vec2i {
    public int x, y;

    public Vec2i() {
        x = y = 0;
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Overload operators as methods
    public Vec2i add(Vec2i v) {
        return new Vec2i(x + v.x, y + v.y);
    }

    public Vec2i subtract(Vec2i v) {
        return new Vec2i(x - v.x, y - v.y);
    }

    public Vec2i multiply(Vec2i v) {
        return new Vec2i(x * v.x, y * v.y);
    }

    public Vec2i divide(Vec2i v) {
        return new Vec2i(x / v.x, y / v.y);
    }

    public Vec2i add(int s) {
        return new Vec2i(x + s, y + s);
    }

    public Vec2i subtract(int s) {
        return new Vec2i(x - s, y - s);
    }

    public Vec2i multiply(int s) {
        return new Vec2i(x * s, y * s);
    }

    public Vec2i divide(int s) {
        return new Vec2i(x / s, y / s);
    }

    public Vec2i negate() {
        return new Vec2i(-x, -y);
    }

    public boolean equals(Vec2i v) {
        return x == v.x && y == v.y;
    }

    public boolean notEquals(Vec2i v) {
        return !equals(v);
    }

    public Vec2i flipped() {
        return new Vec2i(y, x);
    }

    public Vec2i opposite() {
        return new Vec2i(-x, -y);
    }

    public Vec2f inverse() {
        return new Vec2f(1 / (float) x, 1 / (float) y);
    }

    public int dot() {
        return x * x + y * y;
    }

    public int dot(Vec2i v) {
        return x * v.x + y * v.y;
    }

    public float length() {
        return (float) Math.sqrt(dot());
    }

    public float magnitude() {
        return length();
    }

    public Vec2i normalize() {
        return divide((int) magnitude());
    }

    public Vec2i clamp(int min, int max) {
        return new Vec2i(
                Math.max(min, Math.min(max, x)),
                Math.max(min, Math.min(max, y))
        );
    }

    public Vec3f toVec3(float z) {
        return new Vec3f(x, y, z);
    }

    public Vec2f toVec2f() {
        return new Vec2f(x, y);
    }

    public Vec2i abs() {
        return new Vec2i(Math.abs(x), Math.abs(y));
    }

    public static float distance(Vec2i a, Vec2i b) {
        return a.subtract(b).magnitude();
    }

    public static Vec2i ZERO() {
        return new Vec2i(0, 0);
    }

    public static Vec2i UNIT() {
        return new Vec2i(1, 1);
    }

    public static Vec2i X() {
        return new Vec2i(1, 0);
    }

    public static Vec2i Y() {
        return new Vec2i(0, 1);
    }

    public static Vec2i UP() {
        return new Vec2i(0, 1);
    }

    public static Vec2i DOWN() {
        return new Vec2i(0, -1);
    }

    public static Vec2i LEFT() {
        return new Vec2i(-1, 0);
    }

    public static Vec2i RIGHT() {
        return new Vec2i(1, 0);
    }
}