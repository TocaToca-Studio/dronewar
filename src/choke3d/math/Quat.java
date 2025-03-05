package choke3d.math;

/**
 *
 * @author tocatoca
 */
public class Quat {
    public float w, x, y, z;

    // Constructors
    public Quat() {
        this.w = 1;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Quat(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Quat IDENTITY() {
        return new Quat(1, 0, 0, 0);
    }

    // Set quaternion from Euler angles (in radians)
    public void setEuler(Vec3f euler) {
        float cy = (float) Math.cos(euler.z * 0.5);
        float sy = (float) Math.sin(euler.z * 0.5);
        float cp = (float) Math.cos(euler.y * 0.5);
        float sp = (float) Math.sin(euler.y * 0.5);
        float cr = (float) Math.cos(euler.x * 0.5);
        float sr = (float) Math.sin(euler.x * 0.5);

        w = cr * cp * cy + sr * sp * sy;
        x = sr * cp * cy - cr * sp * sy;
        y = cr * sp * cy + sr * cp * sy;
        z = cr * cp * sy - sr * sp * cy;
    }

    // Get Euler angles from quaternion (in radians)
    public Vec3f getEuler() {
        Vec3f euler = new Vec3f();

        // Roll (x-axis rotation)
        float sinr_cosp = 2 * (w * x + y * z);
        float cosr_cosp = 1 - 2 * (x * x + y * y);
        euler.x = (float) Math.atan2(sinr_cosp, cosr_cosp);

        // Pitch (y-axis rotation)
        float sinp = 2 * (w * y - z * x);
        if (Math.abs(sinp) >= 1)
            euler.y = (float) Math.copySign(Math.PI / 2, sinp); // use 90 degrees if out of range
        else
            euler.y = (float) Math.asin(sinp);

        // Yaw (z-axis rotation)
        float siny_cosp = 2 * (w * z + x * y);
        float cosy_cosp = 1 - 2 * (y * y + z * z);
        euler.z = (float) Math.atan2(siny_cosp, cosy_cosp);

        return euler;
    }

    // Apply a rotation around an arbitrary axis (angle in radians)
    public void rotate(float angle, Vec3f axis) {
        float half_angle = angle * 0.5f;
        float s = (float) Math.sin(half_angle);
        Quat rotation = new Quat((float) Math.cos(half_angle), axis.x * s, axis.y * s, axis.z * s);
        Quat result = this.multiply(rotation).normalize();
        this.w = result.w;
        this.x = result.x;
        this.y = result.y;
        this.z = result.z;
    }

    // Quaternion multiplication
    public Quat multiply(Quat q) {
        return new Quat(
            w * q.w - x * q.x - y * q.y - z * q.z,
            w * q.x + x * q.w + y * q.z - z * q.y,
            w * q.y - x * q.z + y * q.w + z * q.x,
            w * q.z + x * q.y - y * q.x + z * q.w
        );
    }

    // Scalar multiplication
    public Quat multiply(float s) {
        return new Quat(w * s, x * s, y * s, z * s);
    }

    // Quaternion addition
    public Quat add(Quat q) {
        return new Quat(w + q.w, x + q.x, y + q.y, z + q.z);
    }

    // Quaternion conjugate
    public Quat conjugate() {
        return new Quat(w, -x, -y, -z);
    }

    // Quaternion magnitude
    public float magnitude() {
        return (float) Math.sqrt(w * w + x * x + y * y + z * z);
    }

    // Normalize quaternion
    public Quat normalize() {
        float mag = magnitude();
        return new Quat(w / mag, x / mag, y / mag, z / mag);
    }
    public Vec4f to_angles() {
        // Dados: quaternion (x, y, z, w)
        float angle = (float) Math.toDegrees(2 * Math.acos(w)); // Converte para graus
        float scale = (float) Math.sqrt(1 - w * w);
        
        float axisX = x / scale;
        float axisY = y / scale;
        float axisZ = z / scale;
        return new Vec4f(axisX,axisY,axisZ,angle);
    }
    public static Quat lookAt(Vec3f dir) {
        // Normaliza o vetor direção
        dir=dir.normalized();  // Garantir que o vetor direção tenha magnitude 1

        // Calcula o vetor cruzado entre o vetor FORWARD e o vetor direção
        Vec3f cross = Vec3f.FORWARD().cross(dir);

        // Se o vetor cruzado for zero, significa que os vetores são colineares
        if (cross.magnitude() == 0) {
            // Se os vetores são colineares (caso especial), o quaternion seria identidade ou rotacionado de 180°
            // Dependendo da situação, você pode escolher rotacionar 180° ou devolver o quaternion identidade.
            return new Quat(1f, 0f, 0f, 0f);  // Quaternion identidade
        }

        // Calcula o produto escalar entre os vetores
        float dot = Vec3f.dot(Vec3f.FORWARD(), dir);

        // Clampe o valor do produto escalar para evitar erro no acos
        dot = Math.max(-1.0f, Math.min(1.0f, dot));

        // Calcula o ângulo entre os vetores
        float angle = (float) Math.acos(dot);  // ângulo entre os vetores

        // Retorna o quaternion que representa a rotação
        return new Quat(angle, cross.x, cross.y, cross.z);
    }
    public static Quat lookAtWithYLock(Vec3f dir) {
        // Normaliza o vetor direção
        dir=dir.normalized();

        // Calcula a direção no plano XY (trava a rotação ao longo do eixo Y)
        Vec3f dirXY = new Vec3f(dir.x, 0, dir.z);
        dirXY=dirXY.normalized();

        // Calcula o vetor cruzado entre o vetor FORWARD e o vetor direção no plano XY
        Vec3f cross = Vec3f.FORWARD().cross(dirXY);

        // Se o vetor cruzado for zero, significa que os vetores são colineares
        if (cross.magnitude() == 0) {
            // Caso especial quando os vetores são colineares, o quaternion seria identidade
            return new Quat(1f, 0f, 0f, 0f);  // Quaternion identidade
        }

        // Calcula o produto escalar entre os vetores
        float dot = Vec3f.dot(Vec3f.FORWARD(), dirXY);

        // Clampe o valor do produto escalar para evitar erro no acos
        dot = Math.max(-1.0f, Math.min(1.0f, dot));

        // Calcula o ângulo entre os vetores
        float angle = (float) Math.acos(dot);

        // Retorna o quaternion que representa a rotação
        return new Quat(angle, cross.x, cross.y, cross.z);
    }

    Quat copy() {
        return new Quat(w,x,y,z);
    }
}
