package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员资金-更新已提现金额对象
 * @author chenyufei
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFundsModifyAlreadyDrawCashAmountByCustomerIdRequest extends BaseRequest {

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;


    /**
     * 已提现金额
     */
    @Schema(description = "已提现金额")
    private BigDecimal alreadyDrawCashAmount;
}
