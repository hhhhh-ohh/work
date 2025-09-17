package com.wanmi.sbc.marketing.api.request.giftcard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OldSendNewGiftCardCancelRequest extends GiftCardCancelRequest implements Serializable {
    private static final long serialVersionUID = 5036585577605234040L;
    /**
     * 对应校服小助手订单号
     */
    private String orderSn;

    /**
     * 对应校服小助手订单详细列表
     */
    private List<OldSendNewGiftCardCancelChildRequest> oldSendNewGiftCardCancelChildRequestList;

}
