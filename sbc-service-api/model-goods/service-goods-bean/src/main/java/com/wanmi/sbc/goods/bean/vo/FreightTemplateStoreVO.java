package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺运费模板
 * Created by sunkun on 2018/5/3.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class FreightTemplateStoreVO extends FreightTemplateVO {

    private static final long serialVersionUID = 6089864652175009716L;

    /**
     * 配送地id(逗号分隔)
     */
    @Schema(description = "配送地id，逗号分隔")
    private String destinationArea;

    /**
     * 配送地名称(逗号分隔)
     */
    @Schema(description = "配送地名称，逗号分隔")
    private String destinationAreaName;

    /**
     * 运费计费规则(0:满金额包邮,1:固定运费)
     */
    @Schema(description = "运费计费规则")
    private DefaultFlag freightType;

    /**
     * 满多少金额包邮
     */
    @Schema(description = "满多少金额包邮")
    private BigDecimal satisfyPrice;

    /**
     * 不满金额的运费
     */
    @Schema(description = "不满金额的运费")
    private BigDecimal satisfyFreight;

    /**
     * 固定的运费
     */
    @Schema(description = "固定的运费")
    private BigDecimal fixedFreight;

    /**
     * 店铺运费模板已选区域
     */
    @Schema(description = "店铺运费模板已选区域")
    private List<Long> selectedAreas;
}
