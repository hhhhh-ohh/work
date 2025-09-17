package com.wanmi.sbc.customer.api.request.payingmembercustomerrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className PayingMemberQueryDiscountRequest
 * @description
 * @date 2022/5/24 11:30 AM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberQueryDiscountRequest extends BaseRequest {
    private static final long serialVersionUID = 2760792947857924964L;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    @NotBlank
    private String customerId;

    /**
     * 等级id
     */
    @Schema(description = "等级id")
    @NotNull
    private Integer levelId;
}
