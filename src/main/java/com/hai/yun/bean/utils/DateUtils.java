package com.hai.yun.bean.utils;

import org.joda.time.DateTime;

public class DateUtils {


    /**
     * 获得时间的字节 6位
     *
     * @return
     */
    public static byte[] getTime(DateTime time) {

        byte[] bytes = new byte[6];
        bytes[0] = BinaryUtils.getByte(((time.year().get()) % 1000));
        bytes[1] = BinaryUtils.getByte(time.monthOfYear().get());
        bytes[2] = BinaryUtils.getByte(time.dayOfMonth().get());
        bytes[3] = BinaryUtils.getByte(time.hourOfDay().get());
        bytes[4] = BinaryUtils.getByte(time.getMinuteOfHour());
        bytes[5] = BinaryUtils.getByte(time.getSecondOfMinute());
        return bytes;

    }


    /**
     * 获得时间
     *
     * @param times
     * @return
     */
    public static DateTime getTime(byte[] times) {
        int year = 2000 + BinaryUtils.getInt(times[0]);
        int month = BinaryUtils.getInt(times[1]);
        int day = BinaryUtils.getInt(times[2]);
        int hours = BinaryUtils.getInt(times[3]);
        int minute = BinaryUtils.getInt(times[4]);
        int seconde = BinaryUtils.getInt(times[5]);
        DateTime dateTime = new DateTime(year, month, day, hours, minute, seconde);

        return dateTime;
    }
}
