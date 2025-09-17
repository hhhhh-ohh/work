package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * 授信申请参数
 *
 * @author zhegnyang
 * @since 2021-03-04 10:22:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditOrderQueryRequest extends BaseRequest {

    /**
     * 订单ID
     */
    @NotBlank
    @Schema(description = "订单ID")
    private String orderId;
}
