package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:40 2019/3/4
 * @Description: 下单分销单品信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class TradeDistributeItemDTO extends BaseRequest {

    private static final long serialVersionUID = 3487204390777682101L;

    /**
     * 单品id
     */
    @Schema(description = "单品id")
    private String goodsInfoId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    private Long num;

    /**
     * 总sku的实付金额
     */
    @Schema(description = "总sku的实付金额")
    private BigDecimal actualPaidPrice;

    /**
     * 返利人佣金比例
     */
    @Schema(description = "返利人佣金比例")
    private BigDecimal commissionRate;

    /**
     * 返利人佣金
     */
    @Schema(description = "返利人佣金")
    private BigDecimal commission;

    /**
     * 总佣金(返利人佣金 + 提成人佣金)
     */
    @Schema(description = "总佣金(返利人佣金 + 提成人佣金)")
    private BigDecimal totalCommission;

    /**
     * 提成人佣金列表
     */
    @Schema(description = "提成人佣金列表")
    private List<TradeDistributeItemCommissionDTO> commissions = new ArrayList<>();

}
