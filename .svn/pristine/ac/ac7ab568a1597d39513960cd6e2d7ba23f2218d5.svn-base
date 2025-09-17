package com.wanmi.sbc.customer.provider.impl.payingmemberlevel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelProvider;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.*;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.PayingMemberLevelModifyResponse;
import com.wanmi.sbc.customer.payingmemberlevel.model.root.PayingMemberLevel;
import com.wanmi.sbc.customer.payingmemberlevel.service.PayingMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>付费会员等级表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@RestController
@Validated
public class PayingMemberLevelController implements PayingMemberLevelProvider {
	@Autowired
	private PayingMemberLevelService payingMemberLevelService;

	@Override
	public BaseResponse add(@RequestBody @Valid PayingMemberLevelAddRequest payingMemberLevelAddRequest) {
//		PayingMemberLevel payingMemberLevel = KsBeanUtil.convert(payingMemberLevelAddRequest, PayingMemberLevel.class);
		payingMemberLevelService.add(payingMemberLevelAddRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<PayingMemberLevelModifyResponse> modify(@RequestBody @Valid PayingMemberLevelModifyRequest payingMemberLevelModifyRequest) {
//		PayingMemberLevel payingMemberLevel = KsBeanUtil.convert(payingMemberLevelModifyRequest, PayingMemberLevel.class);
		payingMemberLevelService.modify(payingMemberLevelModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberLevelDelByIdRequest payingMemberLevelDelByIdRequest) {
		PayingMemberLevel payingMemberLevel = KsBeanUtil.convert(payingMemberLevelDelByIdRequest, PayingMemberLevel.class);
		payingMemberLevel.setDelFlag(DeleteFlag.YES);
		payingMemberLevelService.deleteById(payingMemberLevel);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberLevelDelByIdListRequest payingMemberLevelDelByIdListRequest) {
		payingMemberLevelService.deleteByIdList(payingMemberLevelDelByIdListRequest.getLevelIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyStatus(PayingMemberLevelStatusRequest payingMemberLevelStatusRequest) {
		payingMemberLevelService.modifyLevelState(payingMemberLevelStatusRequest.getStatus());
		return BaseResponse.SUCCESSFUL();
	}

}

