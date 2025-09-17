package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

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
 * @Date: 2018-12-06 16:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeConfirmPayOrderRequest extends BaseRequest {

    /**
     * 支付单id列表
     */
    @Schema(description = "支付单id列表")
    private List<String> payOrderIds;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

}
