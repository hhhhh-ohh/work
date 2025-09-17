package com.wanmi.sbc.customer.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AccountType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Schema
@Data
public class EmployeeExcelImportRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "文件后缀名")
    @NotBlank
    private String ext;

    /**
     * 公司ID
     */
    @Schema(description = "公司ID")
    private Long companyInfoId;

    /**
     * 操作员id
     */
    @Schema(description = "操作员id")
    private String userId;

    /**
     * 账户类型
     */
    @Schema(description = "账户类型")
    private AccountType accountType;
}
