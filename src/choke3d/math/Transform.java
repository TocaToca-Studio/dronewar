package choke3d.math;

/**
 *
 * @author tocatoca
 */ 
public class Transform {
    public Vec3f position, scale;
    public Quat rotation;
    public float rotation2d = 0;

    // Default constructor
    public Transform() {
        this.position = new Vec3f(0, 0, 0);
        this.scale = new Vec3f(1, 1, 1);
        this.rotation = new Quat(1, 0, 0, 0);
    }

    // Set angles using Euler angles
    public void setAngles(Vec3f angles) {
        rotation.setEuler(angles);
    }

    // Get angles as Euler angles
    public Vec3f getAngles() {
        return rotation.getEuler();
    }

    // Set 2D rotation angle
    public void setRotation2D(float zangle) {
        this.rotation2d = zangle;
        rotation.setEuler(new Vec3f(0, 0, zangle));
    }

    // Get 2D rotation angle
    public float getRotation2D() {
        return rotation2d;
    }

    // Generate transformation matrix
    public Mat4f matrix() {
        return Mat4f.IDENTITY()
                    .scaled(scale)
                    .rotated(rotation)
                    .translated(position.x, position.y, position.z);
    }

    // Generate view matrix
    public Mat4f viewMatrix() {
        return Mat4f.mul(
            Mat4f.IDENTITY().rotated(rotation.conjugate().normalize()), 
            Mat4f.IDENTITY().translated(-position.x, -position.y, -position.z)
        );
    }

    // Static method to convert model matrix to view matrix
    public static Mat4f modelToViewMatrix(Mat4f model) {
        return Mat4f.mul(
            Mat4f.IDENTITY().rotated(model.to_quaternion().conjugate()),
            Mat4f.IDENTITY().translated(model.translation().opposite())
        );
    }
}
