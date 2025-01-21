package choke3d.gl;
  
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse; 
import choke3d.math.Vec3f;
import choke3d.math.Mat4f;
import choke3d.math.Quat;
import choke3d.math.Transform;
import choke3d.math.Vec2f; 
import choke3d.vika.frontend.Camera;
/**
 *
 * @author tocatocaq
 */
public class FPSCamera extends Camera {
    public Transform transform=new Transform();
    public float velocity=10;  
    public FPSCamera() {
        super();
        this.setup3D(60);
    }
    public Vec2f angles=new Vec2f(0,0);
    public void update(float delta) { 
        Vec3f mov=new Vec3f(0,0,0);
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) mov.z-=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) mov.z+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_E)) mov.y+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)) mov.y-=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) mov.x+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) mov.x-=delta;
        if(Mouse.isButtonDown(1)) {
            Quat rot=Quat.IDENTITY();
            angles.x-=delta*Mouse.getDY();
            angles.y+=delta*Mouse.getDX();
            rot.rotate(angles.x, new Vec3f(1f,0,0));
            rot.rotate(angles.y, new Vec3f(0f,1f,0));
            transform.rotation=rot;
        }
        mov=Mat4f.IDENTITY().translated(mov).rotated(transform.rotation).translation();
        transform.position=transform.position.add(mov);
        super.setViewMatrix(transform.viewMatrix());
    }
}
