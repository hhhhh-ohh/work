package com.wanmi.sbc.goods.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author houshuai
 * @date 2022/2/9 18:00
 * @description <p> 预售导入商品信息 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
public class ExcelPresaleGoodsInfoVO extends GoodsInfoVO {
    private static final long serialVersionUID = 1L;

    /**
     * 预售价格
     */
    @Schema(description = "预售价格")
    private BigDecimal presalePrice;

    /**
     * 预售数量
     */
    @Schema(description = "预售数量")
    private Long num;

    /**
     * 定金
     */
    @Schema(description = "定金")
    private BigDecimal handSelPrice;

    /**
     * 定金
     */
    @Schema(description = "定金膨胀")
    private BigDecimal inflationPrice;

}
