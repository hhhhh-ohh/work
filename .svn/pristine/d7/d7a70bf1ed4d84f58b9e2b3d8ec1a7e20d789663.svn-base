package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: sbc-micro-service
 * @description:
 * @create: 2019-03-11 16:05
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributionGoodsChangeRequest extends BaseRequest {

    @Schema(description = "spuId,商品ID")
    @NotNull
    private String goodsId;

    @Schema(description = "是否返回规格明细")
    private Boolean showSpecFlag;

    @Schema(description = "是否返回可售性")
    private Boolean showVendibilityFlag;

    @Schema(description = "是否返回供应商商品相关信息")
    private Boolean showProviderInfoFlag;

    @Schema(description = "是否填充LM商品库存")
    private Boolean fillLmInfoFlag;

    @Schema(description = "是否包含已经删除的商品")
    @Builder.Default
    private Boolean showDeleteFlag = Boolean.FALSE;
}
