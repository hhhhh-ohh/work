package com.wanmi.sbc.elastic.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.Validate;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsCustomerCheckStateModifyRequest extends BaseRequest {
    private static final long serialVersionUID = -8427145177039519006L;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态(0:待审核,1:已审核,2:审核未通过)",contentSchema = com.wanmi.sbc.customer.bean.enums.CheckState.class)
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

    /**
     * true: 企业会员，false:普通会员
     */
    @Schema(description = "企业会员")
    private Boolean enterpriseCustomer = Boolean.FALSE;


    /**
     * 审核状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "审核状态 1：待审核 2：已审核 3：审核未通过")
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 审核驳回原因
     */
    @Schema(description = "审核驳回原因")
    private String enterpriseCheckReason;

    /**
     * 统一参数校验入口
     */
    @Override
    public void checkParam() {
        if (enterpriseCustomer) {
            Validate.notNull(this.enterpriseCheckState, ValidateUtil.NULL_EX_MESSAGE, "enterpriseCheckState");
        } else {
            Validate.notNull(this.checkState, ValidateUtil.NULL_EX_MESSAGE, "checkState");
        }
    }
}
