package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.ThirdPlatformTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据父订单号获取订单集合返回结构</p>
 * Created by of628-wenzhi on 2019-07-22-15:26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ThirdTradeListByTradeIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 订单对象集合
     */
    @Schema(description = "订单对象结合")
    private List<ThirdPlatformTradeVO> tradeList;
}
