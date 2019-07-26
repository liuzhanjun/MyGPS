package com.hai.yun.bean.utils;


import org.junit.Test;

import static org.junit.Assert.*;

public class HeartBeatUtilsTest {

    @Test
    public void getTerminalInfo() {
//        byte s=SatelliteSnorUtils.getSatelLiteNum(18);
//        System.out.println(s);


        byte[] bytes = BinaryUtils.getBytes(1, 3);
        String s = BinaryUtils.intTo0x(BinaryUtils.getInt(bytes), 3);
        System.out.println(s);

        System.out.println(BinaryUtils.intTo0x(BinaryUtils.getInt(BinaryUtils.getByte(0|0x30)),1));

        

    }
}