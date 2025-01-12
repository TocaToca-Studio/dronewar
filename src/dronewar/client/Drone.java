package dronewar.client;

import choke3d.math.Quat;
import choke3d.math.Vec3f;
import choke3d.math.Vec4f;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author tocatoca
 */
public class Drone {
    Quat rotation=new Quat();
    Vec3f position=new Vec3f();
    
    Drone() {
        rotation=new Quat();
        rotation.rotate((float) Math.toRadians(90), Vec3f.LEFT());
    }
    
    void draw() { 
        glPushMatrix();
        Vec4f angles=rotation.to_angles();
        glTranslatef(position.x,position.y,position.z);
        glRotatef(angles.w, angles.x, angles.y, angles.z);
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

        glPopMatrix();
    }
}
