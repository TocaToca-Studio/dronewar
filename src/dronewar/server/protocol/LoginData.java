package dronewar.server.protocol;

import choke3d.utils.BinaryFormat;
import choke3d.utils.BinaryPackage;

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
