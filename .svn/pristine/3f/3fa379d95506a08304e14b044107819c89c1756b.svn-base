package com.wanmi.sbc.empower.bean.vo.channel.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xufan
 * @Date: 2020/3/7 15:24
 * @Description: 运单信息
 *
 */
@Data
public class VopWaybillCode implements Serializable {

    private static final long serialVersionUID = 3530862157913824020L;
    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 父订单号。
     * 此字段为0 未拆单
     */
    private Long parentId;

    /**
     * 承运商。可以为“京东快递”或者商家自行录入的承运商名称
     */
    private String carrier;

    /**
     * 运单号
     */
    private String deliveryOrderId;
}
