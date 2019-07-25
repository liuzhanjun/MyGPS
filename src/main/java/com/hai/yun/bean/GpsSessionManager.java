package com.hai.yun.bean;

import com.hai.yun.bean.utils.BinaryUtils;
import com.hai.yun.bean.utils.DateUtils;
import com.hai.yun.bean.utils.GPSUtils;
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
     * @param listNo          信息序列号
     * @param time            时间
     * @param len             信息长度
     * @param satelliteNumber 卫星数
     * @param point           经纬度
     * @param speed           速度
     * @param gpsInfo         gps状态信息
     * @return
     */
    public byte[] getGPSInfoPkg(int listNo, DateTime time, int len, int satelliteNumber, EarthPoint point, int speed, GPSInfo gpsInfo) {
        byte[] content = GPSUtils.setGPSContent(time, len, satelliteNumber, point, speed, gpsInfo);
        return dealBuilder(AgreeMentNos.gpsInfo, content, listNo);
    }


    public byte[] getLBSPkg(int listNo) {
        byte[] content = null;
//        LBSUtils.
        return dealBuilder(AgreeMentNos.LBSInfo, content, listNo);
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
