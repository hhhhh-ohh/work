package com.wanmi.sbc.order.provider.impl.refundcallbackresult;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.refundcallbackresult.RefundCallBackResultProvider;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultAddRequest;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultModifyResultStatusRequest;
import com.wanmi.sbc.order.api.response.refundcallbackresult.RefundCallBackResultAddResponse;
import com.wanmi.sbc.order.bean.dto.RefundCallBackResultDTO;
import com.wanmi.sbc.order.refundcallbackresult.model.root.RefundCallBackResult;
import com.wanmi.sbc.order.refundcallbackresult.service.RefundCallBackResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>退款回调结果保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@RestController
@Validated
public class RefundCallBackResultController implements RefundCallBackResultProvider {

	@Autowired
	private RefundCallBackResultService refundCallBackResultService;

	@Override
	public BaseResponse<RefundCallBackResultAddResponse> add(@RequestBody @Valid RefundCallBackResultAddRequest request) {
		RefundCallBackResultDTO refundCallBackResultDTO = request.getRefundCallBackResultDTO();
		RefundCallBackResult refundCallBackResult = KsBeanUtil.convert(refundCallBackResultDTO,RefundCallBackResult.class);
		RefundCallBackResult result = refundCallBackResultService.add(refundCallBackResult);
		return BaseResponse.success(new RefundCallBackResultAddResponse(result.getId()));
	}

	@Override
	public BaseResponse modifyResultStatusByBusinessId(@RequestBody @Valid RefundCallBackResultModifyResultStatusRequest request) {
		refundCallBackResultService.updateStatus(request.getBusinessId(),request.getResultStatus());
		return BaseResponse.SUCCESSFUL();
	}

}

