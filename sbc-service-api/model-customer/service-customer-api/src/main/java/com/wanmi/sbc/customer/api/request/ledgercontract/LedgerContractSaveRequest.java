package com.wanmi.sbc.customer.api.request.ledgercontract;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerContractSaveRequest
 * @description
 * @date 2022/9/24 10:09 AM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContractSaveRequest extends BaseRequest {
    private static final long serialVersionUID = 7780395491982132780L;

    /**
     * 正文
     */
    @Schema(description = "正文")
    @NotBlank
    private String body;

    /**
     * 协议内容
     */
    @Schema(description = "协议内容")
    @NotBlank
    private String content;
}
