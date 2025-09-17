package com.wanmi.sbc.elastic.api.request.customerInvoice;


import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

@Schema
@Data
public class EsCustomerInvoiceRejectRequest extends BaseRequest {

    /**
     * 专票ids
     */
    @Schema(description = "专票ids")
    private List<Long> customerInvoiceIds;

    /**
     * 审核状态
     */
    @Schema(description = "增票资质审核状态")
    @NotNull
    private CheckState checkState;

    /**
     * 增票id
     */
    @Schema(description = "增票id")
    @NotNull
    private Long customerInvoiceId;

    /**
     * 审核未通过原因
     */
    @Schema(description = "审核未通过原因")
    @NotBlank
    private String rejectReason;
}
