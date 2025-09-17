package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoCachesByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -4870215527587149437L;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    @NotEmpty
    private List<String> goodsInfoIds;
}
