package com.wanmi.sbc.empower.api.request.deliveryrecord;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 * 达达骑士取消通知回调参数
 * @className DadaMessageRiderCancelRequest
 * @author zhengyang
 * @date 2022/1/18 17:36
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadaMessageRiderCancelRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    private String orderId;

    @Schema(description = "取消原因")
    private String cancelReason;
}
