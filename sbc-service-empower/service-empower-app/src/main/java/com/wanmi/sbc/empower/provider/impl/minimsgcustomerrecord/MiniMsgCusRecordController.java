package com.wanmi.sbc.empower.provider.impl.minimsgcustomerrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordProvider;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordAddRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordDelByIdListRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordDelByIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordModifyByActivityIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordModifyRequest;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordAddResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordModifyResponse;
import com.wanmi.sbc.empower.minimsgcustomerrecord.model.root.MiniMsgCustomerRecord;
import com.wanmi.sbc.empower.minimsgcustomerrecord.service.MiniMsgCustomerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>客户订阅消息信息表保存服务接口实现</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@RestController
@Validated
public class MiniMsgCusRecordController implements MiniMsgCustomerRecordProvider {
	@Autowired
	private MiniMsgCustomerRecordService miniMsgCustomerRecordService;

	@Override
	public BaseResponse<MiniMsgCustomerRecordAddResponse> add(@RequestBody @Valid MiniMsgCustomerRecordAddRequest miniMsgCustomerRecordAddRequest) {
		MiniMsgCustomerRecord miniMsgCustomerRecord = KsBeanUtil.convert(miniMsgCustomerRecordAddRequest, MiniMsgCustomerRecord.class);
		return BaseResponse.success(new MiniMsgCustomerRecordAddResponse(
				miniMsgCustomerRecordService.wrapperVo(miniMsgCustomerRecordService.add(miniMsgCustomerRecord))));
	}

	@Override
	public BaseResponse<MiniMsgCustomerRecordModifyResponse> modify(@RequestBody @Valid MiniMsgCusRecordModifyRequest miniMsgCusRecordModifyRequest) {
		MiniMsgCustomerRecord miniMsgCustomerRecord = KsBeanUtil.convert(miniMsgCusRecordModifyRequest, MiniMsgCustomerRecord.class);
		return BaseResponse.success(new MiniMsgCustomerRecordModifyResponse(
				miniMsgCustomerRecordService.wrapperVo(miniMsgCustomerRecordService.modify(miniMsgCustomerRecord))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid MiniMsgCusRecordDelByIdRequest miniMsgCusRecordDelByIdRequest) {
		miniMsgCustomerRecordService.deleteById(miniMsgCusRecordDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid MiniMsgCusRecordDelByIdListRequest miniMsgCusRecordDelByIdListRequest) {
		miniMsgCustomerRecordService.deleteByIdList(miniMsgCusRecordDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateActivityIdByIdList(MiniMsgCusRecordModifyByActivityIdRequest modifyByActivityIdRequest) {
		miniMsgCustomerRecordService.updateActivityIdByIdList(modifyByActivityIdRequest.getActivityId(),  modifyByActivityIdRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateByActivityId(MiniMsgCusRecordModifyByActivityIdRequest modifyByActivityIdRequest) {
		miniMsgCustomerRecordService.updateByActivityId(modifyByActivityIdRequest.getActivityId());
		return BaseResponse.SUCCESSFUL();
	}

}

