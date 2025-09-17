package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员登录注册-注册Request
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterRequest extends CustomerBaseRequest implements Serializable {


    private static final long serialVersionUID = 1L;

    @Schema(description = "负责业务员")
    private String employeeId;

    @Schema(description = "会员信息-共用DTO")
    @NotNull
    private CustomerDTO customerDTO;

    @Schema(description = "会员id")
    private String customerId;

    @Schema(description = "企业名称")
    private String enterpriseName;

    @Schema(description = "统一社会信用代码")
    private String socialCreditCode;

    @Schema(description = "公司性质")
    private Integer businessNatureType;

    @Schema(description = "公司行业")
    private Integer businessIndustryType;

    @Schema(description = "营业执照地址")
    private String businessLicenseUrl;

    @Schema(description = "企业会员审核状态")
    private DefaultFlag enterpriseCustomerAuditFlag;
}
