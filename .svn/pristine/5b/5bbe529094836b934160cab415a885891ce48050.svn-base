package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 开票项目开关请求
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectSwitchByCompanyInfoIdRequest extends BaseRequest {

    private static final long serialVersionUID = 5854124018385966803L;
    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    @NotNull
    private Long companyInfoId;
}
