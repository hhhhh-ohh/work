package com.wanmi.sbc.goods.api.request.buycyclegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className BuyCycleGoodsBySkuIdRequest
 * @description
 * @date 2022/10/17 1:51 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsBySpuIdRequest extends BaseRequest {

    private static final long serialVersionUID = -6023152048339508917L;
    /**
     * 商品id
     */
    @Schema(description = "商品id")
    @NotBlank
    private String spuId;
}
