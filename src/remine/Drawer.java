package remine;

import org.lwjgl.opengl.GL11; 

/**
 *
 * @author tocatoca
 */
public class Drawer {
    public static void zquad() {
        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(-0.5f,-0.5f,0f);
        GL11.glVertex3f(0.5f,-0.5f,0f);
        GL11.glVertex3f(0.5f,0.5f,0f);
        GL11.glVertex3f(-0.5f,0.5f,0f);
        GL11.glEnd();
    }
    public static void yquad() {
        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(-0.5f,0,-0.5f);
        GL11.glVertex3f(0.5f,0,-0.5f);
        GL11.glVertex3f(0.5f,0,0.5f);
        GL11.glVertex3f(-0.5f,0,0.5f);
        GL11.glEnd();
    }
    public static void xquad() {
        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(0,-0.5f,-0.5f);
        GL11.glVertex3f(0,0.5f,-0.5f);
        GL11.glVertex3f(0,0.5f,0.5f);
        GL11.glVertex3f(0,-0.5f,0.5f);
        GL11.glEnd();
    }
    public static void cube() {  
        GL11.glBegin(GL11.GL_QUADS);
        // +z
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f,-0.5f,0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f,-0.5f,0.5f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(0.5f,0.5f,0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f,0.5f,0.5f);
        // -z
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f,-0.5f,-0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f,-0.5f,-0.5f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(0.5f,0.5f,-0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f,0.5f,-0.5f);
        
        // + y 
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f,0.5f,-0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f,0.5f,-0.5f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(0.5f,0.5f,0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f,0.5f,0.5f); 
        
        
        // -y 
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f,-0.5f,-0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f,-0.5f,-0.5f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(0.5f,-0.5f,0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f,-0.5f,0.5f); 
        
        
        // +x 
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(+0.5f,-0.5f,-0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(+0.5f,0.5f,-0.5f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(+0.5f,0.5f,0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(+0.5f,-0.5f,0.5f);
        
        // -x
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f,-0.5f,-0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(-0.5f,0.5f,-0.5f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(-0.5f,0.5f,0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f,-0.5f,0.5f);
        
        GL11.glEnd();
    }
}
