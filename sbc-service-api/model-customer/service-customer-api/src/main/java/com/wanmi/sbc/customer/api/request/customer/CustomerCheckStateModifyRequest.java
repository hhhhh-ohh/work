package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class CustomerCheckStateModifyRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态(0:待审核,1:已审核,2:审核未通过)",contentSchema = com.wanmi.sbc.customer.bean.enums.CheckState.class)
    @NotNull
    private Integer checkState;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    @NotNull
    private String customerId;

    /**
     * 审核驳回原因
     */
    @Schema(description = "审核驳回原因")
    private String rejectReason;
}
