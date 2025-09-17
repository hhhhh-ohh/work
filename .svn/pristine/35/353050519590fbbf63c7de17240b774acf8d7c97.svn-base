package com.wanmi.sbc.customer.api.request.address;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员收货地址-根据收货地址ID和用户ID查询Request
 */
@Schema
@Data
public class CustomerDeliveryAddressModifyDefaultRequest extends CustomerBaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "收货地址ID")
    @NotBlank
    private String deliveryAddressId;

    @Schema(description = "会员标识UUID")
    @NotBlank
    private String customerId;
}
