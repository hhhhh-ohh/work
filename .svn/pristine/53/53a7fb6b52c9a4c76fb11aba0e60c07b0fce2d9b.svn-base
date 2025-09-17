package com.wanmi.sbc.setting.api.provider.recommendcate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCatePageRequest;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCatePageResponse;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateListRequest;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateListResponse;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateByIdRequest;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateByIdResponse;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateExportRequest;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>笔记分类表查询服务Provider</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@FeignClient(value = "${application.setting.name}", contextId = "RecommendCateQueryProvider")
public interface RecommendCateQueryProvider {

	/**
	 * 分页查询笔记分类表API
	 *
	 * @author 王超
	 * @param recommendCatePageReq 分页请求参数和筛选对象 {@link RecommendCatePageRequest}
	 * @return 笔记分类表分页列表信息 {@link RecommendCatePageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommendcate/page")
	BaseResponse<RecommendCatePageResponse> page(@RequestBody @Valid RecommendCatePageRequest recommendCatePageReq);

	/**
	 * 列表查询笔记分类表API
	 *
	 * @author 王超
	 * @param recommendCateListReq 列表请求参数和筛选对象 {@link RecommendCateListRequest}
	 * @return 笔记分类表的列表信息 {@link RecommendCateListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommendcate/list")
	BaseResponse<RecommendCateListResponse> list(@RequestBody @Valid RecommendCateListRequest recommendCateListReq);

	/**
	 * 单个查询笔记分类表API
	 *
	 * @author 王超
	 * @param recommendCateByIdRequest 单个查询笔记分类表请求参数 {@link RecommendCateByIdRequest}
	 * @return 笔记分类表详情 {@link RecommendCateByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommendcate/get-by-id")
	BaseResponse<RecommendCateByIdResponse> getById(@RequestBody @Valid RecommendCateByIdRequest recommendCateByIdRequest);
}

