package remine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import remine.Drawer;
import choke3d.engine.App;
import choke3d.engine.FPSCamera;
import choke3d.engine.Texture;

/**
 *
 * @author tocatoca
 */
public class Remine extends App {
    Texture dirt;
    
    public void run() {
        init();
        dirt=new Texture();
        try {
            dirt.load(ImageIO.read(this.getClass().getResourceAsStream("assets/dirt.png")));
        } catch (IOException ex) {
            Logger.getLogger(Remine.class.getName()).log(Level.SEVERE, null, ex);
        }
        dirt.bind();
        camera=new FPSCamera();
        camera.setup3D(60); 
        while(running) {
           update(); 
           Drawer.cube();
        }
        destroy();
    }
    public static void main(String args[]) {
        (new Remine()).run();
    }
}
