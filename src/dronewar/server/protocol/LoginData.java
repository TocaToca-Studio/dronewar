package dronewar.server.protocol;

import choke3d.utils.BinaryFormat;
import choke3d.utils.BinaryPackage;

/**
 *
 * @author tocatoca
 */
public class LoginData extends BinaryPackage {
    public String name="";
    public int score=0;
    public LoginData() {
        super();  
        putField("name","string"); 
        putField("score","int");  
    }
    @Override
    public void serialize() {
        putValue("name", name); 
        putValue("score", score); 
    }
    @Override
    public void unserialize() {
        name=getString("name");
        score=getInt("score");
    }
}
