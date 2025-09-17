package com.wanmi.sbc.goods.api.request.enterprise.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 批量新增企业商品请求
 * @author baijianzhong
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseAuditStatusBatchRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -8312305040718761890L;

    /**
     * goodsInfoIds
     */
    @Schema(description = " 批量修改企业价格 ")
    @NotNull
    List<String> goodsInfoIds;

    /**
     *  企业商品审核 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = " 1：待审核 2：已审核 3：审核未通过")
    @NotNull
    private EnterpriseAuditState enterpriseGoodsAuditFlag;

    /**
     * 审核驳回的原因
     */
    @Schema(description = " 驳回的原因 ")
    private String enterPriseGoodsAuditReason;

}
