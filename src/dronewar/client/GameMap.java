package dronewar.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import choke3d.mesh.Heightmap;
import choke3d.vika.backend.JavaImageWraper;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author tocatoca
 */
public class GameMap {
    public Heightmap terrain;
    
    void init() {
        terrain=new Heightmap(2,2); 
        try { 
            terrain.load(ImageIO.read(this.getClass().getResourceAsStream("../assets/map/map0_hmap.jpg")));
            terrain.texture.load(new JavaImageWraper(ImageIO.read(this.getClass().getResourceAsStream("../assets/map/map0_texture.jpg"))));
        } catch (IOException ex) {
            Logger.getLogger(GameMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        terrain.colored_terrain=false;
        terrain.draw_wireframe=false;
        terrain.draw_terrain=true;
        terrain.width=1024;
        terrain.length=1024;
        terrain.hscale=25;
        
        
    }
    void update() {
       
    }
    void draw() {
        if(terrain!=null) {
            
            terrain.draw();
        }
        GL11.glPushMatrix();
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(0, 0, 1);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(0, 0, 1);
        GL11.glVertex3f(1, 0, 1);
        GL11.glPopMatrix();
    }
}
