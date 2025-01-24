package choke3d.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;
import java.nio.FloatBuffer;
import static choke3d.math.MathUtils.COS_FROM_SIN;

/**
 *
 * @author tocatoca
 */
public class Mat4f {
    public float[][] m;

    // Constructors
    public Mat4f() {
        m = new float[4][4];
        m[0][0] = m[1][1] = m[2][2] = m[3][3] = 1;
        m[0][1] = m[0][2] = m[0][3] = 0;
        m[1][0] = m[1][2] = m[1][3] = 0;
        m[2][0] = m[2][1] = m[2][3] = 0;
        m[3][0] = m[3][1] = m[3][2] = 0;
    }

    public Mat4f(
        float m00, float m01, float m02, float m03,
        float m10, float m11, float m12, float m13,
        float m20, float m21, float m22, float m23,
        float m30, float m31, float m32, float m33) {
        m = new float[4][4];
        m[0][0] = m00; m[0][1] = m01; m[0][2] = m02; m[0][3] = m03;
        m[1][0] = m10; m[1][1] = m11; m[1][2] = m12; m[1][3] = m13;
        m[2][0] = m20; m[2][1] = m21; m[2][2] = m22; m[2][3] = m23;
        m[3][0] = m30; m[3][1] = m31; m[3][2] = m32; m[3][3] = m33;
    }

    public static Mat4f IDENTITY() {
        return new Mat4f(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        );
    }

    public static Mat4f ZERO() {
        return new Mat4f(
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
        );
    }

