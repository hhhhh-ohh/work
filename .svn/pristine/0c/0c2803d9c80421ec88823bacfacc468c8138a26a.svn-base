package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 开票项目新增请求
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectAddRequest extends BaseRequest {

    private static final long serialVersionUID = 3696564270214588452L;
    /**
     * 开票项目id
     */
    @Schema(description = "开票项目id")
    private String projectId;

    /**
     * 开票项目名称
     */
    @Schema(description = "开票项目名称")
    @NotBlank
    private String projectName;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    @NotNull
    private Long companyInfoId;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    @NotBlank
    private String operatePerson;

}
