package com.wanmi.sbc.setting.api.provider.wechatvideo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.wechatvideo.WechatVideoSettingModifyBusinessLicenceRequest;
import com.wanmi.sbc.setting.api.request.wechatvideo.WechatVideoSettingModifyFieldRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>视频直播带货应用设置保存服务Provider</p>
 * @author zhaiqiankun
 * @date 2022-04-11 20:18:02
 */
@FeignClient(value = "${application.setting.name}", contextId = "WechatVideoSettingProvider")
public interface WechatVideoSettingProvider {

	/**
	 * 修改指定字段视频直播带货应用设置API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoSettingModifyFieldRequest 修改 {@link WechatVideoSettingModifyFieldRequest}
	 * @return 修改结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/videogoodssetting/update-field")
	BaseResponse updateField(@RequestBody @Valid WechatVideoSettingModifyFieldRequest wechatVideoSettingModifyFieldRequest);

	/**
	 * 保存订单
	 * @param modifyBusinessLicenceRequest 保存营业执照
	 * @return
	 */
	@PostMapping("/setting/${application.setting.version}/videogoodssetting/save-business-licence")
    BaseResponse saveBusinessLicence(@RequestBody @Valid WechatVideoSettingModifyBusinessLicenceRequest modifyBusinessLicenceRequest);
}

