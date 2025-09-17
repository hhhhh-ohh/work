package com.wanmi.sbc.message.api.request.appmessage;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除App站内信消息发送表请求参数</p>
 * @author xuyunpeng
 * @date 2020-01-06 10:53:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppMessageDelByIdRequest extends SmsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private String appMessageId;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;
}
