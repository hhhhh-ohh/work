package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerInfoUpdateRequest
 * @description
 * @date 2022/7/15 4:55 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerInfoUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 8092558176376210083L;

    /**
     * 公司id
     */
    @Schema(description = "公司id")
    @NotBlank
    private String companyInfoId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @NotBlank
    private String name;
}
