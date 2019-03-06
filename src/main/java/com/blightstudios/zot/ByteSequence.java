package com.blightstudios.zot;

import java.util.LinkedList;

/**
 * Extension of Byte ArrayList to provide additional functionality
 *
 * @author Moritz Scheve
 */
public class ByteSequence extends LinkedList<Byte> {
    private static final long serialVersionUID = 1L;

    /**
     * Empty constructor
     */
    public ByteSequence() {
    }

    /**
     * Constructor with byte as parameter for initial byte
     *
     * @param initByte The initial byte
     */
    public ByteSequence(byte initByte) {
        add(initByte);
    }

    /**
     * Constructor with byte array as parameter for initial byte
     *
     * @param initBytes The initial byte array
     */
    public ByteSequence(byte[] initBytes) {
        addAll(initBytes);
    }

    /**
     * Add a integer to the ByteSequence
     *
     * @param newByte The byte to be added
     */
    public void add(int newByte) {
        /*
         * Cast newByte to byte and then to Byte to be able to call add() of
         * ArrayList and not recursive from ByteSequence
         */
        add((Byte) ((byte) newByte));
    }

    /**
     * Add a boolean to the ByteSequence
     *
     * @param newByte The boolean to be added
     */
    public void add(boolean newByte) {
        add((byte) (newByte ? 1 : 0));
    }

    /**
     * Add a byte array to the ArrayList
     *
     * @param newBytes The byte array to be added
     */
    public void addAll(byte[] newBytes) {
        for (byte newByte : newBytes) {
            add(newByte);
        }
    }

    /**
     * Convert ByteSequence
     *
     * @return The ByteSequence as byte array
     */
    public byte[] toByteArray() {
        byte[] bytes = new byte[this.size()];
        for (int i = 0; i < this.size(); i++) {
            bytes[i] = this.get(i);
        }
        return bytes;
    }

    /**
     * Converts the ByteSequence to a more readable hexadecimal representation
     */
    public String toString() {
        String hexChars = "0123456789ABCDEF";

        final StringBuilder hex = new StringBuilder(2 * this.size());
        for (final byte b : this) {
            hex.append(hexChars.charAt((b & 0xF0) >> 4)).append(hexChars.charAt((b & 0x0F)));
        }
        String hexString = hex.toString().replaceAll("..", "$0:");
        return hexString.substring(0, hexString.length() - 1);
    }

    /**
     * Creates a new ByteSequence which is derived
     *
     * @param beginIndex The begin index (inclusive)
     * @param endIntex   The end index (exclusive)
     * @return A sub-sequence of the current ByteSequence
     */
    public ByteSequence subSequence(int beginIndex, int endIntex) {
        ByteSequence bSequence = new ByteSequence();
        for (int i = beginIndex; i < endIntex; i++) {
            bSequence.add(this.get(i));
        }
        return bSequence;
    }
}
