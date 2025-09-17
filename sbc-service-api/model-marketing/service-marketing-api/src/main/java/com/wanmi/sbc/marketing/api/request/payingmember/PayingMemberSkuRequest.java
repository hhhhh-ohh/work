package com.wanmi.sbc.marketing.api.request.payingmember;

import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className PayingMemberSkuRequest
 * @description
 * @date 2022/5/20 9:43 AM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PayingMemberSkuRequest implements Serializable {
    private static final long serialVersionUID = -982154744467554864L;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    @NotBlank
    private String goodsInfoId;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    @NotNull
    private BigDecimal goodsPrice;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /**
     * 商家类型 0、自营 1、第三方
     */
    @Schema(description = "商家类型 0、自营 1、第三方")
    @NotNull
    private BoolFlag companyType;
}
