package choke3d.gl;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11; 

/**
 *
 * @author tocatoca
 */
public class Texture {
    int texture_id = 0;
    public boolean loaded() {
        return texture_id!=0;
    } 
    public void load(BufferedImage img) {
        if (texture_id != 0) {
            // Caso já tenha uma textura carregada, fazemos a limpeza dela antes de carregar uma nova
            GL11.glDeleteTextures(texture_id);
        }

        // Envia a imagem para o OpenGL
        int width = img.getWidth();
        int height = img.getHeight();
        // Converte a BufferedImage para ByteBuffer
        ByteBuffer buffer=ByteBuffer.allocateDirect(3 * width * height);
        for(int x=0;x<width ;x++) {
             for(int y=0;y<height;y++) {
                int rgb = img.getRGB(x, (height -1 ) - y);  // Obtém o valor RGB da imagem
                int r = (rgb >> 16) & 0xFF;  // Extrai o valor do componente vermelho
                int g = (rgb >> 8) & 0xFF;   // Extrai o valor do componente verde
                int b = rgb & 0xFF;          // Extrai o valor do componente azul

                // Coloca os valores de cor no buffer
                buffer.put((byte) r);  // Valor de r
                buffer.put((byte) g);  // Valor de g
                buffer.put((byte) b);  // Valor de b
             }
        }
        buffer.flip();

        // Cria uma nova textura OpenGL
        texture_id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture_id);

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
    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture_id);
    }
    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
