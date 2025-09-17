package com.wanmi.sbc.order.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import com.wanmi.sbc.common.base.Operator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 退单状态变更发送MQ请求体
 * @Autho qiaokang
 * @Date：2019-03-06 15:43:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReturnOrderSendMQRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderId;

    /**
     * 退单号
     */
    @Schema(description = "退单号")
    private String returnId;

    /**
     * 购买人id
     */
    @Schema(description = "购买人id")
    private String customerId;

    /**
     * 是否增加退单 true：增加，false：减少
     */
    @Schema(description = "是否增加退单")
    private boolean addFlag;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

}
