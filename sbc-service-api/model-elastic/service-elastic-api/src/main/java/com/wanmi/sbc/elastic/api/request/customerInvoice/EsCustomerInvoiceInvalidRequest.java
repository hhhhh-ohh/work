package com.wanmi.sbc.elastic.api.request.customerInvoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 会员增票资质-作废Request
 */
@Schema
@Data
public class EsCustomerInvoiceInvalidRequest extends BaseRequest {

    /**
     * 专票ids
     */
    @Schema(description = "专票ids")
    @NotNull
    private List<Long> customerInvoiceIds;
}
