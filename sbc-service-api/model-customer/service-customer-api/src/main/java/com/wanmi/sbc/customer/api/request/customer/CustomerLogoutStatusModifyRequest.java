package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLogoutStatusModifyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 注销原因Id
     */
    @Schema(description = "注销原因Id")
    private String cancellationReasonId;

    /**
     * 注销原因
     */
    @Schema(description = "注销原因")
    private String cancellationReason;
}