    public Mat4f inverse() {
        Mat4f inv = new Mat4f();
        float det;
        int i;

        inv.m[0][0] = m[1][1] * m[2][2] * m[3][3] - 
                      m[1][1] * m[2][3] * m[3][2] - 
                      m[2][1] * m[1][2] * m[3][3] + 
                      m[2][1] * m[1][3] * m[3][2] + 
                      m[3][1] * m[1][2] * m[2][3] - 
                      m[3][1] * m[1][3] * m[2][2];

        inv.m[0][1] = -m[0][1] * m[2][2] * m[3][3] + 
                      m[0][1] * m[2][3] * m[3][2] + 
                      m[2][1] * m[0][2] * m[3][3] - 
                      m[2][1] * m[0][3] * m[3][2] - 
                      m[3][1] * m[0][2] * m[2][3] + 
                      m[3][1] * m[0][3] * m[2][2];

        inv.m[0][2] = m[0][1] * m[1][2] * m[3][3] - 
                      m[0][1] * m[1][3] * m[3][2] - 
                      m[1][1] * m[0][2] * m[3][3] + 
                      m[1][1] * m[0][3] * m[3][2] + 
                      m[3][1] * m[0][2] * m[1][3] - 
                      m[3][1] * m[0][3] * m[1][2];

        inv.m[0][3] = -m[0][1] * m[1][2] * m[2][3] + 
                      m[0][1] * m[1][3] * m[2][2] + 
                      m[1][1] * m[0][2] * m[2][3] - 
                      m[1][1] * m[0][3] * m[2][2] - 
                      m[2][1] * m[0][2] * m[1][3] + 
                      m[2][1] * m[0][3] * m[1][2];

        inv.m[1][0] = -m[1][0] * m[2][2] * m[3][3] + 
                      m[1][0] * m[2][3] * m[3][2] + 
                      m[2][0] * m[1][2] * m[3][3] - 
                      m[2][0] * m[1][3] * m[3][2] - 
                      m[3][0] * m[1][2] * m[2][3] + 
                      m[3][0] * m[1][3] * m[2][2];

        inv.m[1][1] = m[0][0] * m[2][2] * m[3][3] - 
                      m[0][0] * m[2][3] * m[3][2] - 
                      m[2][0] * m[0][2] * m[3][3] + 
                      m[2][0] * m[0][3] * m[3][2] + 
                      m[3][0] * m[0][2] * m[2][3] - 
                      m[3][0] * m[0][3] * m[2][2];

        inv.m[1][2] = -m[0][0] * m[1][2] * m[3][3] + 
                      m[0][0] * m[1][3] * m[3][2] + 
                      m[1][0] * m[0][2] * m[3][3] - 
                      m[1][0] * m[0][3] * m[3][2] - 
                      m[3][0] * m[0][2] * m[1][3] + 
                      m[3][0] * m[0][3] * m[1][2];

        inv.m[1][3] = m[0][0] * m[1][2] * m[2][3] - 
                     m[0][0] * m[1][3] * m[2][2] - 
                     m[1][0] * m[0][2] * m[2][3] + 
                     m[1][0] * m[0][3] * m[2][2] + 
                     m[2][0] * m[0][2] * m[1][3] - 
                     m[2][0] * m[0][3] * m[1][2];

        inv.m[2][0] = m[1][0] * m[2][1] * m[3][3] - 
                      m[1][0] * m[2][3] * m[3][1] - 
                      m[2][0] * m[1][1] * m[3][3] + 
                      m[2][0] * m[1][3] * m[3][1] + 
                      m[3][0] * m[1][1] * m[2][3] - 
                      m[3][0] * m[1][3] * m[2][1];

        inv.m[2][1] = -m[0][0] * m[2][1] * m[3][3] + 
                      m[0][0] * m[2][3] * m[3][1] + 
                      m[2][0] * m[0][1] * m[3][3] - 
                      m[2][0] * m[0][3] * m[3][1] - 
                      m[3][0] * m[0][1] * m[2][3] + 
                      m[3][0] * m[0][3] * m[2][1];

        inv.m[2][2] = m[0][0] * m[1][1] * m[3][3] - 
                      m[0][0] * m[1][3] * m[3][1] - 
                      m[1][0] * m[0][1] * m[3][3] + 
                      m[1][0] * m[0][3] * m[3][1] + 
                      m[3][0] * m[0][1] * m[1][3] - 
                      m[3][0] * m[0][3] * m[1][1];

        inv.m[2][3] = -m[0][0] * m[1][1] * m[2][3] + 
                      m[0][0] * m[1][3] * m[2][1] + 
                      m[1][0] * m[0][1] * m[2][3] - 
                      m[1][0] * m[0][3] * m[2][1] - 
                      m[2][0] * m[0][1] * m[1][3] + 
                      m[2][0] * m[0][3] * m[1][2];

        inv.m[3][0] = -m[1][0] * m[2][1] * m[3][2] + 
                      m[1][0] * m[2][2] * m[3][1] + 
                      m[2][0] * m[1][1] * m[3][2] - 
                      m[2][0] * m[1][2] * m[3][1] - 
                      m[3][0] * m[1][1] * m[2][2] + 
                      m[3][0] * m[1][2] * m[2][1];

        inv.m[3][1] = m[0][0] * m[2][1] * m[3][2] - 
                      m[0][0] * m[2][2] * m[3][1] - 
                      m[2][0] * m[0][1] * m[3][2] + 
                      m[2][0] * m[0][2] * m[3][1] + 
                      m[3][0] * m[0][1] * m[2][2] - 
                      m[3][0] * m[0][2] * m[2][1];

        inv.m[3][2] = -m[0][0] * m[1][1] * m[3][2] + 
                      m[0][0] * m[1][2] * m[3][1] + 
                      m[1][0] * m[0][1] * m[3][2] - 
                      m[1][0] * m[0][2] * m[3][1] - 
                      m[3][0] * m[0][1] * m[1][2] + 
                      m[3][0] * m[0][2] * m[1][1];

        inv.m[3][3] = m[0][0] * m[1][1] * m[2][2] - 
                      m[0][0] * m[1][2] * m[2][1] - 
                      m[1][0] * m[0][1] * m[2][2] + 
                      m[1][0] * m[0][2] * m[2][1] + 
                      m[2][0] * m[0][1] * m[1][2] - 
                      m[2][0] * m[0][2] * m[1][1];

        det = m[0][0] * inv.m[0][0] + m[0][1] * inv.m[1][0] + m[0][2] * inv.m[2][0] + m[0][3] * inv.m[3][0];

        if (det == 0)
            return Mat4f.ZERO();

        det = 1.0f / det;

        for (i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                inv.m[i][j] *= det;

        return inv;
    }
    

    // Matrix multiplication
    public static Mat4f mul(Mat4f m1, Mat4f m2) {
        return new Mat4f(
            m2.m[0][0] * m1.m[0][0] + m2.m[0][1] * m1.m[1][0] + m2.m[0][2] * m1.m[2][0] + m2.m[0][3] * m1.m[3][0],
            m2.m[0][0] * m1.m[0][1] + m2.m[0][1] * m1.m[1][1] + m2.m[0][2] * m1.m[2][1] + m2.m[0][3] * m1.m[3][1],
            m2.m[0][0] * m1.m[0][2] + m2.m[0][1] * m1.m[1][2] + m2.m[0][2] * m1.m[2][2] + m2.m[0][3] * m1.m[3][2],
            m2.m[0][0] * m1.m[0][3] + m2.m[0][1] * m1.m[1][3] + m2.m[0][2] * m1.m[2][3] + m2.m[0][3] * m1.m[3][3],

            m2.m[1][0] * m1.m[0][0] + m2.m[1][1] * m1.m[1][0] + m2.m[1][2] * m1.m[2][0] + m2.m[1][3] * m1.m[3][0],
            m2.m[1][0] * m1.m[0][1] + m2.m[1][1] * m1.m[1][1] + m2.m[1][2] * m1.m[2][1] + m2.m[1][3] * m1.m[3][1],
            m2.m[1][0] * m1.m[0][2] + m2.m[1][1] * m1.m[1][2] + m2.m[1][2] * m1.m[2][2] + m2.m[1][3] * m1.m[3][2],
            m2.m[1][0] * m1.m[0][3] + m2.m[1][1] * m1.m[1][3] + m2.m[1][2] * m1.m[2][3] + m2.m[1][3] * m1.m[3][3],

            m2.m[2][0] * m1.m[0][0] + m2.m[2][1] * m1.m[1][0] + m2.m[2][2] * m1.m[2][0] + m2.m[2][3] * m1.m[3][0],
            m2.m[2][0] * m1.m[0][1] + m2.m[2][1] * m1.m[1][1] + m2.m[2][2] * m1.m[2][1] + m2.m[2][3] * m1.m[3][1],
            m2.m[2][0] * m1.m[0][2] + m2.m[2][1] * m1.m[1][2] + m2.m[2][2] * m1.m[2][2] + m2.m[2][3] * m1.m[3][2],
            m2.m[2][0] * m1.m[0][3] + m2.m[2][1] * m1.m[1][3] + m2.m[2][2] * m1.m[2][3] + m2.m[2][3] * m1.m[3][3],

            m2.m[3][0] * m1.m[0][0] + m2.m[3][1] * m1.m[1][0] + m2.m[3][2] * m1.m[2][0] + m2.m[3][3] * m1.m[3][0],
            m2.m[3][0] * m1.m[0][1] + m2.m[3][1] * m1.m[1][1] + m2.m[3][2] * m1.m[2][1] + m2.m[3][3] * m1.m[3][1],
            m2.m[3][0] * m1.m[0][2] + m2.m[3][1] * m1.m[1][2] + m2.m[3][2] * m1.m[2][2] + m2.m[3][3] * m1.m[3][2],
            m2.m[3][0] * m1.m[0][3] + m2.m[3][1] * m1.m[1][3] + m2.m[3][2] * m1.m[2][3] + m2.m[3][3] * m1.m[3][3]
        );
    }
    public static Mat4f from_dir(Vec3f v)  {
        /* Find cos theta and sin theta */
        float c1 = (float) Math.sqrt(v.x * v.x + v.y * v.y);
        float s1 = v.z;
        /* Find cosθ and sinθ; if gimbal lock, choose (1,0) arbitrarily */
        float c2 = c1!=0f ? v.x / c1 : 1.0f;
        float s2 = c1!=0f ? v.y / c1 : 0.0f;

        return new Mat4f(v.x, -s2, -s1*c2, 0,
                    v.y,  c2, -s1*s2, 0,
                    v.z,   0,     c1, 0,
                    0 ,    0,  0    , 1).transpose();
    }
    static public Mat4f from_angles(Vec3f angle) {
        Mat4f mat=Mat4f.IDENTITY();
        float sinX = (float) sin(angle.x);
        float cosX = COS_FROM_SIN(sinX,angle.x);
        float sinY =  (float) sin(angle.y);
        float cosY =  COS_FROM_SIN(sinY,angle.y);
        float sinZ =  (float) sin(angle.z);
        float cosZ =  COS_FROM_SIN(sinZ,angle.z);
        float m_sinX = -sinX;
        float m_sinY = -sinY;
        float m_sinZ = -sinZ;

        // rotateX
        float nm11 = cosX;
        float nm12 = sinX;
        float nm21 = m_sinX;
        float nm22 = cosX;
        // rotateY
        float nm00 = cosY;
        float nm01 = nm21 * m_sinY;
        float nm02 = nm22 * m_sinY;
        mat.m[0][2] = sinY;
        mat.m[1][2] = (nm21 * cosY);
        mat.m[2][2] = (nm22 * cosY);
        // rotateZ
        mat.m[0][0] = (nm00 * cosZ);
        mat.m[1][0] = (nm01 * cosZ + nm11 * sinZ);
        mat.m[2][0] = (nm02 * cosZ + nm12 * sinZ);
        mat.m[0][1] = (nm00 * m_sinZ);
        mat.m[1][1] = (nm01 * m_sinZ + nm11 * cosZ);
        mat.m[2][1] = (nm02 * m_sinZ + nm12 * cosZ);
        return mat;
    }
    static public Mat4f from_quaternion(Quat q) { 
        Mat4f mat=new Mat4f();
        float xx = q.x * q.x;
        float xy = q.x * q.y;
        float xz = q.x * q.z;
        float xw = q.x * q.w;

        float yy = q.y * q.y;
        float yz = q.y * q.z;
        float yw = q.y * q.w;

        float zz = q.z * q.z;
        float zw = q.z * q.w;

        mat.m[0][0] = 1 - 2 * (yy + zz);
        mat.m[0][1] = 2 * (xy - zw);
        mat.m[0][2] = 2 * (xz + yw);
        mat.m[0][3] = 0;

        mat.m[1][0] = 2 * (xy + zw);
        mat.m[1][1] = 1 - 2 * (xx + zz);
        mat.m[1][2] = 2 * (yz - xw);
        mat.m[1][3] = 0;

        mat.m[2][0] = 2 * (xz - yw);
        mat.m[2][1] = 2 * (yz + xw);
        mat.m[2][2] = 1 - 2 * (xx + yy);
        mat.m[2][3] = 0;

        mat.m[3][0] = 0;
        mat.m[3][1] = 0;
        mat.m[3][2] = 0;
        mat.m[3][3] = 1;

        return mat; 
    }
    static public Mat4f rotation_old(Vec3f angle) {
        Vec3f s=new Vec3f((float)sin(angle.x), (float)sin(angle.y), (float)sin(angle.z)); // sin(angle)
        Vec3f c=new Vec3f((float)cos(angle.x), (float)cos(angle.y), (float)cos(angle.z)); // cos(angle)

        Mat4f n =new Mat4f(
            c.y,-s.x*-s.y, c.x*-s.y, 0,
            1,c.x,s.x,0,
            0,-s.x,c.x,0,
            0,0,0,1
        );
        return new Mat4f(
            n.m[0][0] * c.z, n.m[0][0] * -s.z, s.y, 0,
            n.m[0][1] * c.z + n.m[1][1] * s.z, (n.m[0][1] * -s.z + n.m[1][1] * c.z),(n.m[2][1] * c.y),0,
            n.m[0][2] * c.z + n.m[1][2] * s.z, (n.m[0][2] * -s.z + n.m[1][2] * c.z),(n.m[2][2] * c.y),0,
            0, 0, 0, 1
        );
    }
    /** EXPERIMENTAL */
    static public Mat4f __rotation(Vec3f angle) {
        Vec3f s=new Vec3f((float)sin(angle.x), (float)sin(angle.y), (float)sin(angle.z)); // sin(angle)
        Vec3f c=new Vec3f((float)cos(angle.x), (float)cos(angle.y), (float)cos(angle.z)); // cos(angle)

        return new Mat4f(
            c.x * c.y, c.x * s.y * s.z - s.x * c.z, c.x * s.y * c.z + s.x * s.z, 0,
            s.x * c.y, s.x * s.y * s.z + c.x * c.z, s.x * s.y * c.z - c.x * s.z, 0,
            -s.y, c.y * s.z, c.y * c.z, 0,
            0, 0, 0, 1
        );
    }
    static public Mat4f perspective(float fovy, float aspect, float zNear, float zFar) {
        fovy=(float) Math.toRadians(fovy);
        float tanHalfFovy = (float) tan(fovy / (float) 2);

        Mat4f result = Mat4f.ZERO();
        result.m[0][0] =  1.0f / (aspect * tanHalfFovy);
        result.m[1][1] =  1.0f / (tanHalfFovy);
        result.m[2][2] = -(zFar + zNear) / (zFar - zNear);
        result.m[2][3] = -(float) 1;
        result.m[3][2] = -((float) 2 * zFar * zNear) / (zFar - zNear);
        return result;
    } 
    static public Mat4f perspective_old(float fovy, float aspect, float zNear, float zFar) {
        float f = (float) (1.0 / tan(fovy / 2.0));
        float range = zNear - zFar;
        return new Mat4f(
            f / aspect, 0, 0, 0,
            0, f, 0, 0,
            0, 0, (zNear + zFar) / range, 2 * zFar * zNear / range,
            0, 0, -1, 0
        );
    }
    static public Mat4f perspective_new(float fovy, float aspect, float zNear, float zFar) {
       float top = (float) (zNear * Math.tan(Math.toRadians(fovy / 2.0)));
        float bottom = -top;
        float right = top * aspect;
        float left = -right;

        // Definir a perspectiva com glFrustum
        return frustrum(left, right, bottom, top, zNear, zFar); 
    }
    static public Mat4f frustrum(float left, float right,
               float bottom, float top,
               float nearval, float farval) { 
        
        float x, y, a, b, c, d;
         x = (2.0F * nearval) / (right - left);
        y = (2.0F * nearval) / (top - bottom);
        a = (right + left) / (right - left);
        b = (top + bottom) / (top - bottom);
        c = -(farval + nearval) / (farval - nearval);
        d = -(2.0F * farval * nearval) / (farval - nearval);

        return new Mat4f(
          x,    0.0f,  a,    0.0f,
          0.0f, y,    b,     0.0f,
          0.0f,  0.0f,  c,    d,
         0.0f,  0.0f, -1.0f, 0.0f
        ).transpose();
    }
    static public Mat4f ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        Mat4f result = Mat4f.IDENTITY();
        result.m[0][0] = (float) 2 / (right - left);
        result.m[1][1] = (float) 2 / (top - bottom);
        result.m[2][2] = -(float) 2 / (zFar - zNear);
        result.m[3][0] = -(right + left) / (right - left);
        result.m[3][1] = -(top + bottom) / (top - bottom);
        result.m[3][2] = -(zFar + zNear) / (zFar - zNear);

        return result;
    }
    static public Mat4f ortho_old(float left, float right, float bottom, float top, float zNear, float zFar) {
        float width = right - left;
        float height = top - bottom;
        float depth = zFar - zNear;
        return new Mat4f(
            2 / width, 0, 0, -(right + left) / width,
            0, 2 / height, 0, -(top + bottom) / height,
            0, 0, -2 / depth, -(zFar + zNear) / depth,
            0, 0, 0, 1
        );
    }
    static public Mat4f lookAt(Vec3f eye, Vec3f center) {
        Vec3f up=new Vec3f(0,1,0);
        Vec3f f = (center.subtract(eye)).normalized();
        Vec3f s = Vec3f.cross(f,up).normalized();
        Vec3f u = Vec3f.cross(s,f);
        return new Mat4f(
            s.x, u.x, -f.x, 0,
            s.y, u.y, -f.y, 0,
            s.z, u.z, -f.z, 0,
            -s.dot(eye), -u.dot(eye), f.dot(eye), 1
        );
    }
    /** not tested  */
    public Mat4f transpose() {
        return new Mat4f(
            m[0][0], m[1][0], m[2][0], m[3][0],
            m[0][1], m[1][1], m[2][1], m[3][1],
            m[0][2], m[1][2], m[2][2], m[3][2],
            m[0][3], m[1][3], m[2][3], m[3][3]
        );
    }

