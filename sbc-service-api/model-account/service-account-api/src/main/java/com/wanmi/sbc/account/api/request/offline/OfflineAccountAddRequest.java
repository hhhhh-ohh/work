package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 线下账户新增请求
 * Created by CHENLI on 2017/4/27.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class OfflineAccountAddRequest extends AccountBaseRequest {

    private static final long serialVersionUID = 2961961481743543691L;
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
