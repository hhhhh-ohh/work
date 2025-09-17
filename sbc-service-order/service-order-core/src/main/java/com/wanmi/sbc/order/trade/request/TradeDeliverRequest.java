package com.wanmi.sbc.order.trade.request;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.order.trade.model.entity.value.ShippingItem;
import lombok.Data;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
@Data
public class TradeDeliverRequest extends BaseQueryRequest {

    /**
     * 物流单号
     */
    private String deliverNo;

    /**
     * 物流ID
     */
    private String deliverId;


    /**
     * 发货信息
     */
    @Valid
    private List<ShippingItem> shippingItemList = new ArrayList<>();


    /**
     * 赠品信息
     */
    @Valid
    private List<ShippingItem> giftItemList = new ArrayList<>();

    /**
     * 加价购商品信息
     */
    @Valid
    private List<ShippingItem> preferentialItemList = new ArrayList<>();

    /**
     * 发货时间
     */
    private String deliverTime;

}
