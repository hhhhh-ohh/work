package com.wanmi.ares.view.coupon;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName CouponOverviewView
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/14 11:33
 * @Version 1.0
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponEffectView {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;


    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal payMoney;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountMoney;

    /**
     * roi
     */
    @Schema(description = "roi")
    private BigDecimal roi;

    /**
     * 支付件数
     */
    @Schema(description = "支付件数")
    private Long payGoodsCount;

    /**
     * 支付订单数
     */
    @Schema(description = "支付订单数")
    private Long payTradeCount;

    /**
     * 连带率
     */
    @Schema(description = "连带率")
    private BigDecimal jointRate;

    /**
     * 老用户数
     */
    @Schema(description = "老用户数")
    private Long oldCustomerCount;

    /**
     * 新用户数
     */
    @Schema(description = "新用户数")
    private Long newCustomerCount;

    /**
     * 营销支付人数
     */
    @Schema(description = "营销支付人数")
    private Long payCustomerCount;

    /**
     * 领取张数
     */
    @Schema(description = "领取人/张")
    private String acquireData;


    /**
     * 使用张数
     */
    @Schema(description = "使用人/张")
    private String useData;


    /**
     * 使用率
     */
    @Schema(description = "使用率")
    private BigDecimal useRate;


    // 客单价
    @Schema(description = "客单价）")
    private BigDecimal customerPrice;


    @Schema(description = "供货价")
    private BigDecimal supplyPrice;


}
