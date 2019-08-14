package com.hai.yun.bean.utils;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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


    @Test
    public void testBufferIo(){
        byte[] bytes = new byte[]{0x78, 0x78, 0x0B, 0x01, 0x00, 0x01,
                0x12, 0x01, 0x08, 0x09, 0x1e, 0x0a,
                BinaryUtils.getByte(0xd9),
                BinaryUtils.getByte(0xdc), 0x0d, 0x0a};
        IoBuffer buffer=IoBuffer.wrap(bytes);

        ArrayList<Byte> listbyte=new ArrayList<>();
       while (buffer.position()<buffer.capacity()){
           listbyte.add(buffer.get());
           System.out.println("postion:" + buffer.position() + "  limit:" + buffer.limit() + "  capacity:" + buffer.capacity());

       }
        buffer.flip();
        System.out.println("postion:" + buffer.position() + "  limit:" + buffer.limit() + "  capacity:" + buffer.capacity());

        for (Byte aByte : listbyte) {
            System.out.print("|"+BinaryUtils.byteToOx(BinaryUtils.getInt(aByte)));
        }




    }
}