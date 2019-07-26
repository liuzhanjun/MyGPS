package com.hai.yun.bean;

import com.hai.yun.bean.utils.CRC16;
import com.hai.yun.bean.utils.BinaryUtils;

public class DataPkgInfo {
    /**
     * 起始位
     */
    private final byte[] mSTARTBIT = {0x78, 0x78};

    //包长度
    private byte mPkgLength;

    /**
     * 协议号
     */
    private byte mAgreeMentNO;
    //信息内容
    private byte[] mContent;

    //信息序列号长度为2
    private byte[] mInfolist;
    //校验位长度2字节
    private byte[] mCheckBit;
    //停止位 长度位2
    private byte[] mStopBit = {0x0D, 0x0A}; //停止位 长度位2


    private DataPkgInfo() {

    }

    public byte[] getDataPkg() {
        byte[] content = new byte[10 + mContent.length];
        //添加起始位
        int index = 0;
        index = addInfo(content, index, mSTARTBIT);

        //获得包长度到信息序列号之间的数据，用作后面校验位的处理
        dealStopBit(content, index);


        return content;
    }

    private void dealStopBit(byte[] content, int index) {

        int paklen = BinaryUtils.getInt(mPkgLength);
        //用来存放包长度到信息序列号的数组
        byte[] dealBytes = new byte[paklen - 1];
        int dIndex = 0;
        //包长度
        content[index++] = mPkgLength;
        dealBytes[dIndex++] = mPkgLength;
        //协议号
        content[index++] = mAgreeMentNO;
        dealBytes[dIndex++] = mAgreeMentNO;
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
        index = addInfo(content, index, mStopBit);

    }

    private int addInfo(byte[] content, int index, byte[] mContent) {
        for (int i = 0; i < mContent.length; i++) {
            content[index] = mContent[i];
            index++;
        }
        return index;
    }


    public static class Builder {

        /**
         * 协议号
         */
        private byte agreeMentNO;
        //信息内容
        private byte[] content;

        //信息序列号长度为2
        private byte[] infolist;


        public DataPkgInfo build() {
            DataPkgInfo dataPkgInfo = new DataPkgInfo();
            dataPkgInfo.mAgreeMentNO = this.agreeMentNO;
            dataPkgInfo.mContent = this.content;
            dataPkgInfo.mInfolist = this.infolist;
            int len = 5 + content.length;
            if (len > 255) {
                throw new IndexOutOfBoundsException("长度不能超过255");
            }
            dataPkgInfo.mPkgLength = BinaryUtils.getBytes(len, 1)[0];
            return dataPkgInfo;
        }

        //设置协议号
        public Builder setAgreeMentNo(byte amNO) {
            this.agreeMentNO = amNO;
            return this;
        }

        //内容初始
        public Builder appendContentStart(byte[] content) {
            this.content = content;
            return this;
        }

//        /**
//         * 追加内容
//         *
//         * @param appendContent
//         * @return
//         */
//        public Builder appendContent(byte[] appendContent) {
//            byte[] newContent = new byte[this.content.length + appendContent.length];
//            for (int i = 0; i < this.content.length; i++) {
//                newContent[i] = this.content[i];
//            }
//            for (int i = 0; i < appendContent.length; i++) {
//                newContent[this.content.length + i] = appendContent[i];
//            }
//            this.content = newContent;
//            return this;
//        }


        //设置序列号
        public Builder setMInfolist(int num) {
            this.infolist = BinaryUtils.getBytes(num, 2);
            return this;
        }


    }


}
