package choke3d;
    
import choke3d.math.Vec3f;
import choke3d.math.Mat4f;
import choke3d.math.Quat;
import choke3d.math.Transform;
import choke3d.math.Vec2f; 
import choke3d.vika.frontend.Camera;
import choke3d.vika.frontend.Input;
import choke3d.vika.frontend.Platform;
/**
 *
 * @author tocatocaq
 */
public class FPSCamera extends Camera {
    public Transform transform=new Transform();
    public float velocity=10;  
    public FPSCamera() {
        super();
        this.setup3D(45);
    }
    public Vec2f angles=new Vec2f(0,0);
    public void update(Platform platform) {  
        Vec2f axis=platform.get_input().getAxis(); 
        float delta=(float) platform.get_delta();
        Quat rot=Quat.IDENTITY();
        angles.x-=delta*axis.x;
        angles.y-=delta*axis.y;
        rot.rotate(angles.y, new Vec3f(1f, 0, 0));
        rot.rotate(angles.x, new Vec3f(0f,1f,0));
        transform.rotation=rot; 
            
        Vec3f mov=new Vec3f(0,0,0);
        if(platform.get_input().pressing(Input.TRIANGLE)) mov.z-=1.0f;
        if(platform.get_input().pressing(Input.CROSS)) mov.z+=1.0f;
        mov=Mat4f.IDENTITY().translated(mov).rotated(transform.rotation).translation().mul(velocity*delta);
        transform.position=transform.position.add(mov);
        super.setViewMatrix(transform.viewMatrix());
    }
}
