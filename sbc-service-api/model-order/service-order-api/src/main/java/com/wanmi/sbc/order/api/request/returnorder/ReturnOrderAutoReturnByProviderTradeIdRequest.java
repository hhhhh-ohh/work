package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据供应商id自动退单
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnOrderAutoReturnByProviderTradeIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 供应商退单id
     */
    @Schema(description = "供应商退单id")
    @NotBlank
    private String providerOrderId;

    /**
     * 退单描述
     */
    @Schema(description = "退单描述")
    @NotNull
    private String description;
}
