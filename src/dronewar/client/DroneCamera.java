package dronewar.client;
 
import choke3d.engine.Camera; 
import choke3d.math.Mat4f;
import choke3d.math.Quat;
import choke3d.math.Vec2f; 
import choke3d.math.Vec3f;
import org.lwjgl.input.Mouse;
/**
 *
 * @author tocatocaq
 */
public class DroneCamera extends Camera {
    public float velocity=10;  
    public Drawer target=null;
    Vec3f position=new Vec3f(0,0,0);
    public Vec2f angles=new Vec2f(0,0);
    float zoffset=2;
    
    public DroneCamera() {
        super();
        this.setup3D(60);
    }
    
    public void update(float delta) { 
        transform.position=position.add(
            Mat4f.IDENTITY().translated(
            new Vec3f(0,0,zoffset)
            ).rotated(transform.rotation).translation()
        );
        if(Mouse.isButtonDown(1)) {
            Quat rot=Quat.IDENTITY();
            angles.x-=delta*Mouse.getDY();
            angles.y+=delta*Mouse.getDX();
            rot.rotate(angles.x, new Vec3f(1f,0,0));
            rot.rotate(angles.y, new Vec3f(0f,1f,0));
            transform.rotation=rot;
        }
        /*
        Vec3f mov=new Vec3f(0,0,0);
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) mov.z-=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) mov.z+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_E)) mov.y+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)) mov.y-=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) mov.x+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) mov.x-=delta;
        mov=Mat4f.IDENTITY().translated(mov).rotated(transform.rotation).translation();
        if(target!=null) {
            transform.position=Vec3f.lerp(transform.position, target.position, delta);
        }
        transform.position=transform.position.add(mov);
        */
    }
}
