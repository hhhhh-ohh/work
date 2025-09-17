package com.wanmi.sbc.message.api.provider.minimsgactivitysetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingAddRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingDelByIdListRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingDelByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingModifyByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingModifyByIdsRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingModifyRequest;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingAddResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingModifyResponse;
import com.wanmi.sbc.message.bean.vo.MiniMsgActivitySettingVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息配置表保存服务Provider</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@FeignClient(value = "${application.message.name}", contextId = "MiniMsgActivitySettingProvider")
public interface MiniMsgActivitySettingProvider {

	/**
	 * 新增小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgActivitySettingAddRequest 小程序订阅消息配置表新增参数结构 {@link MiniMsgActivitySettingAddRequest}
	 * @return 新增的小程序订阅消息配置表信息 {@link MiniMsgActivitySettingAddResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/add")
	BaseResponse<MiniMsgActivitySettingAddResponse> add(@RequestBody @Valid MiniMsgActivitySettingAddRequest miniMsgActivitySettingAddRequest);

	/**
	 * 新增小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgActivitySettingVO 小程序订阅消息配置表新增参数结构 {@link MiniMsgActivitySettingVO}
	 * @return 新增的小程序订阅消息配置表信息 {@link }
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/deal-customer-record")
	BaseResponse dealCustomerRecord(@RequestBody MiniMsgActivitySettingVO miniMsgActivitySettingVO);

	/**
	 * 修改小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgActivitySettingModifyRequest 小程序订阅消息配置表修改参数结构 {@link MiniMsgActivitySettingModifyRequest}
	 * @return 修改的小程序订阅消息配置表信息 {@link MiniMsgActivitySettingModifyResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/modify")
	BaseResponse<MiniMsgActivitySettingModifyResponse> modify(@RequestBody @Valid MiniMsgActivitySettingModifyRequest miniMsgActivitySettingModifyRequest);

	/**
	 * 单个删除小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgActivitySettingDelByIdRequest 单个删除参数结构 {@link MiniMsgActivitySettingDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid MiniMsgActivitySettingDelByIdRequest miniMsgActivitySettingDelByIdRequest);

	/**
	 * 批量删除小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgActivitySettingDelByIdListRequest 批量删除参数结构 {@link MiniMsgActivitySettingDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid MiniMsgActivitySettingDelByIdListRequest miniMsgActivitySettingDelByIdListRequest);

	/**
	 * 修改小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param minimsgactivitysettingModifyRequest 小程序订阅消息配置表修改参数结构 {@link MiniMsgActivitySettingModifyRequest}
	 * @return 修改的小程序订阅消息配置表信息 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/modify-by-id")
	BaseResponse modifyById(@RequestBody @Valid MiniMsgActivitySettingModifyByIdRequest minimsgactivitysettingModifyRequest);

	/**
	 * 修改小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param minimsgactivitysettingModifyRequest 小程序订阅消息配置表修改参数结构 {@link MiniMsgActivitySettingModifyRequest}
	 * @return 修改的小程序订阅消息配置表信息 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/modify-scanflag-by-ids")
	BaseResponse modifyScanFlagByIds(@RequestBody @Valid MiniMsgActivitySettingModifyByIdsRequest minimsgactivitysettingModifyRequest);
}

