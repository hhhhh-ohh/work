package com.wanmi.sbc.customer.provider.impl.payingmemberrightsrel;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmemberrightsrel.PayingMemberRightsRelQueryProvider;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelPageRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelQueryRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelPageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelExportResponse;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRightsRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRightsRelPageVO;
import com.wanmi.sbc.customer.payingmemberrightsrel.service.PayingMemberRightsRelService;
import com.wanmi.sbc.customer.payingmemberrightsrel.model.root.PayingMemberRightsRel;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>权益与付费会员等级关联表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@RestController
@Validated
public class PayingMemberRightsRelQueryController implements PayingMemberRightsRelQueryProvider {
	@Autowired
	private PayingMemberRightsRelService payingMemberRightsRelService;

	@Override
	public BaseResponse<PayingMemberRightsRelPageResponse> page(@RequestBody @Valid PayingMemberRightsRelPageRequest payingMemberRightsRelPageReq) {
		PayingMemberRightsRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberRightsRelPageReq, PayingMemberRightsRelQueryRequest.class);
		Page<PayingMemberRightsRel> payingMemberRightsRelPage = payingMemberRightsRelService.page(queryReq);
		Page<PayingMemberRightsRelVO> newPage = payingMemberRightsRelPage.map(entity -> payingMemberRightsRelService.wrapperVo(entity));
		MicroServicePage<PayingMemberRightsRelVO> microPage = new MicroServicePage<>(newPage, payingMemberRightsRelPageReq.getPageable());
		PayingMemberRightsRelPageResponse finalRes = new PayingMemberRightsRelPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberRightsRelListResponse> list(@RequestBody @Valid PayingMemberRightsRelListRequest payingMemberRightsRelListReq) {
		PayingMemberRightsRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberRightsRelListReq, PayingMemberRightsRelQueryRequest.class);
		List<PayingMemberRightsRel> payingMemberRightsRelList = payingMemberRightsRelService.list(queryReq);
		List<PayingMemberRightsRelVO> newList = payingMemberRightsRelList.stream().map(entity -> payingMemberRightsRelService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberRightsRelListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberRightsRelByIdResponse> getById(@RequestBody @Valid PayingMemberRightsRelByIdRequest payingMemberRightsRelByIdRequest) {
		PayingMemberRightsRel payingMemberRightsRel =
		payingMemberRightsRelService.getOne(payingMemberRightsRelByIdRequest.getId());
		return BaseResponse.success(new PayingMemberRightsRelByIdResponse(payingMemberRightsRelService.wrapperVo(payingMemberRightsRel)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberRightsRelExportRequest request) {
		PayingMemberRightsRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberRightsRelQueryRequest.class);
		Long total = payingMemberRightsRelService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberRightsRelExportResponse> exportPayingMemberRightsRelRecord(@RequestBody @Valid PayingMemberRightsRelExportRequest request) {
		PayingMemberRightsRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberRightsRelQueryRequest.class);
		List<PayingMemberRightsRelPageVO> data = KsBeanUtil.convert(payingMemberRightsRelService.page(queryReq).getContent(),PayingMemberRightsRelPageVO.class);
		return BaseResponse.success(new PayingMemberRightsRelExportResponse(data));
	}
}

