package com.blightstudios.zot.packet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the ParseUtil class
 *
 * @author Moritz Scheve
 */
class ParseUtilTest {

    @Test
    void intToBytes() {
        assertThrows(IllegalArgumentException.class, () -> ParseUtil.intToBytes(5, 0));

        assertArrayEquals(new byte[]{-12, 0}, ParseUtil.intToBytes(244, 2));
        assertArrayEquals(new byte[]{-46, 4, 0}, ParseUtil.intToBytes(1234, 3));
    }

    @Test
    void bytesToInt() {
        assertEquals(244, ParseUtil.bytesToInt(new byte[]{-12, 0}));
        assertEquals(1234, ParseUtil.bytesToInt(new byte[]{-46, 4, 0}));
    }
}