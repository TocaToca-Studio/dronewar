package dronewar.server.protocol;

import choke3d.utils.BinaryFormat;
import choke3d.utils.BinaryPackage;
import java.nio.ByteBuffer;

/**
 *
 * @author tocatoca
 */
public class ClientRequest extends BinaryPackage { 
    String command="";
    public ClientRequest() {
        super();
        format.put("command", "string");  
    } 
    @Override
    public void serialize() {
        values.put("command",command);
    }
    @Override
    public void unserialize() {
        command=getString("command");
    }
}
