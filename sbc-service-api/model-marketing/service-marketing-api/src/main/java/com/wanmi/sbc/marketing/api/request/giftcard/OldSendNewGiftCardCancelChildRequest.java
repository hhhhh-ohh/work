package com.wanmi.sbc.marketing.api.request.giftcard;

import lombok.Data;

@Data
public class OldSendNewGiftCardCancelChildRequest {
    /**
     * 对应校服小助手订单详细id
     */
    private Integer orderDetailRetailId;

    /**
     * 数量
     */
    private String number;
}
