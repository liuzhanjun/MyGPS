package com.hai.yun.bean;

import com.hai.yun.bean.utils.BinaryUtils;
import com.hai.yun.bean.utils.CRC16;
import org.joda.time.DateTime;

public class AnalysisPkgInfo extends AnalysisPkg{
    //包长度
    private byte mPkgLength;

    /**
     * 协议号
     */
    private byte mAgreeMentNO;
    //时间
    private byte[] mTime;

    //信息序列号长度为2
    private byte[] mInfolist;
    //校验位长度2字节
    private byte[] mCheckBit;

    private AnalysisPkgInfo() {
    }

    public byte getmPkgLength() {
        return mPkgLength;
    }

    public byte getmAgreeMentNO() {
        return mAgreeMentNO;
    }

    public byte[] getmTime() {
        return mTime;
    }

    public byte[] getmInfolist() {
        return mInfolist;
    }

    public byte[] getmCheckBit() {
        return mCheckBit;
    }

    /**
     * 检查校验位是否正常
     *
     * @return
     */
    public boolean checkCheckBit() {
        byte[] bytes = new byte[BinaryUtils.getInt(mPkgLength) - 1];
        int index = 0;
        bytes[index++] = mPkgLength;
        bytes[index++] = mAgreeMentNO;
        bytes[index++] = mInfolist[0];
        bytes[index++] = mInfolist[1];
        for (int i = 0; i < mTime.length; i++) {
            bytes[index++] = mTime[i];
        }


        byte[] crc = CRC16.getCRC16(bytes);
        System.out.println(BinaryUtils.intTo0x(BinaryUtils.getInt(crc), 2));
        System.out.println(BinaryUtils.intTo0x(BinaryUtils.getInt(mCheckBit), 2));

        int a = BinaryUtils.getInt(crc) ^ BinaryUtils.getInt(mCheckBit);
        return a == 0 ? true : false;
    }

    public static class AnalysisBuilder {
        //包长度
        private byte mPkgLength;

        /**
         * 协议号
         */
        private byte mAgreeMentNO;
        //时间
        private byte[] mTime;

        //信息序列号长度为2
        private byte[] mInfolist;
        //校验位长度2字节
        private byte[] mCheckBit;


        public AnalysisPkgInfo build() {
            AnalysisPkgInfo dataPkgInfo = new AnalysisPkgInfo();
            dataPkgInfo.mAgreeMentNO = this.mAgreeMentNO;
            dataPkgInfo.mPkgLength = this.mPkgLength;
            dataPkgInfo.mTime = this.mTime;
            dataPkgInfo.mInfolist = this.mInfolist;
            dataPkgInfo.mCheckBit = this.mCheckBit;
            return dataPkgInfo;
        }


        public AnalysisBuilder addmPkgLength(byte mPkgLength) {
            this.mPkgLength = mPkgLength;
            return this;
        }

        public AnalysisBuilder addmAgreeMentNO(byte mAgreeMentNO) {
            this.mAgreeMentNO = mAgreeMentNO;
            return this;
        }

        public AnalysisBuilder addmTime(byte[] mTime) {
            this.mTime = mTime;
            return this;
        }

        public AnalysisBuilder addmInfolist(byte[] mInfolist) {
            this.mInfolist = mInfolist;
            return this;
        }

        public AnalysisBuilder addmCheckBit(byte[] mCheckBit) {
            this.mCheckBit = mCheckBit;
            return this;
        }
    }
}
