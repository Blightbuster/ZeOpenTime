package com.blightstudios.zot.packet.Setting;

import com.blightstudios.zot.ByteSequence;
import com.blightstudios.zot.packet.PConstant;
import com.blightstudios.zot.packet.Packet;
import com.blightstudios.zot.packet.ParseUtil;

/**
 * A packet to update the current time zone
 *
 * @author Moritz Scheve
 */
public class TimeZone extends Packet {
    //region Packet specific parameters

    /**
     * The time zone name
     */
    private String name;

    /**
     * If the offset should be added or subtracted from utc time
     */
    private boolean aheadUTC;

    /**
     * The hour offset for the time zone based on utc
     */
    private int hourOffset;

    /**
     * The minute offset for the time zone based on utc
     */
    private int minuteOffset;

    //endregion

    //region Getters & Setters for packet specific parameters

    /**
     * Getter of the name
     *
     * @return The name of the time zone
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name
     *
     * @param name The new name of the time zone
     */
    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name can't be 'null'.");
        this.name = name;
    }

    /**
     * Whether the offset should be added or subtracted from utc time
     *
     * @return If the offset should be added or subtracted from utc time
     */
    public boolean isAheadUTC() {
        return aheadUTC;
    }

    /**
     * Setter whether the offset should be added or subtracted from utc time
     *
     * @param aheadUTC If the offset should be added or subtracted from utc time
     */
    public void setAheadUTC(boolean aheadUTC) {
        this.aheadUTC = aheadUTC;
    }

    /**
     * Getter of the hour offset
     *
     * @return The hour offset compared to utc
     */
    public int getHourOffset() {
        return hourOffset;
    }

    /**
     * Setter of the hour offset
     *
     * @param hourOffset The new hour offset compared to utc
     *                   Must be equals or greater than zero
     */
    public void setHourOffset(int hourOffset) {
        if (hourOffset < 0)
            throw new IllegalArgumentException("Hour offset can't be below 0. Set aheadUTC false instead.");
        this.hourOffset = hourOffset;
    }

    /**
     * Getter of the minute offset
     *
     * @return The minute offset compared to utc
     */
    public int getMinuteOffset() {
        return minuteOffset;
    }

    /**
     * Setter of zone minute offset
     *
     * @param minuteOffset The new minute offset compared to utc
     *                     must be equals or greater than zero
     */
    public void setMinuteOffset(int minuteOffset) {
        if (minuteOffset < 0)
            throw new IllegalArgumentException("Minute offset can't be below 0. Set aheadUTC false instead.");
        this.minuteOffset = minuteOffset;
    }

    //endregion

    //region Packet constructors

    /**
     * Constructor of TimeZone packet
     */
    public TimeZone() {
        this("");
    }

    /**
     * Constructor of TimeZone packet
     *
     * @param rawPacket the packet as byte array
     */
    public TimeZone(byte[] rawPacket) {
        fromByteArray(rawPacket);
    }

    /**
     * Constructor of TimeZone packet
     *
     * @param name The new time zone
     */
    public TimeZone(String name) {
        this(name, false, 0, 0);
    }

    /**
     * Constructor of TimeZone packet
     *
     * @param name       The new time zone
     * @param aheadUTC   If the offset should add or subtract from utc
     * @param hourOffset The hour difference to utc
     *                   Must be equals or greater than zero
     */
    public TimeZone(String name, boolean aheadUTC, int hourOffset) {
        this(name, aheadUTC, hourOffset, 0);
    }

    /**
     * Constructor of TimeZone packet
     *
     * @param name         The new time zone
     * @param aheadUTC     If the offset should add or subtract from utc
     * @param hourOffset   The hour difference to utc
     *                     It must be equals or greater than zero
     * @param minuteOffset The minute difference to utc
     *                     Must be equals or greater than zero
     */
    public TimeZone(String name, boolean aheadUTC, int hourOffset, int minuteOffset) {
        setName(name);
        setAheadUTC(aheadUTC);
        setHourOffset(hourOffset);
        setMinuteOffset(minuteOffset);
    }

    //endregion

    //region Implementing the Packet

    @Override
    protected byte getCommand() {
        return PConstant.COMMAND_CODE_TIME_ZONE;
    }

    @Override
    protected byte getAction() {
        return PConstant.ACTION_SET;
    }

    @Override
    protected int getDataLength() {
        /*
         * Set initial data length to 3 for the following reasons:
         * 1b ahead utc
         * 1b hour offset
         * 1b minute offset
         */
        int dataLength = 3;

        dataLength += name.getBytes().length;
        return dataLength;
    }

    @Override
    public byte[] toByteArray() {
        /*
         * Packet structure:
         * 1b packet start flag
         * 1b command
         * 1b action
         * 2b data length
         * 1b ahead utc
         * 1b hour offset
         * 1b minute offset
         * nb time zone name
         * 1b packet end flag
         */

        ByteSequence byteSeq = new ByteSequence();
        byteSeq.add(PConstant.FLAG_START);
        byteSeq.add(getCommand());
        byteSeq.add(getAction());
        byteSeq.addAll(ParseUtil.intToBytes(getDataLength(), 2));
        byteSeq.add(aheadUTC);
        byteSeq.add(hourOffset);
        byteSeq.add(minuteOffset);
        byteSeq.addAll(name.getBytes());
        byteSeq.add(PConstant.FLAG_END);
        return byteSeq.toByteArray();
    }

    @Override
    protected void fromByteArray(byte[] bytes) {
        ByteSequence data = new ByteSequence(Packet.getData(bytes));

        /*
         * Data structure:
         * 1b ahead utc
         * 1b hour offset
         * 1b minute offset
         * nb time zone name
         */
        setAheadUTC(ParseUtil.byteToBool(data.get(0)));
        setHourOffset(ParseUtil.byteToInt(data.get(1)));
        setMinuteOffset(ParseUtil.byteToInt(data.get(2)));
        ByteSequence nameBytes = data.subSequence(3, data.size());
        setName(new String(nameBytes.toByteArray()));
    }

    //endregion
}
