package com.wanmi.sbc.goods.api.request.enterprise.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业商品审核
 * @author by 柏建忠 on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseAuditCheckRequest extends BaseRequest {

    private static final long serialVersionUID = 8898609669271054413L;

    /**
     * skuId
     */
    @Schema(description = "skuId")
    @NotEmpty
    private String goodsInfoId;

    /**
     * 企业商品的状态
     */
    @Schema(description = "企业商品的状态，0：无状态 1：待审核 2：已审核 3：审核未通过")
    @NotNull
    private EnterpriseAuditState enterpriseAuditState;

    /**
     * 企业购商品审核被驳回的原因
     */
    @Schema(description = "企业购商品审核被驳回的原因")
    private String enterPriseGoodsAuditReason;

}
