package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单操作日志
 * Created by jinwei on 19/03/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeEventLogDTO implements Serializable {

    /**
     * 操作员
     */
    @Schema(description = "操作员")
    private Operator operator;

    /**
     * eventType
     */
    @Schema(description = "eventType")
    private String eventType;

    /**
     * eventDetail
     */
    @Schema(description = "eventDetail")
    private String eventDetail;

    /**
     * eventTime
     */
    @Schema(description = "eventTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime eventTime;
}
