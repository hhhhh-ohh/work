package com.wanmi.sbc.customer.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员银行账户添加/修改共用DTO
 */
@Schema
@Data
public class CustomerAccountAddOrModifyDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账户ID
     */
    @Schema(description = "账户ID")
    private String customerAccountId;

    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 账户名字
     */
    @Schema(description = "账户名字")
    @NotBlank
    @Size(min = 1,max = 50)
    private String customerAccountName;

    /**
     * 银行账号
     */
    @Schema(description = "银行账号")
    @NotBlank
    private String customerAccountNo;

    /**
     * 开户行
     */
    @Schema(description = "开户行")
    @NotBlank
    @Size(min = 1,max = 50)
    private String customerBankName;
}
