package choke3d.vika.frontend.mesh;

import choke3d.math.Vec2f;
import choke3d.math.Vec3f;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tocatoca
 */
public abstract class Mesh {
    public abstract void load(MeshTriangle[] triangles);
    public abstract void unload();
    public static MeshTriangle[] to_triangles(float[][][] data) {
         // Lista para armazenar os triângulos
        ArrayList<MeshTriangle> triangles = new ArrayList<>();

        for (int i = 0; i < data.length; i += 3) {
            MeshTriangle triangle = new MeshTriangle(); 
            for (int j = 0; j < 3; j++) {
                int index = i + j;
                float[] pos = data[index][0];
                float[] norm = data[index][1];
                float[] tex = data[index][2];
                triangle.verts[j].position = new Vec3f(pos[0], pos[1], pos[2]);
                triangle.verts[j].normal = new Vec3f(norm[0], norm[1], norm[2]);
                triangle.verts[j].uv = new Vec2f(tex[0], tex[1]);
            }
            triangles.add(triangle);
        }
        return  triangles.toArray(new MeshTriangle[0]);
    }
    public static MeshTriangle[] get_skybox() {
        float[][][] skybox_data= {
            // Face frontal
            {{-1.0f, -1.0f,  1.0f}, {0.0f, 0.0f, 1.0f}, {0.25f, 0.33f}},
            {{ 1.0f, -1.0f,  1.0f}, {0.0f, 0.0f, 1.0f}, {0.50f, 0.33f}},
            {{ 1.0f,  1.0f,  1.0f}, {0.0f, 0.0f, 1.0f}, {0.50f, 0.66f}},
            {{ 1.0f,  1.0f,  1.0f}, {0.0f, 0.0f, 1.0f}, {0.50f, 0.66f}},
            {{-1.0f,  1.0f,  1.0f}, {0.0f, 0.0f, 1.0f}, {0.25f, 0.66f}},
            {{-1.0f, -1.0f,  1.0f}, {0.0f, 0.0f, 1.0f}, {0.25f, 0.33f}},

            // Face traseira
            {{-1.0f, -1.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {1.00f, 0.33f}},
            {{-1.0f,  1.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {1.00f, 0.66f}},
            {{ 1.0f,  1.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {0.75f, 0.66f}},
            {{ 1.0f,  1.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {0.75f, 0.66f}},
            {{ 1.0f, -1.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {0.75f, 0.33f}},
            {{-1.0f, -1.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {1.00f, 0.33f}},

            // Face esquerda
            {{-1.0f, -1.0f, -1.0f}, {-1.0f, 0.0f, 0.0f}, {0.00f, 0.33f}},
            {{-1.0f, -1.0f,  1.0f}, {-1.0f, 0.0f, 0.0f}, {0.25f, 0.33f}},
            {{-1.0f,  1.0f,  1.0f}, {-1.0f, 0.0f, 0.0f}, {0.25f, 0.66f}},
            {{-1.0f,  1.0f,  1.0f}, {-1.0f, 0.0f, 0.0f}, {0.25f, 0.66f}},
            {{-1.0f,  1.0f, -1.0f}, {-1.0f, 0.0f, 0.0f}, {0.00f, 0.66f}},
            {{-1.0f, -1.0f, -1.0f}, {-1.0f, 0.0f, 0.0f}, {0.00f, 0.33f}},

            // Face direita
            {{ 1.0f, -1.0f,  1.0f}, {1.0f, 0.0f, 0.0f}, {0.50f, 0.33f}},
            {{ 1.0f, -1.0f, -1.0f}, {1.0f, 0.0f, 0.0f}, {0.75f, 0.33f}},
            {{ 1.0f,  1.0f, -1.0f}, {1.0f, 0.0f, 0.0f}, {0.75f, 0.66f}},
            {{ 1.0f,  1.0f, -1.0f}, {1.0f, 0.0f, 0.0f}, {0.75f, 0.66f}},
            {{ 1.0f,  1.0f,  1.0f}, {1.0f, 0.0f, 0.0f}, {0.50f, 0.66f}},
            {{ 1.0f, -1.0f,  1.0f}, {1.0f, 0.0f, 0.0f}, {0.50f, 0.33f}},

            // Face inferior
            {{-1.0f, -1.0f, -1.0f}, {0.0f, -1.0f, 0.0f}, {0.25f, 0.00f}},
            {{ 1.0f, -1.0f, -1.0f}, {0.0f, -1.0f, 0.0f}, {0.50f, 0.00f}},
            {{ 1.0f, -1.0f,  1.0f}, {0.0f, -1.0f, 0.0f}, {0.50f, 0.33f}},
            {{ 1.0f, -1.0f,  1.0f}, {0.0f, -1.0f, 0.0f}, {0.50f, 0.33f}},
            {{-1.0f, -1.0f,  1.0f}, {0.0f, -1.0f, 0.0f}, {0.25f, 0.33f}},
            {{-1.0f, -1.0f, -1.0f}, {0.0f, -1.0f, 0.0f}, {0.25f, 0.00f}},

            // Face superior
            {{-1.0f,  1.0f, -1.0f}, {0.0f, 1.0f, 0.0f}, {0.25f, 1.00f}},
            {{ 1.0f,  1.0f, -1.0f}, {0.0f, 1.0f, 0.0f}, {0.50f, 1.00f}},
            {{ 1.0f,  1.0f,  1.0f}, {0.0f, 1.0f, 0.0f}, {0.50f, 0.66f}},
            {{ 1.0f,  1.0f,  1.0f}, {0.0f, 1.0f, 0.0f}, {0.50f, 0.66f}},
            {{-1.0f,  1.0f,  1.0f}, {0.0f, 1.0f, 0.0f}, {0.25f, 0.66f}},
            {{-1.0f,  1.0f, -1.0f}, {0.0f, 1.0f, 0.0f}, {0.25f, 1.00f}},
        };
        return to_triangles(skybox_data);
    }
    public static MeshTriangle[] get_cube() {
        float[][][] data={
            // positions          // vectors          // texture coords
            // front face
            {{-0.5f, -0.5f,  0.5f},{ 0.0f,  0.0f,  1.0f},{ 0.0001f,  0.0001f}}, // bottom left
            {{ 0.5f, -0.5f,  0.5f},{ 0.0f,  0.0f,  1.0f},{ 0.9999f,  0.0001f}}, // bottom right
            {{ 0.5f,  0.5f,  0.5f},{ 0.0f,  0.0f,  1.0f},{ 0.9999f, 0.9999f}}, // top right
            {{ 0.5f,  0.5f,  0.5f},{ 0.0f,  0.0f,  1.0f},{ 0.9999f, 0.9999f}}, // top right
            {{-0.5f,  0.5f,  0.5f},{ 0.0f,  0.0f,  1.0f},{ 0.0001f, 0.9999f}}, // top left
            {{-0.5f, -0.5f,  0.5f},{ 0.0f,  0.0f,  1.0f},{ 0.0001f,  0.0001f}}, // bottom left

            // back face
            {{-0.5f, -0.5f, -0.5f},{ 0.0f,  0.0f, -1.0f},{ 0.0001f,  0.0001f}}, // bottom left
            {{-0.5f,  0.5f, -0.5f},{ 0.0f,  0.0f, -1.0f},{ 0.0001f, 0.9999f}}, // top left
            {{ 0.5f,  0.5f, -0.5f},{ 0.0f,  0.0f, -1.0f},{ 0.9999f, 0.9999f}}, // top right
            {{ 0.5f,  0.5f, -0.5f},{ 0.0f,  0.0f, -1.0f},{ 0.9999f, 0.9999f}}, // top right
            {{ 0.5f, -0.5f, -0.5f},{ 0.0f,  0.0f, -1.0f},{ 0.9999f,  0.0001f}}, // bottom right
            {{-0.5f, -0.5f, -0.5f},{ 0.0f,  0.0f, -1.0f},{ 0.0001f,  0.0001f}}, // bottom left

            // left face
            {{-0.5f,  0.5f,  0.5f},{-1.0f,  0.0f,  0.0f},{ 0.9999f, 0.9999f}}, // top right
            {{-0.5f,  0.5f, -0.5f},{-1.0f,  0.0f,  0.0f},{ 0.9999f,  0.0001f}}, // top left
            {{-0.5f, -0.5f, -0.5f},{-1.0f,  0.0f,  0.0f},{ 0.0001f,  0.0001f}}, // bottom left
            {{-0.5f, -0.5f, -0.5f},{-1.0f,  0.0f,  0.0f},{ 0.0001f,  0.0001f}}, // bottom left
            {{-0.5f, -0.5f,  0.5f},{-1.0f,  0.0f,  0.0f},{ 0.0001f, 0.9999f}}, // bottom right
            {{-0.5f,  0.5f,  0.5f},{-1.0f,  0.0f,  0.0f},{ 0.9999f, 0.9999f}}, // top right

            // right face
            {{ 0.5f,  0.5f,  0.5f},{ 1.0f,  0.0f,  0.0f},{ 0.9999f, 0.9999f}}, // top left
            {{ 0.5f, -0.5f, -0.5f},{ 1.0f,  0.0f,  0.0f},{ 0.0001f,  0.0001f}}, // bottom right
            {{ 0.5f,  0.5f, -0.5f},{ 1.0f,  0.0f,  0.0f},{ 0.9999f,  0.0001f}}, // top right
            {{ 0.5f, -0.5f, -0.5f},{ 1.0f,  0.0f,  0.0f},{ 0.0001f,  0.0001f}}, // bottom right
            {{ 0.5f,  0.5f,  0.5f},{ 1.0f,  0.0f,  0.0f},{ 0.9999f, 0.9999f}}, // top left
            {{ 0.5f, -0.5f,  0.5f},{ 1.0f,  0.0f,  0.0f},{ 0.0001f, 0.9999f}}, // bottom left

            // top face
            {{-0.5f,  0.5f, -0.5f},{ 0.0f,  1.0f,  0.0f},{ 0.0001f, 0.0001f}}, // bottom left
            {{ 0.5f,  0.5f, -0.5f},{ 0.0f,  1.0f,  0.0f},{ 0.9999f, 0.0001f}}, // bottom right
            {{ 0.5f,  0.5f,  0.5f},{ 0.0f,  1.0f,  0.0f},{ 0.9999f, 0.9999f}}, // top right
            {{ 0.5f,  0.5f,  0.5f},{ 0.0f,  1.0f,  0.0f},{ 0.9999f, 0.9999f}}, // top right
            {{-0.5f,  0.5f,  0.5f},{ 0.0f,  1.0f,  0.0f},{ 0.0001f, 0.9999f}}, // top left
            {{-0.5f,  0.5f, -0.5f},{ 0.0f,  1.0f,  0.0f},{ 0.0001f, 0.0001f}}, // bottom left

            // bottom face
            {{-0.5f, -0.5f, -0.5f},{ 0.0f, -1.0f,  0.0f},{ 0.0001f, 0.0001f}}, // bottom right
            {{ 0.5f, -0.5f,  0.5f},{ 0.0f, -1.0f,  0.0f},{ 0.9999f, 0.9999f}}, // top left
            {{ 0.5f, -0.5f, -0.5f},{ 0.0f, -1.0f,  0.0f},{ 0.9999f, 0.0001f}}, // bottom left
            {{ 0.5f, -0.5f,  0.5f},{ 0.0f, -1.0f,  0.0f},{ 0.9999f, 0.9999f}}, // top left
            {{-0.5f, -0.5f, -0.5f},{ 0.0f, -1.0f,  0.0f},{ 0.0001f, 0.0001f}}, // bottom right
            {{-0.5f, -0.5f,  0.5f},{ 0.0f, -1.0f,  0.0f},{ 0.0001f, 0.9999f}}  // top right
        };
        return to_triangles(data);
    }
    public static MeshTriangle[] get_quad() {
        float[][][] data={
            // positions   // vectors    // texture coords
             {{ 0.5f,  0.5f,0.0f  },{ 0.0f,  0.0f,1.0f  },{ 0.9999f, 0.9999f}}, // top right
             {{ 0.5f, -0.5f,0.0f  },{ 0.0f,  0.0f,1.0f  },{ 0.9999f,  0.0001f}}, // bottom right
             {{-0.5f,  0.5f,0.0f  },{ 0.0f,  0.0f,1.0f  },{ 0.0001f, 0.9999f}}, // top left 
             {{ 0.5f, -0.5f,0.0f  },{ 0.0f,  0.0f,1.0f  },{ 0.9999f,  0.0001f}}, // bottom right
            {{-0.5f, -0.5f,0.0f  },{ 0.0f,  0.0f,1.0f  },{  0.0001f,  0.0001f}}, // bottom left
            {{-0.5f,  0.5f,0.0f  },{ 0.0f,  0.0f,1.0f  },{  0.0001f, 0.9999f}}  // top left 
        };
        return to_triangles(data);
    }
}
