package com.hai.yun.bean;

import com.hai.yun.bean.utils.BinaryUtils;
import com.hai.yun.bean.utils.GPSUtils;
import com.hai.yun.bean.utils.HeartBeatUtils;
import com.hai.yun.bean.utils.LBSUtils;
import org.joda.time.DateTime;

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
        byte[] content = BinaryUtils.mergeBbytes(gps_bytes, lbs_bytes);
        return dealBuilder(AgreeMentNos.GPSAndLBSInfo, content, listNO);
    }

    /**
     * 获得心跳包
     *
     * @param info 心跳包信息
     * @return
     */
    public byte[] getBeartHeatPkg(HeartBeatInfo info) {
        byte[] content = HeartBeatUtils.setHearteatContent(info);
        return dealBuilder(AgreeMentNos.GPSAndLBSInfo, content, info.getListNO());
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
