package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBaseByCustomerIdAndDeleteFlagRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private DeleteFlag deleteFlag;
}
