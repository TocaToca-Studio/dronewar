package choke3d.vika.frontend.image;

import choke3d.math.Color4f;
import choke3d.vika.frontend.Image;
import static choke3d.math.MathUtils.BYTE_TO_FLOAT;
import static choke3d.math.MathUtils.FLOAT_TO_BYTE;
import java.nio.ByteBuffer;

/**
 *
 * @author tocatoca
 */
public class Image32 implements Image {
    protected byte[] buffer=null;
    private int width=0;
    private int height=0;
    public Image32(int w,int h) {
       width=w;height=h;
       buffer=new byte[w*h*3];
    }
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Color4f getPixelColor(int x, int y) {
        int offset=((y*height)+y)*4;
        return new Color4f(
            BYTE_TO_FLOAT(buffer[offset]),
            BYTE_TO_FLOAT(buffer[offset+1]),
            BYTE_TO_FLOAT(buffer[offset+2]), 
            BYTE_TO_FLOAT(buffer[offset+3])
        );
    }

    @Override
    public void setPixelColor(int x, int y, Color4f color) { 
        int offset=((y*height)+y)*4;
        buffer[offset]=FLOAT_TO_BYTE(color.r);
        buffer[offset+1]=FLOAT_TO_BYTE(color.g);
        buffer[offset+2]=FLOAT_TO_BYTE(color.b);
        buffer[offset+3]=FLOAT_TO_BYTE(color.a);
    }

}
