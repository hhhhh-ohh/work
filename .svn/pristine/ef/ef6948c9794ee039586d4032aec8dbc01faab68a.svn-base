package com.wanmi.sbc.empower.api.provider.wechatloginset;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.wechatloginset.WechatLoginSetAddRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>微信授权登录配置保存服务Provider</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@FeignClient(value = "${application.empower.name}", contextId = "WechatLoginSetSaveProvider")
public interface WechatLoginSetSaveProvider {

	/**
	 * 新增微信授权登录配置API
	 *
	 * @author lq
	 * @param wechatLoginSetAddRequest 微信授权登录配置新增参数结构 {@link WechatLoginSetAddRequest}
	 * @return 新增的微信授权登录配置信息
	 */
	@PostMapping("/empower/${application.empower.version}/wechatloginset/add")
	BaseResponse add(@RequestBody @Valid WechatLoginSetAddRequest wechatLoginSetAddRequest);

}

