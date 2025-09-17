package com.wanmi.sbc.empower.api.request.deliveryrecord;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 * 达达消息通知回调参数
 * @className DeliveryRecordDadaMessageNoticeRequest
 * @author zhengyang
 * @date 2022/1/6 17:36
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaMessageNoticeRequest<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "消息类型")
    private int messageType;

    /***
     * 消息体对应消息类型
     * messageType 1 :
     * @see DadaMessageRiderCancelRequest
     */
    @Schema(description = "消息体")
    private T messageBody;
}
