package com.wanmi.sbc.empower.api.request.pay.channelItem;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>新增支付渠道request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:23.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelItemSaveRequest extends PayBaseRequest {

    private static final long serialVersionUID = 2621319816270269374L;
    
    @Schema(description = "支付渠道id")
    private Long id;

    /**
     * 支付项名称
     */
    @Schema(description = "支付项名称")
    private String name;

    /**
     * 支付渠道别称
     */
    @Schema(description = "支付渠道别称")
    private String channel;

    /**
     * 终端: 0 pc 1 h5  2 app
     */
    @Schema(description = "终端")
    private TerminalType terminal;

    /**
     * 支付项代码，同一支付网关唯一
     */
    @Schema(description = "支付项代码，同一支付网关唯一")
    private String code;

    /**
     * 支付网关id
     */
    @Schema(description = "支付网关id")
    @NotNull
    private Long gatewayId;

    /**
     * 是否开启:0关闭 1开启
     */
    @Schema(description = "是否开启")
    private IsOpen isOpen;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
}
