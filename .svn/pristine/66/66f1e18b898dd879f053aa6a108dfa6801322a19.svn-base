package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: xufeng
 * @Description: 营销满返多级优惠实体
 * @Date: 2022-04-06 14:02
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullReturnLevelDTO implements Serializable {

    private static final long serialVersionUID = 4701841638960976835L;
    /**
     *  满返多级促销Id
     */
    @Schema(description = "满返多级促销Id")
    private Long returnLevelId;

    /**
     *  满返Id
     */
    @Schema(description = "营销id")
    private Long marketingId;

    /**
     *  满金额赠
     */
    @Schema(description = "满金额赠")
    private BigDecimal fullAmount;

    /**
     * 满返赠品明细
     */
    @Schema(description = "满返赠品明细列表")
    private List<FullReturnDetailDTO> fullReturnDetailList;

}
