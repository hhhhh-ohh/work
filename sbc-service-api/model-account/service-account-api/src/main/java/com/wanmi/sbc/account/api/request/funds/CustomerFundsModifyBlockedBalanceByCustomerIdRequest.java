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
 * 会员资金-更新冻结金额
 * @author chenyufei
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFundsModifyBlockedBalanceByCustomerIdRequest extends BaseRequest {

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;


    /**
     * 冻结金额
     */
    @Schema(description = "冻结金额")
    private BigDecimal blockedBalance;
}
