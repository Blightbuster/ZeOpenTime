package com.blightstudios.zot;

/**
 * Helper class for additional math functionality
 *
 * @author Moritz Scheve
 */
public class MathHelper {

    /**
     * Clamp a value between min and max (inclusive)
     * from: https://stackoverflow.com/questions/16656651/does-java-have-a-clamp-function
     *
     * @param value The value to be clammed
     * @param min   The minimum possible value
     * @param max   The maximum possible value
     * @return The clamped value
     */
    public static <T extends Comparable<T>> T clamp(T val, T min, T max) {
        if (val.compareTo(min) < 0) return min;
        else if (val.compareTo(max) > 0) return max;
        else return val;
    }
}
