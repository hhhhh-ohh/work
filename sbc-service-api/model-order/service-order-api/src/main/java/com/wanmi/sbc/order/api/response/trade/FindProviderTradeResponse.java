package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;

/**
 * 子订单返回结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class FindProviderTradeResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "查询子订单返回结果")
    private List<TradeVO> tradeVOList;
}
