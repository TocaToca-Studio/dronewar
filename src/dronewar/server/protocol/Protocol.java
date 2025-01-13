package dronewar.server.protocol;

import choke3d.utils.BinaryPackage;
import choke3d.utils.ChecksumUtil;
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
    public static final int SUCCESS = 3;
    public static final int FAIL = 4;
    public static final int GENERAL_UPDATE = 5;
    
    public static byte[] encodeMessage(BinaryPackage messagepack,int message_code) {
        ByteBuffer encoded_data=messagepack.encode();
        ByteBuffer requestbuff=ByteBuffer.allocate(Integer.SIZE+encoded_data.remaining());
        requestbuff.putInt(message_code);
        requestbuff.put(encoded_data);
        // Mensagem para enviar 
        requestbuff.flip(); 
        /*ChecksumUtil.addChecksum(requestbuff);*/
        return requestbuff.array();
    }
    public static DatagramPacket preparePacket(BinaryPackage pack,int message_code, InetAddress address,int port) {
        byte[] messageBytes=encodeMessage(pack,message_code);
        return new DatagramPacket(messageBytes, messageBytes.length, address, port);
    }
    
    public static DatagramPacket preparePacket(int message_code, InetAddress address,int port) {
        ByteBuffer encoded_data=ByteBuffer.allocate(Integer.SIZE);
        encoded_data.putInt(message_code);
        encoded_data.flip();
        byte[] data=encoded_data.array();
        return new DatagramPacket(data, data.length, address, port);
    }
}
