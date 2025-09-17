package com.wanmi.sbc.setting.api.provider.helpcenterarticle;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.*;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleAddResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleByIdResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章信息保存服务Provider</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@FeignClient(value = "${application.setting.name}", contextId = "HelpCenterArticleProvider")
public interface HelpCenterArticleProvider {

	/**
	 * 新增帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleAddRequest 帮助中心文章信息新增参数结构 {@link HelpCenterArticleAddRequest}
	 * @return 新增的帮助中心文章信息信息 {@link HelpCenterArticleAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/add")
	BaseResponse<HelpCenterArticleAddResponse> add(@RequestBody @Valid HelpCenterArticleAddRequest helpCenterArticleAddRequest);

	/**
	 * 修改帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleModifyRequest 帮助中心文章信息修改参数结构 {@link HelpCenterArticleModifyRequest}
	 * @return 修改的帮助中心文章信息信息 {@link HelpCenterArticleModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/modify")
	BaseResponse<HelpCenterArticleModifyResponse> modify(@RequestBody @Valid HelpCenterArticleModifyRequest helpCenterArticleModifyRequest);

	/**
	 * 单个删除帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleDelByIdRequest 单个删除参数结构 {@link HelpCenterArticleDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid HelpCenterArticleDelByIdRequest helpCenterArticleDelByIdRequest);

	/**
	 * 批量删除帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleDelByIdListRequest 批量删除参数结构 {@link HelpCenterArticleDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid HelpCenterArticleDelByIdListRequest helpCenterArticleDelByIdListRequest);

	/**
	 * 增加文章浏览量
	 *
	 * @author 吕振伟
	 * @param request 单个删除参数结构 {@link HelpCenterArticleDelByIdRequest}
	 * @return
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/add-view-num")
	BaseResponse addViewNum(@RequestBody @Valid HelpCenterArticleByIdRequest request);

	/**
	 * 帮助中心文章点击解决
	 *
	 * @author 吕振伟
	 * @param request 帮助中心文章点击解决请求参数 {@link HelpCenterArticleChangeSolveTypeRequest}
	 * @return 帮助中心文章点击解决 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/click-solve")
	BaseResponse clickSolve(@RequestBody @Valid HelpCenterArticleChangeSolveTypeRequest request);

	/**
	 * 帮助中心文章点击未解决
	 *
	 * @author 吕振伟
	 * @param request 帮助中心文章点击未解决请求参数 {@link HelpCenterArticleChangeSolveTypeRequest}
	 * @return 帮助中心文章点击未解决 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/click-unresolved")
	BaseResponse clickUnresolved(@RequestBody @Valid HelpCenterArticleChangeSolveTypeRequest request);



}

