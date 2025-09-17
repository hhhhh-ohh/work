package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.marketing.bean.enums.CouponType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:33 2018/9/29
 * @Description: 订单优惠券
 */
@Schema
@Data
public class TradeCouponDTO implements Serializable {

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
    @Schema(description = "优惠券关联的商品集合")
    private List<String> goodsInfoIds;

    /**
     * 优惠券类型
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;

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

}
