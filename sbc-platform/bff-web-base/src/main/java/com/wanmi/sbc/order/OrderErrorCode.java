package com.wanmi.sbc.order;

/**
 * @author edz
 * @className OrderErrorCode
 * @description TODO
 * @date 2021/8/9 17:08
 **/
public final class OrderErrorCode {

    /**
     * 请完善收货地址
     */
    public final static String COMPLETE_ADDRESS = "K-220001";

    /**
     * 非法越权操作
     */
    public final static String ULTRA_VIRES = "K-220002";

    /**
     * 添加购物车校验-该商品不支持加入购物车
     */
    public final static String ORDER_SAME_CITY_NOT_SUPPORT_CONFIRM = "K-050451";
}
