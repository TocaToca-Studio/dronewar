package choke3d.vika.frontend;

import choke3d.math.Mat4f;
import choke3d.vika.frontend.mesh.Mesh;

/**
 *
 * @author tocatoca
 */
public class DrawObject {
    public Mesh mesh=null;
    public Material material=new Material();
    public Mat4f model=Mat4f.IDENTITY();
    public DrawObject(Mesh mesh) {
        this.mesh=mesh;
    }
}
