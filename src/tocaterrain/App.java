package tocaterrain;

import choke3d.vika.backend.legacy.LegacyTiming; 
import choke3d.FPSCamera;
import choke3d.math.Vec3f;
import choke3d.vika.frontend.Camera;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 *
 * @author tocatoca
 */
public abstract class App { 
    public FPSCamera camera=new FPSCamera();
    public LegacyTiming time=new LegacyTiming();
    public boolean running=true;
    public void init(){ 
        
        try {  
            Display.setVSyncEnabled(true);
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.create();
            time.init();
            running=true;
        } catch (LWJGLException e) {
            e.printStackTrace();  
            running=false;
            return;
        }   
        glEnable(GL11.GL_TEXTURE);
        glEnable(GL_TEXTURE_2D);
        //glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
    }
    
    public void update() {  
        if (Display.isCloseRequested() || running==false) {
            running=false;
            return;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F3)){
            Display.setTitle("FPS:" + 1.0f/time.delta );
        }  
        Display.update();  
        time.update();  
        
    }
      
    public void destroy() {
        running=false;
        Display.destroy();  
    }
    public Vec3f getInputAxis() {
        float delta=1;
        Vec3f mov=new Vec3f(0,0,0);
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) mov.z-=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) mov.z+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_E)) mov.y+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)) mov.y-=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) mov.x+=delta;
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) mov.x-=delta;
        return mov;
    }
}
