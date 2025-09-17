package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.dto.DistributionGoodsInfoModifyDTO;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsGoodsInfoModifyDistributionCommissionRequest extends BaseRequest {

    /**
     * 批量SkuID和分销佣金
     */
    @Schema(description = "批量SkuID和佣金比例")
    private List<DistributionGoodsInfoModifyDTO> distributionGoodsInfoDTOList;

    /**
     * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
     */
    @Schema(description = "分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销")
    private DistributionGoodsAudit distributionGoodsAudit;


    /**
     * 分销商品状态，配合分销开关使用
     */
    @Schema(description = "分销商品状态，配合分销开关使用")
    private Integer distributionGoodsStatus;

    /**
     * 分销创建时间
     */
    @Schema(description = "分销创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime distributionCreateTime;


}
