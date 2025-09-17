package com.wanmi.sbc.message.api.request.pushsend;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除会员推送信息请求参数</p>
 * @author Bob
 * @date 2020-01-08 17:15:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendDelByIdRequest extends SmsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;
}
