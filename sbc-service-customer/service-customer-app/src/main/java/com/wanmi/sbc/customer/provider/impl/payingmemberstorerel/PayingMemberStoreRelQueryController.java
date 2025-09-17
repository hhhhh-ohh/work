package com.wanmi.sbc.customer.provider.impl.payingmemberstorerel;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmemberstorerel.PayingMemberStoreRelQueryProvider;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelPageRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelQueryRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelPageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelExportResponse;
import com.wanmi.sbc.customer.bean.vo.PayingMemberStoreRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberStoreRelPageVO;
import com.wanmi.sbc.customer.payingmemberstorerel.service.PayingMemberStoreRelService;
import com.wanmi.sbc.customer.payingmemberstorerel.model.root.PayingMemberStoreRel;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商家与付费会员等级关联表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@RestController
@Validated
public class PayingMemberStoreRelQueryController implements PayingMemberStoreRelQueryProvider {
	@Autowired
	private PayingMemberStoreRelService payingMemberStoreRelService;

	@Override
	public BaseResponse<PayingMemberStoreRelPageResponse> page(@RequestBody @Valid PayingMemberStoreRelPageRequest payingMemberStoreRelPageReq) {
		PayingMemberStoreRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberStoreRelPageReq, PayingMemberStoreRelQueryRequest.class);
		Page<PayingMemberStoreRel> payingMemberStoreRelPage = payingMemberStoreRelService.page(queryReq);
		Page<PayingMemberStoreRelVO> newPage = payingMemberStoreRelPage.map(entity -> payingMemberStoreRelService.wrapperVo(entity));
		MicroServicePage<PayingMemberStoreRelVO> microPage = new MicroServicePage<>(newPage, payingMemberStoreRelPageReq.getPageable());
		PayingMemberStoreRelPageResponse finalRes = new PayingMemberStoreRelPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberStoreRelListResponse> list(@RequestBody @Valid PayingMemberStoreRelListRequest payingMemberStoreRelListReq) {
		PayingMemberStoreRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberStoreRelListReq, PayingMemberStoreRelQueryRequest.class);
		List<PayingMemberStoreRel> payingMemberStoreRelList = payingMemberStoreRelService.list(queryReq);
		List<PayingMemberStoreRelVO> newList = payingMemberStoreRelList.stream().map(entity -> payingMemberStoreRelService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberStoreRelListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberStoreRelByIdResponse> getById(@RequestBody @Valid PayingMemberStoreRelByIdRequest payingMemberStoreRelByIdRequest) {
		PayingMemberStoreRel payingMemberStoreRel =
		payingMemberStoreRelService.getOne(payingMemberStoreRelByIdRequest.getId(), payingMemberStoreRelByIdRequest.getStoreId());
		return BaseResponse.success(new PayingMemberStoreRelByIdResponse(payingMemberStoreRelService.wrapperVo(payingMemberStoreRel)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberStoreRelExportRequest request) {
		PayingMemberStoreRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberStoreRelQueryRequest.class);
		Long total = payingMemberStoreRelService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberStoreRelExportResponse> exportPayingMemberStoreRelRecord(@RequestBody @Valid PayingMemberStoreRelExportRequest request) {
		PayingMemberStoreRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberStoreRelQueryRequest.class);
		List<PayingMemberStoreRelPageVO> data = KsBeanUtil.convert(payingMemberStoreRelService.page(queryReq).getContent(),PayingMemberStoreRelPageVO.class);
		return BaseResponse.success(new PayingMemberStoreRelExportResponse(data));
	}
}

