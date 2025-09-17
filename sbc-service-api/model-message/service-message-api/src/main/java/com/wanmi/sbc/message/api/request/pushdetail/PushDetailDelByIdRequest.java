package com.wanmi.sbc.message.api.request.pushdetail;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除推送详情请求参数</p>
 * @author Bob
 * @date 2020-01-08 17:16:17
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushDetailDelByIdRequest extends SmsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 友盟推送任务ID
     */
    @Schema(description = "友盟推送任务ID")
    @NotNull
    private String taskId;
}
