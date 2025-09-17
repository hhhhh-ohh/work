package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.TradeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeUpdateHasReturnRequest extends BaseRequest  {

    /**
     * 订单Id
     */
    @Schema(description = "交易Id")
    private List<String> tids;

    /**
     * 供应商订单Id
     */
    @Schema(description = "交易Id")
    private List<String> ptids;
}
