package com.wanmi.sbc.vas.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.SellPlatformAcceptReturnOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.SellPlatformAddReturnOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.SellPlatformReturnOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.SellPlatformUpReturnOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.*;

/**
*
 * @description    退单相关处理
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
public interface SellPlatformReturnOrderService extends SellPlatformBaseService {

    /**
     * @description
     * @author malianfeng
     * @date 2022/4/25 19:33
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<java.lang.String>
     */
    BaseResponse<String> updateAccountAddress(SellPlatformUpdateAccountAddressRequest request);

    /**
     * @description   添加售后单
     * @author  wur
     * @date: 2022/4/24 9:49
     * @param request
     * @return
     **/
    BaseResponse<String> addReturnOrder( SellPlatformAddReturnOrderRequest request);

    /**
     * @description     取消售后
     * @author  wur
     * @date: 2022/4/24 9:49
     * @param request
     * @return
     **/
    BaseResponse cancelReturnOrder(SellPlatformReturnOrderRequest request);

    /**
     * @description    同意退货
     * @author  wur
     * @date: 2022/4/24 9:49
     * @param request
     * @return
     **/
    BaseResponse acceptReturnOrder(SellPlatformAcceptReturnOrderRequest request);

    /**
     * @description   上传退单物流信息
     * @author  wur
     * @date: 2022/4/24 10:20
     * @param request
     * @return
     **/
    BaseResponse upReturnInfo(SellPlatformUpReturnOrderRequest request);

    /**
     * @description    同意退款
     * @author  wur
     * @date: 2022/4/24 9:49
     * @param request
     * @return
     **/
    BaseResponse acceptRefund(SellPlatformReturnOrderRequest request);

    /**
     * @description    拒绝售后
     * @author  wur
     * @date: 2022/4/25 18:29
     * @param request
     * @return
     **/
    BaseResponse rejectReturn(SellPlatformReturnOrderRequest request);

    /**
     * @description    更新售后单
     * @author  wur
     * @date: 2022/4/25 18:29
     * @param request
     * @return
     **/
    BaseResponse updateReturn(SellPlatformAddReturnOrderRequest request);

}
