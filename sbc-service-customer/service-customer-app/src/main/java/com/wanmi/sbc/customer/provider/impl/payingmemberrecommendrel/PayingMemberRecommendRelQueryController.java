package com.wanmi.sbc.customer.provider.impl.payingmemberrecommendrel;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmemberrecommendrel.PayingMemberRecommendRelQueryProvider;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelPageRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelQueryRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelPageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelExportResponse;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRecommendRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRecommendRelPageVO;
import com.wanmi.sbc.customer.payingmemberrecommendrel.service.PayingMemberRecommendRelService;
import com.wanmi.sbc.customer.payingmemberrecommendrel.model.root.PayingMemberRecommendRel;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>推荐商品与付费会员等级关联表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@RestController
@Validated
public class PayingMemberRecommendRelQueryController implements PayingMemberRecommendRelQueryProvider {
	@Autowired
	private PayingMemberRecommendRelService payingMemberRecommendRelService;

	@Override
	public BaseResponse<PayingMemberRecommendRelPageResponse> page(@RequestBody @Valid PayingMemberRecommendRelPageRequest payingMemberRecommendRelPageReq) {
		PayingMemberRecommendRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberRecommendRelPageReq, PayingMemberRecommendRelQueryRequest.class);
		Page<PayingMemberRecommendRel> payingMemberRecommendRelPage = payingMemberRecommendRelService.page(queryReq);
		Page<PayingMemberRecommendRelVO> newPage = payingMemberRecommendRelPage.map(entity -> payingMemberRecommendRelService.wrapperVo(entity));
		MicroServicePage<PayingMemberRecommendRelVO> microPage = new MicroServicePage<>(newPage, payingMemberRecommendRelPageReq.getPageable());
		PayingMemberRecommendRelPageResponse finalRes = new PayingMemberRecommendRelPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberRecommendRelListResponse> list(@RequestBody @Valid PayingMemberRecommendRelListRequest payingMemberRecommendRelListReq) {
		PayingMemberRecommendRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberRecommendRelListReq, PayingMemberRecommendRelQueryRequest.class);
		List<PayingMemberRecommendRel> payingMemberRecommendRelList = payingMemberRecommendRelService.list(queryReq);
		List<PayingMemberRecommendRelVO> newList = payingMemberRecommendRelList.stream().map(entity -> payingMemberRecommendRelService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberRecommendRelListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberRecommendRelByIdResponse> getById(@RequestBody @Valid PayingMemberRecommendRelByIdRequest payingMemberRecommendRelByIdRequest) {
		PayingMemberRecommendRel payingMemberRecommendRel =
		payingMemberRecommendRelService.getOne(payingMemberRecommendRelByIdRequest.getId());
		return BaseResponse.success(new PayingMemberRecommendRelByIdResponse(payingMemberRecommendRelService.wrapperVo(payingMemberRecommendRel)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberRecommendRelExportRequest request) {
		PayingMemberRecommendRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberRecommendRelQueryRequest.class);
		Long total = payingMemberRecommendRelService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberRecommendRelExportResponse> exportPayingMemberRecommendRelRecord(@RequestBody @Valid PayingMemberRecommendRelExportRequest request) {
		PayingMemberRecommendRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberRecommendRelQueryRequest.class);
		List<PayingMemberRecommendRelPageVO> data = KsBeanUtil.convert(payingMemberRecommendRelService.page(queryReq).getContent(),PayingMemberRecommendRelPageVO.class);
		return BaseResponse.success(new PayingMemberRecommendRelExportResponse(data));
	}
}

