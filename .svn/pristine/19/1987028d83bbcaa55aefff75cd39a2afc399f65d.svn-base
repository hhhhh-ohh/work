package com.wanmi.sbc.empower.api.provider.sellplatform.order;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.order.*;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformAddOrderResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformCompanyResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformOrderPayParamResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformQueryOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * @author wur
 * @className PlatformOrderProvider
 * @description 微信视频订单处理
 * @date 2022/4/11 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "PlatformOrderProvider")
public interface PlatformOrderProvider {

    /**
     * @description  同步订单
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/platform/add_order/")
    BaseResponse<PlatformAddOrderResponse> addOrder(@RequestBody @Valid PlatformAddOrderRequest request);

    /**
    *
     * @description 获取支付参数
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/channel/${application.empower.version}/platform/get_pay_param/")
    BaseResponse<PlatformOrderPayParamResponse> getPayParams(PlatformOrderRequest request);

    /**
    *
     * @description 同步支付结果
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/platform/pay/")
    BaseResponse pay(@RequestBody @Valid PlatformOrderPayRequest request);

    /**
     * @description 订单发货
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/platform/delivery_send/")
    BaseResponse deliverySend(@RequestBody @Valid PlatformDeliverySendRequest request);

    /**
    *
     * @description 确认收货
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/platform/delivery_recieve/")
    BaseResponse deliveryRecieve(@RequestBody @Valid PlatformOrderRequest request);

    /**
     * @description   查询订单
     * @author  wur
     * @date: 2022/4/13 11:46
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/platform/query_order/")
    BaseResponse<PlatformQueryOrderResponse> queryOrder(@RequestBody @Valid PlatformOrderRequest request);

    /**
     * @description    关闭订单
     * @author  wur
     * @date: 2022/4/13 13:38
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/platform/close_order/")
    BaseResponse closeOrder(@RequestBody @Valid PlatformOrderRequest request);

    /**
     * @description
     * @author  wur
     * @date: 2022/4/26 15:07
     * @return
     **/
    @PostMapping("/empower/${application.empower.version}/platform/company_list/")
    BaseResponse<PlatformCompanyResponse> getCompanyList(ThirdBaseRequest request);

}
