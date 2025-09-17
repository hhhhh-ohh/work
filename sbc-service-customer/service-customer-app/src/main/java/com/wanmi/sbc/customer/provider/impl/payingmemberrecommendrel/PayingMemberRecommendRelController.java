package com.wanmi.sbc.customer.provider.impl.payingmemberrecommendrel;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.provider.payingmemberrecommendrel.PayingMemberRecommendRelProvider;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelDelByIdListRequest;
import com.wanmi.sbc.customer.payingmemberrecommendrel.service.PayingMemberRecommendRelService;
import com.wanmi.sbc.customer.payingmemberrecommendrel.model.root.PayingMemberRecommendRel;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>推荐商品与付费会员等级关联表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@RestController
@Validated
public class PayingMemberRecommendRelController implements PayingMemberRecommendRelProvider {
	@Autowired
	private PayingMemberRecommendRelService payingMemberRecommendRelService;

	@Override
	public BaseResponse<PayingMemberRecommendRelAddResponse> add(@RequestBody @Valid PayingMemberRecommendRelAddRequest payingMemberRecommendRelAddRequest) {
		PayingMemberRecommendRel payingMemberRecommendRel = KsBeanUtil.convert(payingMemberRecommendRelAddRequest, PayingMemberRecommendRel.class);
		return BaseResponse.success(new PayingMemberRecommendRelAddResponse(
				payingMemberRecommendRelService.wrapperVo(payingMemberRecommendRelService.add(payingMemberRecommendRel))));
	}

	@Override
	public BaseResponse<PayingMemberRecommendRelModifyResponse> modify(@RequestBody @Valid PayingMemberRecommendRelModifyRequest payingMemberRecommendRelModifyRequest) {
		PayingMemberRecommendRel payingMemberRecommendRel = KsBeanUtil.convert(payingMemberRecommendRelModifyRequest, PayingMemberRecommendRel.class);
		return BaseResponse.success(new PayingMemberRecommendRelModifyResponse(
				payingMemberRecommendRelService.wrapperVo(payingMemberRecommendRelService.modify(payingMemberRecommendRel))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberRecommendRelDelByIdRequest payingMemberRecommendRelDelByIdRequest) {
		PayingMemberRecommendRel payingMemberRecommendRel = KsBeanUtil.convert(payingMemberRecommendRelDelByIdRequest, PayingMemberRecommendRel.class);
		payingMemberRecommendRel.setDelFlag(DeleteFlag.YES);
		payingMemberRecommendRelService.deleteById(payingMemberRecommendRel);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRecommendRelDelByIdListRequest payingMemberRecommendRelDelByIdListRequest) {
		payingMemberRecommendRelService.deleteByIdList(payingMemberRecommendRelDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

