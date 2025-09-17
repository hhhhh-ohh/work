package com.wanmi.sbc.marketing.coupon.model.entity;

import com.wanmi.sbc.marketing.bean.enums.CouponDiscountMode;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:33 2018/9/29
 * @Description: 订单优惠券
 */
@Data
@Builder
public class TradeCoupon {

    /**
     * 优惠券码id
     */
    private String couponCodeId;

    /**
     * 优惠券码值
     */
    private String couponCode;

    /**
     * 优惠券关联的商品
     */
    private List<String> goodsInfoIds;

    /**
     * 优惠券类型
     */
    private CouponType couponType;

    /**
     * 优惠券营销类型 0满减券 1满折券 2运费券
     */
    private CouponMarketingType couponMarketingType;

    /**
     * 优惠券优惠方式 0减免 1包邮
     */
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    private BigDecimal maxDiscountLimit;

    /**
     * 优惠金额
     */
    private BigDecimal discountsAmount;

    /**
     * 购满多少钱
     */
    private BigDecimal fullBuyPrice;

}
