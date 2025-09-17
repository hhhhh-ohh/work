package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 营销满折多级优惠实体类
 */
@Schema
@Data
public class MarketingFullDiscountLevelVO extends BasicResponse {

    private static final long serialVersionUID = -8390818474791498333L;
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
