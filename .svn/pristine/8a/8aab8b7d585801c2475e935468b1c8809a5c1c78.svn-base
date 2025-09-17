package com.wanmi.sbc.order.provider.impl.payingmemberrecord;

import com.wanmi.sbc.order.api.request.payingmemberrecord.*;
import com.wanmi.sbc.order.api.response.payingmemberrecord.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordQueryProvider;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordPageVO;
import com.wanmi.sbc.order.payingmemberrecord.service.PayingMemberRecordService;
import com.wanmi.sbc.order.payingmemberrecord.model.root.PayingMemberRecord;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>付费记录表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@RestController
@Validated
public class PayingMemberRecordQueryController implements PayingMemberRecordQueryProvider {
	@Autowired
	private PayingMemberRecordService payingMemberRecordService;

	@Override
	public BaseResponse<PayingMemberRecordPageResponse> page(@RequestBody @Valid PayingMemberRecordPageRequest payingMemberRecordPageReq) {
		PayingMemberRecordQueryRequest queryReq = KsBeanUtil.convert(payingMemberRecordPageReq, PayingMemberRecordQueryRequest.class);
		Page<PayingMemberRecord> payingMemberRecordPage = payingMemberRecordService.page(queryReq);
		Page<PayingMemberRecordVO> newPage = payingMemberRecordPage.map(entity -> payingMemberRecordService.wrapperVo(entity));
		MicroServicePage<PayingMemberRecordVO> microPage = new MicroServicePage<>(newPage, payingMemberRecordPageReq.getPageable());
		PayingMemberRecordPageResponse finalRes = new PayingMemberRecordPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberRecordListResponse> list(@RequestBody @Valid PayingMemberRecordListRequest payingMemberRecordListReq) {
		PayingMemberRecordQueryRequest queryReq = KsBeanUtil.convert(payingMemberRecordListReq, PayingMemberRecordQueryRequest.class);
		List<PayingMemberRecord> payingMemberRecordList = payingMemberRecordService.list(queryReq);
		List<PayingMemberRecordVO> newList = payingMemberRecordList.stream().map(entity -> payingMemberRecordService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberRecordListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberRecordByIdResponse> getById(@RequestBody @Valid PayingMemberRecordByIdRequest payingMemberRecordByIdRequest) {
		PayingMemberRecord payingMemberRecord =
		payingMemberRecordService.getOne(payingMemberRecordByIdRequest.getRecordId());
		return BaseResponse.success(new PayingMemberRecordByIdResponse(payingMemberRecordService.wrapperVo(payingMemberRecord)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberRecordExportRequest request) {
		PayingMemberRecordQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberRecordQueryRequest.class);
		Long total = payingMemberRecordService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberRecordExportResponse> exportPayingMemberRecordRecord(@RequestBody @Valid PayingMemberRecordExportRequest request) {
		PayingMemberRecordQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberRecordQueryRequest.class);
		List<PayingMemberRecordPageVO> data = KsBeanUtil.convert(payingMemberRecordService.page(queryReq).getContent(),PayingMemberRecordPageVO.class);
		return BaseResponse.success(new PayingMemberRecordExportResponse(data));
	}

	@Override
	public BaseResponse<PayingMemberRecordCustomerResponse> findByRights(@RequestBody @Valid PayingMemberRecordRightsRequest request) {
		Map<String, String> map = payingMemberRecordService.findByRights(request);
		return BaseResponse.success(new PayingMemberRecordCustomerResponse(map));
	}

	@Override
	public BaseResponse<PayingMemberRecordByIdResponse> findById(@RequestBody @Valid PayingMemberRecordByIdRequest payingMemberRecordByIdRequest) {
		PayingMemberRecord payingMemberRecord =
				payingMemberRecordService.findById(payingMemberRecordByIdRequest.getRecordId());
		return BaseResponse.success(new PayingMemberRecordByIdResponse(payingMemberRecordService.wrapperVo(payingMemberRecord)));
	}
}

