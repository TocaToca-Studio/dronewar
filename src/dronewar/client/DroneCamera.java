package dronewar.client;
  
import choke3d.math.Transform;
import choke3d.FPSCamera;  
import choke3d.math.Mat4f;
import choke3d.math.Vec3f; 
import choke3d.vika.frontend.Camera;
import choke3d.vika.frontend.Input;
import choke3d.vika.frontend.Platform;
import dronewar.server.game.Drone; 
/**
 *
 * @author tocatoca
 */
public class DroneCamera extends FPSCamera { 
    public Drone target=null; 
    float yoffset=1;
    public boolean follow_drone=false;
    public DroneCamera() {
        super();
        this.setup3D(60);
    }
     
    public void update(Platform platform) { 
        if(platform.get_input().pressed(Input.SELECT)) {
            follow_drone=!follow_drone;
        }
        if(!follow_drone) {
            super.update(platform);
            return;
        }
        float delta=(float) platform.get_delta();
        if(target!=null) { 
            Vec3f target_pos=target.position.copy();
            target_pos=target_pos.add(
             Mat4f.IDENTITY().translated(Vec3f.BACK()).rotated(target.rotation).translation().mul(20)
            );
            target_pos.y+=yoffset ;//+ target.getRadius()
            transform.position=Vec3f.lerp(transform.position, target_pos, delta*5); 
            transform.rotation=target.rotation;
        }
        setViewMatrix(transform.viewMatrix()); 
        
    }
}
