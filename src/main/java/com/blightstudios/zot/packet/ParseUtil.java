package com.blightstudios.zot.packet;

/**
 * Utility class to for easy parsing
 *
 * @author Moritz Scheve
 */
public class ParseUtil {
    /**
     * Convert a integer to n-long byte array
     *
     * @param value     The int to be converted
     * @param arraySize The wanted size of the byte array
     * @return The integer as byte array
     */
    public static byte[] intToBytes(int value, int arraySize) {
        if (arraySize < 1) throw new IllegalArgumentException();
        byte[] intAsBytes = new byte[arraySize];
        for (int i = 0; i < arraySize; i++) {
            intAsBytes[i] = (byte) (value >> i * 8 & 0xFF);
        }
        return intAsBytes;
    }

    /**
     * Convert a unsigned byte to a integer
     *
     * @param b The unsigned byte to be converted to a integer
     * @return The unsigned byte as integer
     */
    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    /**
     * Convert a byte array to a integer
     *
     * @param bytes The bytes to be converted to a integer
     * @return The byte array as integer
     */
    public static int bytesToInt(byte[] bytes) {
        if (bytes.length < 1) throw new IllegalArgumentException();

        int bytesAsInt = 0;
        for (int i = 0; i < bytes.length; i++) {
            bytesAsInt = bytesAsInt | ((bytes[i] & 0xFF) << i * 8);
        }
        return bytesAsInt;
    }

    /**
     * Converts a byte to a boolean
     *
     * @param b The byte to be converted
     *          0 = False
     *          1 = True
     * @return The boolean based on the byte
     */
    public static boolean byteToBool(byte b) {
        return b == 1;
    }
}
