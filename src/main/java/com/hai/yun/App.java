package com.hai.yun;

import com.hai.yun.bean.AgreeMentNos;
import com.hai.yun.bean.DataPkgInfo;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println(AgreeMentNos.IMSISendInfo);


//        byte[] b = getBytes(30000);
//        System.out.println(b[0] + "===" + b[1]);

        byte[] b2 = getBytes(114, 1);
        System.out.println(b2[0]);

        int a = getInt2getInt2(b2);
        System.out.println(a + "----");

    }

    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (data & 0xff);
        bytes[0] = (byte) ((data & 0xff << 8) >> 8);

        return bytes;
    }

    public static byte[] getBytes(int data, int len) {
        // 0   len-1
        //1   len-2
        // 2   len-3
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[len - i - 1] = (byte) ((data & (0xff << (i * 8))) >> (i * 8));
        }
        return bytes;
    }

    public static int getInt(byte[] bytes) {
        return (int) ((0xff & bytes[1]) | (0xff << 8 & (bytes[0] << 8)));
    }

    public static int getInt2getInt2(byte[] bytes) {
        int count = 0;
        int len = bytes.length;

        for (int i = bytes.length - 1; i >= 0; i--) {
            count |= ((0xff << ((len - i - 1) * 8)) & bytes[i] << ((len - i - 1) * 8));
        }
        return count;
    }



}
