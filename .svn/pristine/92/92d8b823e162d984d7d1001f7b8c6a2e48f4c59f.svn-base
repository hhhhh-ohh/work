package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 开票项目删除请求
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectDeleteByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -6807345290249969357L;
    /**
     * 开票项目id
     */
    @Schema(description = "开票项目id")
    @NotBlank
    private String projectId;
}
