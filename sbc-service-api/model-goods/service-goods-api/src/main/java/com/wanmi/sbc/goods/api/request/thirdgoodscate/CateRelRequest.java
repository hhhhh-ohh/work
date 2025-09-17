package com.wanmi.sbc.goods.api.request.thirdgoodscate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema
public class CateRelRequest extends BaseRequest {
    @Schema(description = "渠道来源")
    private ThirdPlatformType thirdPlatformType;

    @Schema(description = "审核状态")
    private AuditStatus auditStatus;
}
