package com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className NewcomerPurchaseDetailRequest
 * @description
 * @date 2022/8/22 6:06 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseDetailRequest extends BaseRequest {
    private static final long serialVersionUID = -597584579555058083L;

    @NotBlank
    @Schema(description = "会员id")
    private String customerId;
}
