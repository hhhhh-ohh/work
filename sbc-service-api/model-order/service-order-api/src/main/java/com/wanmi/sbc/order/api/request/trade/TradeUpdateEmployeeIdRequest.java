package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeUpdateEmployeeIdRequest extends BaseRequest {

    /**
     * 员工ID
     */
    @Schema(description = "员工ID")
    @NotBlank
    private String employeeId;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @NotBlank
    private String customerId;
}
