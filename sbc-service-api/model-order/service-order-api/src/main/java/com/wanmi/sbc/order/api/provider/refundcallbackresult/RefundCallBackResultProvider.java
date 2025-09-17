package com.wanmi.sbc.order.api.provider.refundcallbackresult;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultAddRequest;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultModifyResultStatusRequest;
import com.wanmi.sbc.order.api.response.paycallbackresult.PayCallBackResultAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>退款回调结果保存服务Provider</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@FeignClient(value = "${application.order.name}", contextId = "RefundCallBackResultProvider")
public interface RefundCallBackResultProvider {

	/**
	 * 新增退款回调结果API
	 *
	 * @param request 支付回调结果新增参数结构 {@link RefundCallBackResultAddRequest}
	 * @return 新增的支付回调结果信息 {@link PayCallBackResultAddResponse}
	 */
	@PostMapping("/order/${application.order.version}/refund/callback/add")
    BaseResponse add(@RequestBody @Valid RefundCallBackResultAddRequest request);

	/**
	 * 根据businessId更新退款回调结果状态
	 *
	 * @param request 根据businessId更新退款回调结果状态参数结构 {@link RefundCallBackResultModifyResultStatusRequest}
	 * @return
	 */
	@PostMapping("/order/${application.order.version}/refund/callback/modify-result-status-by-businessId")
	BaseResponse modifyResultStatusByBusinessId(@RequestBody @Valid RefundCallBackResultModifyResultStatusRequest request);

}

