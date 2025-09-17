package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>第二件半价</p>
 * author: weiwenhao
 * Date: 2020-05-22
 */
@Schema
@Data
public class MarketingHalfPriceSecondPieceLevelVO extends BasicResponse {

    private static final long serialVersionUID = -3346008943157501004L;

    /**
     *  第二件半价规则Id
     */
    @Schema(description = "打包级别Id")
    private Long id;

    /**
     *  第二件半价营销ID
     */
    @Schema(description = "打包一口价营销ID")
    private Long marketingId;

    /**
     *  第二件半价件数
     */
    @Schema(description = "number")
    private Long number;

    /**
     *  第二件半价折数
     */
    @Schema(description = "discount")
    private BigDecimal discount;

}
