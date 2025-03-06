package choke3d.vika.backend.legacy;

import choke3d.math.Color4f;
import choke3d.math.Mat4f;
import choke3d.math.Vec2f;
import choke3d.math.Vec2i;
import choke3d.math.Vec3f;
import choke3d.math.Vec4f;
import choke3d.vika.frontend.Camera;
import choke3d.vika.frontend.DrawObject;
import choke3d.vika.frontend.Input;
import choke3d.vika.frontend.Platform;
import choke3d.vika.frontend.Scene;
import choke3d.vika.frontend.Texture;
import choke3d.vika.frontend.mesh.Mesh;
import java.nio.FloatBuffer; 
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_ALPHA;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FOG;
import static org.lwjgl.opengl.GL11.GL_FOG_COLOR;
import static org.lwjgl.opengl.GL11.GL_FOG_DENSITY;
import static org.lwjgl.opengl.GL11.GL_FOG_END;
import static org.lwjgl.opengl.GL11.GL_FOG_HINT;
import static org.lwjgl.opengl.GL11.GL_FOG_MODE;
import static org.lwjgl.opengl.GL11.GL_FOG_START;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFog;
import static org.lwjgl.opengl.GL11.glFogf;
import static org.lwjgl.opengl.GL11.glFogi;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glViewport;

/**
 *
 * @author tocatoca
 */
public class LegacyPlatform extends Platform {   
    public final LegacyTiming time=new LegacyTiming();
    public final LegacyInput input=new LegacyInput();
    public boolean running=true;
    public void init(){ 
         try {  
            Display.setVSyncEnabled(true);
            Display.setDisplayMode(new DisplayMode(800,600));
            
            Display.create();
            running=true;
        } catch (LWJGLException e) {
            e.printStackTrace();  
            running=false;
            return;
        }   
        time.init(); 
        input.init();
        Mesh skybox_mesh=new LegacyMesh();
        skybox_mesh.load(Mesh.get_skybox());
        skybox=new DrawObject(skybox_mesh);
        skybox.material.albedo_color.r=0.5f;
        skybox.material.albedo_color.g=0.5f;
        initFog();
        
    } 
     private static void initFog() {
        glEnable(GL_FOG); // Habilita o fog

        // Configura o tipo de fog
        glFogi(GL_FOG_MODE, GL_LINEAR); // Tipos disponíveis: GL_EXP, GL_EXP2, GL_LINEAR

        // Cor da neblina
        //float[] fogColor = {0.45f, 0.38f, 0.62f, 1.0f}; // Cinza claro
         float[] fogColor = {0.5f, 0.5f, 0.8f, 1.0f}; // Cinza claro
        glFog(GL_FOG_COLOR, BufferUtils.createFloatBuffer(4).put(fogColor).flip());

        // Configurações de densidade e distância do fog
        glFogf(GL_FOG_START, 6.0f); // Início da neblina
        glFogf(GL_FOG_END, 400.0f); // Fim da neblina
        glFogf(GL_FOG_DENSITY, 0.1f); // Densidade (para GL_EXP e GL_EXP2)

        glHint(GL_FOG_HINT, GL_NICEST); // Sugestão de qualidade do fog
    }
    public DrawObject skybox;
    FloatBuffer projection_buffer=BufferUtils.createFloatBuffer(16);
    FloatBuffer view_buffer=BufferUtils.createFloatBuffer(16);
    FloatBuffer model_buffer=BufferUtils.createFloatBuffer(16);
    private FloatBuffer put_matrix(FloatBuffer buff,Mat4f mat) {
        buff.clear();
        for(float[] row : mat.m) buff.put(row);
        buff.flip();
        return buff;
    }
    
    @Override
    public void draw_object(DrawObject obj,Camera camera) {
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
        //Mat4f matrix=camera.getViewMatrix().mul(obj.model).transpose();
        //Mat4f matrix=obj.model.mul(camera.getViewMatrix());
        
        Mat4f matrix=camera.getViewMatrix().mul(obj.model);
        GL11.glLoadMatrix(put_matrix(model_buffer,matrix));  
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL_LINE);
        ((LegacyMesh) obj.mesh).draw();
        glPopMatrix();
    }
    
    @Override
    public void set_skybox_texture(Texture sky) {
        skybox.material.albedo=sky;
    }
    @Override
    public void clear_camera(Camera camera) {
        Vec2f winSize = new Vec2f(Display.getWidth(), Display.getHeight());
        if (camera.fixAspect) camera.aspect = winSize.x / winSize.y;
        Vec2f pos = camera.viewport.position.mul(winSize);
        Vec2f size = camera.viewport.scale.mul(winSize);
        glViewport((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);
        Color4f clearColor=camera.clearColor; 
        glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);

        glEnable(GL11.GL_TEXTURE);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_ALPHA);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
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
        Color4f color=skybox.material.albedo_color;
        glColor4f(color.r,color.g,color.b,color.a);
        if(skybox.material.albedo!=null && skybox.material.albedo instanceof LegacyTexture) {
            LegacyTexture albedo=(LegacyTexture) skybox.material.albedo;
            albedo.bind();
        } else {
            GL11.glBindTexture(GL_TEXTURE_2D, 0);
        }
        ((LegacyMesh) skybox.mesh).draw();
        GL11.glEnable(GL_DEPTH_TEST);
        
        glLoadIdentity();
        
        GL11.glLoadMatrix(put_matrix(view_buffer,camera.getViewMatrix())); 
    }
    public void update() {  
        if (!running) return;  
        if(Display.isCloseRequested()) {
            System.out.print("FECHA LOGOO!!");
            running=false;
            return;
        }
        time.update();
        input.update();
        Display.setTitle("FPS: "+ Math.round(1.0f/time.delta)); 
        Display.update();
    }
      
    public void destroy() {
        //projection_buffer.reset();
        //view_buffer.reset();
        running=false; 
        Display.destroy();  
    } 

    @Override
    public Scene create_scene() {
       return new Scene();
    }
     
    @Override
    public double get_delta() {
       return time.getDelta();
    }

    @Override
    public boolean is_running() {
        return running;
    }

    @Override
    public Input get_input() {
       return input;
    }

    @Override
    public Mesh create_mesh() {
      return new LegacyMesh();
    }

    @Override
    public Vec2i get_window_size() {
        return new Vec2i(Display.getWidth(),Display.getHeight());
    }

    @Override
    public Texture create_texture() {
       return new LegacyTexture();
    }
    
}