package com.hai.yun.bean;

import com.hai.yun.bean.utils.GPSUtils;
import com.hai.yun.bean.utils.LBSUtils;

/**
 * 心跳包信息
 */
public class HeartBeatInfo {

    private int listNO;
    private TerminalInfo mTerminalInfo;//终端信息
    //电压等级 取值0-6  0低电关机 1，电量不足以打电话发短信 2，电量过低 3-6可以正常使用
    private int mVoltageGrade;
    private int mGSMinfoIntensity;//GSM信息强度 0-100
    //预留扩展位  终端当前语言 0x00 0x01 中文 0x00 0x02英文
    //902B超长待机设备这个值作为工作模式和设备休眠状态
    //工作模式：
    // 0x00 =正常模式
    // 0x01 =智能休眠
    //0x03  =深度休眠
    //0x04  =定时开关机
    //设备休眠状态
    //0x00=正常
    //0x01=休眠
    private TerminaExt mExt; //语言或者工作模式920B才选择这个属性


    public HeartBeatInfo addListNO(int listNO) {
        this.listNO = listNO;
        return this;
    }

    public HeartBeatInfo addmTerminalInfo(TerminalInfo mTerminalInfo) {
        this.mTerminalInfo = mTerminalInfo;
        return this;
    }

    public HeartBeatInfo addmVoltageGrade(int mVoltageGrade) {
        this.mVoltageGrade = mVoltageGrade;
        return this;
    }

    public HeartBeatInfo addmGSMinfoIntensity(int mGSMinfoIntensity) {
        this.mGSMinfoIntensity = mGSMinfoIntensity;
        return this;
    }

    public HeartBeatInfo addmExt(TerminaExt mExt) {
        this.mExt = mExt;
        return this;
    }

    public TerminalInfo getmTerminalInfo() {
        return mTerminalInfo;
    }

    public int getmVoltageGrade() {
        return mVoltageGrade;
    }

    public int getmGSMinfoIntensity() {
        return mGSMinfoIntensity;
    }

    public TerminaExt getmExt() {
        return mExt;
    }

    public int getListNO() {
        return listNO;
    }
}
