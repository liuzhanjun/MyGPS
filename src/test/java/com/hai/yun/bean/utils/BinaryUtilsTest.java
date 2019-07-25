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
        byte[] s = BinaryUtils.getIMEI("123456789012345");
        for (byte b : s) {
            System.out.print(b + "|");

        }

    }
}