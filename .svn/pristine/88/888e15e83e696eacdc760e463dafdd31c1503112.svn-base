package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 开票项目列表请求
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectSwitchListSupportByCompanyInfoIdRequest extends BaseRequest {

    private static final long serialVersionUID = -3936641492573504582L;
    /**
     * 批量公司信息Id
     */
    @Schema(description = "批量公司信息Id")
    @NotNull
    private List<Long> companyInfoIds;
}
