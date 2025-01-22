package choke3d.vika.frontend;

import choke3d.math.Vec2i;
import choke3d.vika.frontend.mesh.Mesh;
import choke3d.vika.frontend.mesh.MeshTriangle;

/**
 *
 * @author tocatoca
 */
public abstract class Platform {

    public abstract Scene create_scene();
    public abstract double get_delta();
    public abstract boolean is_running();
    public abstract void init();
    public abstract void update();
    public abstract void destroy();
    public abstract Input get_input(); 
    public abstract Mesh create_mesh();
    public abstract Texture create_texture();
    public Mesh load_mesh(MeshTriangle[] triangles) {
        Mesh m=create_mesh();
        m.load(triangles);
        return m;
    }
    public abstract Vec2i get_window_size();
}
