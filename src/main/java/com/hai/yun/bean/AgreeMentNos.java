package com.hai.yun.bean;

import com.hai.yun.bean.utils.BinaryUtils;

public class AgreeMentNos {
    //登录包
    public static final byte loginNO = 0x01;
    //GPS信息
    public static final byte gpsInfo = 0x10;
    //LBS信息
    public static final byte LBSInfo = 0x11;
    //GPS LBS合并信息
    public static final byte GPSAndLBSInfo = 0x12;
    //状态信息（心跳包）
    public static final byte heartbeat = 0x13;
    //卫星信噪比信息
    public static final byte SSNIRInfo = 0x14;
    //字符串信息
    public static final byte StringInfo = 0x15;
    //报警信息
    public static final byte WarningInfo = 0x16;
    //查询地址信息LBS
    public static final byte queryAddressLBS = 0x17;
    //查询地址信息GPS
    public static final byte queryAddressGPS = 0x1A;
    //LBS多基站定位信息
    public static final byte LBSMultipleBaseStations = 0x18;
    //LBS多基站+wifi定位信息
    public static final byte LBSWIFIMultipBaseStations = 0x2C;
    //IMSI号上报平台信息 //可能有问题0x90
    public static final byte IMSISendInfo = BinaryUtils.getBytes(144, 1)[0];
    //ICCID号上报平台信息0x94
    public static final byte ICCIDSendInfo = BinaryUtils.getBytes(148, 1)[0];
    //录音文件上报平台信息0x8D
    public static final byte recordFileSend = BinaryUtils.getBytes(141, 1)[0];
    //服务器向终端发送信息0x80
    public static final byte serviceSendToClient = BinaryUtils.getBytes(128, 1)[0];
}
