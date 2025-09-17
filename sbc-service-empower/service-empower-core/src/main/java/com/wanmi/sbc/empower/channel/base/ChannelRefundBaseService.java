package com.wanmi.sbc.empower.channel.base;

import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundApplyRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundCancelRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundQueryStatusRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundReasonRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundQueryStatusResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundReasonResponse;

/**
 * @description 对接第三方订单逆向：退货退款接口
 * @author daiyitian
 * @date 2021/5/11 14:36
 */
public interface ChannelRefundBaseService extends ChannelBaseService{

    /**
     * @description 查询退货原因列表
     * @author  hanwei
     * @date 2021/6/1 17:03
     * @param request
     * @return com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundReasonResponse
     **/
    ChannelRefundReasonResponse listRefundReason(ChannelRefundReasonRequest request);

    /**
     * @description 申请退货退款
     * @author  hanwei
     * @date 2021/6/1 17:04
     * @param request
     * @return Boolean
     **/
    Boolean applyRefund(ChannelRefundApplyRequest request);

    /**
     * @description 取消退货退款
     * @author  hanwei
     * @date 2021/6/1 17:04
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    Boolean cancelRefund(ChannelRefundCancelRequest request);

    /**
     * @description 查询退货退款状态
     * @author  hanwei
     * @date 2021/6/1 17:05
     * @param request
     * @return com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundQueryStatusResponse
     **/
    ChannelRefundQueryStatusResponse queryRefundStatus(ChannelRefundQueryStatusRequest request);
}
