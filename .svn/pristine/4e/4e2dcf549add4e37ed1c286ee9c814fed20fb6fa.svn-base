package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>营销满减多级优惠实体</p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
public class MarketingFullReductionLevelDTO implements Serializable {

    private static final long serialVersionUID = 833673835203720781L;

    /**
     *  满减级别Id
     */
    @Schema(description = "满减级别主键Id")
    private Long reductionLevelId;

    /**
     *  满减Id
     */
    @Schema(description = "满减营销Id")
    private Long marketingId;

    /**
     *  满金额
     */
    @Schema(description = "满金额")
    private BigDecimal fullAmount;

    /**
     *  满数量
     */
    @Schema(description = "满数量")
    private Long fullCount;

    /**
     *  满金额|数量后减多少元
     */
    @Schema(description = "满金额|数量后减多少元")
    private BigDecimal reduction;
}
