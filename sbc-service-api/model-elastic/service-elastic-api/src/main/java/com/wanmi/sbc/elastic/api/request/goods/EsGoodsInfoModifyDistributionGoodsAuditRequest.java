package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsGoodsInfoModifyDistributionGoodsAuditRequest extends BaseRequest {

    /**
     * 批量SkuID
     */
    @Schema(description = "批量SkuID")
    @NonNull
    private List<String> goodsInfoIds;

    /**
     * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
     */
    @Schema(description = "分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销")
    private Integer distributionGoodsAudit;


    /**
     * 审核不通过原因
     */
    @Schema(description = "审核不通过原因")
    private String distributionGoodsAuditReason;

}
