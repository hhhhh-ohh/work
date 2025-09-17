package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * com.wanmi.sbc.goods.api.request.info.DistributionGoodsDeleteRequest
 * 删除分销商品请求对象
 *
 * @author CHENLI
 * @dateTime 2019/2/19 上午9:33
 */
@Schema
@Data
public class DistributionGoodsDeleteRequest extends BaseRequest {

    private static final long serialVersionUID = 5260130003195870475L;

    /**
     * 删除分销商品，skuId
     */
    @NotBlank
    @Schema(description = "删除分销商品，skuId")
    private String goodsInfoId;

    /**
     * sku编码
     */
    @Schema(description = "sku编码")
    private String goodsInfoNo;
}
