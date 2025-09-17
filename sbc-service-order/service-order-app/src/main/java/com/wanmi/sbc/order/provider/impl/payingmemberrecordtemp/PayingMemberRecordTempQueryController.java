package com.wanmi.sbc.order.provider.impl.payingmemberrecordtemp;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempPageRequest;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempQueryRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempPageResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempListRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempListResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempByIdRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempByIdResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempExportRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempExportResponse;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempPageVO;
import com.wanmi.sbc.order.payingmemberrecordtemp.service.PayingMemberRecordTempService;
import com.wanmi.sbc.order.payingmemberrecordtemp.model.root.PayingMemberRecordTemp;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>付费记录临时表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@RestController
@Validated
public class PayingMemberRecordTempQueryController implements PayingMemberRecordTempQueryProvider {
	@Autowired
	private PayingMemberRecordTempService payingMemberRecordTempService;

	@Override
	public BaseResponse<PayingMemberRecordTempPageResponse> page(@RequestBody @Valid PayingMemberRecordTempPageRequest payingMemberRecordTempPageReq) {
		PayingMemberRecordTempQueryRequest queryReq = KsBeanUtil.convert(payingMemberRecordTempPageReq, PayingMemberRecordTempQueryRequest.class);
		Page<PayingMemberRecordTemp> payingMemberRecordTempPage = payingMemberRecordTempService.page(queryReq);
		Page<PayingMemberRecordTempVO> newPage = payingMemberRecordTempPage.map(entity -> payingMemberRecordTempService.wrapperVo(entity));
		MicroServicePage<PayingMemberRecordTempVO> microPage = new MicroServicePage<>(newPage, payingMemberRecordTempPageReq.getPageable());
		PayingMemberRecordTempPageResponse finalRes = new PayingMemberRecordTempPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberRecordTempListResponse> list(@RequestBody @Valid PayingMemberRecordTempListRequest payingMemberRecordTempListReq) {
		PayingMemberRecordTempQueryRequest queryReq = KsBeanUtil.convert(payingMemberRecordTempListReq, PayingMemberRecordTempQueryRequest.class);
		List<PayingMemberRecordTemp> payingMemberRecordTempList = payingMemberRecordTempService.list(queryReq);
		List<PayingMemberRecordTempVO> newList = payingMemberRecordTempList.stream().map(entity -> payingMemberRecordTempService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberRecordTempListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberRecordTempByIdResponse> getById(@RequestBody @Valid PayingMemberRecordTempByIdRequest payingMemberRecordTempByIdRequest) {
		PayingMemberRecordTemp payingMemberRecordTemp =
		payingMemberRecordTempService.getOne(payingMemberRecordTempByIdRequest.getRecordId());
		return BaseResponse.success(new PayingMemberRecordTempByIdResponse(payingMemberRecordTempService.wrapperVo(payingMemberRecordTemp)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberRecordTempExportRequest request) {
		PayingMemberRecordTempQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberRecordTempQueryRequest.class);
		Long total = payingMemberRecordTempService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberRecordTempExportResponse> exportPayingMemberRecordTempRecord(@RequestBody @Valid PayingMemberRecordTempExportRequest request) {
		PayingMemberRecordTempQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberRecordTempQueryRequest.class);
		List<PayingMemberRecordTempPageVO> data = KsBeanUtil.convert(payingMemberRecordTempService.page(queryReq).getContent(),PayingMemberRecordTempPageVO.class);
		return BaseResponse.success(new PayingMemberRecordTempExportResponse(data));
	}
}

