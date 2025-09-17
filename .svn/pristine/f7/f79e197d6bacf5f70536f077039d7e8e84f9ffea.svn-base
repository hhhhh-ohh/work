package com.wanmi.sbc.empower.api.request.pay.weixin;

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
public class WxRefundOrderDetailRequest extends WxPayOrderDetailBaseRequest implements Serializable {
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
     * 退款单号
     */
    @Schema(description = "退款单号")
    private String businessId;

}
