package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description   订单运费模板信息
 * @author  wur
 * @date: 2022/7/15 10:10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeFreightTemplateVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

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

    @Schema(description = "目标商品")
    private List<String> skuIdList;

    @Schema(description = "运费")
    private BigDecimal feright;
}
