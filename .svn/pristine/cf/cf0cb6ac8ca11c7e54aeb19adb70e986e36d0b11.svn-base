package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 驳回拼团活动请求对象
 */
@Schema
@Data
public class GrouponActivityRefuseRequest extends BaseRequest {

    /**
     * 活动ID
     */
    @Schema(description = "审核拼团活动，grouponActivityId")
    @NotNull
    private String grouponActivityId;

    /**
     * 分销拼团活动不通过原因
     */
    @NotBlank
    @Schema(description = "拼团活动不通过原因")
    private String auditReason;
}
