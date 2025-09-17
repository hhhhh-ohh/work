package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2021/2/27 13:52
 * @description <p> 授信账户请求参数 </p>
 */
@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplyQueryRequest extends BaseRequest {
    /**
     * 客户ID
     */
    @NotNull
    @Schema(description = "客户ID")
    private String customerId;
}
