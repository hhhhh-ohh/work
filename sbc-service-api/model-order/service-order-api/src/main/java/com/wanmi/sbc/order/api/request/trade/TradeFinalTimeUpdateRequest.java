package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午9:40 2019/4/13
 * @Description:
 */
@Data
@Builder
@Schema
public class TradeFinalTimeUpdateRequest extends BaseRequest {

    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 订单可退时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderReturnTime;

}
