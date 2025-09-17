package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.dto.LogisticsDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 批量发货单条数据处理
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeBatchDeliverDTO implements Serializable {

    /**
     * 子订单id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 物流信息
     */
    @Schema(description = "物流信息")
    private LogisticsDTO logistics;

    /**
     * 发货时间
     */
    @Schema(description = "发货时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deliverTime;

}