    public Mat4f translated(float x,float y, float z) {
        return new Mat4f(
            m[0][0], m[0][1], m[0][2], m[0][3],
            m[1][0], m[1][1], m[1][2], m[1][3],
            m[2][0], m[2][1], m[2][2], m[2][3],
            m[3][0] + x, m[3][1] + y, m[3][2] + z, m[3][3]
        );
    }
    public Mat4f with_translation(Vec4f v) {
        return new Mat4f(
            m[0][0], m[0][1], m[0][2], m[0][3],
            m[1][0], m[1][1], m[1][2], m[1][3],
            m[2][0], m[2][1], m[2][2], m[2][3],
            v.x, v.y, v.z, v.w
        );
    }
    public Mat4f translated(Vec3f v) {return translated(v.x, v.y, v.z);}
    public Mat4f scaled(float x, float y, float z) {
        return new Mat4f(
            m[0][0] * x, m[0][1] , m[0][2] , m[0][3],
            m[1][0] , m[1][1] * y, m[1][2] , m[1][3],
            m[2][0] , m[2][1] , m[2][2] * z, m[2][3],
            m[3][0], m[3][1], m[3][2], m[3][3]
        );
    }
    public Mat4f scaled(Vec3f v) {return scaled(v.x, v.y, v.z);}
    public Mat4f scaled(float s) {return scaled(s, s, s);}
    public Mat4f rotated(Vec3f v) {return mul(from_angles(v),this);}  
    public Mat4f rotated(Quat q) {return mul(from_quaternion(q),this);} 
    public Mat4f rotated(float x,float y,float z) {return rotated(new Vec3f(x,y,z));}
    public Mat4f rotated(float s) {return rotated(0,0,s);} 
    public Vec3f translation() {return new Vec3f(m[3][0], m[3][1], m[3][2]);}
    public Vec4f translation4f() {return new Vec4f(m[3][0], m[3][1], m[3][2], m[3][3]);}
    public Vec3f normalized_translation() {return translation4f().normalize();}
    public Vec2f translation2D() {return new Vec2f(m[3][0], m[3][1]);}
    public Vec3f axis() {return new Vec3f(m[0][0], m[1][0], m[2][0]);}
    public Vec3f scale() {return new Vec3f(m[0][0], m[1][1], m[2][2]);}
    /** NOT TESTED*/
    public float det() {
        return m[0][0] * (m[1][1] * m[2][2] - m[2][1] * m[1][2]) -
                m[0][1] * (m[1][0] * m[2][2] - m[2][0] * m[1][2]) +
                m[0][2] * (m[1][0] * m[2][1] - m[2][0] * m[1][1]);
    }
    public Mat4f mul(Mat4f m) {
        return Mat4f.mul(this,m);
    }

