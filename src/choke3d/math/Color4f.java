package choke3d.math;
  

/**
 *
 * @author tocatoca
 */
public class Color4f {

    public static Color4f from_height(float height) { 
        // Garantir que o valor de 'height' esteja entre 0 e 1
        height = Math.max(0f, Math.min(1f, height));

        // Cores
        Color4f color = new Color4f(); // RGB

        if (height < 0.5f) {
            // Gradiente do azul para o amarelo (baixo para o meio)
            color.r = 0; // Red
            color.g = height * 2f; // Green (crescendo)
            color.b = 1 - height * 2f; // Blue (diminuindo)
        } else {
            // Gradiente do amarelo para o vermelho (meio para o alto)
            color.r = (height - 0.5f) * 2f; // Red (crescendo)
            color.g = 1 - (height - 0.5f) * 2f; // Green (diminuindo)
            color.b = 0; // Blue
        }

        return color;
    }

    public static Color4f from_RGB(int rgb) {
         // Extrair os componentes R, G, B do valor RGB
        int a = (rgb >> 24) & 0xFF;
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        // Converter de 0-255 para 0.0-1.0
        float aFloat = a / 255.0f;
        float rFloat = r / 255.0f;
        float gFloat = g / 255.0f;
        float bFloat = b / 255.0f;
 
       return new Color4f(rFloat, gFloat, bFloat,aFloat);
    }
     public int to_rgb() {
        int _a = (int) (r * 255);
        int _r = (int) (r * 255);
        int _g = (int) (g * 255);
        int _b = (int) (b * 255);
        return (_r << 24) | (_r << 16) | (_g << 8) | _b;
    }
    public float r, g, b, a;

    public Color4f() {
        r = g = b = a = 1.0f;
    } 
    public Color4f(float red, float green, float blue, float alpha) {
        r = red;
        g = green;
        b = blue;
        a = alpha;
    }

    public Color4f(float red, float green, float blue) {
        r = red;
        g = green;
        b = blue;
        a = 1.0f;
    }
 
 
    public static Color4f WHITE() {
        return new Color4f(1, 1, 1, 1);
    }

    public static Color4f BLACK() {
        return new Color4f(0, 0, 0, 1);
    }

    public static Color4f RED() {
        return new Color4f(1, 0, 0, 1);
    }

    public static Color4f GREEN() {
        return new Color4f(0, 1, 0, 1);
    }

    public static Color4f BLUE() {
        return new Color4f(0, 0, 1, 1);
    }

    public static Color4f YELLOW() {
        return new Color4f(1, 1, 0, 1);
    }

    public static Color4f CYAN() {
        return new Color4f(0, 1, 1, 1);
    }

    public static Color4f MAGENTA() {
        return new Color4f(1, 0, 1, 1);
    }

    public static Color4f GRAY() {
        return new Color4f(0.5f, 0.5f, 0.5f, 1);
    }

    public static Color4f LIGHT_GRAY() {
        return new Color4f(0.75f, 0.75f, 0.75f, 1);
    }

    public static Color4f DARK_GRAY() {
        return new Color4f(0.25f, 0.25f, 0.25f, 1);
    }

    public static Color4f ORANGE() {
        return new Color4f(1, 0.5f, 0, 1);
    }

    public static Color4f PURPLE() {
        return new Color4f(0.5f, 0, 0.5f, 1);
    }

    public static Color4f PINK() {
        return new Color4f(1, 0.75f, 0.75f, 1);
    }

    public static Color4f BROWN() {
        return new Color4f(0.5f, 0.25f, 0, 1);
    }

    public static Color4f LIME() {
        return new Color4f(0, 1, 0, 1);
    }

    public static Color4f interpolate(Color4f a, Color4f b, float factor) {
        return new Color4f(
            lerp(a.r, b.r, factor),
            lerp(a.g, b.g, factor),
            lerp(a.b, b.b, factor),
            lerp(a.a, b.a, factor)
        );
    }

    public static Color4f bilinearInterpolate(Color4f c00, Color4f c10, Color4f c01, Color4f c11, float tx, float ty) {
        float r = lerp(lerp(c00.r, c10.r, tx), lerp(c01.r, c11.r, tx), ty);
        float g = lerp(lerp(c00.g, c10.g, tx), lerp(c01.g, c11.g, tx), ty);
        float b = lerp(lerp(c00.b, c10.b, tx), lerp(c01.b, c11.b, tx), ty);
        float a = lerp(lerp(c00.a, c10.a, tx), lerp(c01.a, c11.a, tx), ty);
        return new Color4f(r, g, b, a);
    }

    public float grayscale() {
        return (r+g+b)/3.0f;
    }

    private static float lerp(float a, float b, float factor) {
        return a + factor * (b - a);
    }
    
}
