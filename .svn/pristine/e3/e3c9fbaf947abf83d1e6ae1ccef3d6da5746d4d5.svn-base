package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.goods.bean.enums.ConditionType;
import com.wanmi.sbc.goods.bean.enums.ValuationType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description   购物车单品运费模板免运费规则信息
 * @author  wur
 * @date: 2022/7/12 11:36
 * @return
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsFreeCartVO implements Serializable {

    private static final long serialVersionUID = -2243723680863364216L;

    /**
     * 店铺id
     */
    @Schema(description = "规则Id 用于凑单页")
    private Long freeId;

    /**
     * 计价方式(0:按件数,1:按重量,2:按体积)
     */
    @Schema(description = "计价方式(0:按件数,1:按重量,2:按体积)")
    private ValuationType valuationType;

    /**
     * 包邮条件类别(0:件/重/体积计价方式,1:金额,2:计价方式+金额)
     */
    @Schema(description = "包邮条件类别(0:件/重/体积计价方式,1:金额,2:计价方式+金额)")
    private ConditionType conditionType;

    /**
     * 包邮条件1(件/重/体积)
     */
    @Schema(description = "包邮条件1(件/重/体积)  件数：大于等于  体重和体积：小于等于")
    private BigDecimal conditionOne;

    /**
     * 包邮条件2(金额)
     */
    @Schema(description = "包邮条件2(金额)")
    private BigDecimal conditionTwo;

}
