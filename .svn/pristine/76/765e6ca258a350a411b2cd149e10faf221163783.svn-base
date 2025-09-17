package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2021/3/15 11:32
 * @description <p> 还款详情 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditHasRepaidDetailRequest extends BaseRequest {

    private static final long serialVersionUID = 4537584001409955985L;
    @NotBlank
    @Schema(description = "授信还款主键")
    private String id;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;
}
