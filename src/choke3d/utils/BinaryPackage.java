package choke3d.utils;

import choke3d.math.Quat;
import choke3d.math.Vec3f;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author tocatoca
 */
public class BinaryPackage { 
    public BinaryFormat format = new BinaryFormat();
    public BinaryPackage(BinaryFormat format) {
        this.format=format;
    } 
    public BinaryPackage() {
        
    }
    public void putValue(String field,Object value) {
        values.put(field,value);
    }
    public void putField(String key,String type) {
        format.put(key, type);
    }
    public Object getValue(String field) {
        return values.get(field);
    }
    
    public int getInt(String field) {
        return (Integer) getValue(field);
    }

    public boolean getBool(String field) {
        return (Boolean) getValue(field);
    }

    public float getFloat(String field) {
        return (Float) getValue(field);
    }

    public double getDouble(String field) {
        return (Double) getValue(field);
    }

    public String getString(String field) {
        return (String) getValue(field);
    } 
    public byte[] getBytes(String field) {
        return (byte[]) getValue(field);
    }  
    public Vec3f getVec3f(String field) {
        return (Vec3f) getValue(field);
    }
    public Quat getQuat(String field) {
        return (Quat) getValue(field);
    }
    // FIELD NAME, FIELD VALUE in order
    public LinkedHashMap<String, Object> values = new LinkedHashMap<>();
    public void put(String key,Object value) {
        values.put(key,value);
    }
    
    public int calculateSize() {
        // Calculate buffer size
        int bufferSize = 0;
        for (Map.Entry<String, String> entry : format.fields.entrySet()) {
            switch (entry.getValue()) {
                case "int":
                    bufferSize += Integer.BYTES;
                    break;
                case "float":
                    bufferSize += Float.BYTES;
                    break;
                case "double":
                    bufferSize += Double.BYTES;
                    break;
                case "byte":
                    bufferSize += Byte.BYTES;
                    break;
                case "short":
                    bufferSize += Short.BYTES;
                    break;
                case "long":
                    bufferSize += Long.BYTES;
                    break;
                case "string":
                    String str = (String) values.get(entry.getKey());
                    
                    bufferSize += Integer.BYTES; // Size of the length field
                    bufferSize += str.getBytes(StandardCharsets.UTF_8).length; // Size of string bytes
                    break;
                case "binary": 
                    bufferSize += Integer.BYTES; // Size of the length field
                    bufferSize += ((byte[]) values.get(entry.getKey())).length; // Size of string bytes
                    break;
                case "vec3f":
                    bufferSize += Float.SIZE*3;
                    break;
                case "quat":
                    bufferSize += Float.SIZE*4;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported field type: " + entry.getValue());
            }
        }
        return bufferSize;
    }
    public ByteBuffer encode() {
        if (format == null) {
            throw new IllegalStateException("Format is not defined for this package.");
        } 
        serialize();
        // Allocate buffer
        ByteBuffer buff = ByteBuffer.allocate(calculateSize());

        // Encode values
        for (Map.Entry<String, String> entry : format.fields.entrySet()) {
            String fieldName = entry.getKey();
            String fieldType = entry.getValue();
            Object value = values.get(fieldName);

            if (value == null) {
                throw new IllegalArgumentException("Missing value for field: " + fieldName);
            }

            // Write based on type
            switch (fieldType) {
                case "int":
                    buff.putInt((Integer) value);
                    break;
                case "float":
                    buff.putFloat((Float) value);
                    break;
                case "double":
                    buff.putDouble((Double) value);
                    break;
                case "byte":
                    buff.put((Byte) value);
                case "boolean":
                    buff.put((byte)(((boolean) value) ? 1 : 0));
                    break;
                case "short":
                    buff.putShort((Short) value);
                    break;
                case "long":
                    buff.putLong((Long) value);
                    break;
                case "string":
                    String str = (String) value;
                    byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
                    buff.putInt(strBytes.length); // Write string length
                    buff.put(strBytes); // Write string bytes
                    break;
                case "binary":
                    byte[] binData = (byte[]) values.get(entry.getKey());
                    buff.putInt(binData.length); // Write string length
                    buff.put(binData); // Write string bytes
                    break;
                case "vec3f":
                    Vec3f vec=(Vec3f) value;
                    buff.putFloat(vec.x);
                    buff.putFloat(vec.y);
                    buff.putFloat(vec.z);
                    break;
                case "quat":
                    Quat q=(Quat) value;
                    buff.putFloat(q.w);
                    buff.putFloat(q.x);
                    buff.putFloat(q.y);
                    buff.putFloat(q.z);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported field type: " + fieldType);
            }
        }
        
        buff.flip(); // Prepare buffer for reading
        return buff;
    }
    public ByteBuffer pack() {
        return encode();
    }
    public byte[] packBytes() {
        ByteBuffer buffer=pack();
        byte[] result = new byte[buffer.limit()];
        System.arraycopy(buffer.array(), 0, result, 0, buffer.limit());
        return result;
    }
    public void serialize() {
        
    }
    public void unserialize() {
        
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BinaryPackage [\n");
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            builder.append("  ")
                   .append(entry.getKey())
                   .append(": ")
                   .append(entry.getValue())
                   .append("\n");
        }
        builder.append("]");
        return builder.toString();
    }
    public void decode(ByteBuffer buff) { 

        for (Map.Entry<String, String> entry : format.fields.entrySet()) {
            String fieldName = entry.getKey();
            String fieldType = entry.getValue();

            // Decode based on field type
            switch (fieldType) {
                case "int":
                    values.put(fieldName, buff.getInt());
                    break;
                case "float":
                    values.put(fieldName, buff.getFloat());
                    break;
                case "boolean":
                    values.put(fieldName, buff.get()==1);
                    break;
                case "double":
                    values.put(fieldName, buff.getDouble());
                    break;
                case "byte":
                    values.put(fieldName, buff.get());
                    break;
                case "short":
                    values.put(fieldName, buff.getShort());
                    break;
                case "long":
                    values.put(fieldName, buff.getLong());
                    break;
                case "string":
                    int length = buff.getInt(); // Get string length
                    byte[] strBytes = new byte[length];
                    buff.get(strBytes); // Read string bytes
                    values.put(fieldName, new String(strBytes, StandardCharsets.UTF_8));
                    break;
                case "binary":
                        int binLength = buff.getInt(); // Get binary length
                        byte[] binData = new byte[binLength];
                        buff.get(binData); // Read binary bytes
                        values.put(fieldName, binData);
                        break;
                case "vec3f":
                        values.put(fieldName, 
                                new Vec3f(buff.getFloat(),buff.getFloat(),buff.getFloat())
                        );
                        break;
                case "quat":
                        values.put(fieldName, 
                                new Quat(buff.getFloat(),buff.getFloat(),buff.getFloat(),buff.getFloat())
                        );
                        break;
                default:
                    throw new IllegalArgumentException("Unsupported field type: " + fieldType);
            }
        }
        unserialize();
    }
    
    public void unpack(ByteBuffer buff) {
        decode(buff);
    }
    
}
