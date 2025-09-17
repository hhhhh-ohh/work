package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  营销算价请求 - 订单商品信息
 * @author  wur
 * @date: 2022/2/24 10:37
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountCouponPriceGoodsInfoDTO extends CountPriceGoodsInfoDTO {
    private static final long serialversionuid = 1L;

    /**
     * 商户Id
     */
    @Schema(description = "商户Id")
    @NotBlank
    private Long storeId;

    @Schema(description = "活动ID")
    private Long marketingId;

}