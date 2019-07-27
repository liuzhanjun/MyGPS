package com.hai.yun.bean;

import com.hai.yun.bean.utils.BinaryUtils;
import com.hai.yun.bean.utils.CRC16;
import com.hai.yun.bean.utils.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.hai.yun.bean.GpsSessionManager.*;
import static org.junit.Assert.*;

public class GpsSessionManagerTest {

    @Before
    public void setUp() throws Exception {
        SessionManager.getInstance();
    }


    /**
     * 登录信息测试
     */
    @Test
    public void getLoginInfoPkg() {
        byte[] loginInfoPkg = SessionManager.getLoginInfoPkg("123456789012345", 1);
        syso_bytes(loginInfoPkg);
    }

    private void syso_bytes(byte[] loginInfoPkg) {
        for (int i = 0; i < loginInfoPkg.length; i++) {
            System.out.print(BinaryUtils.byteToOx(BinaryUtils.getInt(loginInfoPkg[i])) + "|");
        }
    }

    /**
     * 解析平台响应登录信息测试
     */
    @Test
    public void analysisLoginInfoPkg() {
        System.out.println(BinaryUtils.byteToOx(217));
//        System.out.println(BinaryUtils.byteToOx(BinaryUtils.getInt(BinaryUtils.getByte(217))));

        System.out.println((BinaryUtils.getByte(220)));

        byte[] bytes = new byte[]{0x78, 0x78, 0x0B, 0x01, 0x00, 0x01,
                0x12, 0x01, 0x08, 0x09, 0x1e, 0x0a,
                BinaryUtils.getByte(0xd9),
                BinaryUtils.getByte(0xdc), 0x0d, 0x0a};

        byte[] bytes2 = new byte[]{0x78, 0x78, 0x05, 0x01, 0x00, 0x01,
//                0x12, 0x01, 0x08, 0x09, 0x1e, 0x0a,
                BinaryUtils.getByte(217),
                BinaryUtils.getByte(0xdc), 0x0d, 0x0a};
        AnalysisPkgInfo a = SessionManager.analysisCommonPkg(bytes);
        boolean ck = a.checkCheckBit();
        System.out.println(ck);

        System.out.println(a.getmPkgLength());
        if (a.getmTime() != null) {
            DateTime time = DateUtils.getTime(a.getmTime());
            System.out.println(time.toString("yyyy-MM-dd hh:mm:ss"));
        }

        byte[] bytes1 = {0x0B, 0x01, 0x00, 0x01,
                0x12, 0x01, 0x08, 0x09, 0x1e, 0x0a};
        byte[] crc16 = CRC16.getCRC16(bytes1);
        System.out.println(BinaryUtils.intTo0x(BinaryUtils.getInt(crc16), 2));
    }

    /**
     * GPS信息包测试
     * 测试内容
     * 时间：2018-1-16 11：50：30
     * 卫星长度12 卫星数 12
     * 纬度22°32.7658′
     * 经度22°32.7658′
     * 速度=255km/h
     * 方向332°北纬 东经 GPS已定位  实时GPS
     * 信息序列号位1
     */
    @Test
    public void getGPSInfoPkg() {
        GpsInfo gpsInfo = new GpsInfo();
        GpsStateDir statedir = new GpsStateDir(332, 1, 0, 1, 0);
        gpsInfo.addTime(new DateTime(2018, 1, 16, 11, 50, 30))
                .addLen(12)
                .addSatelliteNumber(12)
                .addPoint(new EarthPoint(22, 32.7658, 22, 32.7658))
                .addSpeed(255)
                .addGpsStateDir(statedir)
                .addListNo(1);
        byte[] gpsInfoPkg = SessionManager.getGPSInfoPkg(gpsInfo);
        syso_bytes(gpsInfoPkg);
    }

    /**
     * 测试LBS信息包
     * 测试内容
     * 时间：2018-1-16 11：50：30
     * mcc 中国移动460
     * mnc 中国移动0x00
     * lac 0xFFFE
     * cellId 0xFFFEFF
     * 信息序列号1
     */
    @Test
    public void getLBSPkg() {
        LbsInfo lbsinfo = new LbsInfo();
        lbsinfo.addTime(new DateTime(2018, 1, 16, 11, 50, 30))
                .addMcc(460)
                .addMnc(BinaryUtils.getInt((byte) 0x00))
                .addLac(BinaryUtils.getInt(new byte[]{(byte) 0xff, (byte) 0xfE}))
                .addCellId(BinaryUtils.getInt(new byte[]{(byte) 0xff, (byte) 0xfE, (byte) 0xff}))
                .addListNo(1);
        byte[] lbsPkg = SessionManager.getLBSPkg(lbsinfo);
        syso_bytes(lbsPkg);
    }

    /**
     * 测试GPSLBS合并信息包
     */
    @Test
    public void getGPSAndLBSpkg() {
        GpsInfo gpsInfo = new GpsInfo();
        GpsStateDir statedir = new GpsStateDir(332, 1, 0, 1, 0);
        gpsInfo.addTime(new DateTime(2018, 1, 16, 11, 50, 30))
                .addLen(12)
                .addSatelliteNumber(12)
                .addPoint(new EarthPoint(22, 32.7658, 22, 32.7658))
                .addSpeed(255)
                .addGpsStateDir(statedir);
        LbsInfo lbsinfo = new LbsInfo();
        lbsinfo.addTime(new DateTime(2018, 1, 16, 11, 50, 30))
                .addMcc(460)
                .addMnc(BinaryUtils.getInt((byte) 0x00))
                .addLac(BinaryUtils.getInt(new byte[]{(byte) 0xff, (byte) 0xfE}))
                .addCellId(BinaryUtils.getInt(new byte[]{(byte) 0xff, (byte) 0xfE, (byte) 0xff}))
                .addListNo(1);
        byte[] gpsAndLBSpkg = SessionManager.getGPSAndLBSpkg(1, gpsInfo, lbsinfo);
        syso_bytes(gpsAndLBSpkg);
    }
}