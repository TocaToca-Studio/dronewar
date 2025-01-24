package choke3d.network;

/**
 *
 * @author tocatoca
 */
import java.nio.ByteBuffer;

public class ChecksumUtil {

    // Adiciona o checksum ao ByteBuffer (assume que há espaço suficiente para o checksum)
    public static void addChecksum(ByteBuffer buffer) {
        buffer.rewind(); // Garante que começamos do início do buffer
        int checksum = 0;
        int position=buffer.position();
        // Calcula o checksum somando todos os bytes (exceto o espaço do checksum)
        while (buffer.position() <= position) {
            checksum += Byte.toUnsignedInt(buffer.get());
        }

        // Adiciona o checksum no final
        buffer.putInt(checksum);
        
        //buffer.rewind(); // Prepara o buffer para leitura completa novamente
    }

    // Verifica o checksum no ByteBuffer
    public static boolean verifyChecksum(ByteBuffer buffer) {
        buffer.rewind(); // Garante que começamos do início do buffer
        int checksum = 0;

        // Calcula a soma de todos os bytes, exceto o checksum armazenado
        for (int i = 0; i < buffer.limit() - Integer.BYTES; i++) {
            checksum += Byte.toUnsignedInt(buffer.get());
        }

        // Lê o checksum armazenado no final do buffer
        int storedChecksum = buffer.getInt(buffer.limit() - Integer.BYTES);
        System.out.println(checksum != storedChecksum);
        // Verifica se o checksum calculado é igual ao armazenado
        return checksum == storedChecksum;
    }

    // Exemplo de uso
    public static void main(String[] args) {
        // Cria um buffer de exemplo com espaço extra para o checksum
        ByteBuffer buffer = ByteBuffer.allocate(14); // 10 bytes de dados + 4 bytes para checksum
        for (int i = 0; i < 10; i++) {
            buffer.put((byte) i);
        }

        // Adiciona o checksum
        addChecksum(buffer);

        // Verifica o checksum
        boolean isValid = verifyChecksum(buffer);

        System.out.println("Checksum válido: " + isValid);
    }
}