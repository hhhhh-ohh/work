package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.AccountState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRegisterRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank
    private String account;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotEmpty
    private char[] password;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String verifyCode;


    /**
     * 账号类型(0 b2b账号 1 s2b平台端账号 2 s2b商家端账号 3 s2b品牌商端账号)
     */
    @Schema(description = "账号类型")
    private Integer accountType;


    /**
     * 商家/店铺类型（0品牌商城，1商家）
     */
    @Schema(description = "商家/店铺类型")
    private Integer storeType;

    /**
     * 商家编号
     */
    @Schema(description ="商家编号")
    private String companyCode;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description ="商家类型")
    private BoolFlag companyType;

    /**
     * 账号状态
     */
    @Schema(description ="账号状态")
    private AccountState accountState;

    /**
     * 账号禁用原因
     */
    @Schema(description ="账号禁用原因")
    private String accountDisableReason;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    @Schema(description = "联系人名称")
    private String contactPerson;
}
