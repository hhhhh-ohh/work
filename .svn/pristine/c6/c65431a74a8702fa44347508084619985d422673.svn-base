package com.wanmi.sbc.empower.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.returnorder.*;
import com.wanmi.sbc.empower.api.response.sellplatform.returnorder.PlatformAddReturnOrderResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.returnorder.PlatformGetReturnOrderResponse;

/**
*
 * @description    订单相关处理
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
public interface PlatformReturnOrderService extends PlatformBaseService {

    /**
     * @description    上传默认收货地址
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse<PlatformAddReturnOrderResponse> updateAccountAddress(PlatformUpdateShopAddressRequest request);

    /**
     * @description     添加退单数据
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse<PlatformAddReturnOrderResponse> addReturnOrder(PlatformAddReturnOrderRequest request);

    /**
     * @description   取消退单
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse cancelReturnOrder(PlatformReturnOrderRequest request);

    /**
     * @description   同意退单
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse acceptReturn(PlatformAcceptReturnRequest request);

    /**
     * @description    上传退单物流信息
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse upReturnInfo(PlatformUpReturnInfoRequest request);

    /**
     * @description    同意退款
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse acceptRefund(PlatformReturnOrderRequest request);

    /**
     * @description    拒绝售后
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse rejectReturn(PlatformReturnOrderRequest request);

    /**
     * @description     上传退款凭证
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse upReturnCertificates(PlatformUpReturnCertificatesRequest request);

    /**
     * @description     修改退单信息
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse updateReturnOrder(PlatformUpdateReturnOrderRequest request);

    /**
     * @description     查询退单详情
     * @author  wur
     * @date: 2022/4/29 11:36
     * @param request
     * @return
     **/
    BaseResponse<PlatformGetReturnOrderResponse> getReturnOrder(PlatformReturnOrderRequest request);

}
