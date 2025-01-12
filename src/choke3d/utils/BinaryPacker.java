package choke3d.utils;

import java.nio.ByteBuffer; 

/**
 * Abstract class for binary packing and unpacking.
 * Provides utilities for encoding and decoding binary data using defined formats.
 */
public  class BinaryPacker {
 

    public static void main(String[] args) {
        BinaryFormat format = new BinaryFormat();
        format.fields.put("age", "int");
        format.fields.put("name", "string");
        format.fields.put("salary", "float");

        // Create a package and set values
        BinaryPackage pack = new BinaryPackage(format); 
        pack.values.put("age", 25);
        pack.values.put("name", "John Doe");
        pack.values.put("salary", 5000.75f);

        // Encode the package
        ByteBuffer buffer = pack.encode();

        // Decode the package
        BinaryPackage decodedPack = new BinaryPackage();
        decodedPack.decode(buffer);

        // Print decoded values
        System.out.println("Age: " + decodedPack.values.get("age"));
        System.out.println("Name: " + decodedPack.values.get("name"));
        System.out.println("Salary: " + decodedPack.values.get("salary"));
    }
}
