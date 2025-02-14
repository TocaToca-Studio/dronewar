package dronewar.client;
 
import choke3d.math.Mat4f;
import choke3d.math.Transform;
import choke3d.math.Vec2i; 
import choke3d.math.Vec3f;
import choke3d.FPSCamera;
import choke3d.math.Color4f;
import choke3d.math.Quat;
import choke3d.math.Vec2f;
import choke3d.mesh.Heightmap;
import choke3d.mesh.OBJParser;
import choke3d.vika.backend.JavaImageWraper;
import choke3d.vika.backend.legacy.LegacyPlatform; 
import choke3d.vika.frontend.DrawObject;
import choke3d.vika.frontend.Image;
import choke3d.vika.frontend.Input; 
import choke3d.vika.frontend.Texture;
import choke3d.vika.frontend.mesh.Mesh;
import choke3d.vikaTest; 
import dronewar.client.gui.HUD;
import dronewar.server.game.Bullet;
import dronewar.server.game.Drone;
import dronewar.server.game.Player;
import dronewar.server.game.Safezone;
import dronewar.server.protocol.ControlData;
import dronewar.server.protocol.GeneralUpdateData;  
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;  
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINES;
/**
 *
 * @author tocatoca
 */
public class DroneWarClient extends LegacyPlatform { 
    GeneralUpdateData last_update=new GeneralUpdateData(); 
    
    ClientPacketHandler packetHandler=new ClientPacketHandler(this);
    Player player=new Player();
    ControlData client_control=new ControlData();
    
