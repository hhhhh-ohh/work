package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.TradePriceChangeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeModifyPriceRequest extends BaseRequest {

    /**
     * 交易价格
     */
    @Schema(description = "交易价格")
    private TradePriceChangeDTO tradePriceChangeDTO;

    /**
     * 交易单号
     */
    @Schema(description = "交易单号")
    private String tid;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

}
