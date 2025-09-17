package com.wanmi.sbc.setting.api.provider.helpcenterarticlecate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCatePageRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCatePageResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateListRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateListResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateByIdRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateByIdResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateExportRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章信息查询服务Provider</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@FeignClient(value = "${application.setting.name}", contextId = "HelpCenterArticleCateQueryProvider")
public interface HelpCenterArticleCateQueryProvider {

	/**
	 * 分页查询帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleCatePageReq 分页请求参数和筛选对象 {@link HelpCenterArticleCatePageRequest}
	 * @return 帮助中心文章信息分页列表信息 {@link HelpCenterArticleCatePageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/page")
	BaseResponse<HelpCenterArticleCatePageResponse> page(@RequestBody @Valid HelpCenterArticleCatePageRequest helpCenterArticleCatePageReq);

	/**
	 * 列表查询帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleCateListReq 列表请求参数和筛选对象 {@link HelpCenterArticleCateListRequest}
	 * @return 帮助中心文章信息的列表信息 {@link HelpCenterArticleCateListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/list")
	BaseResponse<HelpCenterArticleCateListResponse> list(@RequestBody @Valid HelpCenterArticleCateListRequest helpCenterArticleCateListReq);

	/**
	 * 列表查询帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @return 帮助中心文章信息的列表信息 {@link HelpCenterArticleCateListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/get-cate-list")
	BaseResponse<HelpCenterArticleCateListResponse> getCateList();

	/**
	 * 单个查询帮助中心文章信息API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleCateByIdRequest 单个查询帮助中心文章信息请求参数 {@link HelpCenterArticleCateByIdRequest}
	 * @return 帮助中心文章信息详情 {@link HelpCenterArticleCateByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlecate/get-by-id")
	BaseResponse<HelpCenterArticleCateByIdResponse> getById(@RequestBody @Valid HelpCenterArticleCateByIdRequest helpCenterArticleCateByIdRequest);

}

