package choke3d.vika.frontend;

import choke3d.math.Color4f;

/**
 *
 * @author tocatoca
 */
public interface Image {
    public int getWidth();
    public int getHeight();
    public Color4f getPixelColor(int x,int y); 
    public void setPixelColor(int x,int y,Color4f color); 
}
