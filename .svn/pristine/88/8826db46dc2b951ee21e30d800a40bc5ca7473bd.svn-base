package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退货总金额
 * Created by jinwei on 19/4/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ReturnPriceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申请金额状态，是否启用
     */
    @Schema(description = "申请金额状态，是否启用",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean applyStatus;

    /**
     * 申请金额
     */
    @Schema(description = "申请金额")
    private BigDecimal applyPrice;

    /**
     * 商品总金额
     */
    @Schema(description = "商品总金额")
    private BigDecimal totalPrice;

    /**
     * 实退金额，从退款流水中取的
     */
    @Schema(description = "实退金额，从退款流水中取的")
    private BigDecimal actualReturnPrice;

    /**
     * 供货总额
     */
    @Schema(description = "供货总额")
    private BigDecimal providerTotalPrice;



    /**
     * 应退定金
     */
    private BigDecimal earnestPrice;

    /**
     * 应退尾款
     */
    private BigDecimal tailPrice;

    /**
     * 是否是尾款申请
     */
    private Boolean isTailApply;


    /**
     * 运费
     */
    private BigDecimal fee;

    /**
     * 礼品卡应退金额
     */
    @Schema(description = "礼品卡应退金额")
    private BigDecimal giftCardPrice;
}
