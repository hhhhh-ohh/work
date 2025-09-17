package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * com.wanmi.sbc.goods.api.request.info.DistributionGoodsCheckRequest
 * 审核分销商品请求对象
 *
 * @author CHENLI
 * @dateTime 2019/2/19 上午9:33
 */
@Schema
@Data
public class DistributionGoodsCheckRequest extends BaseRequest {

    private static final long serialVersionUID = -3577642766575572031L;

    /**
     * 审核分销商品，skuId
     */
    @NotBlank
    @Schema(description = "审核分销商品，skuId")
    private String goodsInfoId;
}
