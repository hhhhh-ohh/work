package com.wanmi.sbc.order.api.provider.refundcallbackresult;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultPageRequest;
import com.wanmi.sbc.order.api.response.refundcallbackresult.RefundCallBackResultPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>退款回调结果查询服务Provider</p>
 * @date 2020-07-01 17:34:23
 */
@FeignClient(value = "${application.order.name}", contextId = "RefundCallBackResultQueryProvider")
public interface RefundCallBackResultQueryProvider {

	/**
	 * 分页查询退款回调结果API
	 *
	 * @param request 分页请求参数和筛选对象 {@link RefundCallBackResultPageRequest}
	 * @return 退款回调结果分页列表信息 {@link RefundCallBackResultPageResponse}
	 */
	@PostMapping("/order/${application.order.version}/refund/callback/page")
    BaseResponse<RefundCallBackResultPageResponse> page(@RequestBody @Valid RefundCallBackResultPageRequest request);

}

