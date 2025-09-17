package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 线下账户修改请求
 * Created by CHENLI on 2017/4/27.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class OfflineAccountModifyRequest extends AccountBaseRequest {
    private static final long serialVersionUID = 3072491867725776610L;
    /**
     * 账户id
     */
    @Schema(description = "账户id")
    private Long accountId;

    /**
     * 账户名称
     */
    @Schema(description = "账户名称")
    @NotBlank
    private String accountName;

    /**
     * 开户银行
     */
    @Schema(description = "开户银行")
    @NotBlank
    private String bankName;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank
    private String bankNo;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 支行信息
     */
    @Schema(description = "支行信息")
    private String bankBranch;

}
