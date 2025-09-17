package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAccountModifyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "会员ID")
    private String customerId;

    @Schema(description = "账户")
    private String customerAccount;
}
