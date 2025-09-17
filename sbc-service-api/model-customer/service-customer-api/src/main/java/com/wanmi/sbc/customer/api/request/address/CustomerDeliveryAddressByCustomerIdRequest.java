package com.wanmi.sbc.customer.api.request.address;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员收货地址-根据用户ID查询Request
 */
@Schema
@Data
public class CustomerDeliveryAddressByCustomerIdRequest extends CustomerBaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "会员标识UUID")
    @NotBlank
    private String customerId;
}
