package com.hai.yun.bean.utils;

import java.util.ArrayList;
import java.util.Collections;

public class BinaryUtils {


    /**
     * 十进制转换成二进制
     *
     * @param ten
     * @return
     */
    public static String TenToTwo(int ten) {

        int n = ten / 2;
        int y = ten % 2;

        ArrayList<Integer> count = new ArrayList<Integer>();
        count.add(y);
        while (n > 1) {
            y = n % 2;
            n = n / 2;
            count.add(y);
        }
        if (n == 1) {
            count.add(n);
        }

        Collections.reverse(count);
        StringBuilder builder = new StringBuilder();
        for (Integer integer : count) {
            builder.append(integer);
        }
        return builder.toString();
    }


    /**
     * 二进制转换位十进制
     *
     * @param two 二进制符号
     * @return
     */
    public static int TwoToTen(int two) {
        ArrayList<Integer> count = new ArrayList<Integer>();
        int n = two / 10;
        int y = two % 10;
        count.add(y);
        while (n > 1) {
            y = n % 10;
            n /= 10;
            if (y > 1) {
                throw new RuntimeException("参数 two 应该是一个二进制数");

            }
            count.add(y);
        }

        if (y == 1) {
            count.add(y);
        }

        double nub = 0;
        for (int i = 0; i < count.size(); i++) {
            nub += count.get(i) * (Math.pow(2, i));
        }
        return (int) nub;
    }


    public static boolean isEven(int a) {
        if ((a & 1) == 1) {
            return false;
        }
        return true;
    }


    /**
     * 10进制转二进制字节数组
     * 将data 转为对应字节长度的数
     * 与getInt互转
     *
     * @param data
     * @param len
     * @return
     */
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


    /**
     * 获得一个整数的一个字节表示
     *
     * @param data
     * @return
     */
    public static byte getByte(int data) {
        return (byte) (data & 0xff);
    }


    /**
     * 将字符串转换为16进制2位的unicode字符串
     *
     * @param string
     * @return
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append(Integer.toHexString(c));

        }

        return unicode.toString();
    }

    /**
     * unicode转字符串
     *
     * @param unicode 转单个字符
     * @return
     */
    public static char unicodeTochar(String unicode) {

        int index = Integer.parseInt(unicode, 16);

        return (char) index;
    }

    /**
     * unicode转字符串
     *
     * @param unicode
     * @return
     */
    public static String unicodeToString(String unicode) {
        int len = unicode.length();
        int len2 = (len / 2) + len % 2;
        StringBuffer buffer = new StringBuffer();
        for (int i = len2 - 1; i >= 0; i--) {
            String substring = unicode.substring(len - 2 <= 0 ? 0 : len - 2, len);
            buffer.append(unicodeTochar(substring));
            len -= 2;
        }
        return buffer.reverse().toString();
    }

    /**
     * 将对应二进制字节长度的数转换为10进制
     * 字节长度是bytes数组的长度
     * 与getBytes互转
     *
     * @param bytes
     * @return
     */
    public static int getInt(byte[] bytes) {
        int count = 0;
        int len = bytes.length;

        for (int i = bytes.length - 1; i >= 0; i--) {
            count |= ((0xff << ((len - i - 1) * 8)) & bytes[i] << ((len - i - 1) * 8));
        }
        return count;
    }

    /**
     * 合并x,y
     *
     * @param x
     * @param y
     * @return
     */
    public static byte[] mergeBbytes(byte[] x, byte[] y) {
        byte[] bytes = new byte[x.length + y.length];
        int index = 0;
        for (int i = 0; i < x.length; i++) {
            bytes[index++] = x[i];
        }
        for (int i = 0; i < y.length; i++) {
            bytes[index++] = y[i];
        }
        return bytes;
    }


    /**
     * 合并x,y
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static byte[] mergeBbytes(byte[] x, byte[] y, byte[] z) {
        byte[] bytes = new byte[x.length + y.length];
        int index = 0;
        for (int i = 0; i < x.length; i++) {
            bytes[index++] = x[i];
        }
        for (int i = 0; i < y.length; i++) {
            bytes[index++] = y[i];
        }
        for (int i = 0; i < z.length; i++) {
            bytes[index++] = z[i];
        }
        return bytes;
    }

    /**
     * 获得一个字节的整数
     *
     * @param binary
     * @return
     */
    public static int getInt(byte binary) {
        return binary & 0xff;
    }


//    /**
//     * 10进制转16进制
//     *
//     * @param n
//     * @return
//     */
//    public static String intToHex(int n) {
//        //StringBuffer s = new StringBuffer();
//        StringBuilder sb = new StringBuilder(8);
//        String a;
//        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//        while (n != 0) {
//            sb = sb.append(b[(n & 0xff) % 16]);
//            n = n / 16;
//        }
//        a = sb.reverse().toString();
//        return a;
//    }


    /**
     * @param n 必须是一个字节的 或者根据getInt转过的int
     * @return
     */
    public static String byteToOx(int n) {
        StringBuilder sb = new StringBuilder(8);
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        sb.append(b[(n & 0xff) % 16]);
        n = (byte) (n / 16);
        sb.append(b[(n & 0xff) % 16]);
        return sb.reverse().toString();

    }

    /**
     * 将10进制数转为对应的16进制字符串
     *
     * @param n   十进制数
     * @param len 10进制数表示的位宽  比如这个十进制表示2位 则这个n的只最大只能位65535
     *            len的长度不能超过4位
     * @return
     */
    public static String intTo0x(int n, int len) {
        if (len > 4) {
            len = 4;
        }
        double max = Math.pow(2, (len * 8)) - 1;
        if (n > max) {
            throw new IndexOutOfBoundsException("超出表示长度");
        }

        StringBuffer buffer = new StringBuffer();
        for (int i = len - 1; i >= 0; i--) {
            buffer.append((byteToOx(((n >> (i * 8)) & 0xff))) + "|");
        }
        return buffer.toString();
    }

    public static byte[] getIMEI(String IMEI) {
        return getoxBinary(IMEI);
    }

    /**
     * 功能也是将16进制字符串转为二进制字节数组
     *
     * @param ox
     * @return
     */
    public static byte[] getoxBinary(String ox) {
        int len = ox.length();
        int[] IMEIS = new int[(len / 2) + len % 2];
        int len2 = IMEIS.length;
        //将IMEIS中的元素看作16进制转位10进制
        for (int i = len2 - 1; i >= 0; i--) {
            IMEIS[i] = Integer.parseInt(ox.substring(len - 2 <= 0 ? 0 : len - 2, len), 16);
            len -= 2;
        }
        byte[] result = new byte[len2];
        for (int i = 0; i < IMEIS.length; i++) {
            result[i] = getBytes(IMEIS[i], 1)[0];
        }


        return result;
    }

}
