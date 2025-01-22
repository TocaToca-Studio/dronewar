package choke3d.vika.frontend;

import choke3d.vika.frontend.mesh.Mesh;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author tocatoca
 */
public abstract class Scene {
    protected final HashSet<Camera> cameras=new HashSet<>();
    public void add_camera(Camera camera) { cameras.add(camera); }
    public void remove_camera(Camera camera) { cameras.remove(camera);   }
    
    protected final HashSet<DrawObject> objects=new HashSet<>();
    public void add_object(DrawObject obj) { objects.add(obj); }
    public void remove_obj(DrawObject obj) { objects.remove(obj);   }
    
    protected abstract void clear_camera(Camera camera,int win_width,int win_height); 
    public abstract void init();
    public abstract void draw_object(DrawObject obj);
    public abstract void set_skybox_texture(Texture sky);
    public void render(int win_width,int win_height) {
        for(Camera camera : cameras) {
            clear_camera(camera,win_width,win_height);
            for(DrawObject obj : objects) {
                draw_object(obj);
            }
            
        }
      
    }
    public abstract void destroy();
}
