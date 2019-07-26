package com.hai.yun.bean.utils;

import com.hai.yun.bean.HeartBeatInfo;
import com.hai.yun.bean.TerminaExt;
import com.hai.yun.bean.TerminalInfo;

/**
 * 心跳包工具
 */
public class HeartBeatUtils {


    /**
     * 处理终端信息
     *
     * @param info
     * @return
     */
    private static byte getTerminalInfo(TerminalInfo info) {
        //从最低位到最高位处理
        //第0位
        byte defense = (byte) (info.getmDefense() & 0x01);
        //第1位
        byte acc = (byte) ((info.getmAcc() & 0x01) << 1);
        //2
        byte anElectric = (byte) ((info.getmAnElectric() & 0x01) << 2);
        //3-5
        byte warring = (byte) ((info.getmWarring() & 0x07) << 3);
        //6
        byte gpsPostion = (byte) ((info.getmGpsPostion() & 0x01) << 6);
        //7
        byte oilElectricity = (byte) ((info.getmOilElectricity() & 0x01) << 7);
        return (byte) (defense | acc | anElectric | warring | gpsPostion | oilElectricity);
    }

    /**
     * voltageGrade 处理电压等级 0-6
     *
     * @param voltageGrade
     * @return
     */
    private static byte getVoltageGrade(int voltageGrade) {
        return BinaryUtils.getByte(voltageGrade);
    }


    /**
     * 处理GMS信号强度 0-100
     *
     * @param GSMinfoIntensity
     * @return
     */
    private static byte getGSMinfoIntensity(int GSMinfoIntensity) {
        return BinaryUtils.getByte(GSMinfoIntensity);
    }

    /**
     * 部分终端设置当前语言 0x00 0x01 中文 0x00 0x02英文
     * 部分终端 语言的第一位用作设备外接电压
     * //902B超长待机设备这个值作为工作模式和设备休眠状态
     * //工作模式：
     * // 0x00 =正常模式
     * // 0x01 =智能休眠
     * //0x03  =深度休眠
     * //0x04  =定时开关机
     * //设备休眠状态
     * <p>
     * 获得扩展位信息
     *
     * @param terminaExt
     * @return
     */
    private static byte[] getTerminalExt(TerminaExt terminaExt) {
        byte n01 = BinaryUtils.getByte(terminaExt.getmN01());
        byte n02 = BinaryUtils.getByte(terminaExt.getmNo2());
        byte[] bytes = new byte[2];
        bytes[0] = n01;
        bytes[1] = n02;
        return bytes;
    }


    /**
     * 设置心跳包信息
     *
     * @param info
     * @return
     */
    public static byte[] setHearteatContent(HeartBeatInfo info) {
        byte[] bytes = new byte[5];
        //终端信息
        byte terminalInfo = getTerminalInfo(info.getmTerminalInfo());
        //电压等级
        byte voltageGrade = getVoltageGrade(info.getmVoltageGrade());
        //GSM信息强度
        byte gsMinfoIntensity = getGSMinfoIntensity(info.getmGSMinfoIntensity());
        //扩展信息
        byte[] terminalExt = getTerminalExt(info.getmExt());
        bytes[0] = terminalInfo;
        bytes[1] = voltageGrade;
        bytes[2] = gsMinfoIntensity;
        bytes[3] = terminalExt[0];
        bytes[4] = terminalExt[1];
        return bytes;
    }
}
