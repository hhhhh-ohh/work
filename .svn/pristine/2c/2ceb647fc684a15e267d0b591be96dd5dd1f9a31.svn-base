package com.wanmi.ares.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Schema
public class MarketingTaskRecordQueryRequest extends BaseRequest {

    // 创建时间
    @Schema(description = "创建时间")
    private LocalDate createTime;

    // 开始时间
    @Schema(description = "开始时间")
    private LocalDate createTimeBegin;

    // 结束时间
    @Schema(description = "结束时间")
    private LocalDate createTimeEnd;

    // 活动类型
    @Schema(description = "活动类型")
    private int marketingType;

}
