package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class VerifyTradeMarketingResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单营销信息列表")
    private List<TradeMarketingDTO> tradeMarketingList;
}
