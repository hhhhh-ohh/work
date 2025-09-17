package com.wanmi.sbc.customer.account.request;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 客户账户信息编辑
 * Created by CHENLI on 2017/4/21.
 */
@Data
public class CustomerAccountSaveRequest extends BaseRequest {
    /**
     * 账户ID
     */
    private String customerAccountId;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 账户名字
     */
    @NotBlank
    private String customerAccountName;

    /**
     * 银行账号
     */
    @NotBlank
    private String customerAccountNo;

    /**
     * 开户行
     */
    @NotBlank
    private String customerBankName;
}
