package choke3d.vika;

import choke3d.math.Vec2i;
import choke3d.vika.backend.JavaImageWraper;
import choke3d.vika.backend.legacy.LegacyPlatform; 
import choke3d.vika.frontend.DrawObject;
import choke3d.vika.frontend.Platform;
import choke3d.vika.frontend.Scene;
import choke3d.vika.frontend.Texture;
import choke3d.vika.frontend.mesh.Mesh;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author tocatoca
 */
public class vikaTest {
    Platform platform=new LegacyPlatform(); 
    public FPSCamera camera=new FPSCamera(); 
    public Texture load_texture(String path) {
        Texture txt=platform.create_texture();
        try {
            txt.load(new JavaImageWraper(ImageIO.read(vikaTest.class.getResourceAsStream(path))));
        } catch (IOException ex) {
            Logger.getLogger(vikaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return txt;
    }
    public void run() {
        Scene scene=platform.create_scene(); 
        platform.init();
        scene.init();
        
        Mesh cube=platform.create_mesh(); 
        cube.load(Mesh.get_cube());
        DrawObject cube_obj=new DrawObject(cube); 
        scene.add_object(cube_obj);
        
        
        Vec2i win_size=platform.get_window_size();
        
        scene.set_skybox_texture(
                load_texture("assets/skybox2.jpg")
        );
        
        scene.add_camera(camera);
        while(platform.is_running()) {
            platform.update();
            camera.update(platform);
            scene.render(win_size.x,win_size.y);
        }
        platform.destroy();
    }
    public static void main(String[] args) {
        (new vikaTest()).run();
    }
}
