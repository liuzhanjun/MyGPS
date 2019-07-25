package com.hai.yun.bean.utils;

import com.hai.yun.bean.EarthPoint;
import com.hai.yun.bean.GPSInfo;
import org.joda.time.DateTime;

public class GPSUtils {


    /**
     * @param time            时间
     * @param len             信息长度
     * @param satelliteNumber 卫星数
     * @param point           经纬度
     * @param speed           速度
     * @param gpsInfo         gps状态信息
     * @param ext_content     预留位信息
     * @param ext_len         预留位长度
     * @return
     */
    public static byte[] setGPSContent(DateTime time, int len, int satelliteNumber, EarthPoint point, int speed, GPSInfo gpsInfo, int ext_content, int ext_len) {
        //设置时间
        byte[] time_bytes = setTime(time);
        //设置信息长度和卫星数目
        byte slengthNumber = setGPSlengthNumber(len, satelliteNumber);
        //设置经纬度
        byte[] earth_point_bytes = setLatitudeAndLongitude(point);
        //设置速度
        byte speed_byte = setSpeed(speed);
        //设置状态和方向
        byte[] stateAndDire = setStateAndDire(gpsInfo.getRun_dir_c(), gpsInfo.getLatitude_dir(), gpsInfo.getLongitude_dir(), gpsInfo.getGps_state(), gpsInfo.getGps_time());
        int byte_len = time_bytes.length + 1 + earth_point_bytes.length + 1 + stateAndDire.length;
        //预留位 以后使用
        byte[] extBit = null;
        if (ext_content != 0 && ext_len != 0) {
            extBit = extBit(ext_content, ext_len);
            byte_len += ext_len;
        }

        byte[] result = new byte[byte_len];
        int index = 0;
        //设置时间
        index = setResult(time_bytes, result, index);
        //设置卫星数和长度信息
        result[index++] = slengthNumber;
        //设置经纬度
        index = setResult(earth_point_bytes, result, index);
        //设置速度
        result[index++] = speed_byte;
        //设置状态和方向
        index = setResult(stateAndDire, result, index);
        //设置扩展
        if (ext_content != 0 && ext_len != 0) {
            setResult(extBit, result, index);
        }
        return result;

    }


    private static int setResult(byte[] bytes, byte[] result, int index) {
        for (int i = 0; i < bytes.length; i++) {
            result[index++] = bytes[i];
        }
        return index;
    }

    /**
     * @param time            时间
     * @param len             信息长度
     * @param satelliteNumber 卫星数
     * @param point           经纬度
     * @param speed           速度
     * @param gpsInfo         gps状态信息
     */
    public static byte[] setGPSContent(DateTime time, int len, int satelliteNumber, EarthPoint point, int speed, GPSInfo gpsInfo) {
        return setGPSContent(time, len, satelliteNumber, point, speed, gpsInfo, 0, 0);
    }

    /**
     * 设置GPS信息长度和卫星数目
     * satelliteNumber<=15
     * length<=15
     *
     * @return
     */
    private static byte setGPSlengthNumber(int length, int satelliteNumber) {
        satelliteNumber = satelliteNumber >= 15 ? 15 : satelliteNumber;
        byte len = BinaryUtils.getByte(length);
        byte slNuber = BinaryUtils.getByte(satelliteNumber);
        return (byte) ((len << 4) | slNuber);
    }


    /**
     * 设置经纬度
     *
     * @return
     */
    private static byte[] setLatitudeAndLongitude(EarthPoint point) {

        //计算纬度
        EarthPoint.Latitude latitude = point.getLatitude();
        double latitude_d = (latitude.getLatitude_c() * 60 + latitude.getLatitude_m()) * 30000;
        byte[] latitude_bytes = BinaryUtils.getBytes((int) latitude_d, 4);

        EarthPoint.Longitude longitude = point.getLongitude();
        double longitude_d = (longitude.getLongitude_c() * 60 + longitude.getLongitude_m()) * 30000;
        byte[] longtude_bytes = BinaryUtils.getBytes((int) longitude_d, 4);

        byte[] bytes = new byte[8];
        for (int i = 0; i < latitude_bytes.length; i++) {
            bytes[i] = latitude_bytes[i];
        }
        for (int i = 0; i < longtude_bytes.length; i++) {
            bytes[i + 4] = longtude_bytes[i];
        }


        return bytes;
    }


    /**
     * 设置时间
     *
     * @param time
     * @return
     */
    private static byte[] setTime(DateTime time) {
        return DateUtils.getTime(time);
    }

    /**
     * 设置速度
     *
     * @param speed 0~255
     * @return
     */
    private static byte setSpeed(int speed) {
        return BinaryUtils.getByte(speed);
    }

    /**
     * @param run_dir_c     运行方向度数 0-360°
     * @param latitude_dir  纬度方向 0：南纬 1：北纬
     * @param longitude_dir 经度方向 0：东经 1：西经
     * @param gps_state     GPS定位状态  0：GPS不定位 1：GPS已定位
     * @param gps_time      GPS类型  0：实时GPS  1差分GPS
     * @return
     */
    private static byte[] setStateAndDire(int run_dir_c, int latitude_dir, int longitude_dir, int gps_state, int gps_time) {
        //第一步设置方向 也就是说确定前面的10位
        byte[] dir_c_bytes = BinaryUtils.getBytes(run_dir_c, 2);
        //从最低位开始向最高位取10位
        int dir_c_int = (BinaryUtils.getInt(dir_c_bytes) & 0x03FF);
        //设置纬度方向
        latitude_dir &= 0xff;
        longitude_dir &= 0xff;
        gps_state &= 0xff;
        gps_time &= 0xff;
        int result = dir_c_int | (latitude_dir << 10) | (longitude_dir << 11) | (gps_state << 12) | (gps_time << 13);
        return BinaryUtils.getBytes(result, 2);
    }

    /**
     * 预留扩展位
     *
     * @param content 内容
     * @param len     扩展位的位宽
     * @return
     */
    private static byte[] extBit(int content, int len) {
        return BinaryUtils.getBytes(content, len);
    }


}
