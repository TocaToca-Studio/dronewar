package remine.map;

import java.util.HashMap;

/**
 *
 * @author tocatoca
 */
public class World {
    HashMap<Integer,HashMap<Integer,Chunk>> chunks = new HashMap<>();
    
    Chunk getChunk(int x,int y) {
        if(!chunks.containsKey(x)) {
            chunks. put(x, new HashMap<>());
        }
        HashMap<Integer,Chunk> mapX=chunks.get(x);
        if (!mapX.containsKey(y)) {
            mapX.put(y,new Chunk());
        }
        return mapX.get(y);        
    }
}
