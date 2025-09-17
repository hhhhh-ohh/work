package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 重复的物流单号
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LogisticNoRepeatResponse extends BasicResponse {

    /**
     * 交易单
     */
    @Schema(description = "交易单列表")
    private List<String> logisticNoList;
}
