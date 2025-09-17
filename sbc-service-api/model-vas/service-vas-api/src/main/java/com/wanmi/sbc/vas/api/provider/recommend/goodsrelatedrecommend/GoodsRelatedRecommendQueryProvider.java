package com.wanmi.sbc.vas.api.provider.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.GoodsRelatedRecommendByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.GoodsRelatedRecommendInfoListRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.GoodsRelatedRecommendListRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.GoodsRelatedRecommendPageRequest;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendInfoPageResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendListResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商品相关性推荐查询服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@FeignClient(value = "${application.vas.name}", contextId = "GoodsRelatedRecommendQueryProvider")
public interface GoodsRelatedRecommendQueryProvider {

	/**
	 * 分页查询商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendPageReq 分页请求参数和筛选对象 {@link GoodsRelatedRecommendPageRequest}
	 * @return 商品相关性推荐分页列表信息 {@link GoodsRelatedRecommendPageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/page")
    BaseResponse<GoodsRelatedRecommendPageResponse> page(@RequestBody @Valid GoodsRelatedRecommendPageRequest goodsRelatedRecommendPageReq);

	/**
	 * 列表查询商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendListReq 列表请求参数和筛选对象 {@link GoodsRelatedRecommendListRequest}
	 * @return 商品相关性推荐的列表信息 {@link GoodsRelatedRecommendListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/list")
    BaseResponse<GoodsRelatedRecommendListResponse> list(@RequestBody @Valid GoodsRelatedRecommendListRequest goodsRelatedRecommendListReq);

	/**
	 * 列表查询商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendInfoListRequest 列表请求参数和筛选对象 {@link GoodsRelatedRecommendInfoListRequest}
	 * @return 商品相关性推荐的列表信息 {@link GoodsRelatedRecommendListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/get-goods-related-recommend-info-list")
    BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendInfoList(@RequestBody @Valid GoodsRelatedRecommendInfoListRequest goodsRelatedRecommendInfoListRequest);

	/**
	 * 列表查询商品相关性推荐详情API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendInfoListRequest 列表请求参数和筛选对象 {@link GoodsRelatedRecommendInfoListRequest}
	 * @return 商品相关性推荐的列表信息 {@link GoodsRelatedRecommendListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/get-goods-related-recommend-info-detaill-list")
    BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendDetailInfoList(@RequestBody @Valid GoodsRelatedRecommendInfoListRequest goodsRelatedRecommendInfoListRequest);

	/**
	 * 列表查询商品相关性推荐详情API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendInfoListRequest 列表请求参数和筛选对象 {@link GoodsRelatedRecommendInfoListRequest}
	 * @return 商品相关性推荐的列表信息 {@link GoodsRelatedRecommendListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/get-goods-related-recommend-data-Info-list")
    BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendDataInfoList(@RequestBody @Valid GoodsRelatedRecommendInfoListRequest goodsRelatedRecommendInfoListRequest);

	/**
	 * 列表查询商品相关性推荐详情API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendInfoListRequest 列表请求参数和筛选对象 {@link GoodsRelatedRecommendInfoListRequest}
	 * @return 商品相关性推荐的列表信息 {@link GoodsRelatedRecommendListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/get-goods-related-recommend-choose-list")
    BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendChooseList(@RequestBody @Valid GoodsRelatedRecommendInfoListRequest goodsRelatedRecommendInfoListRequest);

	/**
	 * 单个查询商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendByIdRequest 单个查询商品相关性推荐请求参数 {@link GoodsRelatedRecommendByIdRequest}
	 * @return 商品相关性推荐详情 {@link GoodsRelatedRecommendByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/get-by-id")
    BaseResponse<GoodsRelatedRecommendByIdResponse> getById(@RequestBody @Valid GoodsRelatedRecommendByIdRequest goodsRelatedRecommendByIdRequest);

}

