package dronewar.server.protocol;

import choke3d.utils.BinaryPackage;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 *
 * @author tocatoca
 */
public class Protocol {
    public static final int PING = 0;
    public static final int LOGIN_REQUEST = 1;
    public static final int QUIT = 2;
    public static byte[] encodeMessage(BinaryPackage messagepack,int message_code) {
        ByteBuffer encoded_data=messagepack.encode();
        ByteBuffer requestbuff=ByteBuffer.allocate(Integer.SIZE+encoded_data.remaining());
        requestbuff.putInt(message_code);
        requestbuff.put(encoded_data);
        // Mensagem para enviar 
        requestbuff.flip(); 
        return requestbuff.array();
    }
    public static DatagramPacket preparePacket(BinaryPackage pack,int message_code, InetAddress address,int port) {
        byte[] requestBytes=encodeMessage(pack,message_code);
        return new DatagramPacket(requestBytes, requestBytes.length, address, port);

    }
}
