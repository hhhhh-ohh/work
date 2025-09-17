package com.wanmi.sbc.customer.provider.impl.payingmemberrightsrel;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.provider.payingmemberrightsrel.PayingMemberRightsRelProvider;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelDelByIdListRequest;
import com.wanmi.sbc.customer.payingmemberrightsrel.service.PayingMemberRightsRelService;
import com.wanmi.sbc.customer.payingmemberrightsrel.model.root.PayingMemberRightsRel;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>权益与付费会员等级关联表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@RestController
@Validated
public class PayingMemberRightsRelController implements PayingMemberRightsRelProvider {
	@Autowired
	private PayingMemberRightsRelService payingMemberRightsRelService;

	@Override
	public BaseResponse<PayingMemberRightsRelAddResponse> add(@RequestBody @Valid PayingMemberRightsRelAddRequest payingMemberRightsRelAddRequest) {
		PayingMemberRightsRel payingMemberRightsRel = KsBeanUtil.convert(payingMemberRightsRelAddRequest, PayingMemberRightsRel.class);
		return BaseResponse.success(new PayingMemberRightsRelAddResponse(
				payingMemberRightsRelService.wrapperVo(payingMemberRightsRelService.add(payingMemberRightsRel))));
	}

	@Override
	public BaseResponse<PayingMemberRightsRelModifyResponse> modify(@RequestBody @Valid PayingMemberRightsRelModifyRequest payingMemberRightsRelModifyRequest) {
		PayingMemberRightsRel payingMemberRightsRel = KsBeanUtil.convert(payingMemberRightsRelModifyRequest, PayingMemberRightsRel.class);
		return BaseResponse.success(new PayingMemberRightsRelModifyResponse(
				payingMemberRightsRelService.wrapperVo(payingMemberRightsRelService.modify(payingMemberRightsRel))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberRightsRelDelByIdRequest payingMemberRightsRelDelByIdRequest) {
		PayingMemberRightsRel payingMemberRightsRel = KsBeanUtil.convert(payingMemberRightsRelDelByIdRequest, PayingMemberRightsRel.class);
		payingMemberRightsRel.setDelFlag(DeleteFlag.YES);
		payingMemberRightsRelService.deleteById(payingMemberRightsRel);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRightsRelDelByIdListRequest payingMemberRightsRelDelByIdListRequest) {
		payingMemberRightsRelService.deleteByIdList(payingMemberRightsRelDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

