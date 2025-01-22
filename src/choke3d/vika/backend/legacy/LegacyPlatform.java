package choke3d.vika.backend.legacy;

import choke3d.math.Vec2i;
import choke3d.math.Vec3f;
import choke3d.vika.frontend.Input;
import choke3d.vika.frontend.Platform;
import choke3d.vika.frontend.Scene;
import choke3d.vika.frontend.Texture;
import choke3d.vika.frontend.mesh.Mesh;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author tocatoca
 */
public class LegacyPlatform extends Platform {   
    public final LegacyTiming time=new LegacyTiming();
    public final LegacyInput input=new LegacyInput();
    public boolean running=true;
    public void init(){ 
         try {  
            Display.setVSyncEnabled(true);
            Display.setDisplayMode(new DisplayMode(800,600));
            
            Display.create();
            running=true;
        } catch (LWJGLException e) {
            e.printStackTrace();  
            running=false;
            return;
        }   
        time.init(); 
    } 
    public void update() {  
        if (!running) return;  
        if(Display.isCloseRequested()) {
            System.out.print("FECHA LOGOO!!");
            running=false;
            return;
        }
        time.update();
        input.update();
        Display.setTitle("FPS: "+ Math.round(1.0f/time.delta)); 
        Display.update();
    }
      
    public void destroy() {
        running=false; 
        Display.destroy();  
    } 

    @Override
    public Scene create_scene() {
       return new LegacyScene();
    }
     
    @Override
    public double get_delta() {
       return time.getDelta();
    }

    @Override
    public boolean is_running() {
        return running;
    }

    @Override
    public Input get_input() {
       return input;
    }

    @Override
    public Mesh create_mesh() {
      return new LegacyMesh();
    }

    @Override
    public Vec2i get_window_size() {
        return new Vec2i(Display.getWidth(),Display.getHeight());
    }

    @Override
    public Texture create_texture() {
       return new LegacyTexture();
    }
    
}