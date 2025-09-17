package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class  TradePayCallBackOnlineDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 交易单
     */
    @Schema(description = "交易单")
    private TradeDTO trade;

    /**
     * 支付单
     */
    @Schema(description = "支付单")
    private PayOrderDTO payOrderOld;

}
