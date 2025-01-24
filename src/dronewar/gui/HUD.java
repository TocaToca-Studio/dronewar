package dronewar.gui;

import choke3d.math.Color4f;
import choke3d.math.Mat4f;
import choke3d.math.Vec2i;
import choke3d.vika.backend.JavaImageWraper;
import choke3d.vika.frontend.Camera;
import choke3d.vika.frontend.Platform;
import choke3d.vika.frontend.Texture;
import dronewar.server.game.Drone;
import dronewar.server.game.Player;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author tocatoca
 */
public class HUD {
    private final Vec2i resolution=new Vec2i(640,480);
    private Camera camera=new Camera();  
    private Texture text=null;
    public void init(Platform platform) {
        camera.setupHUD(resolution.x, resolution.y); 
        camera.setViewMatrix(Mat4f.IDENTITY().translated(0, 0, -1));
        camera.clearColor=new Color4f(1,1,1,0);
        text=platform.create_texture();
        try {
            text.load(
                    new JavaImageWraper(
                            ImageIO.read(
                                    this.getClass().getResourceAsStream("../assets/ascii.png")
                            )
                    )
            );
        } catch (IOException ex) {
            Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void drawText(String str, float x, float y,float font_size,Color4f color) {
        final int GRID_SIZE = 16;         // Textura 16x16
        final float FACTOR = 1f / GRID_SIZE; // Pré-calculado para evitar divisões repetidas
        text.bind();
        glPushMatrix();
        glTranslatef(x, y, 0);
        GL11.glScalef(font_size*0.75f, font_size, font_size);
        
        glColor4f(color.r,color.g,color.b,color.a);
        
        glBegin(GL_QUADS);
        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i); // Obtém o código do caractere
            //int c=3;
            // Calcula as coordenadas UV para a textura 16x16
            float u = (c % GRID_SIZE) * FACTOR;      // Coord. horizontal
            float v = (c / GRID_SIZE) * FACTOR;      // Coord. vertical
            //u=1-u;
            //v=1-v;
            // Desenha o caractere usando as coordenadas UV e a posição dos vértices
            glTexCoord2f(u, v);                     glVertex2f(i, 0);
            glTexCoord2f(u + FACTOR, v);            glVertex2f(i + 1, 0);
            glTexCoord2f(u + FACTOR, v + FACTOR);   glVertex2f(i + 1, 1);
            glTexCoord2f(u, v + FACTOR);            glVertex2f(i, 1);
        }

        glEnd();

        glPopMatrix();
        text.unbind();
    }
    public void drawShadowedText(String str, float x,float y,float font_size,Color4f color) {
        float off=(2f/16f) * font_size;
        drawText(str,x+(off*0.75f),y+off,font_size,Color4f.BLACK()); 
        drawText(str,x,y,font_size,color);
    }
    
    public void draw(Platform platform,Drone drone) { 
        // limpa e configura viewport
        platform.clear_camera(camera);
        
         
            // Habilita texturas e configura blending para transparência
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        GL11.glEnable(GL_ALPHA);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Exibe texto para a energia do jogador
        //drawBar(80, 200, 100, 10, drone.energy / 100.0f); // Barra de energia
        drawShadowedText("Energy:", 10, 200, 16,Color4f.WHITE());
       
        // Exibe texto para a posição do jogador
        drawShadowedText(String.format("Position: (%.1f, %.1f, %.1f)", 
        drone.position.x, drone.position.y, drone.position.z), 10, 180, 16,Color4f.WHITE());

        // Exibe texto para a velocidade do jogador
        drawShadowedText(String.format("Velocity: %.1f", drone.velocity), 10, 160, 16,Color4f.WHITE());

        // Desabilita texturas e blending
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);

    }
    

    // Método para desenhar uma barra de progresso
    private void drawBar(float x, float y, float width, float height, float percentage) {
        // Desenha o fundo da barra
        glColor4f(0.5f, 0.5f, 0.5f, 1); // Cor cinza
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();

        // Desenha a barra preenchida
        glColor4f(0.0f, 1.0f, 0.0f, 1); // Cor verde
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width * percentage, y);
        glVertex2f(x + width * percentage, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }
    public void destroy(Platform platform) {
        
    }
    
}
