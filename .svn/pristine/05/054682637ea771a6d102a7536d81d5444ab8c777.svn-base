package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.info.DistributionGoodsBatchCheckRequest
 * 批量审核分销商品请求对象
 *
 * @author CHENLI
 * @dateTime 2019/2/19 上午9:33
 */
@Schema
@Data
public class DistributionGoodsBatchCheckRequest extends BaseRequest {

    private static final long serialVersionUID = 2852743900314970768L;

    /**
     * 批量审核分销商品，skuIds
     */
    @Schema(description = "批量skuIds")
    @NotNull
    private List<String> goodsInfoIds;
}
