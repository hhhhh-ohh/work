package com.wanmi.sbc.empower.api.request.pay.weixin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description
 * @author  
 * @date  
 * @param
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadShippingInfoOrderKeyRequest implements Serializable {

    private static final long serialVersionUID = 7417600807845033416L;
    /**
     * 订单单号类型，用于确认需要上传详情的订单。枚举值1，使用下单商户号和商户侧单号；枚举值2，使用微信支付单号
     */
    private Integer order_number_type;

    /**
     * 原支付交易对应的微信订单号
     */
    private String transaction_id;

    /**
     * 支付下单商户的商户号，由微信支付生成并下发
     */
    private String mchid;

    /**
     * 商户系统内部订单号，只能是数字、大小写字母`_-*`且在同一个商户号下唯一
     */
    private String out_trade_no;

}
