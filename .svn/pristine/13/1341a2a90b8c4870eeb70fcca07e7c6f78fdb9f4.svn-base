package com.wanmi.sbc.account.api.response.funds;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFundsTodayResponse extends BasicResponse {

    /**
     * 收入金额
     */
    @Schema(description = "收入金额")
    private BigDecimal receiptAmount;

    /**
     * 支出金额
     */
    @Schema(description = "支出金额")
    private BigDecimal paymentAmount;
}
