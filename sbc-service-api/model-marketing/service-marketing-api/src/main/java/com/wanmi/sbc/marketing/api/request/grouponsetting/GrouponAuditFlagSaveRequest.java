package com.wanmi.sbc.marketing.api.request.grouponsetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class GrouponAuditFlagSaveRequest extends BaseRequest {

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Schema(description = "是否开启拼团商品审核")
    @NotNull
    private DefaultFlag auditFlag;

}
