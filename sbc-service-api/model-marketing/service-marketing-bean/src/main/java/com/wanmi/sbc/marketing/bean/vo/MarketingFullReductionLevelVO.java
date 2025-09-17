package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 满减
 */
@Schema
@Data
public class MarketingFullReductionLevelVO extends BasicResponse {

    private static final long serialVersionUID = 2824604859587896961L;

    /**
     *  满减级别Id
     */
    @Schema(description = "满减级别主键Id")
    private Long reductionLevelId;

    /**
     *  满赠Id
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
