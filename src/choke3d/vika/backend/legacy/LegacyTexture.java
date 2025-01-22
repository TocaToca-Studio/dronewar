package choke3d.vika.backend.legacy;

import choke3d.math.Color4f;
import choke3d.math.MathUtils;
import choke3d.vika.frontend.Image;
import choke3d.vika.frontend.Texture;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author tocatoca
 */
public class LegacyTexture implements Texture {
 int texture_id = 0;
    public boolean loaded() {
        return texture_id!=0;
    }  
    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture_id);
    }
    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    } 
    @Override
    public void load(Image img) {
       
         if (texture_id != 0) {
            // Caso já tenha uma textura carregada, fazemos a limpeza dela antes de carregar uma nova
            GL11.glDeleteTextures(texture_id);
        }

        // Cria uma nova textura OpenGL
        texture_id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture_id);
        
        // Envia a imagem para o OpenGL
        int width = img.getWidth();
        int height = img.getHeight();
        // Converte a BufferedImage para ByteBuffer
        ByteBuffer buffer=ByteBuffer.allocateDirect(3 * width * height);
        for(int x=0;x<width ;x++) {
             for(int y=0;y<height;y++) {
                Color4f color = img.getPixelColor(x, (height -1 ) - y);  // Obtém o valor RGB da imagem 

                // Coloca os valores de cor no buffer
                buffer.put(MathUtils.FLOAT_TO_BYTE(color.r));  // Valor de r
                buffer.put(MathUtils.FLOAT_TO_BYTE(color.g));  // Valor g
                buffer.put(MathUtils.FLOAT_TO_BYTE(color.b));  // Valor b
             }
        }
        buffer.flip();


        // Define parâmetros de texturização
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        int format = GL11.GL_RGB;
        
        // Cria a textura no OpenGL
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, format, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, buffer);
        
        System.out.println("Textura carregada = "+ texture_id);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        // Gera mipmaps 
        //GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
    }

    @Override
    public void unload() {
        if(loaded()) {
            GL11.glDeleteTextures(texture_id);
            texture_id=0;
        }
    }

}
