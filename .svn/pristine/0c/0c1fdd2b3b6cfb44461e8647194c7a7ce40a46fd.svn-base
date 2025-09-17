package com.wanmi.sbc.empower.provider.impl.sms;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.sms.SmsSettingSaveProvider;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingAddRequest;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingDelByIdListRequest;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingDelByIdRequest;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.sms.SmsSettingAddResponse;
import com.wanmi.sbc.empower.api.response.sms.SmsSettingModifyResponse;
import com.wanmi.sbc.empower.sms.model.root.SmsSetting;
import com.wanmi.sbc.empower.sms.service.SmsSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>短信配置保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:15:28
 */
@RestController
@Validated
public class SmsSettingSaveController implements SmsSettingSaveProvider {
	@Autowired
	private SmsSettingService smsSettingService;

	@Override
	public BaseResponse<SmsSettingAddResponse> add(@RequestBody @Valid SmsSettingAddRequest smsSettingAddRequest) {
		SmsSetting smsSetting = new SmsSetting();
		KsBeanUtil.copyPropertiesThird(smsSettingAddRequest, smsSetting);
		return BaseResponse.success(new SmsSettingAddResponse(
				smsSettingService.wrapperVo(smsSettingService.add(smsSetting))));
	}

	@Override
	public BaseResponse<SmsSettingModifyResponse> modify(@RequestBody @Valid SmsSettingModifyRequest smsSettingModifyRequest) {
		SmsSetting smsSetting = new SmsSetting();
		KsBeanUtil.copyPropertiesThird(smsSettingModifyRequest, smsSetting);
		return BaseResponse.success(new SmsSettingModifyResponse(
				smsSettingService.wrapperVo(smsSettingService.modify(smsSetting))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid SmsSettingDelByIdRequest smsSettingDelByIdRequest) {
		smsSettingService.deleteById(smsSettingDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid SmsSettingDelByIdListRequest smsSettingDelByIdListRequest) {
		smsSettingService.deleteByIdList(smsSettingDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

