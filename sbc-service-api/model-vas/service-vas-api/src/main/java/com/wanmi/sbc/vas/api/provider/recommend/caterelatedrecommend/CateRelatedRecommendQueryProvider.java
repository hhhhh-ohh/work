package com.wanmi.sbc.vas.api.provider.recommend.caterelatedrecommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.caterelatedrecommend.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分类相关性推荐查询服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@FeignClient(value = "${application.vas.name}", contextId = "CateRelatedRecommendQueryProvider")
public interface CateRelatedRecommendQueryProvider {

	/**
	 * 分页查询分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param cateRelatedRecommendPageReq 分页请求参数和筛选对象 {@link CateRelatedRecommendPageRequest}
	 * @return 分类相关性推荐分页列表信息 {@link CateRelatedRecommendPageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/page")
    BaseResponse<CateRelatedRecommendPageResponse> page(@RequestBody @Valid CateRelatedRecommendPageRequest cateRelatedRecommendPageReq);

	/**
	 * 列表查询分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param cateRelatedRecommendListReq 列表请求参数和筛选对象 {@link CateRelatedRecommendListRequest}
	 * @return 分类相关性推荐的列表信息 {@link CateRelatedRecommendListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/list")
    BaseResponse<CateRelatedRecommendListResponse> list(@RequestBody @Valid CateRelatedRecommendListRequest cateRelatedRecommendListReq);

	/**
	 * 列表查询分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param cateRelatedRecommendInfoListReq 列表请求参数和筛选对象 {@link CateRelatedRecommendInfoListRequest}
	 * @return 分类相关性推荐的列表信息 {@link CateRelatedRecommendListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/get-cate-relate-recommend-info-list")
    BaseResponse<CateRelatedRecommendInfoListResponse> getCateRelateRecommendInfoList(@RequestBody @Valid CateRelatedRecommendInfoListRequest cateRelatedRecommendInfoListReq);

	/**
	 * 列表查询分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param relatedRecommendDetailListRequest 列表请求参数和筛选对象 {@link CateRelatedRecommendDetailListRequest}
	 * @return 分类相关性推荐的列表信息 {@link CateRelatedRecommendListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/get-cate-relate-recommend-detail-list")
    BaseResponse<CateRelatedRecommendDetailListResponse> getCateRelateRecommendDetailList(@RequestBody @Valid CateRelatedRecommendDetailListRequest relatedRecommendDetailListRequest);

	/**
	 * 单个查询分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param cateRelatedRecommendByIdRequest 单个查询分类相关性推荐请求参数 {@link CateRelatedRecommendByIdRequest}
	 * @return 分类相关性推荐详情 {@link CateRelatedRecommendByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/get-by-id")
    BaseResponse<CateRelatedRecommendByIdResponse> getById(@RequestBody @Valid CateRelatedRecommendByIdRequest cateRelatedRecommendByIdRequest);

}

