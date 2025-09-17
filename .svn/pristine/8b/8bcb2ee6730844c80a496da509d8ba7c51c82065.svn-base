package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 项目开票开关请求
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectSwitchListByCompanyInfoIdRequest extends BaseRequest {

    private static final long serialVersionUID = 5180802425361341180L;
    /**
     * 批量公司信息Id
     */
    @Schema(description = "批量公司信息Id")
    @NotNull
    private List<Long> companyInfoIds;
}
