package com.wanmi.sbc.order.api.response.orderperformance;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单业绩汇总响应类
 * 用于返回订单业绩相关的汇总统计数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单业绩汇总响应")
public class OrderPerformanceSummaryResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 累计收益
     * 用户通过推广订单获得的总佣金收益
     */
    @Schema(description = "累计收益")
    private BigDecimal cumulativeIncome;

    /**
     * 累计订单数
     * 用户推广成功的订单总数量
     */
    @Schema(description = "累计订单数")
    private Integer cumulativeOrderCount;

    /**
     * 待结算收益
     * 已完成但尚未结算的订单产生的佣金收益
     */
    @Schema(description = "待结算收益")
    private BigDecimal pendingSettlementIncome;

    /**
     * 待结算订单数
     * 已完成但尚未结算的订单数量
     */
    @Schema(description = "待结算订单数")
    private Integer pendingSettlementOrderCount;

    /**
     * 可提现收益
     * 用户当前可以提现的佣金金额
     */
    @Schema(description = "可提现收益")
    private BigDecimal withDrawIncome;

    /**
     * 订单总数
     * 用户推广的所有订单数量（包括已完成、待结算等状态）
     */
    @Schema(description = "订单总数")
    private Integer totalOrderCount;

    /**
     * 总销售额
     * 用户推广订单的总金额
     */
    @Schema(description = "总销售额")
    private BigDecimal totalSalesAmount;

    /**
     * 已提现金额
     * 用户已经成功提现的佣金总额
     */
    @Schema(description = "已提现")
    private BigDecimal withDrawAmount;

    /**
     * 可提现金额
     * 用户当前账户中可提现的总金额（可能包括未结算收益）
     */
    @Schema(description = "可提现")
    private BigDecimal availableWithDrawAmount;
}
