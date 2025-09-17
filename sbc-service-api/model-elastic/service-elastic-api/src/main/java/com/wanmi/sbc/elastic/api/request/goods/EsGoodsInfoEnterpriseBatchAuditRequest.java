package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.BatchEnterPrisePriceDTO;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Builder
public class EsGoodsInfoEnterpriseBatchAuditRequest extends BaseRequest {


    @Schema(description = "商品修改企业价参数")
    private List<BatchEnterPrisePriceDTO> batchEnterPrisePriceDTOS;

    @Schema(description = "企业购审核状态")
    private EnterpriseAuditState enterpriseAuditState;

}
