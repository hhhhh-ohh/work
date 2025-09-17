package com.wanmi.sbc.order.provider.impl.payingmemberrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecord.*;
import com.wanmi.sbc.order.api.response.payingmemberrecord.PayingMemberRecordAddResponse;
import com.wanmi.sbc.order.api.response.payingmemberrecord.PayingMemberRecordModifyResponse;
import com.wanmi.sbc.order.payingmemberrecord.model.root.PayingMemberRecord;
import com.wanmi.sbc.order.payingmemberrecord.service.PayingMemberRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>付费记录表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@RestController
@Validated
public class PayingMemberRecordController implements PayingMemberRecordProvider {
	@Autowired
	private PayingMemberRecordService payingMemberRecordService;

	@Override
	public BaseResponse<PayingMemberRecordAddResponse> add(@RequestBody @Valid PayingMemberRecordAddRequest payingMemberRecordAddRequest) {
		PayingMemberRecord payingMemberRecord = KsBeanUtil.convert(payingMemberRecordAddRequest, PayingMemberRecord.class);
		return BaseResponse.success(new PayingMemberRecordAddResponse(
				payingMemberRecordService.wrapperVo(payingMemberRecordService.add(payingMemberRecord))));
	}

	@Override
	public BaseResponse<PayingMemberRecordModifyResponse> modify(@RequestBody @Valid PayingMemberRecordModifyRequest payingMemberRecordModifyRequest) {
		PayingMemberRecord payingMemberRecord = KsBeanUtil.convert(payingMemberRecordModifyRequest, PayingMemberRecord.class);
		return BaseResponse.success(new PayingMemberRecordModifyResponse(
				payingMemberRecordService.wrapperVo(payingMemberRecordService.modify(payingMemberRecord))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberRecordDelByIdRequest payingMemberRecordDelByIdRequest) {
		PayingMemberRecord payingMemberRecord = KsBeanUtil.convert(payingMemberRecordDelByIdRequest, PayingMemberRecord.class);
		payingMemberRecord.setDelFlag(DeleteFlag.YES);
		payingMemberRecordService.deleteById(payingMemberRecord);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRecordDelByIdListRequest payingMemberRecordDelByIdListRequest) {
		payingMemberRecordService.deleteByIdList(payingMemberRecordDelByIdListRequest.getRecordIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse rightsCoupon() {
		payingMemberRecordService.rightsCoupon();
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse refundPayingMember(@RequestBody PayingMemberRecordModifyRequest payingMemberRecordModifyRequest) {
		payingMemberRecordService.refundPayingMember(payingMemberRecordModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse autoUpdateState(@RequestBody PayingMemberRecordListRequest payingMemberRecordModifyRequest) {
		List<String> recordIdList = payingMemberRecordModifyRequest.getRecordIdList();
		payingMemberRecordService.autoUpdateState(recordIdList);
		return BaseResponse.SUCCESSFUL();
	}

}

