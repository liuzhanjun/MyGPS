package com.hai.yun.bean;

public class AnalysisPkg {
    /**
     * 起始位
     */
    protected final byte[] mSTARTBIT = {0x78, 0x78};
    //停止位 长度位2
    protected final byte[] mStopBit = {0x0D, 0x0A}; //停止位 长度位2
}
