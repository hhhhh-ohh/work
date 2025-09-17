package com.wanmi.sbc.setting.api.provider.helpcenterarticlerecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordPageRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordPageResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordListRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordListResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordByIdRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章记录查询服务Provider</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@FeignClient(value = "${application.setting.name}", contextId = "HelpCenterArticleRecordQueryProvider")
public interface HelpCenterArticleRecordQueryProvider {

	/**
	 * 分页查询帮助中心文章记录API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleRecordPageReq 分页请求参数和筛选对象 {@link HelpCenterArticleRecordPageRequest}
	 * @return 帮助中心文章记录分页列表信息 {@link HelpCenterArticleRecordPageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlerecord/page")
	BaseResponse<HelpCenterArticleRecordPageResponse> page(@RequestBody @Valid HelpCenterArticleRecordPageRequest helpCenterArticleRecordPageReq);

	/**
	 * 列表查询帮助中心文章记录API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleRecordListReq 列表请求参数和筛选对象 {@link HelpCenterArticleRecordListRequest}
	 * @return 帮助中心文章记录的列表信息 {@link HelpCenterArticleRecordListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlerecord/list")
	BaseResponse<HelpCenterArticleRecordListResponse> list(@RequestBody @Valid HelpCenterArticleRecordListRequest helpCenterArticleRecordListReq);

	/**
	 * 单个查询帮助中心文章记录API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleRecordByIdRequest 单个查询帮助中心文章记录请求参数 {@link HelpCenterArticleRecordByIdRequest}
	 * @return 帮助中心文章记录详情 {@link HelpCenterArticleRecordByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlerecord/get-by-id")
	BaseResponse<HelpCenterArticleRecordByIdResponse> getById(@RequestBody @Valid HelpCenterArticleRecordByIdRequest helpCenterArticleRecordByIdRequest);

}

