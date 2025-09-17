package com.wanmi.sbc.order.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by feitingting on 2019/5/25.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponRecentActivityQueryRequest extends BaseRequest {
    @Schema(description = "团编号")
    @NotBlank
    private String activityId;
}
