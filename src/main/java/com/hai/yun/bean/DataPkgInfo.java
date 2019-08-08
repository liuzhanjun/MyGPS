package com.hai.yun.bean;

import com.hai.yun.bean.utils.CRC16;
import com.hai.yun.bean.utils.BinaryUtils;
import org.joda.time.DateTime;

public class DataPkgInfo {
    /**
     * 起始位
     */
    private byte[] mSTARTBIT = {0x78, 0x78};
    public static final byte[] mSTARTBIT_2 = {0x79, 0x79};
    //包长度
    private byte[] mPkgLength;
    //包长度所占字节长度（此属性用于后面计算，以及以后修改属性的字节长度）
    private int mPkgLength_L = 1;

    /**
     * 协议号
     */
    private byte[] mAgreeMentNO;
    public final int mAgreeMentNO_L = 1;
    //信息内容
    private byte[] mContent;

    //信息序列号长度为2
    private byte[] mInfolist;
    public static final int mInfolist_L = 2;
    //校验位长度2字节
    private byte[] mCheckBit;
    public static final int mCheckBit_L = 2;
    //停止位 长度位2
    private final byte[] mStopBit = {0x0D, 0x0A}; //停止位 长度位2


    private DataPkgInfo() {

    }

    public int getmPkgLength_L() {
        return mPkgLength_L;
    }

    public void setmPkgLength_L(int mPkgLength_L) {
        this.mPkgLength_L = mPkgLength_L;
    }

    public byte[] getmSTARTBIT() {
        return mSTARTBIT;
    }

    public byte[] getmPkgLength() {
        return mPkgLength;
    }

    public byte[] getmAgreeMentNO() {
        return mAgreeMentNO;
    }

    public byte[] getmContent() {
        return mContent;
    }

    public byte[] getmInfolist() {
        return mInfolist;
    }

    public byte[] getmCheckBit() {
        return mCheckBit;
    }

    public byte[] getmStopBit() {
        return mStopBit;
    }

    public byte[] getDataPkg() {
        //包的总长度
        int all_pkg_len = mSTARTBIT.length//起始位长度
                + mPkgLength_L//包长度
                + mAgreeMentNO_L//协议号长度
                + mContent.length//信息内容长度
                + mInfolist_L//信息序列号长度
                + mCheckBit_L//校验位长度
                + mStopBit.length;//停止位长度
        byte[] content = new byte[all_pkg_len];
        //添加起始位
        int index = 0;
        index = addInfo(content, index, mSTARTBIT);

        //获得包长度到信息序列号之间的数据，用作后面校验位的处理
        dealStopBit(content, index);


        return content;
    }

    private void dealStopBit(byte[] content, int index) {
        int paklen = BinaryUtils.getInt(mPkgLength) + mPkgLength_L - mCheckBit_L;

        System.out.println(paklen);
        //用来存放包长度到信息序列号的数组(校验的信息内容长度=包长度的内容所表示的长度+包长度的字节长度-检验位的长度)
        byte[] dealBytes = new byte[paklen];
        int dIndex = 0;
        //包长度
        index = addInfo(content, index, mPkgLength);
        dIndex = addInfo(dealBytes, dIndex, mPkgLength);

        //协议号
        index = addInfo(content, index, mAgreeMentNO);
        dIndex = addInfo(dealBytes, dIndex, mAgreeMentNO);


        //信息内容
        index = addInfo(content, index, mContent);
        dIndex = addInfo(dealBytes, dIndex, mContent);
        //信息序列号
        index = addInfo(content, index, mInfolist);
        dIndex = addInfo(dealBytes, dIndex, mInfolist);
        //校验位
        byte[] crc = CRC16.getCRC16(dealBytes);
        System.out.println("CRC=" + BinaryUtils.intTo0x(BinaryUtils.getInt(crc), 2));

        index = addInfo(content, index, crc);
        //停止位
        addInfo(content, index, mStopBit);

    }

    private int addInfo(byte[] content, int index, byte[] mContent) {
        for (int i = 0; i < mContent.length; i++) {
            content[index] = mContent[i];
            index++;
        }
        return index;
    }


    public static class Builder {
        private byte[] mSTARTBIT = {0x78, 0x78};

        /**
         * 协议号
         */
        private byte[] agreeMentNO;
        //信息内容
        private byte[] content;

        //信息序列号长度为2
        private byte[] infolist;

        private int mPkgLength_L = 1;

        public DataPkgInfo build() {
            DataPkgInfo dataPkgInfo = new DataPkgInfo();
            dataPkgInfo.mPkgLength_L = mPkgLength_L;
            dataPkgInfo.mAgreeMentNO = this.agreeMentNO;
            dataPkgInfo.mContent = this.content;
            dataPkgInfo.mInfolist = this.infolist;
            int len = dataPkgInfo.mAgreeMentNO_L //协议号长度
                    + content.length//内容长度
                    + dataPkgInfo.mInfolist_L//信息序列号长度
                    + dataPkgInfo.mCheckBit_L;//校验位长度

            System.out.println(len + "=====len======" + dataPkgInfo.mPkgLength_L);
            dataPkgInfo.mPkgLength = BinaryUtils.getBytes(len, dataPkgInfo.mPkgLength_L);

            return dataPkgInfo;
        }


        public Builder setmSTARTBIT(byte[] mSTARTBIT) {
            this.mSTARTBIT = mSTARTBIT;
            return this;
        }

        //设置协议号
        public Builder setAgreeMentNo(byte[] amNO) {
            this.agreeMentNO = amNO;
            return this;
        }

        public Builder setmPkgLength_L(int mPkgLength_L) {
            this.mPkgLength_L = mPkgLength_L;
            return this;
        }

        //内容初始
        public Builder appendContentStart(byte[] content) {
            this.content = content;
            return this;
        }

        //设置序列号
        public Builder setMInfolist(int num) {
            this.infolist = BinaryUtils.getBytes(num, DataPkgInfo.mInfolist_L);
            return this;
        }


    }


}
