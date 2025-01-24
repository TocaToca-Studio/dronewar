package choke3d.vika.backend;

import choke3d.math.Color4f;
import choke3d.vika.frontend.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author tocatoca
 */
public class JavaImageWraper implements Image  {
    BufferedImage img=null;
    
    public JavaImageWraper(BufferedImage image) {
        img=image;
    }
    @Override
    public Color4f getPixelColor(int x, int y) {
        return Color4f.from_RGB(img.getRGB(x, y));
    } 

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    @Override
    public void setPixelColor(int x, int y, Color4f color) {
        img.setRGB(x, y, color.to_rgb());
    }

}
