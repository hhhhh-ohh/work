package com.wanmi.sbc.setting.api.provider.helpcenterarticle;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.*;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticlePageResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleListResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleByIdResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章信息查询服务Provider</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@FeignClient(value = "${application.setting.name}", contextId = "HelpCenterArticleQueryProvider")
public interface HelpCenterArticleQueryProvider {

	/**
	 * 分页查询帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticlePageReq 分页请求参数和筛选对象 {@link HelpCenterArticlePageRequest}
	 * @return 帮助中心文章信息分页列表信息 {@link HelpCenterArticlePageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/page")
	BaseResponse<HelpCenterArticlePageResponse> page(@RequestBody @Valid HelpCenterArticlePageRequest helpCenterArticlePageReq);

	/**
	 * 列表查询帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleListReq 列表请求参数和筛选对象 {@link HelpCenterArticleListRequest}
	 * @return 帮助中心文章信息的列表信息 {@link HelpCenterArticleListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/list")
	BaseResponse<HelpCenterArticleListResponse> list(@RequestBody @Valid HelpCenterArticleListRequest helpCenterArticleListReq);

	/**
	 * 单个查询帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleByIdRequest 单个查询帮助中心文章信息请求参数 {@link HelpCenterArticleByIdRequest}
	 * @return 帮助中心文章信息详情 {@link HelpCenterArticleByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/get-by-id")
	BaseResponse<HelpCenterArticleByIdResponse> getById(@RequestBody @Valid HelpCenterArticleByIdRequest helpCenterArticleByIdRequest);

	@PostMapping("/setting/${application.setting.version}/helpcenterarticle/change-type-by-id")
	BaseResponse changeTypeById(@RequestBody @Valid HelpCenterArticleChangeTypeByIdRequest helpCenterArticleByIdRequest);

}

