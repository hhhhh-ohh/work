package com.wanmi.sbc.order.api.response.paytraderecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.bean.enums.TradeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/***
 * 业务ID和交易流水号映射返回值
 * @className PayTradeNoMapResponse
 * @author zhengyang
 * @date 2022/4/22 6:13 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTradeNoMapResponse implements Serializable {

    @Schema(description = "业务ID和交易流水号映射")
    private Map<String, String> tradeNoMap;
}
