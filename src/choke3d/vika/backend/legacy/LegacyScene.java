package choke3d.vika.backend.legacy;

import choke3d.math.Color4f;
import choke3d.math.Mat4f;
import choke3d.math.Vec2f;
import choke3d.math.Vec4f;
import choke3d.vika.frontend.Camera;
import choke3d.vika.frontend.DrawObject;
import choke3d.vika.frontend.mesh.Mesh;
import choke3d.vika.frontend.Scene;
import choke3d.vika.frontend.Texture;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author tocatoca
 */
public class LegacyScene extends Scene {
    public DrawObject skybox;
    @Override
    public void init() { 
        glEnable(GL11.GL_TEXTURE);
        glEnable(GL_TEXTURE_2D);
        Mesh skybox_mesh=new LegacyMesh();
        skybox_mesh.load(Mesh.get_skybox());
        skybox=new DrawObject(skybox_mesh);
        skybox.material.albedo_color.r=0.5f;
        skybox.material.albedo_color.g=0.5f;
    }

    FloatBuffer projection_buffer=BufferUtils.createFloatBuffer(16);
    FloatBuffer modelview_buffer=BufferUtils.createFloatBuffer(16);
    FloatBuffer model_buffer=BufferUtils.createFloatBuffer(16);
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
        Vec4f angles=camera.getViewMatrix().to_quaternion().conjugate().to_angles();
        GL11.glRotatef(angles.w, angles.x, angles.y, angles.z);
        //glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);
        GL11.glDisable(GL_DEPTH_TEST);
        draw_object(skybox);
        GL11.glEnable(GL_DEPTH_TEST);
        
        glLoadIdentity();
        
        GL11.glLoadMatrix(put_matrix(modelview_buffer,camera.getViewMatrix())); 
    }
     
    @Override
    public void destroy() {
     projection_buffer.reset();
     modelview_buffer.reset();
    }

    @Override
    public void draw_object(DrawObject obj) {
        if(obj.mesh==null || obj.material==null) {
            System.out.println("SEM MESH OU SEM MATERIAL");
            return;
        }
        Color4f color=obj.material.albedo_color;
        glColor4f(color.r,color.g,color.b,color.a);
        if(obj.material.albedo!=null && obj.material.albedo instanceof LegacyTexture) {
            LegacyTexture albedo=(LegacyTexture) obj.material.albedo;
            albedo.bind();
        } else {
            GL11.glBindTexture(GL_TEXTURE_2D, 0);
        }
        glPushMatrix();
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL_LINE);
        ((LegacyMesh) obj.mesh).draw();
        GL11.glLoadMatrix(put_matrix(model_buffer,obj.model));  
        glPopMatrix();
    }

    @Override
    public void set_skybox_texture(Texture sky) {
        skybox.material.albedo=sky;
    }
    
    
}
