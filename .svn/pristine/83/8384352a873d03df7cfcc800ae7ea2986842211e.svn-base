package com.wanmi.sbc.order.provider.impl.payingmemberrecordtemp;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempAddRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempAddResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempModifyRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempModifyResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempDelByIdRequest;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempDelByIdListRequest;
import com.wanmi.sbc.order.payingmemberrecordtemp.service.PayingMemberRecordTempService;
import com.wanmi.sbc.order.payingmemberrecordtemp.model.root.PayingMemberRecordTemp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>付费记录临时表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@RestController
@Validated
public class PayingMemberRecordTempController implements PayingMemberRecordTempProvider {
	@Autowired
	private PayingMemberRecordTempService payingMemberRecordTempService;

	@Override
	public BaseResponse<PayingMemberRecordTempAddResponse> add(@RequestBody @Valid PayingMemberRecordTempAddRequest payingMemberRecordTempAddRequest) {
		PayingMemberRecordTemp payingMemberRecordTemp = KsBeanUtil.convert(payingMemberRecordTempAddRequest, PayingMemberRecordTemp.class);
		return BaseResponse.success(new PayingMemberRecordTempAddResponse(
				payingMemberRecordTempService.wrapperVo(payingMemberRecordTempService.add(payingMemberRecordTemp))));
	}

	@Override
	public BaseResponse<PayingMemberRecordTempModifyResponse> modify(@RequestBody @Valid PayingMemberRecordTempModifyRequest payingMemberRecordTempModifyRequest) {
		PayingMemberRecordTemp payingMemberRecordTemp = KsBeanUtil.convert(payingMemberRecordTempModifyRequest, PayingMemberRecordTemp.class);
		return BaseResponse.success(new PayingMemberRecordTempModifyResponse(
				payingMemberRecordTempService.wrapperVo(payingMemberRecordTempService.modify(payingMemberRecordTemp))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberRecordTempDelByIdRequest payingMemberRecordTempDelByIdRequest) {
		PayingMemberRecordTemp payingMemberRecordTemp = KsBeanUtil.convert(payingMemberRecordTempDelByIdRequest, PayingMemberRecordTemp.class);
		payingMemberRecordTemp.setDelFlag(DeleteFlag.YES);
		payingMemberRecordTempService.deleteById(payingMemberRecordTemp);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRecordTempDelByIdListRequest payingMemberRecordTempDelByIdListRequest) {
		payingMemberRecordTempService.deleteByIdList(payingMemberRecordTempDelByIdListRequest.getRecordIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

