package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.account.bean.enums.WithdrawAmountStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员资金-提现金额更新对象
 * @author: Geek Wang
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
public class CustomerFundsModifyRequest extends BaseRequest {

    /**
     * 会员资金ID
     */
    @Schema(description = "会员资金ID")
    private String customerFundsId;

    /**
     * 提现金额
     */
    @Schema(description = "提现金额")
    private BigDecimal withdrawAmount;

    /**
     * 提现状态
     */
    @Schema(description = "提现状态")
    private WithdrawAmountStatus withdrawAmountStatus;

    /**
     * 消费金额
     */
    @Schema(description = "使用金额")
    private BigDecimal expenseAmount;
}
