package com.wanmi.sbc.customer.api.request.invoice;


import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员增票资质配置-新增Request
 */
@Schema
@Data
public class CustomerInvoiceConfigAddRequest extends CustomerBaseRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单开票配置")
    @NotNull
    private Integer status;
}
