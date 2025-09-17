package com.wanmi.sbc.setting.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.TaskBizType;
import com.wanmi.sbc.common.enums.TaskResult;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
public class TaskLogAddRequest extends SettingBaseRequest {

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

    /**
     * 任务备注信息
     */
    @Schema(description = "任务备注信息")
    private String remarks;

    /**
     * 异常堆栈信息
     */
    @Schema(description = "异常堆栈信息")
    private String stackMessage;

    /**
     * 创建时间
     */
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
