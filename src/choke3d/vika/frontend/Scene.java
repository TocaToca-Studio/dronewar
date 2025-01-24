package choke3d.vika.frontend;
 
import java.util.HashSet;

/**
 *
 * @author tocatoca
 */
public class Scene {
    protected final HashSet<Camera> cameras=new HashSet<>();
    public void add_camera(Camera camera) { cameras.add(camera); }
    public void remove_camera(Camera camera) { cameras.remove(camera);   }
    
    protected final HashSet<DrawObject> objects=new HashSet<>();
    public void add_object(DrawObject obj) { objects.add(obj); }
    public void remove_obj(DrawObject obj) { objects.remove(obj);   }
    
    public void init() {}
    public void destroy() {} 

    public void render(Platform platform) {
        for(Camera camera : cameras) {
            platform.clear_camera(camera);
            for(DrawObject obj : objects) {
                platform.draw_object(obj,camera);
            } 
        } 
    }}
