package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class GoodsByTypeRequest extends BaseQueryRequest {

    /**
     * 商品类型
     */
    @Schema(description = "商品类型")
    @NotNull
    private Integer goodsType;
}
