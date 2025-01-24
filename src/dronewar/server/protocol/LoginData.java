package dronewar.server.protocol;

import choke3d.network.BinaryFormat;
import choke3d.network.BinaryPackage;

/**
 *
 * @author tocatoca
 */
public class LoginData extends BinaryPackage {
    public String name=""; 
    public LoginData() {
        super();  
        putField("name","string");  
    }
    @Override
    public void serialize() {
        putValue("name", name);  
    }
    @Override
    public void unserialize() {
        name=getString("name"); 
    }
}
