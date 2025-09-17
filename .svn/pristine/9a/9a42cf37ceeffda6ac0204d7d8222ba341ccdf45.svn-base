package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className GoodsInfoFilterCycleRequest
 * @description
 * @date 2022/10/31 17:53
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoFilterCycleRequest extends BaseRequest {
    private static final long serialVersionUID = -4029722441279284624L;

    /**
     * 商品ids
     */
    @NotEmpty
    @Schema(description = "商品ids")
    private List<String> goodsInfoIds;
}
