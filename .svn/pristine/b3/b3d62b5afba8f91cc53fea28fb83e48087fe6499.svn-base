package com.wanmi.sbc.customer.api.request.address;

import com.wanmi.sbc.customer.bean.dto.CustomerDeliveryAddressDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 会员收货地址-根据employeeID查询Request
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerDeliveryAddressModifyRequest extends CustomerDeliveryAddressDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "操作人员id", hidden = true)
    private String employeeId;

    /**
     * 收货地址ID
     */
    @NotBlank
    @Schema(description = "收货地址ID")
    private String deliveryAddressId;
}
