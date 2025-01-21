package dronewar.client;

import choke3d.math.Quat;
import choke3d.math.Vec3f;
import choke3d.math.Vec4f;
import dronewar.server.game.Bullet;
import dronewar.server.game.Drone;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author tocatoca
 */
public class Drawer { 
    
    public static void draw_bullet(Bullet bullet) {
        glPushMatrix();
        Vec3f position=bullet.position;
        glTranslatef(position.x,position.y,position.z); 
        cube();
        glPopMatrix();
    }
    public static void draw_drone(Drone drone) { 
        Quat rotation=new Quat();
        Vec3f position=drone.getPosition();
        rotation.rotate((float) -Math.toRadians(90), Vec3f.LEFT());
        rotation=rotation.multiply(drone.rotation);
        glPushMatrix();
        Vec4f angles=rotation.conjugate().to_angles();
        glTranslatef(position.x,position.y,position.z);
        glRotatef(angles.w, angles.x, angles.y, angles.z);
        glScalef(40,40,40);
        // Corpo principal
        glColor3f(0.5f, 0.5f, 0.5f);
        glBegin(GL_QUADS);
        glVertex3f(-0.2f, -0.1f, 0.0f);
        glVertex3f(0.2f, -0.1f, 0.0f);
        glVertex3f(0.2f, 0.1f, 0.0f);
        glVertex3f(-0.2f, 0.1f, 0.0f);
        glEnd();

        // Asa esquerda
        glColor3f(0.3f, 0.3f, 0.8f);
        glBegin(GL_TRIANGLES);
        glVertex3f(-0.2f, 0.0f, 0.0f);
        glVertex3f(-0.4f, -0.2f, 0.0f);
        glVertex3f(-0.2f, -0.1f, 0.0f);
        glEnd();

        // Asa direita
        glColor3f(0.3f, 0.3f, 0.8f);
        glBegin(GL_TRIANGLES);
        glVertex3f(0.2f, 0.0f, 0.0f);
        glVertex3f(0.4f, -0.2f, 0.0f);
        glVertex3f(0.2f, -0.1f, 0.0f);
        glEnd();

        // Cauda
        glColor3f(0.8f, 0.3f, 0.3f);
        glBegin(GL_TRIANGLES);
        glVertex3f(-0.05f, 0.1f, 0.0f);
        glVertex3f(0.05f, 0.1f, 0.0f);
        glVertex3f(0.0f, 0.3f, 0.0f);
        glEnd();

        // Bico
        glColor3f(0.3f, 0.8f, 0.3f);
        glBegin(GL_TRIANGLES);
        glVertex3f(-0.05f, -0.1f, 0.0f);
        glVertex3f(0.05f, -0.1f, 0.0f);
        glVertex3f(0.0f, -0.3f, 0.0f);
        glEnd();
        glTranslatef(0,0,0.2f);
        glColor3f(0.5f, 0.5f, 0.5f);
        glBegin(GL_QUADS);
        glVertex3f(-0.2f, -0.1f, 0.0f);
        glVertex3f(0.2f, -0.1f, 0.0f);
        glVertex3f(0.2f, 0.1f, 0.0f);
        glVertex3f(-0.2f, 0.1f, 0.0f);
        glEnd();

        // Asa esquerda
        glColor3f(0.3f, 0.3f, 0.8f);
        glBegin(GL_TRIANGLES);
        glVertex3f(-0.2f, 0.0f, 0.0f);
        glVertex3f(-0.4f, -0.2f, 0.0f);
        glVertex3f(-0.2f, -0.1f, 0.0f);
        glEnd();

        // Asa direita
        glColor3f(0.3f, 0.3f, 0.8f);
        glBegin(GL_TRIANGLES);
        glVertex3f(0.2f, 0.0f, 0.0f);
        glVertex3f(0.4f, -0.2f, 0.0f);
        glVertex3f(0.2f, -0.1f, 0.0f);
        glEnd();

        // Cauda
        glColor3f(0.8f, 0.3f, 0.3f);
        glBegin(GL_TRIANGLES);
        glVertex3f(-0.05f, 0.1f, 0.0f);
        glVertex3f(0.05f, 0.1f, 0.0f);
        glVertex3f(0.0f, 0.3f, 0.0f);
        glEnd();

        // Bico
        glColor3f(0.3f, 0.8f, 0.3f);
        glBegin(GL_TRIANGLES);
        glVertex3f(-0.05f, -0.1f, 0.0f);
        glVertex3f(0.05f, -0.1f, 0.0f);
        glVertex3f(0.0f, -0.3f, 0.0f);
        glEnd();

        glPopMatrix();
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
