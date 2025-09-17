package com.wanmi.sbc.goods.api.request.buycyclegoods;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除周期购spu表请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsDelByIdRequest extends GoodsBaseRequest {


    private static final long serialVersionUID = -6790615630121970273L;
    /**
     * spuId
     */
    @Schema(description = "spuId")
    @NotNull
    private Long id;
}
