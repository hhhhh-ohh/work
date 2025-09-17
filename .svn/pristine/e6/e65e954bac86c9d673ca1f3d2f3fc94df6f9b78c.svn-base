package com.wanmi.sbc.customer.api.request.account;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员银行账户-根据用户ID查询Request
 * @Author: wanggang
 * @CreateDate: 2018/9/11 11:05
 * @Version: 1.0
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccountByCustomerIdRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "会员标识UUID")
    @NotBlank
    private String customerId;
}
