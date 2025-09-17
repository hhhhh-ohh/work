package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description   购物车店铺运费模板信息
 * @author  wur
 * @date: 2022/7/12 11:36
 * @return
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateStoreCartVO extends BasicResponse {

    private static final long serialVersionUID = -2243723680863364216L;

    /**
     * 运费模板Id
     */
    @Schema(description = "运费模板Id")
    private long freightTemplateId;

    /**
     * 运费计费规则(0:满金额包邮,1:固定运费)
     */
    @Schema(description = "计费规则 0:满金额包邮,1:固定运费")
    private DefaultFlag freightType;

    /**
     * 满多少金额包邮
     */
    @Schema(description = "freightType=0时 满多少金额包邮")
    private BigDecimal satisfyPrice;

    /**
     * 不满金额的运费
     */
    @Schema(description = "freightType=0时 不满金额的运费")
    private BigDecimal satisfyFreight;

    /**
     * 固定的运费
     */
    @Schema(description = "freightType=1时 固定的运费")
    private BigDecimal fixedFreight;



}
