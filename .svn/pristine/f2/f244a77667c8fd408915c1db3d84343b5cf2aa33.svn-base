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
 * 退货日志记录
 * Created by jinwei on 21/4/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ReturnEventLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
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
     * 描述
     */
    @Schema(description = "描述")
    private String remark;

    /**
     * eventTime
     */
    @Schema(description = "eventTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime eventTime = LocalDateTime.now();
}
