package com.wanmi.sbc.goods.api.request.buycyclegoodsinfo;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除周期购sku表请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoDelByIdRequest extends GoodsBaseRequest {

    private static final long serialVersionUID = -5323370288922436L;
    /**
     * skuId
     */
    @Schema(description = "skuId")
    @NotNull
    private String goodsInfoId;
}
