package com.hai.yun.bean;

import org.joda.time.DateTime;

/**
 * GPS信息
 * listNo          信息序列号
 * time            时间
 * len             信息长度
 * satelliteNumber 卫星数
 * point           经纬度
 * speed           速度
 * gpsStateDir     gps状态信息
 */
public class GpsInfo {
    private int listNo;//信息序列号
    private DateTime time;//时间
    private int len;//信息长度
    private int satelliteNumber;//卫星数
    private EarthPoint point;//经纬度
    private int speed;//速度
    private GpsStateDir gpsStateDir;//gps状态信息
    private byte[] ext_content = null;//预留扩展位信息


    public GpsInfo addListNo(int listNo) {
        this.listNo = listNo;
        return this;
    }

    public GpsInfo addTime(DateTime time) {
        this.time = time;
        return this;
    }

    public GpsInfo addLen(int len) {
        this.len = len;
        return this;
    }

    public GpsInfo addSatelliteNumber(int satelliteNumber) {
        this.satelliteNumber = satelliteNumber;
        return this;
    }

    public GpsInfo addPoint(EarthPoint point) {
        this.point = point;
        return this;
    }

    public GpsInfo addSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public GpsInfo addGpsStateDir(GpsStateDir gpsStateDir) {
        this.gpsStateDir = gpsStateDir;
        return this;
    }

    public GpsInfo addExt_content(byte[] ext_content) {
        this.ext_content = ext_content;
        return this;
    }


    public int getListNo() {
        return listNo;
    }

    public DateTime getTime() {
        return time;
    }

    public int getLen() {
        return len;
    }

    public int getSatelliteNumber() {
        return satelliteNumber;
    }

    public EarthPoint getPoint() {
        return point;
    }

    public int getSpeed() {
        return speed;
    }

    public GpsStateDir getGpsStateDir() {
        return gpsStateDir;
    }

    public byte[] getExt_content() {
        return ext_content;
    }


}
