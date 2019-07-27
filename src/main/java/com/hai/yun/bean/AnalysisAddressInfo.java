package com.hai.yun.bean;

/**
 * 解析地址信息包
 */
public class AnalysisAddressInfo extends AnalysisPkg {

    //包长度
    protected byte mPkgLength;

    /**
     * 协议号
     */
    protected byte mAgreeMentNO;

    //指令长度
    protected byte mCommandLen;

    //服务器标志
    protected byte[] mService_tip;

    //ADDRESS
    protected final byte[] mAddress = {41, 44, 44, 52, 45, 53, 53};



}
