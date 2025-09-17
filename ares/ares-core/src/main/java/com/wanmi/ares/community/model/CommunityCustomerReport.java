package com.wanmi.ares.community.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author edz
 * @className CommunitycustomerReport
 * @description TODO
 * @date 2023/8/10 17:49
 **/
@Data
public class CommunityCustomerReport {
    /**
     * 时间
     */
    private LocalDate createDate;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 支付订单数
     */
    private Long payOrderNum;

    /**
     * 支付金额
     */
    private BigDecimal payTotalPrice;

    /**
     * 帮卖订单数
     */
    private Long assistOrderNum;

    /**
     * 帮卖订单金额
     */
    private BigDecimal assistOrderTotalPrice;
}
