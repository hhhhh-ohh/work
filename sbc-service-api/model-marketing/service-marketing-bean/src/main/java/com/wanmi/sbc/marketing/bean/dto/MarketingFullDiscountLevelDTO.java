package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>营销满折多级优惠实体类</p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
public class MarketingFullDiscountLevelDTO implements Serializable {

    private static final long serialVersionUID = 6116272116001406708L;

    /**
     * 满折级别Id
     */
    @Schema(description = "满折级别Id")
    private Long discountLevelId;

    /**
     * 满折ID
     */
    @Schema(description = "营销ID")
    private Long marketingId;

    /**
     * 满金额
     */
    @Schema(description = "满金额")
    private BigDecimal fullAmount;

    /**
     * 满数量
     */
    @Schema(description = "满数量")
    private Long fullCount;

    /**
     * 满金额|数量后折扣
     */
    @Schema(description = "满金额|数量后折扣")
    private BigDecimal discount;

}
