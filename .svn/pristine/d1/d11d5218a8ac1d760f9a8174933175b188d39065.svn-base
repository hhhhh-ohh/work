package com.wanmi.sbc.vas.api.provider.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
*
 * @description SellPlatformReturnOrderProvider  第三方平台回单流程
 * @author  wur
 * @date: 2022/4/18 9:39
 **/
@FeignClient(value = "${application.vas.name}", contextId = "SellPlatformReturnOrderProvider")
public interface SellPlatformReturnOrderProvider {

    /**
     * @description 更新商家信息
     * @author malianfeng
     * @date 2022/4/25 19:31
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/vas/${application.vas.version}/sell-platform/return-order/update-account-address")
    BaseResponse updateAccountAddress(@RequestBody @Valid SellPlatformUpdateAccountAddressRequest request);

    /**
     * @description     生成售后单
     * @author  wur
     * @date: 2022/4/23 20:33
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/return-order/add")
    BaseResponse<String> addReturnOrder(@RequestBody @Valid SellPlatformAddReturnOrderRequest request);

    /**
     * @description   取消售后
     * @author  wur
     * @date: 2022/4/23 20:33
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/return-order/cancel")
    BaseResponse cancelReturnOrder(@RequestBody @Valid SellPlatformReturnOrderRequest request);

    /**
     * @description    同意退货
     * @author  wur
     * @date: 2022/4/23 20:33
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/return-order/accept")
    BaseResponse acceptReturnOrder(@RequestBody @Valid SellPlatformAcceptReturnOrderRequest request);

    /**
     * @description    上传退单信息
     * @author  wur
     * @date: 2022/4/24 10:32
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/return-order/up-info")
    BaseResponse upReturnInfo(@RequestBody @Valid SellPlatformUpReturnOrderRequest request);

    /**
     * @description   同意退款
     * @author  wur
     * @date: 2022/4/24 9:21
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/return-order/accept-refund")
    BaseResponse acceptRefund(@RequestBody @Valid SellPlatformReturnOrderRequest request);

    /**
     * @description   拒绝售后
     * @author  wur
     * @date: 2022/4/26 13:38
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/return-order/reject")
    BaseResponse rejectReturn(@RequestBody @Valid SellPlatformReturnOrderRequest request);

    /**
     * @description   修改订单
     * @author  wur
     * @date: 2022/4/27 10:44
     * @param request
     * @return
     **/
    @PostMapping("/vas/${application.vas.version}/sell-platform/return-order/update")
    BaseResponse updateReturn(@RequestBody @Valid SellPlatformAddReturnOrderRequest request);

}
