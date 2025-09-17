package com.wanmi.sbc.message.api.request.appmessage;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppMessageSetReadRequest extends BaseRequest {

    private static final long serialVersionUID = 6070684987650814273L;
    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * app消息id
     */
    @Schema(description = "app消息id")
    private String messageId;
}
