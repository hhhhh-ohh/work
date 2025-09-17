package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>根据团号获取订单集合返回结构</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeListByGrouponNoResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 订单对象集合
     */
    @Schema(description = "订单对象结合")
    private List<TradeVO> tradeVOList;
}
