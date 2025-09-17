package com.wanmi.sbc.customer.api.request.address;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员收货地址-根据收货地址ID查询Request
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDeliveryAddressByIdRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 收货地址ID
     */
    @Schema(description = "收货地址ID")
    @NotBlank
    private String deliveryAddressId;
}
