package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * 根据不同拼团状态更新不同的统计数据（已成团、待成团、团失败人数）
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrouponActivityModifyStatisticsNumByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -7217715189337194269L;

    /**
     * 拼团活动ID
     */
    @Schema(description = "拼团活动ID")
    @NotBlank
    private String grouponActivityId;

    /**
     * 人数
     */
    @Schema(description = "人数")
    @NonNull
    private Integer grouponNum;

    /**
     * 拼团状态
     */
    @Schema(description = "拼团状态")
    @NonNull
    private GrouponOrderStatus grouponOrderStatus;


}
