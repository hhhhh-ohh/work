package com.wanmi.sbc.empower.api.request.pay;

import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayCloseOrderBaseRequest;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.WxPayTradeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName PayCloseOrderRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/9 14:40
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayCloseOrderRequest extends WxPayCloseOrderBaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 微信支付类型
     */
    @Schema(description = "微信支付类型")
    private WxPayTradeType wxPayTradeType;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String businessId;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    @Builder.Default
    private PayType payType = PayType.WXPAY;

    /**
     * 申请价格
     */
    private BigDecimal applyPrice;
}
