package dronewar.server.game;

import choke3d.utils.BinaryPackage;

/**
 *
 * @author tocatoca
 */
public class Player extends BinaryPackage { 
    public String name="Player";
    public int score=0;
    public int id=0;
    
    public Player() {
        super();  
        putField("name","string");   
        putField("id","int");   
        putField("score","int");   
    }
    @Override
    public void serialize() {
        putValue("name", name);  
        putValue("id", id); 
        putValue("score", score); 
    }
    @Override
    public void unserialize() {
        name=getString("name"); 
        id=getInt("id"); 
        score=getInt("score"); 
    }
}
