package com.hai.yun.bean;

public class EarthPoint {
//经度0°——180°（东行,标注E）0°——180°（西行,标注W）
//纬度0°——90°N、0°——90°S

    private Latitude latitude;//纬度
    private Longitude longitude;//经度

    /**
     * @param latitude_c  纬度 °
     * @param latitude_m  纬度 ′
     * @param longitude_c 经度 °
     * @param longitude_m 经度 ′
     */
    public EarthPoint(int latitude_c, double latitude_m, int longitude_c, double longitude_m) {
        this.latitude = new Latitude(latitude_c, latitude_m);
        this.longitude = new Longitude(longitude_c, longitude_m);
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    public class Latitude {
        int latitude_c;//度
        double latitude_m;//分

        public Latitude(int latitude_c, double latitude_m) {
            this.latitude_c = latitude_c;
            this.latitude_m = latitude_m;
        }

        public int getLatitude_c() {
            return latitude_c;
        }

        public double getLatitude_m() {
            return latitude_m;
        }
    }

    public class Longitude {
        int longitude_c;//度
        double longitude_m;//分

        public Longitude(int longitude_c, double longitude_m) {
            this.longitude_c = longitude_c;
            this.longitude_m = longitude_m;
        }

        public int getLongitude_c() {
            return longitude_c;
        }

        public double getLongitude_m() {
            return longitude_m;
        }
    }

}