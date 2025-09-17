package com.wanmi.sbc.customer.provider.impl.payingmemberdiscountrel;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.provider.payingmemberdiscountrel.PayingMemberDiscountRelProvider;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelDelByIdListRequest;
import com.wanmi.sbc.customer.payingmemberdiscountrel.service.PayingMemberDiscountRelService;
import com.wanmi.sbc.customer.payingmemberdiscountrel.model.root.PayingMemberDiscountRel;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>折扣商品与付费会员等级关联表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@RestController
@Validated
public class PayingMemberDiscountRelController implements PayingMemberDiscountRelProvider {
	@Autowired
	private PayingMemberDiscountRelService payingMemberDiscountRelService;

	@Override
	public BaseResponse<PayingMemberDiscountRelAddResponse> add(@RequestBody @Valid PayingMemberDiscountRelAddRequest payingMemberDiscountRelAddRequest) {
		PayingMemberDiscountRel payingMemberDiscountRel = KsBeanUtil.convert(payingMemberDiscountRelAddRequest, PayingMemberDiscountRel.class);
		return BaseResponse.success(new PayingMemberDiscountRelAddResponse(
				payingMemberDiscountRelService.wrapperVo(payingMemberDiscountRelService.add(payingMemberDiscountRel))));
	}

	@Override
	public BaseResponse<PayingMemberDiscountRelModifyResponse> modify(@RequestBody @Valid PayingMemberDiscountRelModifyRequest payingMemberDiscountRelModifyRequest) {
		PayingMemberDiscountRel payingMemberDiscountRel = KsBeanUtil.convert(payingMemberDiscountRelModifyRequest, PayingMemberDiscountRel.class);
		return BaseResponse.success(new PayingMemberDiscountRelModifyResponse(
				payingMemberDiscountRelService.wrapperVo(payingMemberDiscountRelService.modify(payingMemberDiscountRel))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberDiscountRelDelByIdRequest payingMemberDiscountRelDelByIdRequest) {
		PayingMemberDiscountRel payingMemberDiscountRel = KsBeanUtil.convert(payingMemberDiscountRelDelByIdRequest, PayingMemberDiscountRel.class);
		payingMemberDiscountRel.setDelFlag(DeleteFlag.YES);
		payingMemberDiscountRelService.deleteById(payingMemberDiscountRel);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberDiscountRelDelByIdListRequest payingMemberDiscountRelDelByIdListRequest) {
		payingMemberDiscountRelService.deleteByIdList(payingMemberDiscountRelDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

