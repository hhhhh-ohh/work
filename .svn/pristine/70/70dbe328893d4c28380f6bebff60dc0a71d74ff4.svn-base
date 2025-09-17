package com.wanmi.sbc.customer.api.request.account;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 会员银行账户-根据银行账号查询Request
 * @Author: wanggang
 * @CreateDate: 2018/9/11 11:05
 * @Version: 1.0
 */
@Schema
@Data
public class CustomerAccountByCustomerAccountNoRequest extends CustomerBaseRequest{

    private static final long serialVersionUID = 1L;

    @Schema(description = "银行账号")
    @NotBlank
    private String customerAccountNo;
}
