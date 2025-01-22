package choke3d.vika.frontend;

import choke3d.math.Color4f;
import choke3d.math.Mat4f;
import choke3d.math.Rect;

/**
 *
 * @author tocatoca
 */
public class Camera {
    public boolean fixAspect, isPerspective;
    public float fov, near, far, aspect,
                 orthoLeft, orthoRight,
                 orthoBottom, orthoTop;
    public CameraMode mode=CameraMode.CAMERA_3D;
    public Color4f clearColor=Color4f.BLACK();
    public float fogDensity=1;
    public Color4f fogColor= Color4f.GRAY();
    public Rect viewport = new Rect();  
    protected Mat4f view_matrix=Mat4f.IDENTITY();
    
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
 

    public Rect getViewport() { return viewport; }
    public Mat4f getProjectionMatrix() {
           if (isPerspective) {
            return Mat4f.perspective(fov, aspect, near, far);
        }
        return fixAspect ?
                Mat4f.ortho(orthoBottom * aspect, orthoTop * aspect, orthoBottom, orthoTop, near, far) :
                Mat4f.ortho(orthoLeft, orthoRight, orthoBottom, orthoTop, near, far);
    }
    public void setViewMatrix(Mat4f view_matrix) {
        if(view_matrix==null) view_matrix=Mat4f.IDENTITY();
        this.view_matrix=view_matrix;
    }
    public Mat4f getViewMatrix() {
        return view_matrix;
    }
    public void setAspectRation(float aspect_ratio) {
        this.aspect=aspect_ratio;
    }
    public float getAspectRatio() {
        return aspect;
    }
}
