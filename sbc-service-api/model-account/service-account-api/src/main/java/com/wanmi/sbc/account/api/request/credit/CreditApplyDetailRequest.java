package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 授信申请详情查询
 * @author zhengyang
 * @since 2021/3/18 14:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplyDetailRequest extends BaseRequest {

    /**
     * 申请ID
     */
    @NotBlank
    @Schema(description = "申请ID")
    private String applyId;
}
