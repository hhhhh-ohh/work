package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 开票项目查询请求
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectQueryRequest extends BaseRequest {

    private static final long serialVersionUID = -601840733048742607L;
    /**
     * 开票项目名称
     */
    @Schema(description = "开票项目名称")
    @NotBlank
    private String projectName;

    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    @NotNull
    private Long companyInfoId;
}
