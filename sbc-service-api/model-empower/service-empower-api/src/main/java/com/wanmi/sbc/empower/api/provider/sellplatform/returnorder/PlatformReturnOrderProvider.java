package com.wanmi.sbc.empower.api.provider.sellplatform.returnorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.returnorder.*;
import com.wanmi.sbc.empower.api.response.sellplatform.returnorder.PlatformGetReturnOrderResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.returnorder.PlatformAddReturnOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * @author wur
 * @className PlatformReturnOrderProvider
 * @description 微信视频售后订单处理
 * @date 2022/4/11 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "PlatformReturnOrderProvider")
public interface PlatformReturnOrderProvider {

    /**
     *
     * @description 跟新默认退货地址
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/update_account_address/")
    BaseResponse updateAccountAddress(PlatformUpdateShopAddressRequest request);

    /**
     * @description   新增售后订单
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request   data 退单号
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/add_return_order/")
    BaseResponse<PlatformAddReturnOrderResponse> addReturnOrder(@RequestBody @Valid PlatformAddReturnOrderRequest request);

    /**
    *
     * @description 取消售后
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/cancel_return_order/")
    BaseResponse cancelReturnOrder(@RequestBody @Valid PlatformReturnOrderRequest request);

    /**
     * @description 同意退单
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/accept_return/")
    BaseResponse acceptReturn(@RequestBody @Valid PlatformAcceptReturnRequest request);

    /**
    *
     * @description 上传退货物流信息
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/up_return_info/")
    BaseResponse upReturnInfo(@RequestBody @Valid PlatformUpReturnInfoRequest request);

    /**
    *
     * @description 同意退款
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/accept_refund/")
    BaseResponse acceptRefund(@RequestBody @Valid PlatformReturnOrderRequest request);

    /**
    *
     * @description 拒绝售后
     * @author  wur
     * @date: 2022/4/11 17:15
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/reject_return/")
    BaseResponse rejectReturn(@RequestBody @Valid PlatformReturnOrderRequest request);

    /**
     *
     * @description 上传退款凭证
     * @author  wur
     * @date: 2022/4/11 17:15
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/up_return_certificates/")
    BaseResponse upReturnCertificates(@RequestBody @Valid PlatformUpReturnCertificatesRequest request);

    /**
     * @description   查询退单详情
     * @author  wur
     * @date: 2022/4/13 16:20
     * @param request
     * @return
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/get_return_order/")
    BaseResponse<PlatformGetReturnOrderResponse> getReturnOrder(@RequestBody @Valid PlatformReturnOrderRequest request);


    /**
     * @description   修改退单信息
     * @author  wur
     * @date: 2022/4/26 9:33
     * @param request
     * @return
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/update_return_order/")
    BaseResponse updateReturn(@RequestBody @Valid PlatformUpdateReturnOrderRequest request);

}
