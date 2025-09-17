package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.CouponDiscountMode;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.MarketingCustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wur
 * @className CountCouponPriceVO
 * @description TODO
 * @date 2022/2/28 15:10
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountCouponPriceVO extends BasicResponse {
    private static final long serialversionuid = 1L;
    /**
     * 优惠券码id
     */
    @Schema(description = "优惠券码id")
    private String couponCodeId;

    /**
     * 优惠券码值
     */
    @Schema(description = "优惠券码值")
    private String couponCode;

    /**
     * 优惠券关联的商品
     */
    @Schema(description = "优惠券关联的商品id集合")
    private List<String> goodsInfoIds;

    /**
     * 优惠券类型
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;

    /**
     * 优惠券营销活动
     */
    @Schema(description = "优惠券营销活动")
    private CouponMarketingType couponMarketingType;

    /**
     * 运费券是否包邮
     */
    @Schema(description = "运费券是否包邮")
    private CouponDiscountMode couponDiscountMode;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountsAmount;

    /**
     * 购满多少钱
     */
    @Schema(description = "购满多少钱")
    private BigDecimal fullBuyPrice;

    /**
     * 客户目标类型
     */
    @Schema(description = "客户目标类型")
    private MarketingCustomerType marketingCustomerType;
}