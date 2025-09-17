package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className ConfirmProviderOrderRequest
 * @description
 * @author 黄昭
 * @date 2021/10/18 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ConfirmProviderOrderRequest extends BaseRequest {

    private static final long serialVersionUID = 1802963311323004365L;
    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String id;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;
}
