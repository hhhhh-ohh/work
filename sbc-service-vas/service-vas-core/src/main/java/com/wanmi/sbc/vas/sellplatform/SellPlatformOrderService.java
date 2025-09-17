package com.wanmi.sbc.vas.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformAddOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformDeliverySendRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformOrderRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformExpressListResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformOrderResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformQueryOrderResponse;

/**
*
 * @description    订单相关处理
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
public interface SellPlatformOrderService extends SellPlatformBaseService {

    /**
     * @description    添加订单
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    BaseResponse<SellPlatformOrderResponse> addOrder(SellPlatformAddOrderRequest request);

    /**
     * @description   查询订单
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    BaseResponse<SellPlatformQueryOrderResponse> queryOrder(SellPlatformOrderRequest request);

    /**
     * @description    取消订单
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    BaseResponse cancelOrder(SellPlatformOrderRequest request);

    /**
     * @description    订单发货
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    BaseResponse deliverySend(SellPlatformDeliverySendRequest request);

    /**
     * @description
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    BaseResponse deliveryReceive(SellPlatformOrderRequest request);

    /**
     * @description   查询物流公司
     * @author  wur
     * @date: 2022/4/29 11:31
     * @return
     **/
    BaseResponse<SellPlatformExpressListResponse> getExpressList();

}
