package com.wanmi.sbc.department.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Data
@Schema
public class DepartmentExcelImportRequest extends BaseRequest {

    @NotBlank
    @Schema(description = "文件后缀名")
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
}
