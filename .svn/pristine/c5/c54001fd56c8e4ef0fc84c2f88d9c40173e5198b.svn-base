package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.ValuationType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description   购物车单品运费模板非免运费规则信息
 * @author  wur
 * @date: 2022/7/12 11:36
 * @return
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsExpressCartVO implements Serializable {

    private static final long serialVersionUID = -2243723680863364216L;

    /**
     * 店铺id
     */
    @Schema(description = "规则Id 用于凑单页")
    private Long expressId;

    /**
     * 计价方式(0:按件数,1:按重量,2:按体积)
     */
    @Schema(description = "计价方式(0:按件数,1:按重量,2:按体积)")
    private ValuationType valuationType;

    /**
     * 首件/重/体积
     */
    @Schema(description = "首件/重/体积")
    private BigDecimal freightStartNum;

    /**
     * 对应于首件/重/体积的起步价
     */
    @Schema(description = "对应于首件/重/体积的起步价")
    private BigDecimal freightStartPrice;

    /**
     * 续件/重/体积
     */
    @Schema(description = "续件/重/体积")
    private BigDecimal freightPlusNum;

    /**
     * 对应于续件/重/体积的价格
     */
    @Schema(description = "对应于续件/重/体积的价格")
    private BigDecimal freightPlusPrice;

}
