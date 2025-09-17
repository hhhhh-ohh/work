package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.enums.TaskBizType;
import com.wanmi.sbc.common.enums.TaskResult;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotNull;

/**
 * @description 定时任务日志添加
 * @author malianfeng
 * @date 2021/9/8 18:54
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskLogCountByParamsRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 业务ID
     */
    @Schema(description = "业务ID")
    private String bizId;

    /**
     * 业务类型，0精准发券 1消息发送 2商品调价
     */
    @NotNull
    @Schema(description = "业务类型，0精准发券 1消息发送 2商品调价")
    private TaskBizType taskBizType;

    /**
     * 店铺ID
     */
    @NotNull
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 任务执行结果，0执行失败 1执行成功
     */
    @NotNull
    @Schema(description = "任务执行结果，0执行失败 1执行成功")
    private TaskResult taskResult;
}
