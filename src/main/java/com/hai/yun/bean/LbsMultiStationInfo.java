package com.hai.yun.bean;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class LbsMultiStationInfo {
    private int mListNo;//信息序列号
    private DateTime mTime;//时间
    private int mMcc;//移动用户所属国家代号
    public static final int mMcc_L = 2;//mMcc所占长度
    private int mMnc;//移动网号码
    public static final int mMnc_L = 1;//mMnc所占长度
    private int mLac;//位置区码
    public static final int mLac_L = 2;//mLac所占长度
    //临近小区基站编码
    //临近小区基站信号强度
    private Mci_Mciss[] mMci_Mciss;
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

    public LbsMultiStationInfo addmMci_Mciss(Mci_Mciss[] mMci_Mciss) {
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

    public Mci_Mciss[] getmMci_Mciss() {
        return mMci_Mciss;
    }

    public byte[] getExtContent() {
        return extContent;
    }

    public static class Mci_Mciss {
        private int mMci;//
        public static final int mMci_L = 2;
        private int mMciss;//
        public static final int mMciss_L = 1;

        public int getmMci() {
            return mMci;
        }

        public void setmMci(int mMci) {
            this.mMci = mMci;
        }



        public int getmMciss() {
            return mMciss;
        }

        public void setmMciss(int mMciss) {
            this.mMciss = mMciss;
        }

    }

}
