package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Builder
public class EsGoodsInfoEnterpriseAuditRequest extends BaseRequest {

    /**
     * 批量SkuID
     */
    @Schema(description = "批量SkuID")
    @NonNull
    private List<String> goodsInfoIds;

    /**
     * 企业购商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过
     */
    @Schema(description = "企业购商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销")
    private EnterpriseAuditState enterPriseAuditStatus;


    /**
     * 审核不通过原因
     */
    @Schema(description = "审核不通过原因")
    private String enterPriseGoodsAuditReason;

}
