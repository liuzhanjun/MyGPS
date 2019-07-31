package com.hai.yun.bean;

import com.hai.yun.bean.utils.BinaryUtils;
import com.hai.yun.bean.utils.CRC16;

public class AnalysisRecordPkg extends AnalysisPkg {
    //包长度
    private byte[] mPkgLength;
    public static final int mPkgLength_L = 2;

    /**
     * 协议号
     */
    private byte mAgreeMentNO;
    //接收状态标志
    private byte mAccept_state;
    //信息序列号长度为2
    private byte[] mInfolist;
    //校验位长度2字节
    private byte[] mCheckBit;


    /**
     * 检查校验位是否正常
     *
     * @return
     */
    public boolean checkCheckBit() {
        byte[] bytes = new byte[BinaryUtils.getInt(mPkgLength) - 1];
        int index = 0;
        index = BinaryUtils.setResult(mPkgLength, bytes, index);
        bytes[index++] = mAgreeMentNO;
        bytes[index++] = mAccept_state;
        BinaryUtils.setResult(mInfolist, bytes, index);

        byte[] crc = CRC16.getCRC16(bytes);
        System.out.println(BinaryUtils.intTo0x(BinaryUtils.getInt(crc), 2));
        System.out.println(BinaryUtils.intTo0x(BinaryUtils.getInt(mCheckBit), 2));

        int a = BinaryUtils.getInt(crc) ^ BinaryUtils.getInt(mCheckBit);
        return a == 0 ? true : false;
    }

    public byte[] getmPkgLength() {
        return mPkgLength;
    }

    public void setmPkgLength(byte[] mPkgLength) {
        this.mPkgLength = mPkgLength;
    }

    public byte getmAgreeMentNO() {
        return mAgreeMentNO;
    }

    public void setmAgreeMentNO(byte mAgreeMentNO) {
        this.mAgreeMentNO = mAgreeMentNO;
    }

    public byte getmAccept_state() {
        return mAccept_state;
    }

    public void setmAccept_state(byte mAccept_state) {
        this.mAccept_state = mAccept_state;
    }

    public byte[] getmInfolist() {
        return mInfolist;
    }

    public void setmInfolist(byte[] mInfolist) {
        this.mInfolist = mInfolist;
    }

    public byte[] getmCheckBit() {
        return mCheckBit;
    }

    public void setmCheckBit(byte[] mCheckBit) {
        this.mCheckBit = mCheckBit;
    }
}
