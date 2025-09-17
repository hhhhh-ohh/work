package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wur
 * @className Freight
 * @description 供应商运费信息
 * @date 2021/9/9 14:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ProviderFreightVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 商家运费
     */
    @Schema(description = "商家运费")
    private BigDecimal supplierFreight;

    /**
     * 供应商ID
     */
    @Schema(description = "供应商ID")
    private Long providerId;

    /**
     * 运费承担者
     */
    @Schema(description = "运费承担者")
    private Integer bearFreight;

    /**
     * 商家承担的运费 当使用运费券是 此字段可能会有数据
     */
    @Schema(description = "商家承担的运费 当使用运费券是 此字段可能会有数据")
    private BigDecimal supplierBearFreight;

}