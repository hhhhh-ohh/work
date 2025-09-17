package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author houshuai
 * @date 2021/4/7 15:45
 * @description <p> 订单信息响应体 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditTradeVOPageResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "查询订单返回结果")
    private MicroServicePage<TradeVO> tradeVOList;
}