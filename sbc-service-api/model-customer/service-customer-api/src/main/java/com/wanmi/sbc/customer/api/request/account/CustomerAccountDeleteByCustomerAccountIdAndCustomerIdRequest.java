package com.wanmi.sbc.customer.api.request.account;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 会员银行账户-根据银行账号ID和用户ID删除Request
 * @Author: wanggang
 * @CreateDate: 2018/9/11 11:05
 * @Version: 1.0
 */
@Schema
@Data
public class CustomerAccountDeleteByCustomerAccountIdAndCustomerIdRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "客户银行账号ID")
    @NotBlank
    private String customerAccountId;

    @Schema(description = "会员标识UUID")
    @NotBlank
    private String customerId;
}
