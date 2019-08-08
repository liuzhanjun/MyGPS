package com.hai.yun.bean.utils;

import org.junit.Before;
import org.junit.Test;

public class BinaryUtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getBytes() {

        byte[] bytes = BinaryUtils.getBytes(13, 1);
        int anInt = BinaryUtils.getInt(bytes);
        System.out.println(anInt);


    }

    @Test
    public void getInt() {
    }


    @Test
    public void getIMEI() {


//        String phone = "13800138000";
//        String str = BinaryUtils.string2Unicode(phone);
//        System.out.println(str);
//        System.out.println("==="+BinaryUtils.unicodeToString(str));
//
//        System.out.println(BinaryUtils.getoxBinary(str).length);

//        byte[] bytes = GPSUtils.setPhone("龙", 21);
//        for (byte aByte : bytes) {
//            System.out.print(BinaryUtils.byteToOx(BinaryUtils.getInt(aByte))+"|");
//        }

    }

    @Test
    public void stringToAscii() {
        String d = BinaryUtils.stringToAscii_ox("DYD=Success!123412");
        String d2 = BinaryUtils.string2Unicode("DYD=Success!123412");
        System.out.println("d1="+d);
        System.out.println("d2="+d2);

        byte[] bytes = BinaryUtils.getoxBinary(d);

        String s = BinaryUtils.bytesToString(bytes);
        System.out.println(s);
        


    }
}