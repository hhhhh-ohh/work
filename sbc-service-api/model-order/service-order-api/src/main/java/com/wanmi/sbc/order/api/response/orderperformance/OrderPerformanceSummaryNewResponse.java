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
public class OrderPerformanceSummaryNewResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;


    /**
     * 代理商类型：1小B 2一级代理商 3二级代理商 4一级合作商
     */
    @Schema(description = "代理商类型：1小B 2一级代理商 3二级代理商 4一级合作商")
    private Integer type;

    /**
     * 累计收益
     */
    @Schema(description = "累计收益")
    private BigDecimal totalProfit;

    /**
     * 累计销售额（门店总销售额）
     */
    @Schema(description = "累计销售额")
    private BigDecimal totalSales;

    /**
     * 累计订单数
     */
    @Schema(description = "累计订单数")
    private Integer totalOrderCount;

    /**
     * 春秋装累积校服总数
     */
    @Schema(description = "春秋装累积校服总数")
    private Integer springAutumnUniformCount;

    /**
     * 夏装累积校服总数
     */
    @Schema(description = "夏装累积校服总数")
    private Integer summerUniformCount;

    /**
     * 冬装累积校服总数
     */
    @Schema(description = "冬装累积校服总数")
    private Integer winterUniformCount;

    /**
     * 总销售数量（门店销售数量）
     */
    @Schema(description = "校服总销售数")
    private Integer totalUniformCount;

    /**
     * 门店名称
     */
    @Schema(description = "门店名称")
    private String shopName;

    /**
     * 总佣金收益
     */
    @Schema(description = "总佣金收益")
    private BigDecimal totalCommissionIncome;

    /**
     * 已提现总收益
     */
    @Schema(description = "已提现总收益")
    private BigDecimal totalWithdrawnIncome;

    /**
     * 待提现总收益
     */
    @Schema(description = "待提现总收益")
    private BigDecimal totalPendingWithdrawalIncome;

    /**
     * 用户总数
     */
    @Schema(description = "用户总数")
    private Integer userCount;

    /**
     * 管理门店数量（门店总数量）
     */
    @Schema(description = "管理门店数量")
    private Integer managedStoreCount;

    /**
     * 待结算订单数
     */
    @Schema(description = "待结算订单数")
    private Integer pendingSettlementOrderCount;

    /**
     * 待结算收益
     */
    @Schema(description = "待结算收益")
    private BigDecimal pendingSettlementProfit;

    /**
     * 可提现收益
     */
    @Schema(description = "可提现收益")
    private BigDecimal withDrawableProfit;
}
