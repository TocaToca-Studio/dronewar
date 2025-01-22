package choke3d.vika.backend.legacy;

import choke3d.vika.frontend.mesh.Mesh;
import choke3d.vika.frontend.mesh.MeshTriangle;
import choke3d.vika.frontend.mesh.MeshVertex;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author tocatoca
 */
public class LegacyMesh extends Mesh {
    protected int list_id=0;
    @Override
    public void load(MeshTriangle[] triangles) {
        unload();
        list_id=glGenLists(1);
        glNewList(list_id, GL_COMPILE);
        glBegin(GL_TRIANGLES);
        for(MeshTriangle tri : triangles) {
            for(MeshVertex vert : tri.verts) {
                GL11.glNormal3f(vert.normal.x, vert.normal.y, vert.normal.z);
                GL11.glTexCoord2f(vert.uv.y, vert.uv.x);
                GL11.glVertex3f(vert.position.x, vert.position.y, vert.position.z);
            }
        }
        glEnd();
        glEndList();
    }
    
    public void draw() {
        if(list_id!=0) glCallList(list_id);
    }
    @Override
    public void unload() {
        if(list_id!=0) {
            glDeleteLists(list_id, 1);
            list_id=0;
        }
    }

}
