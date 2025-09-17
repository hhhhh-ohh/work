package com.wanmi.sbc.empower.api.constant;

/***
 * 收货状态声明
 * @className DeliveryStatusConstant
 * @author zhengyang
 * @date 2022/1/24 19:39
 **/
public final class DeliveryStatusConstant {
    private DeliveryStatusConstant(){}

    /**
     * 待取货
     */
    public static final int WAIT_TAKE = 2;


    /**
     * 配送中
     */
    public static final int DELIVERY = 3;

    /**
     * 已完成
     */
    public static final int FINISH = 4;

    /**
     * 已取消
     */
    public static final int CANCEL = 5;
}
