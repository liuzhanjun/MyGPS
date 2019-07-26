package com.hai.yun.bean.utils;

import org.junit.Before;
import org.junit.Test;

public class BinaryUtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getBytes() {

        int a = Integer.parseInt("89", 16);
        System.out.println(a);

        byte[] bs = BinaryUtils.getBytes(a, 1);

        System.out.println("0=" + bs[0]);

        int s = BinaryUtils.getInt(bs);
        System.out.println(s);


    }

    @Test
    public void getInt() {
    }


    @Test
    public void getIMEI() {


        String phone = "13800138000";
        String str = BinaryUtils.string2Unicode(phone);
        System.out.println(str);
        System.out.println("==="+BinaryUtils.unicodeToString(str));

        System.out.println(BinaryUtils.getoxBinary(str).length);


    }
}