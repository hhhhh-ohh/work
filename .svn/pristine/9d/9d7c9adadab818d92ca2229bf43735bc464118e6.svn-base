package com.wanmi.sbc.empower.api.request.pay;

import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayOrderDetailBaseRequest;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.WxPayTradeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PayOrderDetailRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/9/17 13:56
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayOrderDetailRequest extends WxPayOrderDetailBaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 微信支付类型
     */
    private WxPayTradeType wxPayTradeType;

    /**
     * 订单号
     */
    private String businessId;

    /**
     * 订单支付发送时间戳
     */
    @Schema(description = "订单支付发送时间戳")
    private String txnTime;

    @Builder.Default
    private PayType payType = PayType.WXPAY;

    private Integer payVersionAdd;

    private String orderCode;

}
