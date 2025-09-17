package com.wanmi.sbc.empower.api.provider.wechatloginset;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.wechatloginset.WechatLoginSetByIdRequest;
import com.wanmi.sbc.empower.api.request.wechatloginset.WechatLoginSetByStoreIdRequest;
import com.wanmi.sbc.empower.api.request.wechatloginset.WechatServiceStatusByStoreIdRequest;
import com.wanmi.sbc.empower.api.response.wechatloginset.WechatLoginSetByIdResponse;
import com.wanmi.sbc.empower.api.response.wechatloginset.WechatLoginSetResponse;
import com.wanmi.sbc.empower.api.response.wechatloginset.WechatLoginSetServerStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>微信授权登录配置查询服务Provider</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@FeignClient(value = "${application.empower.name}", contextId = "WechatLoginSetQueryProvider")
public interface WechatLoginSetQueryProvider {

	/**
	 * 单个查询微信授权登录配置API
	 *
	 * @author lq
	 * @param wechatLoginSetByIdRequest 单个查询微信授权登录配置请求参数 {@link WechatLoginSetByIdRequest}
	 * @return 微信授权登录配置详情 {@link WechatLoginSetByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/wechatloginset/get-by-id")
	BaseResponse<WechatLoginSetByIdResponse> getById(@RequestBody @Valid WechatLoginSetByIdRequest
                                                             wechatLoginSetByIdRequest);

	/**
	 * 查询微信授权登录配置API
	 *
	 * @author lq
	 * @return 微信授权登录配置详情 {@link WechatLoginSetResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/wechatloginset/get-info")
	BaseResponse<WechatLoginSetResponse> getInfo();

	/**
	 * 门店id查询平台微信登录配置
	 *
	 * @author lq
	 * @return 微信授权登录配置详情 {@link WechatLoginSetResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/wechatloginset/getInfoByStoreId")
	BaseResponse<WechatLoginSetResponse> getInfoByStoreId(@RequestBody @Valid WechatLoginSetByStoreIdRequest wechatLoginSetByStoreIdRequest);

	/**
	 * 根据终端类型，获取授信开关状态
	 *
	 * @author lq
	 * @return 微信授权登录配置详情 {@link WechatLoginSetServerStatusResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/wechatloginset/get-server-status")
	BaseResponse<WechatLoginSetServerStatusResponse> getWechatServerStatus();

	/**
	 * 根据门店id查询终端类型，获取授信开关状态
	 *
	 * @author lq
	 * @return 微信授权登录配置详情 {@link WechatLoginSetServerStatusResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/wechatloginset/getWechatServerStatusByStoreId")
	BaseResponse<WechatLoginSetServerStatusResponse> getWechatServerStatusByStoreId(@RequestBody @Valid WechatServiceStatusByStoreIdRequest wechatServiceStatusByStoreIdRequest);

}

