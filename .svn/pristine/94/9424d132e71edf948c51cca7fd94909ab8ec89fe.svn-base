package com.wanmi.sbc.empower.api.provider.channel.vop.order;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.vop.VopOrderFreightQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.VopOrderRePayRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.order.VopQueryOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.order.VopQueryOrderTrackRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.VopOrderFreightQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.order.VopQueryOrderDetailResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.order.VopQueryOrderTrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author daiyitian
 * @description 京东VOP订单服务Provider
 * @date 2021/5/10 15:58
 */
@FeignClient(value = "${application.empower.name}", contextId = "VopOrderProvider")
public interface VopOrderProvider {

    /**
     * 重起发起支付
     *
     * @param request 第三方订单号 {@link VopOrderRePayRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/vop/${application.empower.version}/order/re-pay")
    BaseResponse rePay(@RequestBody @Valid VopOrderRePayRequest request);

    /**
     * 查询订单运费
     *
     * @param request 查询订单运费请求类 {@link VopOrderFreightQueryRequest}
     * @return 查询订单运费响应类 {@link VopOrderFreightQueryResponse}
     */
    @PostMapping("/vop/${application.empower.version}/order/query-freight")
    BaseResponse<VopOrderFreightQueryResponse> queryFreight(
            @RequestBody @Valid VopOrderFreightQueryRequest request);

    /**
     * 查询订单详情
     *
     * @param request 查询订单详情请求类 {@link VopQueryOrderDetailRequest}
     * @return 查询订单详情响应类 {@link VopQueryOrderDetailResponse}
     */
    @PostMapping("/vop/${application.empower.version}/order/query-detail")
    BaseResponse<VopQueryOrderDetailResponse> queryOrderDetail(
            @RequestBody @Valid VopQueryOrderDetailRequest request);

    /**
     * 查询订单配送信息
     *
     * @param request 查询订单详情请求类 {@link VopQueryOrderTrackRequest}
     * @return 查询订单详情响应类 {@link VopQueryOrderTrackResponse}
     */
    @PostMapping("/vop/${application.empower.version}/order/query-track")
    BaseResponse<VopQueryOrderTrackResponse> queryOrderTrack(
            @RequestBody @Valid VopQueryOrderTrackRequest request);
}
