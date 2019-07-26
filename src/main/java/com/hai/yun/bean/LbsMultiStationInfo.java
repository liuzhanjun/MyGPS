package com.hai.yun.bean;

import org.joda.time.DateTime;

public class LbsMultiStationInfo {
    private int mListNo;//信息序列号
    private DateTime mTime;//时间
    private int mMcc;//移动用户所属国家代号
    private int mMnc;//移动网号码
    private int mLac;//位置区码
    //临近小区基站编码
    //临近小区基站信号强度
    private int[][] mMci_Mciss = new int[7][2];
    //扩展位
    private byte[] extContent;

    public LbsMultiStationInfo addmListNo(int mListNo) {
        this.mListNo = mListNo;
        return this;
    }

    public LbsMultiStationInfo addmTime(DateTime mTime) {
        this.mTime = mTime;
        return this;
    }

    public LbsMultiStationInfo addmMcc(int mMcc) {
        this.mMcc = mMcc;
        return this;
    }

    public LbsMultiStationInfo addmMnc(int mMnc) {
        this.mMnc = mMnc;
        return this;
    }

    public LbsMultiStationInfo addmLac(int mLac) {
        this.mLac = mLac;
        return this;
    }

    public LbsMultiStationInfo addmMci_Mciss(int[][] mMci_Mciss) {
        this.mMci_Mciss = mMci_Mciss;
        return this;
    }

    public LbsMultiStationInfo addExtContent(byte[] extContent) {
        this.extContent = extContent;
        return this;
    }

    public int getmListNo() {
        return mListNo;
    }

    public DateTime getmTime() {
        return mTime;
    }

    public int getmMcc() {
        return mMcc;
    }

    public int getmMnc() {
        return mMnc;
    }

    public int getmLac() {
        return mLac;
    }

    public int[][] getmMci_Mciss() {
        return mMci_Mciss;
    }

    public byte[] getExtContent() {
        return extContent;
    }


    //    private int mMci;//移动基站
//    private int mMciss;//主小区信号强度
//    private int mMci_1;//临近小区基站编码
//    private int mMciss_1;//临近小区基站信号强度
//    private int mMci_2;//临近小区基站编码
//    private int mMciss_2;//临近小区基站信号强度
//    private int mMci_3;//临近小区基站编码
//    private int mMciss_3;//临近小区基站信号强度
//    private int mMci_4;//临近小区基站编码
//    private int mMciss_4;//临近小区基站信号强度
//    private int mMci_5;//临近小区基站编码
//    private int mMciss_5;//临近小区基站信号强度


}
