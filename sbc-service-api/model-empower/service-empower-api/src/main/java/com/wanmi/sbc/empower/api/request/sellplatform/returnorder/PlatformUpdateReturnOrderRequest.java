package com.wanmi.sbc.empower.api.request.sellplatform.returnorder;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
*
 * @description  WxChannelsUpdateReturnOrderRequest 更新售后单
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformUpdateReturnOrderRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 微信侧售后单号
     */
    private String aftersale_id;

    /**
     * 外部售后单号，和aftersale_id二选一
     */
    private String out_aftersale_id;

    /**
     * 协商文本内容
     */
    private String openid;

    /**
     * 退款金额,单位：分
     */
    private Integer orderamt;

    /**
     * 售后类型，1:退款,2:退款退货
     */
    private Integer type;

    /**
     * 退款原因
     */
    private String refund_reason;

    /**
     * 见：ChannelsEmAfterSalesReason
     */
    private Integer refund_reason_type;


}
