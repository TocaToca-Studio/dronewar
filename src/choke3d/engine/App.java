package choke3d.engine;

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
    public Camera camera=new Camera();
    public Timing time=new Timing();
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
        camera.update(time.delta);
        camera.clear(Display.getWidth(),Display.getHeight());
        
    }
      
    public void destroy() {
        running=false;
        Display.destroy();  
    }
}
