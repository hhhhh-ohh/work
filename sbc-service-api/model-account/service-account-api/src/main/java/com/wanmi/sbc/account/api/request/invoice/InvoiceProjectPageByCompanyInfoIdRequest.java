package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 开票项目分页请求
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectPageByCompanyInfoIdRequest extends BaseRequest {

    private static final long serialVersionUID = -7288230451917016225L;
    /**
     * 分页请求
     */
    @Schema(description = "分页请求")
    @NotNull
    private BaseQueryRequest baseQueryRequest;

    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    @NotNull
    private Long companyInfoId;
}
