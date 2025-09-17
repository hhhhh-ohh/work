package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.CouponType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*
 * @description  营销算价返回
 * @author  wur
 * @date: 2022/2/24 10:37
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPriceItemCouponVO extends BasicResponse {
    private static final long serialversionuid = 1L;

    /**
     * 优惠券码id
     */
    private String couponCodeId;

    /**
     * 优惠券码
     */
    private String couponCode;

    /**
     * 优惠券类型
     */
    private CouponType couponType;

    /**
     * 除去优惠金额后的商品均摊价
     */
    private BigDecimal splitPrice;

    /**
     * 优惠金额
     */
    private BigDecimal reducePrice;
}