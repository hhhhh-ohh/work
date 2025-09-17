package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>满赠</p>
 * author: weiwenhao
 * Date: 2020-04-13
 */
@Schema
@Data
public class MarketingBuyoutPriceLevelDTO implements Serializable {

    private static final long serialVersionUID = 1478291590016883513L;
    /**
     *  一口价Id
     */
    @Schema(description = "打包级别Id")
    private Long reductionLevelId;

    /**
     *  一口价营销ID
     */
    @Schema(description = "打包一口价营销ID")
    private Long marketingId;

    /**
     * 满金额
     */
    @Schema(description = "满金额")
    private BigDecimal fullAmount;


    /**
     *任选数量
     */
    @Schema(description = "任选数量")
    private Long choiceCount;

}
