package com.wanmi.sbc.customer.provider.impl.payingmemberdiscountrel;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmemberdiscountrel.PayingMemberDiscountRelQueryProvider;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelPageRequest;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelQueryRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelPageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelExportResponse;
import com.wanmi.sbc.customer.bean.vo.PayingMemberDiscountRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberDiscountRelPageVO;
import com.wanmi.sbc.customer.payingmemberdiscountrel.service.PayingMemberDiscountRelService;
import com.wanmi.sbc.customer.payingmemberdiscountrel.model.root.PayingMemberDiscountRel;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>折扣商品与付费会员等级关联表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@RestController
@Validated
public class PayingMemberDiscountRelQueryController implements PayingMemberDiscountRelQueryProvider {
	@Autowired
	private PayingMemberDiscountRelService payingMemberDiscountRelService;

	@Override
	public BaseResponse<PayingMemberDiscountRelPageResponse> page(@RequestBody @Valid PayingMemberDiscountRelPageRequest payingMemberDiscountRelPageReq) {
		PayingMemberDiscountRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberDiscountRelPageReq, PayingMemberDiscountRelQueryRequest.class);
		Page<PayingMemberDiscountRel> payingMemberDiscountRelPage = payingMemberDiscountRelService.page(queryReq);
		Page<PayingMemberDiscountRelVO> newPage = payingMemberDiscountRelPage.map(entity -> payingMemberDiscountRelService.wrapperVo(entity));
		MicroServicePage<PayingMemberDiscountRelVO> microPage = new MicroServicePage<>(newPage, payingMemberDiscountRelPageReq.getPageable());
		PayingMemberDiscountRelPageResponse finalRes = new PayingMemberDiscountRelPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberDiscountRelListResponse> list(@RequestBody @Valid PayingMemberDiscountRelListRequest payingMemberDiscountRelListReq) {
		PayingMemberDiscountRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberDiscountRelListReq, PayingMemberDiscountRelQueryRequest.class);
		List<PayingMemberDiscountRel> payingMemberDiscountRelList = payingMemberDiscountRelService.list(queryReq);
		List<PayingMemberDiscountRelVO> newList = payingMemberDiscountRelList.stream().map(entity -> payingMemberDiscountRelService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberDiscountRelListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberDiscountRelByIdResponse> getById(@RequestBody @Valid PayingMemberDiscountRelByIdRequest payingMemberDiscountRelByIdRequest) {
		PayingMemberDiscountRel payingMemberDiscountRel =
		payingMemberDiscountRelService.getOne(payingMemberDiscountRelByIdRequest.getId());
		return BaseResponse.success(new PayingMemberDiscountRelByIdResponse(payingMemberDiscountRelService.wrapperVo(payingMemberDiscountRel)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberDiscountRelExportRequest request) {
		PayingMemberDiscountRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberDiscountRelQueryRequest.class);
		Long total = payingMemberDiscountRelService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberDiscountRelExportResponse> exportPayingMemberDiscountRelRecord(@RequestBody @Valid PayingMemberDiscountRelExportRequest request) {
		PayingMemberDiscountRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberDiscountRelQueryRequest.class);
		List<PayingMemberDiscountRelPageVO> data = KsBeanUtil.convert(payingMemberDiscountRelService.page(queryReq).getContent(),PayingMemberDiscountRelPageVO.class);
		return BaseResponse.success(new PayingMemberDiscountRelExportResponse(data));
	}
}

