package com.wanmi.sbc.customer.api.request.invoice;


import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 会员增票资质-作废Request
 */
@Schema
@Data
public class CustomerInvoiceInvalidRequest extends CustomerBaseRequest implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 专票ids
     */
    @Schema(description = "专票ids")
    @NotNull
    private List<Long> customerInvoiceIds;


}
