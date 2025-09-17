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
 * 会员资金-更新余额
 * @author chenyufei
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFundsModifyAccountBalanceByCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = -7408254050042895335L;
    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;


    /**
     * 账户余额
     */
    @Schema(description = "账户余额")
    private BigDecimal accountBalance;
}
