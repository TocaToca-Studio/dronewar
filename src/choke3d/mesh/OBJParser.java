package choke3d.mesh;

import choke3d.math.Vec2f;
import choke3d.math.Vec3f;
import choke3d.vika.frontend.mesh.MeshTriangle;
import choke3d.vika.frontend.mesh.MeshVertex;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tocatoca
 */
public class OBJParser {
    
    public static class OBJFileData {
        public  String filename;
        public Map<String, OBJMesh> objects = new HashMap<>();
        public List<String> mtlfiles = new ArrayList<>();
    }
    public static class OBJTriangle {
        public int[] vertexIndices=new int[3];
        public int[] uvIndices=new int[3];
        public int[] normalIndices=new int[3];
        public boolean with_error=false;
    }
    public static class OBJVertex  { 
        public Vec3f vertex=new Vec3f();
        public Vec3f normal=new Vec3f();
        public Vec2f uv=new Vec2f();
    }
    public  static  class OBJMesh {
        String name="";
        ArrayList<Vec3f> vertices=new ArrayList<>();
        ArrayList<Vec3f> normals=new ArrayList<>();
        ArrayList<Vec2f> uvs=new ArrayList<>();
        ArrayList<OBJTriangle> triangles=new ArrayList<>();
        int group_id;
        String materrial_name="";
        
        public OBJVertex[] get_vertices() {
            ArrayList<OBJVertex> verts=new ArrayList<>();
            verts.ensureCapacity(triangles.size()*3);
            for(OBJTriangle tri : triangles) {
                for(int i=0;i<3;i++) {
                    OBJVertex v=new OBJVertex();
                    v.vertex=vertices.get(tri.vertexIndices[i]);
                    v.normal=normals.get(tri.normalIndices[i]);
                    v.uv=uvs.get(tri.uvIndices[i]);
                    verts.add(v);
                }
            }
            return verts.toArray(new OBJVertex[0]);
        }
        public  MeshTriangle[] get_geometry() {
            ArrayList<MeshTriangle> tris=new ArrayList<>();
            tris.ensureCapacity(triangles.size());
            for(OBJTriangle tri : triangles) {
                MeshTriangle t=new MeshTriangle();
                for(int i=0;i<3;i++) {
                    MeshVertex v=new MeshVertex();
                    v.position=vertices.get(tri.vertexIndices[i]);
                    v.normal=normals.get(tri.normalIndices[i]);
                    v.uv=uvs.get(tri.uvIndices[i]);
                    t.verts[i]=v;
                }
                tris.add(t);
            }
            return tris.toArray(new MeshTriangle[0]);
        }
        
    }
    
    public static OBJFileData parseObjFile(String objFileContent, String filename) {
        OBJFileData result = new OBJFileData();
        result.filename = filename;

        BufferedReader file = new BufferedReader(new StringReader(objFileContent));
        OBJMesh currentMesh = new OBJMesh();

        try {
            String line;
            while ((line = file.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (tokens.length == 0) continue;

                String prefix = tokens[0];

                switch (prefix) {
                    case "o":
                    case "g":
                        if (!currentMesh.name.isEmpty()) {
                            result.objects.put(currentMesh.name, currentMesh);
                        }
                        currentMesh = new OBJMesh();
                        currentMesh.name = tokens.length > 1 ? tokens[1] : "";
                        break;

                    case "v":
                        currentMesh.vertices.add(new Vec3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3])
                        ));
                        break;

                    case "vn":
                        currentMesh.normals.add(new Vec3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3])
                        ));
                        break;

                    case "vt":
                        currentMesh.uvs.add(new Vec2f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2])
                        ));
                        break;

                    case "f":
                        OBJTriangle triangle = new OBJTriangle();
                        for (int i = 0; i < 3; i++) {
                            String[] vertexData = tokens[i + 1].split("/");
                            triangle.vertexIndices[i] = Integer.parseInt(vertexData[0]) - 1;
                            if (vertexData.length > 1 && !vertexData[1].isEmpty()) {
                                triangle.uvIndices[i] = Integer.parseInt(vertexData[1]) - 1;
                            }
                            if (vertexData.length > 2 && !vertexData[2].isEmpty()) {
                                triangle.normalIndices[i] = Integer.parseInt(vertexData[2]) - 1;
                            }
                        }
                        currentMesh.triangles.add(triangle);
                        break;

                    case "usemtl":
                        currentMesh.materrial_name = tokens.length > 1 ? tokens[1] : "";
                        break;

                    case "mtllib":
                        if (tokens.length > 1) {
                            result.mtlfiles.add(tokens[1]);
                        }
                        break;

                    default:
                        // Handle other cases as needed
                        break;
                }
            }

            // Add the last mesh
            if (!currentMesh.name.isEmpty()) {
                result.objects.put(currentMesh.name, currentMesh);
            }

        } catch (IOException e) {
            System.err.println("Cannot parse OBJ content");
        }

        return result;
    }

}
