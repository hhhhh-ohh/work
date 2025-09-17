package com.wanmi.sbc.vas.api.provider.recommend.recommendcatemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.RecommendCateManageByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.RecommendCateManageInfoListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.RecommendCateManageListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.RecommendCateManagePageRequest;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageInfoListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManagePageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分类推荐管理查询服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendCateManageQueryProvider")
public interface RecommendCateManageQueryProvider {

	/**
	 * 分页查询分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManagePageReq 分页请求参数和筛选对象 {@link RecommendCateManagePageRequest}
	 * @return 分类推荐管理分页列表信息 {@link RecommendCateManagePageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/page")
    BaseResponse<RecommendCateManagePageResponse> page(@RequestBody @Valid RecommendCateManagePageRequest recommendCateManagePageReq);

	/**
	 * 列表查询分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManageListReq 列表请求参数和筛选对象 {@link RecommendCateManageListRequest}
	 * @return 分类推荐管理的列表信息 {@link RecommendCateManageListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/list")
    BaseResponse<RecommendCateManageListResponse> list(@RequestBody @Valid RecommendCateManageListRequest recommendCateManageListReq);

	/**
	 * 列表查询分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManageListReq 列表请求参数和筛选对象 {@link RecommendCateManageInfoListRequest}
	 * @return 分类推荐管理的列表信息 {@link RecommendCateManageInfoListRequest}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/get-recommend-cate-info-list")
    BaseResponse<RecommendCateManageInfoListResponse> getRecommendCateInfoList(@RequestBody @Valid RecommendCateManageInfoListRequest recommendCateManageListReq);

	/**
	 * 单个查询分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManageByIdRequest 单个查询分类推荐管理请求参数 {@link RecommendCateManageByIdRequest}
	 * @return 分类推荐管理详情 {@link RecommendCateManageByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/get-by-id")
    BaseResponse<RecommendCateManageByIdResponse> getById(@RequestBody @Valid RecommendCateManageByIdRequest recommendCateManageByIdRequest);

}

