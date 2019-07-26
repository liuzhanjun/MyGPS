package com.hai.yun.bean;

import com.hai.yun.bean.utils.*;

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
     * 获得GPS信息包 0x01
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
     * 终端信息  （心跳包）
     *
     * @param info 终端信息
     * @return
     */
    public byte[] getBeartHeatPkg(HeartBeatInfo info) {
        byte[] content = HeartBeatUtils.setHearteatContent(info);
        return dealBuilder(AgreeMentNos.GPSAndLBSInfo, content, info.getListNO());
    }

    /**
     * 卫星信噪比
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





    private byte[] dealBuilder(byte agreeMentNO, byte[] content, int listNo) {

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
