package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>支付渠道项VO</p>
 * Created by of628-wenzhi on 2018-08-10-下午3:45.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayChannelItemVO implements Serializable{

    private static final long serialVersionUID = 8256591345170228032L;
    @Schema(description = "支付渠道项id")
    private Long id;

    /**
     * 支付项名称
     */
    @Schema(description = "支付项名称")
    private String name;

    /**
     * 支付渠道
     */
    @Schema(description = "支付渠道")
    private String channel;

    /**
     * 是否开启:0关闭 1开启
     */
    @Schema(description = "是否开启")
    private IsOpen isOpen;

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
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 支付网关
     */
    @Schema(description = "支付网关")
    private PayGatewayVO gateway;

    private LakalaCasherPayItemVO lakalaCasherPayItemVO;
}
