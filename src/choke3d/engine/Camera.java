package choke3d.engine;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import choke3d.math.Color4f;
import choke3d.math.Mat4f;
import choke3d.math.Rect;
import choke3d.math.Transform;
import choke3d.math.Vec2f;
import choke3d.math.Vec4f;

/**
 *
 * @author tocatoca
 */
public class Camera {
    
    public Transform transform=new Transform();  
    public boolean fixAspect, isPerspective;
    public float fov, near, far, aspect,
                 orthoLeft, orthoRight,
                 orthoBottom, orthoTop;
    public CameraMode mode;
    public Color4f clearColor;
    public float fogDensity;
    public Color4f fogColor;
    public Rect viewport;  

    // Enum for camera modes
    public enum CameraMode {
        CAMERA_HUD,
        CAMERA_2D,
        CAMERA_3D
    }

    // Default constructor
    public Camera() {
        mode = CameraMode.CAMERA_2D;
        fixAspect = false;
        isPerspective = false;
        clearColor = Color4f.BLACK();
        fov = 70;
        near = -0.1f;
        far = 600;
        aspect = 1.0f;
        orthoLeft = -1;
        orthoRight = 1;
        orthoBottom = -1;
        orthoTop = 1;
        viewport = new Rect();
        fogDensity = 1;
    }

    // Setup 2D camera
    public void setup2D(float zoom) {
        mode = CameraMode.CAMERA_2D;
        isPerspective = false;
        fixAspect = true;
        near = -0.01f;
        far = 30;
        float halfWidth = zoom * 0.5f;
        orthoTop = halfWidth;
        orthoBottom = -halfWidth;
        orthoLeft = -halfWidth;
        orthoRight = halfWidth;
    }

    // Setup HUD camera
    public void setupHUD(int screenW, int screenH) {
        mode = CameraMode.CAMERA_HUD;
        isPerspective = false;
        fixAspect = false;
        near = -0.01f;
        far = 30;
        orthoTop = 0;
        orthoBottom = screenW;
        orthoLeft = 0;
        orthoRight = screenH;
    }

    // Setup 3D camera
    public void setup3D(float fovy) {
        mode = CameraMode.CAMERA_3D;
        isPerspective = true;
        fixAspect = true;
        near = 0.1f;
        far = 3000;
        fogDensity = 2;
        fov = fovy;
    }

    // Generate projection matrix
    public Mat4f projectionMatrix() {
        if (isPerspective) {
            return Mat4f.perspective(fov, aspect, near, far);
        }
        return fixAspect ?
                Mat4f.ortho(orthoBottom * aspect, orthoTop * aspect, orthoBottom, orthoTop, near, far) :
                Mat4f.ortho(orthoLeft, orthoRight, orthoBottom, orthoTop, near, far);
    }

    // Draw viewport
    public void clear(int winWidth, int winHeight) { 
        Vec2f winSize = new Vec2f(winWidth, winHeight);
        if (fixAspect) aspect = winSize.x / winSize.y;
        Vec2f pos = viewport.position.mul(winSize);
        Vec2f size = viewport.scale.mul(winSize);
        glViewport((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);

        glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);

        //glEnable(GL_ALPHA);
        //glEnable(GL_BLEND);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (mode == CameraMode.CAMERA_3D) {
            if (clearColor.a > 0.5) glClear(GL_COLOR_BUFFER_BIT);
            glEnable(GL_DEPTH_TEST);
            glClear(GL_DEPTH_BUFFER_BIT);
        } else {
            if (clearColor.a > 0.5) glClear(GL_COLOR_BUFFER_BIT);
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_CULL_FACE);
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        if(this.isPerspective) { 
            // Calcular os valores top, bottom, left e right para glFrustum
            float top = (float) (near * Math.tan(Math.toRadians(fov / 2.0)));
            float bottom = -top;
            float right = top * this.aspect;
            float left = -right;

            // Definir a perspectiva com glFrustum
            glFrustum(left, right, bottom, top, near, far); 
            
        }else {
            GL11.glOrtho(orthoLeft, orthoRight, orthoBottom, orthoTop, near,far); 
        } 
        // Definir a matriz de modelagem
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity(); 
        
        Vec4f axisAngle=transform.rotation.to_angles();
        GL11.glRotatef(axisAngle.w, axisAngle.x, axisAngle.y, axisAngle.z);
        GL11.glTranslatef(-transform.position.x, -transform.position.y, -transform.position.z);
    } 
    public void update(float delta) {
        
    }
}