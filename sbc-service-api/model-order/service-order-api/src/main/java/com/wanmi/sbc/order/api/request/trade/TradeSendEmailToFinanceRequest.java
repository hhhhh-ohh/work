package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

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
public class TradeSendEmailToFinanceRequest extends BaseRequest {

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private String orderId;

    /**
     * url
     */
    @Schema(description = "url")
    private String url;

}
