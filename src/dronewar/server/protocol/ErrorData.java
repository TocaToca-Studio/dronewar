package dronewar.server.protocol;

import choke3d.network.BinaryPackage;

/**
 *
 * @author tocatoca
 */
public class ErrorData extends BinaryPackage {
    public String message=""; 
    public ErrorData() {
        super();  
        putField("message","string");   
    }
    @Override
    public void serialize() {
        putValue("message", message);  
    }
    @Override
    public void unserialize() {
        message=getString("message"); 
    }
}