package choke3d.vika.backend.legacy;

import choke3d.math.Color4f;
import choke3d.math.Mat4f;
import choke3d.math.Vec2f;
import choke3d.math.Vec4f;
import choke3d.vika.frontend.Camera;
import choke3d.vika.frontend.mesh.Mesh;
import choke3d.vika.frontend.Scene;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author tocatoca
 */
public class LegacyScene extends Scene {
    
    @Override
    public void init() { 
        glEnable(GL11.GL_TEXTURE);
        glEnable(GL_TEXTURE_2D);
    }

    FloatBuffer projection_buffer=BufferUtils.createFloatBuffer(16);
    FloatBuffer modelview_buffer=BufferUtils.createFloatBuffer(16);
    private FloatBuffer put_matrix(FloatBuffer buff,Mat4f mat) {
        buff.clear();
        for(float[] row : mat.m) buff.put(row);
        buff.flip();
        return buff;
    }
    @Override
    protected void clear_camera(Camera camera,int win_width,int win_height) {
        Vec2f winSize = new Vec2f(win_width, win_height);
        if (camera.fixAspect) camera.aspect = winSize.x / winSize.y;
        Vec2f pos = camera.viewport.position.mul(winSize);
        Vec2f size = camera.viewport.scale.mul(winSize);
        glViewport((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);
        Color4f clearColor=camera.clearColor;
        glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);

        //glEnable(GL_ALPHA);
        //glEnable(GL_BLEND);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (camera.mode == Camera.CameraMode.CAMERA_3D) {
            if (clearColor.a > 0.5) glClear(GL_COLOR_BUFFER_BIT); 
            glEnable(GL_DEPTH_TEST);
            glClear(GL_DEPTH_BUFFER_BIT);
        } else {
            if (clearColor.a > 0.5) glClear(GL_COLOR_BUFFER_BIT);
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_CULL_FACE);
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity(); /*
        if(camera.isPerspective) { 
            // Calcular os valores top, bottom, left e right para glFrustum
            float top = (float) (camera.near * Math.tan(Math.toRadians(fov / 2.0)));
            float bottom = -top;
            float right = top * camera.aspect;
            float left = -right;

            // Definir a perspectiva com glFrustum
            glFrustum(left, right, bottom, top, camera.near, camera.far); 
            
        }else {
            GL11.glOrtho(camera.orthoLeft, camera.orthoRight, camera.orthoBottom, camera.orthoTop, camera.near,camera.far); 
        } 
        */ 
        GL11.glLoadMatrix(put_matrix(projection_buffer,camera.getProjectionMatrix()));
        // Definir a matriz de modelagem
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity(); 
        
        GL11.glLoadMatrix(put_matrix(modelview_buffer,camera.getViewMatrix())); 
    }
     
    @Override
    public void destroy() {
     projection_buffer.reset();
     modelview_buffer.reset();
    }

    @Override
    public void draw_object(Mesh obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
