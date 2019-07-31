package com.hai.yun.bean;

public class IccIdInfo {
    private String mIMEI;
    private String mIMSI;
    private String mIccid;

    public String getmIMEI() {
        return mIMEI;
    }

    public IccIdInfo addmIMEI(String mIMEI) {
        this.mIMEI = mIMEI;
        return this;
    }

    public String getmIMSI() {
        return mIMSI;
    }

    public IccIdInfo addmIMSI(String mIMSI) {
        this.mIMSI = mIMSI;
        return this;
    }

    public String getmIccid() {
        return mIccid;
    }

    public IccIdInfo addmIccid(String mIccid) {
        this.mIccid = mIccid;
        return this;
    }
}
