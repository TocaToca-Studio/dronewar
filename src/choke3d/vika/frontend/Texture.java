// fé é a vitória
package choke3d.vika.frontend;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author tocatoca
 */
public interface Texture {
    public void load(Image texture);
    public void unload(); 
    
    public void bind();
    public void unbind();
}
