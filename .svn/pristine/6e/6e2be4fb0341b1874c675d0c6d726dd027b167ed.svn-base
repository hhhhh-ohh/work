package com.wanmi.sbc.marketing.api.provider.communitysetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingModifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区拼团商家设置表保存服务Provider</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunitySettingProvider")
public interface CommunitySettingProvider {

	/**
	 * 修改社区拼团商家设置表API
	 *
	 * @author dyt
	 * @param communitySettingModifyRequest 社区拼团商家设置表修改参数结构 {@link CommunitySettingModifyRequest}
	 * @return 操作信息 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitysetting/modify")
	BaseResponse modify(@RequestBody @Valid CommunitySettingModifyRequest communitySettingModifyRequest);
}

