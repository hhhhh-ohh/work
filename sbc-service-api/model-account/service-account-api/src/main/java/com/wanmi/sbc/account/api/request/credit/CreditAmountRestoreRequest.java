package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 授信账户请求恢复
 * @author zhengyang
 * @since  2021-03-03 14:51
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditAmountRestoreRequest extends BaseRequest {
    @NotBlank
    @Schema(description = "会员ID")
    private String customerId;

    @NotNull
    @Schema(description = "余额金额")
    private BigDecimal amount;
}
