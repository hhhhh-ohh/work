package com.wanmi.sbc.empower.api.provider.sms;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingAddRequest;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingDelByIdListRequest;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingDelByIdRequest;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.sms.SmsSettingAddResponse;
import com.wanmi.sbc.empower.api.response.sms.SmsSettingModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>短信配置保存服务Provider</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:15:28
 */
@FeignClient(value = "${application.empower.name}",contextId = "SmsSettingSaveProvider")
public interface SmsSettingSaveProvider {

	/**
	 * 新增短信配置API
	 *
	 * @author lvzhenwei
	 * @param smsSettingAddRequest 短信配置新增参数结构 {@link SmsSettingAddRequest}
	 * @return 新增的短信配置信息 {@link SmsSettingAddResponse}
	 */
	@PostMapping("/sms/${application.empower.version}/smssetting/add")
	BaseResponse<SmsSettingAddResponse> add(@RequestBody @Valid SmsSettingAddRequest smsSettingAddRequest);

	/**
	 * 修改短信配置API
	 *
	 * @author lvzhenwei
	 * @param smsSettingModifyRequest 短信配置修改参数结构 {@link SmsSettingModifyRequest}
	 * @return 修改的短信配置信息 {@link SmsSettingModifyResponse}
	 */
	@PostMapping("/sms/${application.empower.version}/smssetting/modify")
	BaseResponse<SmsSettingModifyResponse> modify(@RequestBody @Valid SmsSettingModifyRequest smsSettingModifyRequest);

	/**
	 * 单个删除短信配置API
	 *
	 * @author lvzhenwei
	 * @param smsSettingDelByIdRequest 单个删除参数结构 {@link SmsSettingDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.empower.version}/smssetting/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid SmsSettingDelByIdRequest smsSettingDelByIdRequest);

	/**
	 * 批量删除短信配置API
	 *
	 * @author lvzhenwei
	 * @param smsSettingDelByIdListRequest 批量删除参数结构 {@link SmsSettingDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.empower.version}/smssetting/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid SmsSettingDelByIdListRequest smsSettingDelByIdListRequest);

}

