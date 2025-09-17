package com.wanmi.sbc.vas.api.provider.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformAddOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformDeliverySendRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformOrderRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformExpressListResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformOrderResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformQueryOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
*
 * @description SallPlatformOrderProvider  代销平台平台回单流程
 * @author  wur
 * @date: 2022/4/18 9:39
 **/
@FeignClient(value = "${application.vas.name}", contextId = "SellPlatformOrderProvider")
public interface SellPlatformOrderProvider {

    /**
    *
     * @description     订单
     * @author  wur
     * @date: 2022/4/18 9:43
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/order/add")
    BaseResponse<SellPlatformOrderResponse> addOrder(@RequestBody @Valid SellPlatformAddOrderRequest request);

    /**
     * @description   查询订单详情
     * @author  wur
     * @date: 2022/4/22 14:35
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/order/query")
    BaseResponse<SellPlatformQueryOrderResponse> queryOrder(@RequestBody @Valid SellPlatformOrderRequest request);

    /**
     * @description   取消订单
     * @author  wur
     * @date: 2022/4/21 20:31
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/order/cancel")
    BaseResponse cancelOrder(@RequestBody @Valid SellPlatformOrderRequest request);

    /**
     * @description     发货
     * @author  wur
     * @date: 2022/4/21 20:31
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/order/delivery-send")
    BaseResponse deliverySend(@RequestBody @Valid SellPlatformDeliverySendRequest request);

    /**
     * @description   确认收货
     * @author  wur
     * @date: 2022/4/21 20:31
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/order/delivery-receive")
    BaseResponse deliveryReceive(@RequestBody @Valid SellPlatformOrderRequest request);


    /**
     * @description 获取物流公司列表
     * @author malianfeng
     * @date 2022/4/26 18:39
     */
    @PostMapping("/vas/${application.vas.version}/sell-platform/order/get-express-list")
    BaseResponse<SellPlatformExpressListResponse> getExpressList(@RequestBody @Valid SellPlatformBaseRequest request);

}
