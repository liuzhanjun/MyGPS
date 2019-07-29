package com.hai.yun.bean.utils;

import com.hai.yun.bean.EarthPoint;
import com.hai.yun.bean.GpsInfo;
import com.hai.yun.bean.GpsStateDir;
import org.joda.time.DateTime;

public class GPSUtils {


    /**
     * @param time            时间
     * @param len             GPS信息长度
     * @param satelliteNumber 卫星数
     * @param point           经纬度
     * @param speed           速度
     * @param gpsStateDir     gps状态信息
     * @param ext_content     预留位信息
     * @return
     */
    private static byte[] setGPSContent(DateTime time, int len, int satelliteNumber, EarthPoint point, int speed, GpsStateDir gpsStateDir, byte[] ext_content) {
        //设置时间
        byte[] time_bytes = setTime(time);
        //设置信息长度和卫星数目
        byte slengthNumber = setGPSlengthNumber(len, satelliteNumber);
        //设置经纬度
        byte[] earth_point_bytes = setLatitudeAndLongitude(point);
        //设置速度
        byte[] speed_byte = setSpeed(speed);
        //设置状态和方向
        byte[] stateAndDire = setStateAndDire(gpsStateDir.getRun_dir_c(), gpsStateDir.getLatitude_dir(), gpsStateDir.getLongitude_dir(), gpsStateDir.getGps_state(), gpsStateDir.getGps_time());
        //内容长度
        int byte_len = time_bytes.length//时间长度
                + GpsInfo.len_satelliteNumber_L//GPS信息和卫星数所占长度
                + earth_point_bytes.length//经纬度所占长度
                + GpsInfo.speed_L//速度所占长度
                + stateAndDire.length;//状态方向所占长度
        //预留位
        if (ext_content != null) {
            byte_len += ext_content.length;
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
        index = setResult(speed_byte, result, index);
        //设置状态和方向
        index = setResult(stateAndDire, result, index);
        //设置扩展
        if (ext_content != null) {
            setResult(ext_content, result, index);
        }
        return result;

    }



    private static byte[] setGPSAddressContent(DateTime time, int len, int satelliteNumber, EarthPoint point, int speed, GpsStateDir gpsStateDir, byte[] ext_content, String phone) {
        //设置时间
        byte[] time_bytes = setTime(time);
        //设置信息长度和卫星数目
        byte slengthNumber = setGPSlengthNumber(len, satelliteNumber);
        //设置经纬度
        byte[] earth_point_bytes = setLatitudeAndLongitude(point);
        //设置速度
        byte[] speed_byte = setSpeed(speed);
        //设置状态和方向
        byte[] stateAndDire = setStateAndDire(gpsStateDir.getRun_dir_c(), gpsStateDir.getLatitude_dir(), gpsStateDir.getLongitude_dir(), gpsStateDir.getGps_state(), gpsStateDir.getGps_time());
        //设置电话
        byte[] phone_bytes = BinaryUtils.getPhoneBytes(phone, GpsInfo.Phone_L);
        int byte_len = time_bytes.length
                + GpsInfo.len_satelliteNumber_L//GPS信息和卫星数所占长度
                + earth_point_bytes.length
                + GpsInfo.speed_L//速度所占长度
                + stateAndDire.length
                + phone_bytes.length;
        //预留位
        if (ext_content != null) {
            byte_len += ext_content.length;
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
        index = setResult(speed_byte, result, index);
        //设置状态和方向
        index = setResult(stateAndDire, result, index);

        //设置电话
        index = setResult(phone_bytes, result, index);
        //设置扩展
        if (ext_content != null) {
            setResult(ext_content, result, index);
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
     * 设置gps信息
     *
     * @param info
     * @return
     */
    public static byte[] setGPSContent(GpsInfo info) {
//        time, len, satelliteNumber, point, speed, gpsStateDir
        return setGPSContent(info.getTime(), info.getLen(), info.getSatelliteNumber(), info.getPoint(), info.getSpeed(), info.getGpsStateDir(), info.getExt_content());

    }


    /**
     * GPS查询地址
     *
     * @param info gps 信息
     *             语言在gps的预留扩展位里面
     * @return
     */
    public static byte[] setGSPqueryAddress(GpsInfo info) {
        return setGPSAddressContent(info.getTime(), info.getLen(), info.getSatelliteNumber(), info.getPoint(), info.getSpeed(), info.getGpsStateDir(), info.getExt_content(), info.getmPhone());

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
        byte[] latitude_bytes = BinaryUtils.getBytes((int) latitude_d, GpsInfo.point_L / 2);

        EarthPoint.Longitude longitude = point.getLongitude();
        double longitude_d = (longitude.getLongitude_c() * 60 + longitude.getLongitude_m()) * 30000;
        byte[] longtude_bytes = BinaryUtils.getBytes((int) longitude_d, GpsInfo.point_L / 2);

        byte[] bytes = new byte[GpsInfo.point_L];
        for (int i = 0; i < latitude_bytes.length; i++) {
            bytes[i] = latitude_bytes[i];
        }
        for (int i = 0; i < longtude_bytes.length; i++) {
            bytes[i + GpsInfo.point_L / 2] = longtude_bytes[i];
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
    private static byte[] setSpeed(int speed) {
        return BinaryUtils.getBytes(speed, GpsInfo.speed_L);
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
