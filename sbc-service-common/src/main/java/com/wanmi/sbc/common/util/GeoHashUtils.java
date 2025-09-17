package com.wanmi.sbc.common.util;

import ch.hsr.geohash.GeoHash;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 * GeoHash工具类
 * @className GeoHashUtils
 * @author zhengyang
 * @date 2021/7/7 18:21
 **/
public final class GeoHashUtils {
    private GeoHashUtils(){

    }

    /***
     * 精度-米数映射
     */
    private static List<Integer> PRECISION_LIST = new ArrayList<>();
    private static final  double EARTH_RADIUS = 6378137;//赤道半径
    private static double rad(double d){
        return d * Math.PI / 180.0;
    }
    static {
        PRECISION_LIST.add(78000);
        PRECISION_LIST.add(20000);
        PRECISION_LIST.add(2400);
        PRECISION_LIST.add(610);
        PRECISION_LIST.add(76);
        PRECISION_LIST.add(19);
    }

    /***
     * 获得GeoHash值，默认12位
     * @param latitude      纬度
     * @param longitude     经度
     * @return              GeoHashBase编码
     */
    public static String getGeoHash(double latitude, double longitude){
        return getGeoHash(latitude, longitude, 12);
    }

    /***
     * 获得GeoHash值
     * @param latitude      纬度
     * @param longitude     经度
     * @param precision     精度
     * @return              GeoHashBase编码
     */
    public static String getGeoHash(double latitude, double longitude, int precision){
        return GeoHash.withCharacterPrecision(latitude, longitude, precision).toBase32();
    }

    /***
     * 获得GeoHash值
     * @param latitude      纬度
     * @param longitude     经度
     * @param meter         误差米数
     * @return              GeoHashBase编码
     */
    public static String getGeoHashByMeter(double latitude, double longitude, int meter){
        return getGeoHash(latitude, longitude, getPrecisionByMeter(meter));
    }

    /**
     * 根据误差米数获得精度位数
     * @param meter 误差米数
     * @return      精度位数
     */
    private static int getPrecisionByMeter(Integer meter){
        // 默认最大精度
        if(Objects.isNull(meter)){
            return 12;
        }
        // 最小3位精度
        if(meter >= PRECISION_LIST.get(0)){
            return 3;
        }
        // 最大12位精度
        if(meter < PRECISION_LIST.get(5)){
            return 12;
        }
        for (int i = 0; i < PRECISION_LIST.size(); i++) {
            if(meter >= PRECISION_LIST.get(i)){
                return i + 3;
            }
        }
        return 12;
    }

    /***
     * 获得两组经纬度之间的距离
     * @param lon1      经度1
     * @param lat1      纬度1
     * @param lon2      经度2
     * @param lat2      纬度2
     * @return          距离差-单位米
     */
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 *Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        // 单位米
        return s * EARTH_RADIUS;
    }
}
