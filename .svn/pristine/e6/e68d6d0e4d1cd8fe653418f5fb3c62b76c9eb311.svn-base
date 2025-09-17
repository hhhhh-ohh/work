package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * com.wanmi.sbc.goods.api.request.info.DistributionGoodsRefuseRequest
 * 驳回分销商品请求对象
 *
 * @author CHENLI
 * @dateTime 2019/2/19 上午9:33
 */
@Schema
@Data
public class DistributionGoodsRefuseRequest extends BaseRequest {

    private static final long serialVersionUID = 6161932349967876456L;

    /**
     * 审核分销商品，skuId
     */
    @Schema(description = "审核分销商品，skuId")
    @NotBlank
    private String goodsInfoId;

    /**
     * 分销商品审核不通过原因
     */
    @NotBlank
    @Schema(description = "分销商品审核不通过原因")
    private String distributionGoodsAuditReason;
}
