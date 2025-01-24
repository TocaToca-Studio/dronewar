package choke3d.mesh;
  
import java.awt.image.BufferedImage;
import static org.lwjgl.opengl.GL11.*; 
import choke3d.math.Vec3f;
import choke3d.math.Color4f;
import choke3d.math.Vec2f;
import choke3d.vika.backend.legacy.LegacyTexture; 
import choke3d.vika.frontend.Image;
import choke3d.vika.frontend.image.Image24;
import choke3d.vika.frontend.mesh.MeshTriangle; 
import java.util.ArrayList;

/**
 *
 * @author tocatoca
 */
public class Heightmap { 
    public int width , length;
    public int xres,zres;
    public float[][] heightmap;
    public float xzscale=1;
    public float hscale=2; 
    
    
    public Heightmap(int w,int h) {
        width=w;length=h;
        xres=w;zres=h;
        heightmap=new float[w][h];
    }
    
    public void load(Image image, int w, int h) {  
        // Atualizar resolução do heightmap
        xres = w;
        zres = h;
        heightmap = new float[xres][zres];

        // Dimensões da imagem original
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        // Escala entre o heightmap e a imagem
        float scaleX = (float) imgWidth / xres;
        float scaleY = (float) imgHeight / zres;

        // Preencher o heightmap diretamente
        for (int x = 0; x < xres; x++) {
            for (int y = 0; y < zres; y++) { 
                // Mapear coordenadas do heightmap para a imagem
                int imgX = (int) (x * scaleX);
                int imgY = (int) (y * scaleY);

                // Inverter a coordenada Y se necessário
                int flippedY = imgHeight - 1 - imgY;

                int flippedX = imgWidth - 1 - imgX;
                // Obter o valor de cinza do pixel da imagem
                float grayscale = image.getPixelColor(imgY, imgX).grayscale();

                // Normalizar para valores entre 0 e 1 (opcional)
                heightmap[y][x] = Math.max(0.0f, Math.min(1.0f, grayscale));
            }
        }
    }


    public MeshTriangle[] generate_geometry() { 
        Vec2f factor = new Vec2f(1.0f / xres, 1.0f / zres);
        ArrayList<MeshTriangle> triangles = new ArrayList<>();

        for (int x = 0; x < xres - 1; x++) {
            for (int z = 0; z < zres - 1; z++) {
                // Obter alturas dos quatro vértices ao redor da célula
                float h1 = heightmap[x][z]; 
                Vec2f uv1 = new Vec2f(x * factor.x, z * factor.y);
                float h2 = heightmap[x + 1][z]; 
                Vec2f uv2 = new Vec2f((x * factor.x) + factor.x, z * factor.y);
                float h3 = heightmap[x][z + 1]; 
                Vec2f uv3 = new Vec2f(x * factor.x, (z * factor.y) + factor.y);
                float h4 = heightmap[x + 1][z + 1];  
                Vec2f uv4 = new Vec2f((x * factor.x) + factor.x, (z * factor.y) + factor.y);

                // Cálculo das posições
                Vec3f p1 = new Vec3f(uv1.x, h1, uv1.y);
                Vec3f p2 = new Vec3f(uv2.x, h2, uv2.y);
                Vec3f p3 = new Vec3f(uv3.x, h3, uv3.y);
                Vec3f p4 = new Vec3f(uv4.x, h4, uv4.y);

                // Primeiro triângulo
                Vec3f normal1 = p2.subtract(p1).cross(p3.subtract(p1)).normalized();
                MeshTriangle tri_1 = new MeshTriangle();
                tri_1.verts[0].uv = uv1;
                tri_1.verts[0].position = p1;
                tri_1.verts[0].normal = normal1;
                tri_1.verts[1].uv = uv2;
                tri_1.verts[1].position = p2;
                tri_1.verts[1].normal = normal1;
                tri_1.verts[2].uv = uv3;
                tri_1.verts[2].position = p3;
                tri_1.verts[2].normal = normal1;
                triangles.add(tri_1);

                // Segundo triângulo
                Vec3f normal2 = p4.subtract(p2).cross(p3.subtract(p2)).normalized();
                MeshTriangle tri_2 = new MeshTriangle();
                tri_2.verts[0].uv = uv2;
                tri_2.verts[0].position = p2;
                tri_2.verts[0].normal = normal2;
                tri_2.verts[1].uv = uv4;
                tri_2.verts[1].position = p4;
                tri_2.verts[1].normal = normal2;
                tri_2.verts[2].uv = uv3;
                tri_2.verts[2].position = p3;
                tri_2.verts[2].normal = normal2;
                triangles.add(tri_2);
            }
        }

        return triangles.toArray(new MeshTriangle[0]);
    }

    public Image getHeightColoredTexture() { 
        Image24 img=new Image24(xres,zres);
         for (int x = 0; x < xres - 1; x++) {
            for (int z = 0; z < zres - 1; z++) { 
                float h1 = heightmap[x][z]; 
                img.setPixelColor(x, z, Color4f.from_height(h1));
            }
        }
        return img;
    } 
}
