package com.blightstudios.zot.packet.Setting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the TimeZone packet
 *
 * @author Moritz Scheve
 */
class TimeZoneTest {
    private byte[] rawPacket1 = new byte[]{0x6f, 0x18, 0x71, 0x03, 0x00, 0x00, 0x00, 0x00, -113};
    private byte[] rawPacket2 = new byte[]{0x6f, 0x18, 0x71, 0x0d, 0x00, 0x00, 0x00, 0x00, 0x53, 0x61, 0x63, 0x72, 0x61, 0x6d, 0x65, 0x6e, 0x74, 0x6f, -113};
    private byte[] rawPacket3 = new byte[]{0x6f, 0x18, 0x71, 0x0a, 0x00, 0x01, 0x0c, 0x00, 0x4c, -61, -68, 0x62, 0x65, 0x63, 0x6b, -113};
    private byte[] rawPacket4 = new byte[]{0x6f, 0x18, 0x71, 0x0c, 0x00, 0x00, 0x42, 0x42, 0x44, 0x65, 0x61, 0x74, 0x68, 0x73, 0x74, 0x61, 0x72, -113};

    @Test
    void getLength() {
        assertThrows(IllegalArgumentException.class, () -> new TimeZone(null, false, -12).getLength());
        assertThrows(IllegalArgumentException.class, () -> new TimeZone(null, true, 0, -15).getLength());

        assertEquals(9, new TimeZone().getLength());
        assertEquals(19, new TimeZone("Sacramento").getLength());
        assertEquals(16, new TimeZone("Lübeck", true, 12).getLength());
        assertEquals(18, new TimeZone("Deathstar", false, 66, 66).getLength());
    }

    @Test
    void getDataLength() {
        assertThrows(IllegalArgumentException.class, () -> new TimeZone(null, false, -12).getDataLength());
        assertThrows(IllegalArgumentException.class, () -> new TimeZone(null, true, -15).getDataLength());

        assertEquals(3, new TimeZone().getDataLength());
        assertEquals(13, new TimeZone("Sacramento").getDataLength());
        assertEquals(10, new TimeZone("Lübeck", true, 12).getDataLength());
        assertEquals(12, new TimeZone("Deathstar", false, 66, 66).getDataLength());
    }

    @Test
    void byteArrayConversion() {
        assertArrayEquals(rawPacket1, new TimeZone().toByteArray());
        assertArrayEquals(rawPacket2, new TimeZone("Sacramento").toByteArray());
        assertArrayEquals(rawPacket3, new TimeZone("Lübeck", true, 12).toByteArray());
        assertArrayEquals(rawPacket4, new TimeZone("Deathstar", false, 66, 66).toByteArray());

        assertArrayEquals(rawPacket1, new TimeZone(rawPacket1).toByteArray());
        assertArrayEquals(rawPacket2, new TimeZone(rawPacket2).toByteArray());
        assertArrayEquals(rawPacket3, new TimeZone(rawPacket3).toByteArray());
        assertArrayEquals(rawPacket4, new TimeZone(rawPacket4).toByteArray());
    }
}