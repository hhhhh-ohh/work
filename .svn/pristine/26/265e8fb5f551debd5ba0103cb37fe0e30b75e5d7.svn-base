package com.wanmi.sbc.setting.api.provider.wechatvideo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.wechatvideo.WechatVideoSettingByTypeRequest;
import com.wanmi.sbc.setting.api.request.wechatvideo.WechatVideoSettingListRequest;
import com.wanmi.sbc.setting.api.request.wechatvideo.WechatVideoSettingModifyStatusRequest;
import com.wanmi.sbc.setting.api.response.wechatvideo.WechatVideoSettingByTypeResponse;
import com.wanmi.sbc.setting.api.response.wechatvideo.WechatVideoSettingEnableResponse;
import com.wanmi.sbc.setting.api.response.wechatvideo.WechatVideoSettingListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>视频直播带货应用设置查询服务Provider</p>
 * @author zhaiqiankun
 * @date 2022-04-11 20:18:02
 */
@FeignClient(value = "${application.setting.name}", contextId = "WechatVideoSettingQueryProvider")
public interface WechatVideoSettingQueryProvider {

	/**
	 * 列表查询视频直播带货应用设置API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoSettingListRequest 列表请求参数和筛选对象 {@link WechatVideoSettingListRequest}
	 * @return 视频直播带货应用设置的列表信息 {@link WechatVideoSettingListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/videogoodssetting/list")
	BaseResponse<WechatVideoSettingListResponse> list(@RequestBody @Valid WechatVideoSettingListRequest wechatVideoSettingListRequest);

	/**
	 * 单个查询视频直播带货应用设置API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoSettingByTypeRequest 单个查询视频直播带货应用设置请求参数 {@link WechatVideoSettingByTypeRequest}
	 * @return 视频直播带货应用设置详情 {@link WechatVideoSettingByTypeResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/videogoodssetting/get-by-type")
	BaseResponse<WechatVideoSettingByTypeResponse> getByType(@RequestBody @Valid WechatVideoSettingByTypeRequest wechatVideoSettingByTypeRequest);

	/**
	 * @description 查询视频直播带货是否已启用
	 * @author malianfeng
	 * @date 2022/4/18 15:33
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.api.response.wechatvideosetting.WechatVideoSettingEnableResponse>
	 */
	@PostMapping("/setting/${application.setting.version}/videogoodssetting/is-enable")
	BaseResponse<WechatVideoSettingEnableResponse> isEnable();

	/**
	 * @description 校验当前流程
	 * @author malianfeng
	 * @date 2022/4/25 12:26
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.api.response.wechatvideosetting.WechatVideoSettingByIdResponse>
	 */
	@PostMapping("/setting/${application.setting.version}/videogoodssetting/check-setting")
	BaseResponse<WechatVideoSettingByTypeResponse> checkSetting(@RequestBody @Valid WechatVideoSettingModifyStatusRequest modifyStatusRequest);

}

