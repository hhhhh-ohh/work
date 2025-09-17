package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.ProviderTradeVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yangzhen
 * @Description
 * @Date 18:18 2020/11/28
 * @Param
 * @return
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeGetByIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 订单对象
     */
    @Schema(description = "订单对象")
    private List<TradeVO> tradeVO;


}