    public DroneCamera camera=new DroneCamera(); 
    public Texture load_texture(String path) {
        Texture txt=create_texture();
        try {
            txt.load(new JavaImageWraper(ImageIO.read(vikaTest.class.getResourceAsStream(path))));
        } catch (IOException ex) {
            Logger.getLogger(vikaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return txt;
    } 
    public Mesh load_obj(String path) {
        Mesh mesh=create_mesh();
        try {
            InputStream inputStream=vikaTest.class.getResourceAsStream(path);
            String data=new String(inputStream.readAllBytes(), StandardCharsets.UTF_8); ;
            OBJParser.OBJFileData obj=OBJParser.parseObjFile(data, path);
            mesh.load(
                obj.objects.values().toArray(new OBJParser.OBJMesh[0])[0].get_geometry()
            );
        } catch (IOException ex) {
            Logger.getLogger(vikaTest.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        return mesh;
    }
    public DrawObject drone_obj=null;
    public DrawObject bullet_obj=null;
    public DrawObject safezone_obj=null;
    
    public DrawObject cube_obj=null;
    public DrawObject quad_obj=null;
    public DrawObject terrain_obj=null;
    public HUD hud=new HUD();
    double t=0;
    @Override
    public void update() {
        t+=get_delta();
        super.update(); 
        //Display.setTitle(player.name); 
        if(camera.follow_drone) {
            client_control.movement=get_input().getAxis();
            client_control.fire=get_input().pressing(Input.CROSS);
        }
        Vec2i win_size=get_window_size();
        camera.update(this); 
        clear_camera(camera);
         synchronized(last_update.drones) {
            for(Drone d : last_update.drones) {
                if(d.player==player.id) {  
                   camera.target=d; 
                }
                draw_drone(d);
            }
        }
        synchronized(last_update.bullets) {
            for(Bullet b : last_update.bullets) { 
                draw_bullet(b);
            }
        }
        
        cube_obj.material.albedo_color.r=(float) Math.sin(t);
        draw_object(cube_obj,camera);
        draw_object(terrain_obj,camera);
        draw_safezone(last_update.safezone);
        if(camera.target!=null) {
            hud.draw(this, camera.target);
        }
        
    } 
    
    @Override
    public void init() {
        super.init(); 
        hud.init(this);
        Mesh cube=create_mesh();
        cube.load(Mesh.get_cube());
        Mesh quad=create_mesh();
        quad.load(Mesh.get_quad());
        quad_obj=new DrawObject(quad);
        Mesh sphere=load_obj("assets/sphere.obj");
        cube_obj=new DrawObject(cube); 
        drone_obj=new DrawObject(load_obj("assets/drone.obj")); 
        Mesh bullet=load_obj("assets/bullet.obj");
        bullet_obj=new DrawObject(bullet); 
        safezone_obj=new DrawObject(sphere); 
        
           
        Heightmap terrain=new Heightmap(128,128);
        Image terrain_image;
        try {
            terrain_image = new JavaImageWraper(ImageIO.read(vikaTest.class.getResourceAsStream("assets/terrain_hmap.jpg")));
            
            terrain.load(terrain_image, 128, 128);
        } catch (IOException ex) {
            Logger.getLogger(vikaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Mesh terrain_mesh=create_mesh();
        terrain_mesh.load(terrain.generate_geometry());
        
        
        terrain_obj=new DrawObject(terrain_mesh); 
        Texture terrain_texture=create_texture();
        terrain_texture.load(terrain.getHeightColoredTexture());
        cube_obj.material.albedo=terrain_texture;
        terrain_obj.material.albedo=load_texture("assets/terrain_texture.jpg");
        
        Transform terrain_transform=new Transform();
        terrain_transform.scale=new Vec3f(1024,16,1024);
        terrain_transform.position=terrain_transform.scale.divide(2.0f).negate();
        terrain_obj.model=terrain_transform.matrix();
        
        
        set_skybox_texture(
                load_texture("assets/skybox.png")
        );
    }
    public void run()   {
        init(); 
         
        while(is_running()) {
            update();
        }
        destroy();
        packetHandler.socket.close();
        System.exit(0);
    }
    public static void main(String[] args) {
        (new DroneWarClient()).run();
    }
    
    
    
    /*
    void draw() { 
        map.draw();  
       
    }
    public void run() {
        init();  
        //camera=new FPSCamera();
        camera=new DroneCamera();
        camera.setup3D(60);
        
  
        map.init(); 
        while(running) {
           update(); 
           draw();
        }
        destroy();
    }
    public static void main(String args[]) {
        LoginFrame.main(args);
        //(new DroneWarClient()).run();
    }
    */
    private void draw_lifebar(Drone d) {
        float rad = d.getRadius();
        Transform t = new Transform();

        // Posiciona a barra acima do drone
        t.position = d.position.copy();
        t.position.y += rad + (rad / 2); 

        t.scale = new Vec3f(5, 1, 1);

        // Vetor da câmera para o drone (direção inversa)
        Vec3f toDrone = d.position.copy().subtract(camera.transform.position);
        toDrone.y = 0; // Ignora componente vertical
        toDrone=toDrone.normalized();

        // Calcula o ângulo usando atan2 para obter a orientação correta em 360°
        float angle = (float) Math.atan2(toDrone.z, toDrone.x);

        // Aplica a rotação em torno do eixo Y
        
        t.rotation.rotate((float) (angle+ Math.toRadians(90)), Vec3f.UP());
         

        quad_obj.model = t.matrix();
        quad_obj.material.albedo_color = Color4f.WHITE();
        draw_object(quad_obj, camera);
    }
    private void draw_drone(Drone d) {
        Transform transform=new Transform();
        transform.position=d.position;
        transform.rotation=d.get_rotation();
        transform.scale=new Vec3f(1,1,1).mul(d.getDiameter());
        drone_obj.model=transform.matrix();
        drone_obj.material.albedo_color=d.get_color();
        //GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        draw_object(drone_obj,camera);
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        draw_lifebar(d);
    }
    private void draw_safezone(Safezone d) {
        Transform transform=new Transform();
        transform.position=d.position; 
        transform.scale=new Vec3f(1,1,1).mul(d.getDiameter());
        safezone_obj.model=transform.matrix();
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        draw_object(safezone_obj,camera);
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }
    private void draw_bullet(Bullet d) {
        Transform transform=new Transform();
        transform.position=d.position; 
        transform.scale=new Vec3f(1,1,1).mul(d.getDiameter());
        bullet_obj.model=transform.matrix();
        draw_object(bullet_obj,camera);
    }
}
