package com.wanmi.sbc.account.api.response.funds;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员资金-统计汇总对象
 * @author: Geek Wang
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFundsStatisticsResponse extends BasicResponse {

    @Schema(description = "余额总额")
    private BigDecimal accountBalanceTotal;

    @Schema(description = "冻结余额总额")
    private BigDecimal blockedBalanceTotal;

    @Schema(description = "可提现余额总额")
    private BigDecimal withdrawAmountTotal;

    /**
     * 已提现金额
     */
    @Schema(description = "已提现金额")
    private BigDecimal alreadyDrawAmount;

    @Schema(description = "待自动提现金额")
    private BigDecimal ledgerWithdrawAmount;

    @Schema(description = "已自动提现金额")
    private BigDecimal ledgerAlreadyDrawAmount;

    public CustomerFundsStatisticsResponse(BigDecimal accountBalanceTotal, BigDecimal blockedBalanceTotal, BigDecimal withdrawAmountTotal) {
        this.accountBalanceTotal = accountBalanceTotal;
        this.blockedBalanceTotal = blockedBalanceTotal;
        this.withdrawAmountTotal = withdrawAmountTotal;
    }
}
