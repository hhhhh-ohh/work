package com.wanmi.sbc.vas.api.provider.recommend.caterelatedrecommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.caterelatedrecommend.CateRelatedRecommendAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.caterelatedrecommend.CateRelatedRecommendModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分类相关性推荐保存服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@FeignClient(value = "${application.vas.name}", contextId = "CateRelatedRecommendProvider")
public interface CateRelatedRecommendProvider {

	/**
	 * 新增分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param cateRelatedRecommendAddRequest 分类相关性推荐新增参数结构 {@link CateRelatedRecommendAddRequest}
	 * @return 新增的分类相关性推荐信息 {@link CateRelatedRecommendAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/add")
    BaseResponse<CateRelatedRecommendAddResponse> add(@RequestBody @Valid CateRelatedRecommendAddRequest cateRelatedRecommendAddRequest);

	/**
	 * 批量新增分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param cateRelatedRecommendAddListRequest 分类相关性推荐批量新增参数结构 {@link CateRelatedRecommendAddListRequest}
	 * @return 批量新增的分类相关性推荐信息 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/add-list")
    BaseResponse addList(@RequestBody @Valid CateRelatedRecommendAddListRequest cateRelatedRecommendAddListRequest);

	/**
	 * 修改分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param cateRelatedRecommendModifyRequest 分类相关性推荐修改参数结构 {@link CateRelatedRecommendModifyRequest}
	 * @return 修改的分类相关性推荐信息 {@link CateRelatedRecommendModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/modify")
    BaseResponse<CateRelatedRecommendModifyResponse> modify(@RequestBody @Valid CateRelatedRecommendModifyRequest cateRelatedRecommendModifyRequest);


	/**
	 * 更新分类相关性推荐权重API
	 *
	 * @author lvzhenwei
	 * @param request 分类相关性推荐修改权重参数结构 {@link CateRelatedRecommendUpdateWeightRequest}
	 * @return 修改的分类相关性推荐权重信息 {@link CateRelatedRecommendModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/update-weight")
    BaseResponse updateWeight(@RequestBody @Valid CateRelatedRecommendUpdateWeightRequest request);

	/**
	 * 单个删除分类相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param cateRelatedRecommendDelByIdRequest 单个删除参数结构 {@link CateRelatedRecommendDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/caterelatedrecommend/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid CateRelatedRecommendDelByIdRequest cateRelatedRecommendDelByIdRequest);

}

