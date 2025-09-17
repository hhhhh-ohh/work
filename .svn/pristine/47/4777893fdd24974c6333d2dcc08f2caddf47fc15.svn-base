package com.wanmi.sbc.empower.api.request.apppush;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import com.wanmi.sbc.empower.bean.enums.AppPushAppType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: sbc-micro-service
 * @description: 请求参数
 * @create: 2020-01-10 15:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppPushCancelRequest extends EmpowerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "任务ID")
    @NotBlank
    private String taskId;

    @Schema(description = "APP类型")
    @NotNull
    private AppPushAppType type;
}
