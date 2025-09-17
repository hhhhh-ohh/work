package com.wanmi.sbc.order.provider.impl.payingmemberpayrecord;

import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.payingmemberpayrecord.PayingMemberPayRecordQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordPageRequest;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordQueryRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordPageResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordListRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordListResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordByIdRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordByIdResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordExportRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordExportResponse;
import com.wanmi.sbc.order.bean.vo.PayingMemberPayRecordVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberPayRecordPageVO;
import com.wanmi.sbc.order.payingmemberpayrecord.service.PayingMemberPayRecordService;
import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>付费会员支付记录表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@RestController
@Validated
public class PayingMemberPayRecordQueryController implements PayingMemberPayRecordQueryProvider {
	@Autowired
	private PayingMemberPayRecordService payingMemberPayRecordService;

	@Override
	public BaseResponse<PayingMemberPayRecordPageResponse> page(@RequestBody @Valid PayingMemberPayRecordPageRequest payingMemberPayRecordPageReq) {
		PayingMemberPayRecordQueryRequest queryReq = KsBeanUtil.convert(payingMemberPayRecordPageReq, PayingMemberPayRecordQueryRequest.class);
		Page<PayingMemberPayRecord> payingMemberPayRecordPage = payingMemberPayRecordService.page(queryReq);
		Page<PayingMemberPayRecordVO> newPage = payingMemberPayRecordPage.map(entity -> payingMemberPayRecordService.wrapperVo(entity));
		MicroServicePage<PayingMemberPayRecordVO> microPage = new MicroServicePage<>(newPage, payingMemberPayRecordPageReq.getPageable());
		PayingMemberPayRecordPageResponse finalRes = new PayingMemberPayRecordPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberPayRecordListResponse> list(@RequestBody @Valid PayingMemberPayRecordListRequest payingMemberPayRecordListReq) {
		PayingMemberPayRecordQueryRequest queryReq = KsBeanUtil.convert(payingMemberPayRecordListReq, PayingMemberPayRecordQueryRequest.class);
		List<PayingMemberPayRecord> payingMemberPayRecordList = payingMemberPayRecordService.list(queryReq);
		List<PayingMemberPayRecordVO> newList = payingMemberPayRecordList.stream().map(entity -> payingMemberPayRecordService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberPayRecordListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberPayRecordByIdResponse> getById(@RequestBody @Valid PayingMemberPayRecordByIdRequest payingMemberPayRecordByIdRequest) {
		PayingMemberPayRecord payingMemberPayRecord =
		payingMemberPayRecordService.getOne(payingMemberPayRecordByIdRequest.getId());
		return BaseResponse.success(new PayingMemberPayRecordByIdResponse(payingMemberPayRecordService.wrapperVo(payingMemberPayRecord)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberPayRecordExportRequest request) {
		PayingMemberPayRecordQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberPayRecordQueryRequest.class);
		Long total = payingMemberPayRecordService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberPayRecordExportResponse> exportPayingMemberPayRecordRecord(@RequestBody @Valid PayingMemberPayRecordExportRequest request) {
		PayingMemberPayRecordQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberPayRecordQueryRequest.class);
		List<PayingMemberPayRecordPageVO> data = KsBeanUtil.convert(payingMemberPayRecordService.page(queryReq).getContent(),PayingMemberPayRecordPageVO.class);
		return BaseResponse.success(new PayingMemberPayRecordExportResponse(data));
	}

	@Override
	public BaseResponse<PayingMemberPayRecordByIdResponse> getTradeRecordByOrderCode(@RequestBody @Valid TradeRecordByOrderCodeRequest recordByOrderCodeRequest) {
		PayingMemberPayRecord payingMemberPayRecord = payingMemberPayRecordService.findByBusinessId(recordByOrderCodeRequest.getOrderId());
		return BaseResponse.success(PayingMemberPayRecordByIdResponse.builder()
				.payingMemberPayRecordVO(payingMemberPayRecordService.wrapperVo(payingMemberPayRecord))
				.build());
	}
}

