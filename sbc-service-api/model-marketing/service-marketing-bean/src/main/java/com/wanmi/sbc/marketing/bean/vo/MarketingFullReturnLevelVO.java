package com.wanmi.sbc.marketing.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author 黄昭
 * @className MarketingFullReturnLevelVO
 * @description TODO
 * @date 2022/4/7 18:38
 **/
@Schema
@Data
public class MarketingFullReturnLevelVO implements Serializable {
    private static final long serialVersionUID = -6248980667548846884L;

    /**
     *  满返多级促销Id
     */
    private Long returnLevelId;

    /**
     *  满返Id
     */
    private Long marketingId;

    /**
     *  满金额赠
     */
    private BigDecimal fullAmount;

    /**
     * 满返赠券明细
     */
    @Schema(description = "满返赠券明细列表")
    private List<MarketingFullReturnDetailVO> fullReturnDetailList;
}