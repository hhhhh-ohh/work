package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerContractRequest
 * @description
 * @date 2022/7/4 2:18 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContractRequest extends BaseRequest {
    private static final long serialVersionUID = -7923540711598668578L;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @NotBlank
    private String phone;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;
}
