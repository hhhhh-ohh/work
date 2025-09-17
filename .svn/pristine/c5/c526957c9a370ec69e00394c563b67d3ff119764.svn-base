package com.wanmi.sbc.marketing.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wur
 * @className GiftCardTransBusinessVO
 * @description 礼品卡交易明细
 * @date 2022/12/8 18:38
 **/
@Schema
@Data
public class GiftCardTransBusinessItemVO implements Serializable {

    private static final long serialVersionUID = 4043264917929737209L;
    /**
     * 适用商品
     */
    @Schema(description = "适用商品Id")
    private String itemId;

    @Schema(description = "加价购活动Id")
    private Long preferentialMarketingId;

    /**
     * 实际支付金额
     */
    @Schema(description = "实际支付金额")
    private BigDecimal splitPrice;

    /**
     * 抵扣金额
     */
    @Schema(description = "抵扣金额")
    private BigDecimal deductionPrice;

}