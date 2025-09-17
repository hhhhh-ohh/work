package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 订单确认页验证请求
 * @author daiyitian
 * @date 2021/5/13 15:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeVerifyPurchaseRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "会员id", hidden = true)
    private String customerId;

    @Schema(description = "终端token", hidden = true)
    private String terminalToken;

    @Schema(description = "会员地址id")
    private String addressId;
}
