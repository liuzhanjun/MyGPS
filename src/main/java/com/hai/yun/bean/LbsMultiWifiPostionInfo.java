package com.hai.yun.bean;

import org.joda.time.DateTime;

public class LbsMultiWifiPostionInfo {
    private int mListNo;//信息序列号
    private DateTime mTime;//时间
    private int mMcc;//移动用户所属国家代号
    public static final int mMcc_L = 2;//移动用户所属国家代号所占长度
    private int mMnc;//移动网号码
    public static final int mMnc_L = 1;//mMnc所占长度
    private Lac_Ci_Rssi[] mLac_ci_rssis;//固定7个

    private int mTime_lead;//时间提前量
    public static final int time_lead_L = 1;//时间提前量所占长度
    private int mWifiNumber;//wifi数量
    public static final int mWifiNumber_L = 1;

    private WiFiInfo[] mWifiinfo;//最多为8个

    public int getmListNo() {
        return mListNo;
    }

    public void setmListNo(int mListNo) {
        this.mListNo = mListNo;
    }

    public DateTime getmTime() {
        return mTime;
    }

    public void setmTime(DateTime mTime) {
        this.mTime = mTime;
    }

    public int getmMcc() {
        return mMcc;
    }

    public void setmMcc(int mMcc) {
        this.mMcc = mMcc;
    }

    public int getmMnc() {
        return mMnc;
    }

    public void setmMnc(int mMnc) {
        this.mMnc = mMnc;
    }

    public Lac_Ci_Rssi[] getmLac_ci_rssis() {
        return mLac_ci_rssis;
    }

    public void setmLac_ci_rssis(Lac_Ci_Rssi[] mLac_ci_rssis) {
        this.mLac_ci_rssis = mLac_ci_rssis;
    }

    public int getmTime_lead() {
        return mTime_lead;
    }

    public void setmTime_lead(int mTime_lead) {
        this.mTime_lead = mTime_lead;
    }

    public int getmWifiNumber() {
        return mWifiNumber;
    }

    public void setmWifiNumber(int mWifiNumber) {
        this.mWifiNumber = mWifiNumber;
    }

    public WiFiInfo[] getmWifiinfo() {
        return mWifiinfo;
    }

    public void setmWifiinfo(WiFiInfo[] mWifiinfo) {
        this.mWifiinfo = mWifiinfo;
    }

    public static class WiFiInfo {
        private byte[] mWifiMac;//wifiMac地址
        public static final int mWifiMac_L = 6;
        private int mWifiInten;//WiFi强度
        public static final int mWifiInten_L = 1;

        public byte[] getmWifiMac() {
            return mWifiMac;
        }

        public void setmWifiMac(byte[] mWifiMac) {
            this.mWifiMac = mWifiMac;
        }

        public int getmWifiInten() {
            return mWifiInten;
        }

        public void setmWifiInten(int mWifiInten) {
            this.mWifiInten = mWifiInten;
        }
    }

    public static class Lac_Ci_Rssi {
        private int mLac;
        public static final int mLac_L = 2;
        private int mCi;
        public static final int mCi_L = 3;
        private int mRssi;
        public static final int mRssi_L = 1;

        public int getmLac() {
            return mLac;
        }

        public void setmLac(int mLac) {
            this.mLac = mLac;
        }

        public int getmCi() {
            return mCi;
        }

        public void setmCi(int mCi) {
            this.mCi = mCi;
        }

        public int getmRssi() {
            return mRssi;
        }

        public void setmRssi(int mRssi) {
            this.mRssi = mRssi;
        }
    }

}
