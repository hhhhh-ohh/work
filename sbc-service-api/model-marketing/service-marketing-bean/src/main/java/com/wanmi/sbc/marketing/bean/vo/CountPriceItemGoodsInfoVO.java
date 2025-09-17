package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
public class CountPriceItemGoodsInfoVO extends BasicResponse {
    private static final long serialversionuid = 1L;

    /**
     * id
     */
    @Schema(description = "商品ID")
    private String goodsInfoId;

    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 市场价
     */
    @Schema(description = "市场价")
    private BigDecimal price;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Long num;


    @Schema(description = "优惠后金额")
    private BigDecimal splitPrice;

    /**
     * 总优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountsAmount = BigDecimal.ZERO;

    /**
     * 活动信息
     */
    @Schema(description = "活动信息")
    private List<CountPriceItemMarketingVO> marketingList = new ArrayList<>();

    @Schema(description = "优惠券信息")
    private List<CountPriceItemCouponVO> couponList = new ArrayList<>();
}