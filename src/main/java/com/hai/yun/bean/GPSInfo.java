package com.hai.yun.bean;

/**
 * GPS信息
 */
public class GPSInfo {

    private int run_dir_c;// 运行方向度数 0-360°
    private int latitude_dir;// 纬度方向 0：南纬 1：北纬
    private int longitude_dir;// 经度方向 0：东经 1：西经
    private int gps_state;// GPS定位状态  0：GPS不定位 1：GPS已定位
    private int gps_time;// GPS类型  0：实时GPS  1差分GPS

    public GPSInfo(int run_dir_c, int latitude_dir, int longitude_dir, int gps_state, int gps_time) {
        this.run_dir_c = run_dir_c;
        this.latitude_dir = latitude_dir;
        this.longitude_dir = longitude_dir;
        this.gps_state = gps_state;
        this.gps_time = gps_time;
    }

    public int getRun_dir_c() {
        return run_dir_c;
    }

    public int getLatitude_dir() {
        return latitude_dir;
    }

    public int getLongitude_dir() {
        return longitude_dir;
    }

    public int getGps_state() {
        return gps_state;
    }

    public int getGps_time() {
        return gps_time;
    }
}
