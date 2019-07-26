package com.hai.yun.bean;

import org.joda.time.DateTime;

/**
 * LBS信息包
 * listNo 信息序列号
 * time   时间
 * mcc    移动用户所属国家代号
 * mnc    移动网号码
 * lac    位置区码
 * cellId 移动基站
 */
public class LbsInfo {
    private int listNo;//信息序列号
    private DateTime time;//时间
    private int mcc;//移动用户所属国家代号
    private int mnc;//移动网号码
    private int lac;//位置区码
    private int cellId;//移动基站


    public LbsInfo addListNo(int listNo) {
        this.listNo = listNo;
        return this;
    }

    public LbsInfo addTime(DateTime time) {
        this.time = time;
        return this;
    }

    public LbsInfo addMcc(int mcc) {
        this.mcc = mcc;
        return this;
    }

    public LbsInfo addMnc(int mnc) {
        this.mnc = mnc;
        return this;
    }

    public LbsInfo addLac(int lac) {
        this.lac = lac;
        return this;
    }

    public LbsInfo addCellId(int cellId) {
        this.cellId = cellId;
        return this;
    }

    public int getListNo() {
        return listNo;
    }

    public DateTime getTime() {
        return time;
    }

    public int getMcc() {
        return mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public int getLac() {
        return lac;
    }

    public int getCellId() {
        return cellId;
    }
}
