package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import com.wanmi.sbc.order.bean.enums.BackRestrictedType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回限售数量的请求实体
 * @author baijz
 * @date 2018/5/5.13:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeBackRestrictedRequest extends BaseRequest {

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String tradeId;

    /**
     * 退单号
     */
    @Schema(description = "退单号")
    private String backOrderId;

    /**
     * 退还限售数量的类型
     */
    @Schema(description = "退还限售数量的类型")
    private BackRestrictedType backRestrictedType;
}
