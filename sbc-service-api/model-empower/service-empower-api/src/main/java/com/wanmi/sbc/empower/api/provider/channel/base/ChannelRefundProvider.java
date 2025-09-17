package com.wanmi.sbc.empower.api.provider.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundApplyRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundCancelRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundQueryStatusRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundReasonRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundQueryStatusResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundReasonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 订单逆向：退货退款服务
 * @author  hanwei
 * @date 2021/5/28
 **/
@FeignClient(value = "${application.empower.name}", contextId = "ChannelRefundProvider")
public interface ChannelRefundProvider {

    /**
     * @description 查询退货原因列表
     * @author  hanwei
     * @date 2021/6/1 17:03
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundReasonResponse>
     **/
    @PostMapping("/empower/${application.empower.version}/channel/listRefundReason")
    BaseResponse<ChannelRefundReasonResponse> listRefundReason(@RequestBody @Valid ChannelRefundReasonRequest request);

    /**
     * @description 申请退货退款
     * @author  hanwei
     * @date 2021/6/1 17:04
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/empower/${application.empower.version}/channel/applyRefund")
    BaseResponse applyRefund(@RequestBody @Valid ChannelRefundApplyRequest request);

    /**
     * @description 取消退货退款
     * @author  hanwei
     * @date 2021/6/1 17:04
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/empower/${application.empower.version}/channel/cancelRefund")
    BaseResponse cancelRefund(@RequestBody @Valid ChannelRefundCancelRequest request);

    /**
     * @description 查询退货退款状态
     * @author  hanwei
     * @date 2021/6/1 17:05
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundQueryStatusResponse>
     **/
    @PostMapping("/empower/${application.empower.version}/channel/queryRefundStatus")
    BaseResponse<ChannelRefundQueryStatusResponse> queryRefundStatus(@RequestBody @Valid ChannelRefundQueryStatusRequest request);

}
