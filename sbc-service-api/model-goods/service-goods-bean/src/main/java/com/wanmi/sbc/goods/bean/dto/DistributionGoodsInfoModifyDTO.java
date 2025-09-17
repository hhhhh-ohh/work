package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DistributionGoodsInfoModifyDTO
 * 分销商品信息传输对象
 * @author chenli
 * @dateTime 2018/11/6 下午2:29
 */
@Data
@Schema
public class DistributionGoodsInfoModifyDTO implements Serializable {

    private static final long serialVersionUID = -5767321288912426912L;

    /**
     * 商品SKU编号
     */
    @NotBlank
    @Schema(description = "商品SKU编号")
    private String goodsInfoId;


    /**
     * 佣金比例
     */
    @NotNull
    @Schema(description = "佣金比例")
    private BigDecimal commissionRate;

    /**
     * 分销佣金
     */
    @NotNull
    @Schema(description = "分销佣金")
    private BigDecimal distributionCommission;

}
