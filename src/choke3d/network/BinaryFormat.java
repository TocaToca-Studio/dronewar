package choke3d.network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author tocatoca
 */
 public class BinaryFormat {
    // K, V -> FIELD NAME, FIELD TYPE in order
    public LinkedHashMap<String, String> fields = new LinkedHashMap<>();
    public void put(String key,String value) {
        fields.put(key,value);
    }
    
}
