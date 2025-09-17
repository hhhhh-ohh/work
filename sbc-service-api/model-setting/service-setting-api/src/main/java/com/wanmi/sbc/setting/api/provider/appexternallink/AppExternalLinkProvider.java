package com.wanmi.sbc.setting.api.provider.appexternallink;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkAddRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkAddResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkModifyRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkModifyResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkDelByIdRequest;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>AppExternalLink保存服务Provider</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@FeignClient(value = "${application.setting.name}", contextId = "AppExternalLinkProvider")
public interface AppExternalLinkProvider {

	/**
	 * 新增AppExternalLinkAPI
	 *
	 * @author 黄昭
	 * @param appExternalLinkAddRequest AppExternalLink新增参数结构 {@link AppExternalLinkAddRequest}
	 * @return 新增的AppExternalLink信息 {@link AppExternalLinkAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternallink/add")
	BaseResponse<AppExternalLinkAddResponse> add(@RequestBody @Valid AppExternalLinkAddRequest appExternalLinkAddRequest);

	/**
	 * 修改AppExternalLinkAPI
	 *
	 * @author 黄昭
	 * @param appExternalLinkModifyRequest AppExternalLink修改参数结构 {@link AppExternalLinkModifyRequest}
	 * @return 修改的AppExternalLink信息 {@link AppExternalLinkModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternallink/modify")
	BaseResponse<AppExternalLinkModifyResponse> modify(@RequestBody @Valid AppExternalLinkModifyRequest appExternalLinkModifyRequest);

	/**
	 * 单个删除AppExternalLinkAPI
	 *
	 * @author 黄昭
	 * @param appExternalLinkDelByIdRequest 单个删除参数结构 {@link AppExternalLinkDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternallink/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid AppExternalLinkDelByIdRequest appExternalLinkDelByIdRequest);
}

