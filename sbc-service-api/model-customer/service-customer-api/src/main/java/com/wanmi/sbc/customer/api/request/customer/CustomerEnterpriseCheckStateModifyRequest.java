package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class CustomerEnterpriseCheckStateModifyRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 审核状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "审核状态 1：待审核 2：已审核 3：审核未通过")
    @NotNull
    private EnterpriseCheckState enterpriseCheckState;

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
    private String enterpriseCheckReason;
}
