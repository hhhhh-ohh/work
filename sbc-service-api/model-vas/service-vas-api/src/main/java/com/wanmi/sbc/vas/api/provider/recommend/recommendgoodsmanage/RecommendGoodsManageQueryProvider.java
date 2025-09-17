package com.wanmi.sbc.vas.api.provider.recommend.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.RecommendGoodsManageByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.RecommendGoodsManageListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.RecommendGoodsManagePageRequest;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageInfoListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManagePageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商品推荐管理查询服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendGoodsManageQueryProvider")
public interface RecommendGoodsManageQueryProvider {

	/**
	 * 分页查询商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManagePageReq 分页请求参数和筛选对象 {@link RecommendGoodsManagePageRequest}
	 * @return 商品推荐管理分页列表信息 {@link RecommendGoodsManagePageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/page")
    BaseResponse<RecommendGoodsManagePageResponse> page(@RequestBody @Valid RecommendGoodsManagePageRequest recommendGoodsManagePageReq);

	/**
	 * 列表查询商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManageListReq 列表请求参数和筛选对象 {@link RecommendGoodsManageListRequest}
	 * @return 商品推荐管理的列表信息 {@link RecommendGoodsManageListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/list")
    BaseResponse<RecommendGoodsManageListResponse> list(@RequestBody @Valid RecommendGoodsManageListRequest recommendGoodsManageListReq);

	/**
	 * 列表查询商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManageListReq 列表请求参数和筛选对象 {@link RecommendGoodsManageListRequest}
	 * @return 商品推荐管理的列表信息 {@link RecommendGoodsManageListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/get-recommend-goods-info-list")
    BaseResponse<RecommendGoodsManageInfoListResponse> getRecommendGoodsInfoList(@RequestBody @Valid RecommendGoodsManageListRequest recommendGoodsManageListReq);

	/**
	 * 单个查询商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManageByIdRequest 单个查询商品推荐管理请求参数 {@link RecommendGoodsManageByIdRequest}
	 * @return 商品推荐管理详情 {@link RecommendGoodsManageByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/get-by-id")
    BaseResponse<RecommendGoodsManageByIdResponse> getById(@RequestBody @Valid RecommendGoodsManageByIdRequest recommendGoodsManageByIdRequest);

}

