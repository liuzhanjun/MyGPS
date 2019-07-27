package com.hai.yun.bean;

import com.hai.yun.bean.utils.*;
import org.joda.time.DateTime;

import java.util.Collections;

/**
 * GPS会话信息 单例模式
 */
public enum GpsSessionManager {

    SessionManager {
        @Override
        public GpsSessionManager getInstance() {
            return this;
        }
    };

    public abstract GpsSessionManager getInstance();

    private DataPkgInfo.Builder builder = new DataPkgInfo.Builder();

    /**
     * 0x01
     * 登陆信息包
     *
     * @param IMEI   机器码
     * @param listNo 信息序列号
     * @return
     */
    public byte[] getLoginInfoPkg(String IMEI, int listNo) {

        return dealBuilder(AgreeMentNos.loginNO, BinaryUtils.getIMEI(IMEI), listNo);
    }


    /**
     * //解析平台响应的信息包
     * 这个方法适用于常规平台响应
     *
     * @param infos 返回的信息
     * @return
     */
    public AnalysisPkgInfo analysisCommonPkg(byte infos[]) {
        AnalysisPkgInfo.AnalysisBuilder analysisBuilder = new AnalysisPkgInfo.AnalysisBuilder();
        //获得包长度
        byte pkglen = infos[2];
        //获得协议号
        byte agreeMentNo = infos[3];
        //获得信息序列号
        byte[] infolist = new byte[]{infos[4], infos[5]};
        //获得校验位倒数第四位和第三位
        byte[] checkBite = new byte[]{infos[infos.length - 4], infos[infos.length - 3]};
        //解析时间
        DateTime d_time = null;
        //如果长度大于5说明有时间要解析
        int len = BinaryUtils.getInt(pkglen);
        byte[] time = null;
        if (len > 5) {
            time = new byte[6];
            int index = 0;
            for (int i = 6; i < 12; i++) {
                time[index++] = infos[i];
            }

        }

        AnalysisPkgInfo analysisinfo = analysisBuilder
                .addmPkgLength(pkglen)
                .addmAgreeMentNO(agreeMentNo)
                .addmTime(time)
                .addmInfolist(infolist)
                .addmCheckBit(checkBite).build();

        return analysisinfo;

    }


    /**
     * 获得GPS信息包 0x10
     *
     * @param info gps 信息包
     * @return
     */
    public byte[] getGPSInfoPkg(GpsInfo info) {
        byte[] content = GPSUtils.setGPSContent(info);
        return dealBuilder(AgreeMentNos.gpsInfo, content, info.getListNo());
    }


    /**
     * 获得LBS信息包 0x11
     *
     * @param info lbs信息包
     * @return
     */
    public byte[] getLBSPkg(LbsInfo info) {
        byte[] content = LBSUtils.setLBSContent(info);
        return dealBuilder(AgreeMentNos.LBSInfo, content, info.getListNo());
    }


    /**
     * 获得GPS 和LBS合并信息包
     * 0x12
     *
     * @param listNO  信息序列号
     * @param gpsInfo gps信息
     * @param lbsInfo lbs信息
     * @return
     */
    public byte[] getGPSAndLBSpkg(int listNO, GpsInfo gpsInfo, LbsInfo lbsInfo) {
        byte[] gps_bytes = GPSUtils.setGPSContent(gpsInfo);
        byte[] lbs_bytes = LBSUtils.setLBSContent(lbsInfo);
        byte[] lbs_re_bytes = new byte[lbs_bytes.length - 6];
        //lbs要去掉日期 也就是前六位
        int index = 0;
        for (int i = 6; i < lbs_bytes.length; i++) {
            lbs_re_bytes[index++] = lbs_bytes[i];
        }
        byte[] content = BinaryUtils.mergeBbytes(gps_bytes, lbs_re_bytes);
        return dealBuilder(AgreeMentNos.GPSAndLBSInfo, content, listNO);
    }

    /**
     * 0x13
     * 终端信息  （心跳包）
     *
     * @param info 终端信息
     * @return
     */
    public byte[] getBeartHeatPkg(HeartBeatInfo info) {
        byte[] content = HeartBeatUtils.setHearteatContent(info);
        return dealBuilder(AgreeMentNos.heartbeat, content, info.getListNO());
    }

    /**
     * 卫星信噪比
     * 0x14
     *
     * @param listNo     信息序列号
     * @param num        卫星数目
     * @param xzb        信噪比
     * @param extContent 预留扩展位
     * @return
     */
    public byte[] getSateliteSnor(int listNo, int num, byte[] xzb, byte[] extContent) {
        return dealBuilder(AgreeMentNos.SSNIRInfo, SatelliteSnorUtils.setSateliteSnor(num, xzb, extContent), listNo);
    }


    /**
     * 报警信息
     * 0x16
     *
     * @param listNO  信息序列号
     * @param gpsInfo gps信息
     * @param lbsInfo lbs信息
     * @param info    终端信息
     * @return
     */
    public byte[] getWarring(int listNO, GpsInfo gpsInfo, LbsInfo lbsInfo, HeartBeatInfo info) {
        //GPS
        byte[] gps_bytes = GPSUtils.setGPSContent(gpsInfo);
        //LBS
        byte[] lbs_bytes = LBSUtils.setLBSContent(lbsInfo);
        //终端信息
        byte[] heart_content = HeartBeatUtils.setHearteatContent(info);

        byte[] lbs_re_bytes = new byte[lbs_bytes.length - 5];
        //lbs要去掉日期 也就是前六位 并在前面加1位 ：表示lbs长度
        int index = 0;
        lbs_re_bytes[index++] = BinaryUtils.getByte(lbs_re_bytes.length);
        for (int i = 6; i < lbs_bytes.length; i++) {
            lbs_re_bytes[index++] = lbs_bytes[i];
        }

        byte[] content = BinaryUtils.mergeBbytes(gps_bytes, lbs_re_bytes, heart_content);
        return dealBuilder(AgreeMentNos.WarningInfo, content, listNO);
    }


    /**
     * 多基站定位信息
     *
     * @param info
     * @return
     */
    public byte[] getLBSMultiStation(LbsMultiStationInfo info) {
        byte[] content = LBSUtils.setLBSMultiStationContent(info);
        return dealBuilder(AgreeMentNos.LBSMultipleBaseStations, content, info.getmListNo());
    }


    /**
     * 0x1A
     * GPS查询地址
     *
     * @param gpsInfo
     * @return
     */
    public byte[] gpsQueryAddress(GpsInfo gpsInfo) {
        byte[] content = GPSUtils.setGSPqueryAddress(gpsInfo);
        return dealBuilder(AgreeMentNos.queryAddressGPS, content, gpsInfo.getListNo());
    }

    /**
     * 解析查询地址回复
     *
     * @param bytes
     * @return
     */
    public byte analysisQueryAddressPkg(byte[] bytes) {
        return 1;
    }


    private byte[] dealBuilder(byte [] agreeMentNO, byte[] content, int listNo) {

        //登录信息
        DataPkgInfo datap = builder
                //协议号
                .setAgreeMentNo(agreeMentNO)
                //内容
                .appendContentStart(content)
                //信息序列号
                .setMInfolist(listNo)
                .build();
        return datap.getDataPkg();
    }


}
