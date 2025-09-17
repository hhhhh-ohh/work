package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.ProviderTradeVO;
import com.wanmi.sbc.order.bean.vo.TradeDeliverVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeGetByIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 订单对象
     */
    @Schema(description = "订单对象")
    private TradeVO tradeVO;

    /**
     * 子订单对象
     */
    @Schema(description = "子订单对象")
    private List<ProviderTradeVO> providerTradeVOs;

    /**
     * 发货单对象
     */
    @Schema(description = "发货单对象")
    private TradeDeliverVO tradeDeliverVO;

}
