package tocaterrain;
 
import java.awt.image.BufferedImage;
import static org.lwjgl.opengl.GL11.*; 
import choke3d.math.Vec3f;
import choke3d.math.Color4f;
import choke3d.vika.backend.legacy.LegacyTexture;
import choke3d.vika.frontend.Texture;

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
    public LegacyTexture texture=new LegacyTexture();
    
    public boolean draw_wireframe=true;
    public boolean colored_wireframe=true;
    public boolean draw_terrain=false;
    public boolean colored_terrain=false; 
    
    public Heightmap(int w,int h) {
        width=w;length=h;
        xres=w;zres=h;
        heightmap=new float[w][h];
    }
    
    public void load(BufferedImage image) {  
        if(xres!=image.getWidth() || zres!=image.getHeight()) {
            xres=image.getWidth();
            zres=image.getHeight();
            heightmap=new float[xres][zres]; 
        }
        
        for (int x = 0; x < xres; x++) {
            for (int y = 0; y < zres; y++) { 
                heightmap[y][x] =  Color4f.from_RGB(image.getRGB(x,(zres -1 ) - y)).grayscale(); 
            }
        }
        
    }
    
    void draw_mesh() { 
        glColor3f(1,1,1);
         for (int x = 0; x < xres - 1; x++) {
            for (int z = 0; z < zres - 1; z++) {
                // Obter alturas dos quatro vértices ao redor da célula
                float h1 = heightmap[x][z]; 
                Vec3f uv1 = new Vec3f((x)/(float)xres,(z)/(float)zres,0);
                float h2 = heightmap[x + 1][z] ; 
                Vec3f uv2 = new Vec3f((x+1)/(float)xres,(z)/(float)zres,0);
                float h3 = heightmap[x][z + 1] ; 
                Vec3f uv3 = new Vec3f((x)/(float)xres,(z +1)/(float)zres,0);
                float h4 = heightmap[x + 1][z + 1];  
                Vec3f uv4 = new Vec3f((x+1)/(float)xres,(z+1)/(float)zres,0);
                
                float x1=(float)1/(float)xres;
                float z1=(float)1/(float)zres;
                float _x=x*x1;
                float _z=z*z1;
                // Primeiro triângulo 
                glTexCoord2f(uv1.x, uv1.y);
                glVertex3f(_x, h1, _z); 
                glTexCoord2f(uv2.x, uv2.y);
                glVertex3f(_x + x1, h2, _z); 
                glTexCoord2f(uv3.x, uv3.y);
                glVertex3f(_x, h3, _z + z1);

                // Segundo triângulo
                glTexCoord2f(uv2.x, uv2.y);
                glVertex3f(_x + x1, h2, _z); 
                glTexCoord2f(uv4.x, uv4.y);
                glVertex3f(_x + x1, h4, _z + z1); 
                glTexCoord2f(uv3.x, uv3.y);
                glVertex3f(_x, h3, _z + z1);
            }
        }
    }
    void draw_colored() {
         for (int x = 0; x < xres - 1; x++) {
            for (int z = 0; z < zres - 1; z++) {
                // Obter alturas dos quatro vértices ao redor da célula
                float h1 = heightmap[x][z];
                Color4f c1 = Color4f.from_height(h1);
                float h2 = heightmap[x + 1][z] ;
                Color4f c2 = Color4f.from_height(h2);
                float h3 = heightmap[x][z + 1] ;
                Color4f c3 = Color4f.from_height(h3);
                float h4 = heightmap[x + 1][z + 1];
                Color4f c4 = Color4f.from_height(h4);
                
                float x1=1f/(float)xres;
                float z1=1f/(float)zres;
                float _x=x*x1;
                float _z=z*z1;
                // Primeiro triângulo
                glColor3f(c1.r,c1.g,c1.b);
                glVertex3f(_x, h1, _z);
                glColor3f(c2.r,c2.g,c2.b);
                glVertex3f(_x + x1, h2, _z);
                glColor3f(c3.r,c3.g,c3.b);
                glVertex3f(_x, h3, _z + z1);

                // Segundo triângulo
                glColor3f(c2.r,c2.g,c2.b);
                glVertex3f(_x + x1, h2, _z);
                glColor3f(c4.r,c4.g,c4.b);
                glVertex3f(_x + x1, h4, _z + z1);
                glColor3f(c3.r,c3.g,c3.b);
                glVertex3f(_x, h3, _z + z1);
            }
        }
        glColor3f(1,1,1);
    }
    
    
    public void draw() { 
        
        glPushMatrix();
        glScalef(width,hscale,length); 
        glTranslatef(-0.5f,-0.5f,-0.5f);
        
        texture.bind();
        if(draw_terrain) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glBegin(GL_TRIANGLES); 
                if(colored_terrain) {
                    draw_colored();
                } else {
                    glColor3f(1,1,1);
                    draw_mesh();
                }
            glEnd();
        }
        texture.unbind();
        if(draw_wireframe) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            glBegin(GL_TRIANGLES); 
                if(colored_wireframe) {
                    draw_colored();
                } else {
                    glColor3f(1,0,0);
                    draw_mesh();
                }
            glEnd();
        }
        glPopMatrix();
        
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        
    }
}
