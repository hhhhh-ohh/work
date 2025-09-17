package com.wanmi.sbc.order.provider.impl.payingmemberpayrecord;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.order.api.provider.payingmemberpayrecord.PayingMemberPayRecordProvider;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordAddRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordAddResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordModifyRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordModifyResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordDelByIdRequest;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordDelByIdListRequest;
import com.wanmi.sbc.order.payingmemberpayrecord.service.PayingMemberPayRecordService;
import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>付费会员支付记录表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@RestController
@Validated
public class PayingMemberPayRecordController implements PayingMemberPayRecordProvider {
	@Autowired
	private PayingMemberPayRecordService payingMemberPayRecordService;

	@Override
	public BaseResponse<PayingMemberPayRecordAddResponse> add(@RequestBody @Valid PayingMemberPayRecordAddRequest payingMemberPayRecordAddRequest) {
		PayingMemberPayRecord payingMemberPayRecord = KsBeanUtil.convert(payingMemberPayRecordAddRequest, PayingMemberPayRecord.class);
		return BaseResponse.success(new PayingMemberPayRecordAddResponse(
				payingMemberPayRecordService.wrapperVo(payingMemberPayRecordService.add(payingMemberPayRecord))));
	}

	@Override
	public BaseResponse<PayingMemberPayRecordModifyResponse> modify(@RequestBody @Valid PayingMemberPayRecordModifyRequest payingMemberPayRecordModifyRequest) {
		PayingMemberPayRecord payingMemberPayRecord = KsBeanUtil.convert(payingMemberPayRecordModifyRequest, PayingMemberPayRecord.class);
		return BaseResponse.success(new PayingMemberPayRecordModifyResponse(
				payingMemberPayRecordService.wrapperVo(payingMemberPayRecordService.modify(payingMemberPayRecord))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberPayRecordDelByIdRequest payingMemberPayRecordDelByIdRequest) {
		PayingMemberPayRecord payingMemberPayRecord = KsBeanUtil.convert(payingMemberPayRecordDelByIdRequest, PayingMemberPayRecord.class);
		payingMemberPayRecord.setDelFlag(DeleteFlag.YES);
		payingMemberPayRecordService.deleteById(payingMemberPayRecord);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberPayRecordDelByIdListRequest payingMemberPayRecordDelByIdListRequest) {
		payingMemberPayRecordService.deleteByIdList(payingMemberPayRecordDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

