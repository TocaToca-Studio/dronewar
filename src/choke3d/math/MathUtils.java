package choke3d.math;

public class MathUtils {
    public static final float TH_PI = (float)3.14159265358979323846;
    public static final float TH_HALF_PI = (float)1.57079632679489661923;
    public static final float TH_TWO_PI = (float)6.28318530717958647692;
    public static final float TH_DEG_TO_RAD = (float)0.017453292519943295769236907684886;
    public static final float TH_RAD_TO_DEG = (float)57.295779513082320876798154814105;
    public final float TH_EULER = (float)2.718281828459045235360287471352;
    public static float DEGTORAD(float deg) { return deg * TH_DEG_TO_RAD;}
    public static float RADTODEG(float rad) { return rad * TH_RAD_TO_DEG;}
    public static float SQUARE(float x) { return x * x; }
    public static float CLAMP(float x, float min, float max) { return x < min ? min : (x > max ? max : x); }
    public static  int CLAMP(int x, int min, int max) { return x < min ? min : (x > max ? max : x); }
    public static float FAST_LERP(float a,float b,float t) { return a + (b - a) * t; }
    public static float LERP(float a,float b,float t) { return ((1.0f-t)*a+(t*b)); }
    public static float ABS(float x) { return x < 0 ? -x : x; } 
    public static boolean FLOAT_EQUALS(float a, float b) { return ABS(a - b) < 1e-6; }
    public static float MIN(float a, float b) { return a < b ? a : b; }
    public static float MAX(float a, float b) { return a > b ? a : b; }
    public static float SIGN(float x) { return x < 0 ? -1 : 1; }  
    public static  byte BYTE_INTERPOLATE(byte a,byte b,float factor) {
        return (byte) LERP((float) a,(float) b,factor);
    }
    /** EXPERIMENTAL THIS MAY NOT WORK PROPERLY*/
    public static float COS_FROM_SIN(float _sin, float _angle) {
        float _cos=(float) Math.sqrt(1-SQUARE(_sin));
        float a=_angle+TH_HALF_PI;
        float b = a - ((int)(a / TH_TWO_PI) * TH_TWO_PI);
        if (b < 0) b += TH_TWO_PI;
        return (b >= TH_PI) ? -_cos : _cos;
    }
    public static int LCM(int a,int b) {
        for(int i=2;i<=MIN(a,b);i++)  {
            if((a % i == 0) && (b%i == 0))
            return i;
        }
        return 1;
    }

    public static byte FLOAT_TO_BYTE(float value) {
        return (byte) (value * 255);
    }

    public static float BYTE_TO_FLOAT(byte value) {
        return (value & 0xFF) / 255.0f;
    }
}
