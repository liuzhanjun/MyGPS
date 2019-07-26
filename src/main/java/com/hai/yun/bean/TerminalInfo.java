package com.hai.yun.bean;

/**
 * 终端信息
 */
public class TerminalInfo {
    //属性排列从低位到高位
    private byte mDefense;//0：撤防，1：设防
    private byte mAcc;//0:ACC低 1：Acc高
    private byte mAnElectric;// 0：未接外电 1：已接外电
    private byte mWarring;// 0：正常 1：震动报警 2：断电报警 3 ：低电报警 4：SOS报警
    private byte mGpsPostion;//0：GPS未定位 1：已定位
    private byte mOilElectricity;//0：油电接通 1：油电断开

    public TerminalInfo addmDefense(byte mDefense) {
        this.mDefense = mDefense;
        return this;
    }

    public TerminalInfo addmAcc(byte mAcc) {
        this.mAcc = mAcc;
        return this;
    }

    public TerminalInfo addmAnElectric(byte mAnElectric) {
        this.mAnElectric = mAnElectric;
        return this;
    }

    public TerminalInfo addmWarring(byte mWarring) {
        this.mWarring = mWarring;
        return this;
    }

    public TerminalInfo addmGpsPostion(byte mGpsPostion) {
        this.mGpsPostion = mGpsPostion;
        return this;
    }

    public TerminalInfo addmOilElectricity(byte mOilElectricity) {
        this.mOilElectricity = mOilElectricity;
        return this;
    }

    public byte getmDefense() {
        return mDefense;
    }

    public byte getmAcc() {
        return mAcc;
    }

    public byte getmAnElectric() {
        return mAnElectric;
    }

    public byte getmWarring() {
        return mWarring;
    }

    public byte getmGpsPostion() {
        return mGpsPostion;
    }

    public byte getmOilElectricity() {
        return mOilElectricity;
    }
}
