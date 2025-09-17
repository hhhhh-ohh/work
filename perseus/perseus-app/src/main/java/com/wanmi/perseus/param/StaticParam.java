package com.wanmi.perseus.param;

import com.wanmi.ares.constants.MQConstant;

import java.util.Random;

/**
 * 静态参数
 * Author: bail
 * Time: 2017/9/29.13:16
 */
public class StaticParam {
    private StaticParam(){}

    public static final String QUEUE_NAME = MQConstant.Q_FLOW_CUSTOMER_SYNCHRONIZATION;//mq的目的地名称
    public static final String MARKETING_SKU_QUEUE_NAME =  MQConstant.Q_FOLLOW_MARKETING_SKU; //营销流量的mq

    public static final int EXPIRE_HOUR = 2;//失效时间的小时
    public static final int EXPIRE_MINUTE = 0;//失效时间的分钟'

    public static final String END_ALL = "ALL";//三个端总计
    public static final String END_PC = "PC";//pc端
    public static final String END_H5 = "H5";//h5端
    public static final String END_APP = "APP";//app端
    public static final String END_MINIPROGRAM = "MINIPROGRAM";//小程序（小程序内嵌h5）


    public static final String PV_KEY = "%s:pv:%s";//平台总的 分端统计每日pv 如:pc:pv:20170929
    public static final String UV_KEY = "%s:uv:%s";//平台总的 分端统计每日uv 如:pc:uv:20170929
    public static final String SKU_PV_KEY = "%s:pv:%s:sku:%s:shopid:%s";//某sku页 分端统计每日pv 如:pc:pv:20170929:sku:esa3df2kfakdf5es2fj
    public static final String SKU_UV_KEY = "%s:uv:%s:sku:%s:shopid:%s";//某sku页 分端统计每日uv 如:pc:uv:20170929:sku:esa3df2kfakdf5es2fj(pc端) , all:uv:20170929:sku:esa3df2kfakdf5es2fj(所有端汇总)
    public static final String PLAT_SKU_PV_KEY = "%s:pv:%s:platsku";//平台中sku页总的 分端统计每日pv 如:pc:pv:20170929:platsku
    public static final String PLAT_SKU_UV_KEY = "%s:uv:%s:platsku";//平台中sku页总的 分端统计每日uv 如:pc:uv:20170929:platsku(pc端) , all:uv:20170929:platsku(所有端汇总)
    public static final String SHOP_SKU_PV_KEY = "%s:pv:%s:shopsku:%s";//某店铺中sku页总的 分端统计每日pv 如:pc:pv:20170921:shopsku:2
    public static final String SHOP_SKU_UV_KEY = "%s:uv:%s:shopsku:%s";//某店铺中sku页总的 分端统计每日uv 如:pc:uv:20170921:shopsku:2(pc端) , all:uv:20170921:shopsku:2(所有端汇总)
    public static final String SHOP_PV_KEY = "%s:pv:%s:shop:%s";//某店铺总的 分端统计每日pv 如:pc:pv:20170921:shop:2
    public static final String SHOP_UV_KEY = "%s:uv:%s:shop:%s";//某店铺总的 分端统计每日uv 如:pc:uv:20170921:shop:2(pc端) , all:uv:20170921:shop:2(所有端汇总)
    public static final String MARKETING_SKU_PV_KEY = "mskupv:%s:mid:%s:mtype:%s:sku:%s:shopid:%s"; //营销活动SKU对应的pv 统计每日pv 如：mskupv:20210201:mid:12:mtype:0:sku:esa3df2kfakdf5es2fj
    public static final String MARKETING_PV_KEY = "mpv:%s:mid:%s:mtype:%s"; //营销活动对应的pv 统计每日pv 如：mpv:20210201:mid:12:mtype:0
    public static final String MARKETING_SKU_UV_KEY = "mskuuv:%s:mid:%s:mtype:%s:sku:%s:shopid:%s"; //营销活动SKU对应的uv 统计每日uv 如：mskuuv:20210201:mid:12:mtype:0:sku:esa3df2kfakdf5es2fj

    public static final int PAGE_SIZE = 100; //分页大小
}
