package com.hai.yun.bean;

/**
 * 解析地址信息包
 */
public class AnalysisAddressInfo extends AnalysisPkg {

    //包长度
    private byte[] mPkgLength;
    private int mPkgLength_L;
    /**
     * 协议号
     */
    private byte[] mAgreeMentNO;
    private final int mAgreeMentNO_L = 1;


    //指令长度
    private byte[] mCommandLen;
    private int mCommandLen_L;

    //服务器标志
    private byte[] mService_tip;
    private final int mService_tip_L = 4;//服务器标志的长度

    //ADDRESS
    private final byte[] mAddress_const = {0x41, 0x44, 0x44, 0x52, 0x45, 0x53, 0x53};
    //分隔符
    private final byte[] separator_1 = {0x26, 0x26};
    //地址内容
    private byte[] mAddress_unicode;
    //分隔符
    private final byte[] separator_2 = {0x26, 0x26};

    //电话
    private byte[] mPhone;
    private final int mPhone_L = 21;
    //结束符
    private byte[] end_str = {0x23, 0x23};

    //信息序列号长度为2
    private byte[] mInfolist;
    private final int mInfolist_L = 2;
    //校验位长度2字节
    private byte[] mCheckBit;
    private final int mCheckBit_L = 2;


    public byte[] getmPkgLength() {
        return mPkgLength;
    }

    public void setmPkgLength(byte[] mPkgLength) {
        this.mPkgLength = mPkgLength;
    }

    public int getmPkgLength_L() {
        return mPkgLength_L;
    }

    public void setmPkgLength_L(int mPkgLength_L) {
        this.mPkgLength_L = mPkgLength_L;
    }

    public byte[] getmAgreeMentNO() {
        return mAgreeMentNO;
    }

    public void setmAgreeMentNO(byte[] mAgreeMentNO) {
        this.mAgreeMentNO = mAgreeMentNO;
    }

    public int getmAgreeMentNO_L() {
        return mAgreeMentNO_L;
    }

    public byte[] getmCommandLen() {
        return mCommandLen;
    }

    public void setmCommandLen(byte[] mCommandLen) {
        this.mCommandLen = mCommandLen;
    }

    public int getmCommandLen_L() {
        return mCommandLen_L;
    }

    public void setmCommandLen_L(int mCommandLen_L) {
        this.mCommandLen_L = mCommandLen_L;
    }

    public byte[] getmService_tip() {
        return mService_tip;
    }

    public void setmService_tip(byte[] mService_tip) {
        this.mService_tip = mService_tip;
    }

    public int getmService_tip_L() {
        return mService_tip_L;
    }

    public byte[] getmAddress_const() {
        return mAddress_const;
    }

    public byte[] getSeparator_1() {
        return separator_1;
    }

    public byte[] getmAddress_unicode() {
        return mAddress_unicode;
    }

    public void setmAddress_unicode(byte[] mAddress_unicode) {
        this.mAddress_unicode = mAddress_unicode;
    }

    public byte[] getSeparator_2() {
        return separator_2;
    }

    public byte[] getmPhone() {
        return mPhone;
    }

    public void setmPhone(byte[] mPhone) {
        this.mPhone = mPhone;
    }

    public int getmPhone_L() {
        return mPhone_L;
    }

    public byte[] getEnd_str() {
        return end_str;
    }

    public void setEnd_str(byte[] end_str) {
        this.end_str = end_str;
    }

    public byte[] getmInfolist() {
        return mInfolist;
    }

    public void setmInfolist(byte[] mInfolist) {
        this.mInfolist = mInfolist;
    }

    public int getmInfolist_L() {
        return mInfolist_L;
    }

    public byte[] getmCheckBit() {
        return mCheckBit;
    }

    public void setmCheckBit(byte[] mCheckBit) {
        this.mCheckBit = mCheckBit;
    }

    public int getmCheckBit_L() {
        return mCheckBit_L;
    }
}
