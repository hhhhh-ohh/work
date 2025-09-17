package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className GetFreightInGoodsInfoResponse
 * @description TODO
 * @date 2022/7/6 9:23
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFreightInGoodsInfoResponse implements Serializable {

    @Schema(description = "运费模板Id，VOP linkedmall返回空 用于凑单页")
    private Long freightTemplateId;

    @Schema(description = "免运费规则Id，用于凑单页 如果有返回凑单页查询会使用")
    private Long freeId;

    @Schema(description = "使用的运费模板类别(0:店铺运费,1:单品运费) 如果有返回凑单页查询会使用")
    private DefaultFlag freightTemplateType = DefaultFlag.YES;

    @Schema(description = "运费描述")
    private String freightDescribe;

    @Schema(description = "是否支持凑单")
    private Boolean collectFlag;

    @Schema(description = "供应商Id 如果有返回凑单页查询会使用")
    private Long providerStoreId;

}