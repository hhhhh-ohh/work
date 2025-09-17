package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 开票项目获取请求
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1305673868701114986L;
    /**
     * 开票项目获取请求id
     */
    @Schema(description = "开票项目获取请求id")
    @NotBlank
    private String projcetId;
}