    public Vec3f mul(Vec3f v) {
        return Mat4f.mul(this,Mat4f.IDENTITY().translated(v)).translation();
    }
    
    public Vec4f mul(Vec4f v) {
        return Mat4f.mul(this,Mat4f.IDENTITY().with_translation(v)).translation4f();
    }
      // Extract quaternion from a 4x4 matrix
    public Quat to_quaternion() {
        Quat q=new Quat();

        float trace = m[0][0] + m[1][1] + m[2][2];
        if (trace > 0) {
            float s = (float) (0.5f / sqrt(trace + 1.0f));
            q.w = 0.25f / s;
            q.x = (m[2][1] - m[1][2]) * s;
            q.y = (m[0][2] - m[2][0]) * s;
            q.z = (m[1][0] - m[0][1]) * s;
        } else {
            if (m[0][0] > m[1][1] && m[0][0] > m[2][2]) {
                float s = (float) (2.0f * sqrt(1.0f + m[0][0] - m[1][1] - m[2][2]));
                q.w = (m[2][1] - m[1][2]) / s;
                q.x = 0.25f * s;
                q.y = (m[0][1] + m[1][0]) / s;
                q.z = (m[0][2] + m[2][0]) / s;
            } else if (m[1][1] > m[2][2]) {
                float s = (float) (2.0f * sqrt(1.0f + m[1][1] - m[0][0] - m[2][2]));
                q.w = (m[0][2] - m[2][0]) / s;
                q.x = (m[0][1] + m[1][0]) / s;
                q.y = 0.25f * s;
                q.z = (m[1][2] + m[2][1]) / s;
            } else {
                float s = (float) (2.0f * sqrt(1.0f + m[2][2] - m[0][0] - m[1][1]));
                q.w = (m[1][0] - m[0][1]) / s;
                q.x = (m[0][2] + m[2][0]) / s;
                q.y = (m[1][2] + m[2][1]) / s;
                q.z = 0.25f * s;
            }
        }

        return q;
    } 
    
}  