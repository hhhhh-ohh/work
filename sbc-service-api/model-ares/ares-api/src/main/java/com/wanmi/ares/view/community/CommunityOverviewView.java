package com.wanmi.ares.view.community;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author edz
 * @className CommunityOverviewView
 * @description TODO
 * @date 2023/8/18 10:43
 **/
@Data
public class CommunityOverviewView implements Serializable {

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate createDate;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 支付订单数
     */
    private Long payOrderNum;

    /**
     * 支付总金额
     */
    private BigDecimal payTotalPrice;

    /**
     * 退单数
     */
    private Long returnNum;

    /**
     * 退单金额
     */
    private BigDecimal returnTotalPrice;

    /**
     * 参团会员数量
     */
    private Long customerNum;

    /**
     * 参团团长数
     */
    private Long leaderNum;

    /**
     * 帮卖订单数
     */
    private Long assistOrderNum;

    /**
     * 帮卖订单总额
     */
    private BigDecimal assistOrderTotalPrice;

    /**
     * 帮卖退单数
     */
    private Long assistReturnNum;

    /**
     * 帮卖退单总额
     */
    private BigDecimal assistReturnTotalPrice;

    /**
     * 帮卖订单占比
     */
    private BigDecimal assistOrderRatio;

    /**
     * 团长发展会员数
     */
    private Long leaderCustomerNum;

    /**
     * 已入账佣金
     */
    private BigDecimal commissionReceived;

    /**
     * 已入账自提佣金
     */
    private BigDecimal commissionReceivedPickup;

    /**
     * 已入账帮卖佣金
     */
    private BigDecimal commissionReceivedAssist;

    /**
     * 未入账佣金
     */
    private BigDecimal commissionPending;

    /**
     * 未入账自提佣金
     */
    private BigDecimal commissionPendingPickup;

    /**
     * 未入账帮卖佣金
     */
    private BigDecimal commissionPendingAssist;
}
