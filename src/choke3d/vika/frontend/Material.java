// fé é a vitória
package choke3d.vika.frontend;
 
import choke3d.math.Color4f;


/**
 *
 * @author tocatoca
 */
public class Material {  
    public Color4f albedo_color=new Color4f();
    public Texture albedo=null;
    public Texture normal_map=null;
    public float specular=0;
    public Color4f getColor() {
        return albedo_color;
    }
    public Texture get_albedo() { return albedo;}
    public Texture get_normalmap() { return normal_map; }
    public float get_specular() { return specular; }
}
