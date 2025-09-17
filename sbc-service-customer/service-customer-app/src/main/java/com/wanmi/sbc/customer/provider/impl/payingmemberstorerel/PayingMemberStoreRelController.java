package com.wanmi.sbc.customer.provider.impl.payingmemberstorerel;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.provider.payingmemberstorerel.PayingMemberStoreRelProvider;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelDelByIdListRequest;
import com.wanmi.sbc.customer.payingmemberstorerel.service.PayingMemberStoreRelService;
import com.wanmi.sbc.customer.payingmemberstorerel.model.root.PayingMemberStoreRel;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>商家与付费会员等级关联表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@RestController
@Validated
public class PayingMemberStoreRelController implements PayingMemberStoreRelProvider {
	@Autowired
	private PayingMemberStoreRelService payingMemberStoreRelService;

	@Override
	public BaseResponse<PayingMemberStoreRelAddResponse> add(@RequestBody @Valid PayingMemberStoreRelAddRequest payingMemberStoreRelAddRequest) {
		PayingMemberStoreRel payingMemberStoreRel = KsBeanUtil.convert(payingMemberStoreRelAddRequest, PayingMemberStoreRel.class);
		return BaseResponse.success(new PayingMemberStoreRelAddResponse(
				payingMemberStoreRelService.wrapperVo(payingMemberStoreRelService.add(payingMemberStoreRel))));
	}

	@Override
	public BaseResponse<PayingMemberStoreRelModifyResponse> modify(@RequestBody @Valid PayingMemberStoreRelModifyRequest payingMemberStoreRelModifyRequest) {
		PayingMemberStoreRel payingMemberStoreRel = KsBeanUtil.convert(payingMemberStoreRelModifyRequest, PayingMemberStoreRel.class);
		return BaseResponse.success(new PayingMemberStoreRelModifyResponse(
				payingMemberStoreRelService.wrapperVo(payingMemberStoreRelService.modify(payingMemberStoreRel))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberStoreRelDelByIdRequest payingMemberStoreRelDelByIdRequest) {
		PayingMemberStoreRel payingMemberStoreRel = KsBeanUtil.convert(payingMemberStoreRelDelByIdRequest, PayingMemberStoreRel.class);
		payingMemberStoreRel.setDelFlag(DeleteFlag.YES);
		payingMemberStoreRelService.deleteById(payingMemberStoreRel);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberStoreRelDelByIdListRequest payingMemberStoreRelDelByIdListRequest) {
		payingMemberStoreRelService.deleteByIdList(payingMemberStoreRelDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

