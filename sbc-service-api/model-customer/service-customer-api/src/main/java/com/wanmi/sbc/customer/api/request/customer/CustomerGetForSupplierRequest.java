package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGetForSupplierRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "客户ID")
    private String customerId;

    @Schema(description = "供应商下的客户")
    private Long companyInfoId;

    @Schema(description = "商铺Id")
    private Long storeId;
}
