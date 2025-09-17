package com.wanmi.sbc.order.provider.impl.refundcallbackresult;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.refundcallbackresult.RefundCallBackResultQueryProvider;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultPageRequest;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultQueryRequest;
import com.wanmi.sbc.order.api.response.refundcallbackresult.RefundCallBackResultPageResponse;
import com.wanmi.sbc.order.bean.vo.RefundCallBackResultVO;
import com.wanmi.sbc.order.refundcallbackresult.model.root.RefundCallBackResult;
import com.wanmi.sbc.order.refundcallbackresult.service.RefundCallBackResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>退款回调结果查询服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@RestController
@Validated
public class RefundCallBackResultQueryController implements RefundCallBackResultQueryProvider {
	@Autowired
	private RefundCallBackResultService refundCallBackResultService;

	@Override
	public BaseResponse<RefundCallBackResultPageResponse> page(@RequestBody @Valid RefundCallBackResultPageRequest request) {
		RefundCallBackResultQueryRequest queryReq = KsBeanUtil.convert(request, RefundCallBackResultQueryRequest.class);
		Page<RefundCallBackResult> payCallBackResultPage = refundCallBackResultService.page(queryReq);
		Page<RefundCallBackResultVO> newPage = payCallBackResultPage.map(entity -> refundCallBackResultService.wrapperVo(entity));
		MicroServicePage<RefundCallBackResultVO> microPage = new MicroServicePage<>(newPage, request.getPageable());
		RefundCallBackResultPageResponse finalRes = new RefundCallBackResultPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

}

