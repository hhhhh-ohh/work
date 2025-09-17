package com.wanmi.sbc.setting.api.provider.helpcenterarticlecate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.*;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateAddResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章信息保存服务Provider</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@FeignClient(value = "${application.setting.name}", contextId = "HelpCenterArticleCateProvider")
public interface HelpCenterArticleCateProvider {

	/**
	 * 新增帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleCateAddRequest 帮助中心文章信息新增参数结构 {@link HelpCenterArticleCateAddRequest}
	 * @return 新增的帮助中心文章信息信息 {@link HelpCenterArticleCateAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/add")
	BaseResponse<HelpCenterArticleCateAddResponse> add(@RequestBody @Valid HelpCenterArticleCateAddRequest helpCenterArticleCateAddRequest);

	/**
	 * 新增帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleCateAddRequest 帮助中心文章信息新增参数结构 {@link HelpCenterArticleCateAddRequest}
	 * @return 新增的帮助中心文章信息信息 {@link HelpCenterArticleCateAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/sort")
	BaseResponse sort(@RequestBody @Valid HelpCenterArticleCateSortModifyRequest helpCenterArticleCateAddRequest);


	/**
	 * 修改帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleCateModifyRequest 帮助中心文章信息修改参数结构 {@link HelpCenterArticleCateModifyRequest}
	 * @return 修改的帮助中心文章信息信息 {@link HelpCenterArticleCateModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/modify")
	BaseResponse<HelpCenterArticleCateModifyResponse> modify(@RequestBody @Valid HelpCenterArticleCateModifyRequest helpCenterArticleCateModifyRequest);

	/**
	 * 单个删除帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleCateDelByIdRequest 单个删除参数结构 {@link HelpCenterArticleCateDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid HelpCenterArticleCateDelByIdRequest helpCenterArticleCateDelByIdRequest);

	/**
	 * 批量删除帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleCateDelByIdListRequest 批量删除参数结构 {@link HelpCenterArticleCateDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid HelpCenterArticleCateDelByIdListRequest helpCenterArticleCateDelByIdListRequest);

}

