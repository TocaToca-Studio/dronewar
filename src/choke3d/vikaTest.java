package choke3d;

import choke3d.math.Transform;
import choke3d.math.Vec2i;
import choke3d.math.Vec3f;
import choke3d.mesh.Heightmap;
import choke3d.mesh.OBJParser;
import choke3d.mesh.OBJParser.OBJFileData;
import choke3d.mesh.OBJParser.OBJMesh;
import choke3d.vika.backend.JavaImageWraper;
import choke3d.vika.backend.legacy.LegacyPlatform; 
import choke3d.vika.frontend.DrawObject;
import choke3d.vika.frontend.Image;
import choke3d.vika.frontend.Platform;
import choke3d.vika.frontend.Scene;
import choke3d.vika.frontend.Texture;
import choke3d.vika.frontend.mesh.Mesh;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    public Mesh load_obj(String path) {
        Mesh mesh=platform.create_mesh();
        try {
            InputStream inputStream=vikaTest.class.getResourceAsStream(path);
            String data=new String(inputStream.readAllBytes(), StandardCharsets.UTF_8); ;
            OBJFileData obj=OBJParser.parseObjFile(data, path);
            mesh.load(
                obj.objects.values().toArray(new OBJMesh[0])[0].get_geometry()
            );
        } catch (IOException ex) {
            Logger.getLogger(vikaTest.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        return mesh;
    }
    public void run() {
        Scene scene=platform.create_scene(); 
        platform.init();
        scene.init();
        
        Mesh cube=platform.create_mesh(); 
        cube.load(Mesh.get_cube());
        DrawObject cube_obj=new DrawObject(load_obj("assets/drone.obj")); 
        scene.add_object(cube_obj);
        
        Heightmap terrain=new Heightmap(128,128);
        Image terrain_image;
        try {
            terrain_image = new JavaImageWraper(ImageIO.read(vikaTest.class.getResourceAsStream("assets/terrain_hmap.jpg")));
            
            terrain.load(terrain_image, 128, 128);
        } catch (IOException ex) {
            Logger.getLogger(vikaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Mesh terrain_mesh=platform.create_mesh();
        terrain_mesh.load(terrain.generate_geometry());
        
        
        DrawObject terrain_obj=new DrawObject(terrain_mesh); 
        Texture terrain_texture=platform.create_texture();
        terrain_texture.load(terrain.getHeightColoredTexture());
        cube_obj.material.albedo=terrain_texture;
        terrain_obj.material.albedo=load_texture("assets/terrain_texture.jpg");
        
        Transform terrain_transform=new Transform();
        terrain_transform.scale=new Vec3f(1024,16,1024);
        terrain_transform.position=terrain_transform.scale.divide(2.0f).negate();
        terrain_obj.model=terrain_transform.matrix();
        
        scene.add_object(terrain_obj);
        
        Vec2i win_size=platform.get_window_size();
        
        platform.set_skybox_texture(
            load_texture("assets/skybox.png")
        );
        
        scene.add_camera(camera);
        while(platform.is_running()) {
            platform.update();
            camera.velocity=20;
            camera.update(platform);
            scene.render(platform);
        }
        platform.destroy();
    }
    public static void main(String[] args) {
        (new vikaTest()).run();
    }
}
