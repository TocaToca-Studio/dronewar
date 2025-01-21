package dronewar.server.protocol;

import choke3d.utils.BinaryPackage;
import choke3d.utils.ChecksumUtil;
import choke3d.utils.UDPCompression;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            ByteBuffer encoded_data=messagepack.encode();
            ByteBuffer requestbuff=ByteBuffer.allocate(Integer.SIZE+encoded_data.remaining());
            requestbuff.putInt(message_code);
            requestbuff.put(encoded_data);
            // Mensagem para enviar
            requestbuff.flip();
            /*ChecksumUtil.addChecksum(requestbuff);*/
            byte[] pack=UDPCompression.compress(requestbuff.array());
            /*if(pack.length>600) {
                System.out.println("ENVIANDO PACOTE DE "+pack.length+" bytes");
            }*/
            return pack;
        } catch (IOException ex) {
            Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new byte[] {};
    }
    public static DatagramPacket preparePacket(BinaryPackage pack,int message_code, InetAddress address,int port) {
        byte[] messageBytes=encodeMessage(pack,message_code);
        return new DatagramPacket(messageBytes, messageBytes.length, address, port);
    }
    
    public static DatagramPacket preparePacket(int message_code, InetAddress address,int port) {
        try {
            ByteBuffer encoded_data=ByteBuffer.allocate(Integer.SIZE);
            encoded_data.putInt(message_code);
            encoded_data.flip();
            byte[] data=UDPCompression.compress(encoded_data.array());
            return new DatagramPacket(data, data.length, address, port);
        } catch (IOException ex) {
            Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
