package com.wanmi.sbc.setting.api.provider.helpcenterarticlerecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordAddRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordAddResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordModifyRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordModifyResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordDelByIdRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章记录保存服务Provider</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@FeignClient(value = "${application.setting.name}", contextId = "HelpCenterArticleRecordProvider")
public interface HelpCenterArticleRecordProvider {

	/**
	 * 新增帮助中心文章记录API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleRecordAddRequest 帮助中心文章记录新增参数结构 {@link HelpCenterArticleRecordAddRequest}
	 * @return 新增的帮助中心文章记录信息 {@link HelpCenterArticleRecordAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlerecord/add")
	BaseResponse<HelpCenterArticleRecordAddResponse> add(@RequestBody @Valid HelpCenterArticleRecordAddRequest helpCenterArticleRecordAddRequest);

	/**
	 * 修改帮助中心文章记录API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleRecordModifyRequest 帮助中心文章记录修改参数结构 {@link HelpCenterArticleRecordModifyRequest}
	 * @return 修改的帮助中心文章记录信息 {@link HelpCenterArticleRecordModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlerecord/modify")
	BaseResponse<HelpCenterArticleRecordModifyResponse> modify(@RequestBody @Valid HelpCenterArticleRecordModifyRequest helpCenterArticleRecordModifyRequest);

	/**
	 * 单个删除帮助中心文章记录API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleRecordDelByIdRequest 单个删除参数结构 {@link HelpCenterArticleRecordDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlerecord/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid HelpCenterArticleRecordDelByIdRequest helpCenterArticleRecordDelByIdRequest);

	/**
	 * 批量删除帮助中心文章记录API
	 *
	 * @author 吕振伟
	 * @param helpCenterArticleRecordDelByIdListRequest 批量删除参数结构 {@link HelpCenterArticleRecordDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/helpcenterarticlerecord/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid HelpCenterArticleRecordDelByIdListRequest helpCenterArticleRecordDelByIdListRequest);

}

