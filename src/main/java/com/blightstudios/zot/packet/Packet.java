package com.blightstudios.zot.packet;

import com.blightstudios.zot.ByteSequence;

/**
 * A packet which is transmitted between the watch and the server
 * Packets consist of a command, its length and data
 *
 * @author Moritz Scheve
 */
public abstract class Packet {
    /**
     * Getter of the command
     *
     * @return the command byte
     */
    protected abstract byte getCommand();

    /**
     * Getter of the action
     *
     * @return the action byte
     */
    protected abstract byte getAction();

    /**
     * Getter of the length of the whole packet
     *
     * @return the length of the packet
     */
    public int getLength() {
        return toByteArray().length;
    }

    /**
     * Getter of the length of the data contained in the packet
     *
     * @return the data length of the packet
     */
    protected abstract int getDataLength();

    /**
     * The packet may be scattered in multiple pieces if its bigger then the MTU
     *
     * @return the packet as byte array
     */
    public abstract byte[] toByteArray();

    /**
     * Converts a byte array into a packed
     *
     * @param rawPacket the raw bytes of the packet
     */
    protected abstract void fromByteArray(byte[] rawPacket);

    /**
     * Converts the packet to a more readable hexadecimal representation
     */
    public String toString() {
        String hexChars = "0123456789ABCDEF";
        byte[] bytes = this.toByteArray();

        final StringBuilder hex = new StringBuilder(2 * bytes.length);
        for (final byte b : bytes) {
            hex.append(hexChars.charAt((b & 0xF0) >> 4)).append(hexChars.charAt((b & 0x0F)));
        }
        String hexString = hex.toString().replaceAll("..", "$0:");
        return hexString.substring(0, hexString.length() - 1);
    }

    /**
     * Extracts the command from a raw packet
     *
     * @param rawPacket the raw bytes of the packet
     * @return the command byte
     */
    private static byte getCommand(byte[] rawPacket) {
        return rawPacket[1];
    }

    /**
     * Extracts the action from a raw packet
     *
     * @param rawPacket the raw bytes of the packet
     * @return the action byte
     */
    private static byte getAction(byte[] rawPacket) {
        return rawPacket[2];
    }

    /**
     * Extracts the data from a raw packet
     *
     * @param rawPacket the raw bytes of the packet
     * @return the data of the packet
     */
    protected static byte[] getData(byte[] rawPacket) {
        ByteSequence bSequence = new ByteSequence(rawPacket);

        int beginIndex = 5;
        int endIndex = bSequence.size() - 1;

        return bSequence.subSequence(beginIndex, endIndex).toByteArray();
    }
}
