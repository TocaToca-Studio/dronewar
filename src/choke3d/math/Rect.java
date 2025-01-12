package choke3d.math;

/**
 *
 * @author tocatoca
 */
public class Rect {
    public Vec2f position, scale;

    // Default constructor
    public Rect() {
        this.position = new Vec2f(0, 0);
        this.scale = new Vec2f(1, 1);
    }

    // Parameterized constructor
    public Rect(Vec2f position, Vec2f scale) {
        this.position = position;
        this.scale = scale;
    }

}