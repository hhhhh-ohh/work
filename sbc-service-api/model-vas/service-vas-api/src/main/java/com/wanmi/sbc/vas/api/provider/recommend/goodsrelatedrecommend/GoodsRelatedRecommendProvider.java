package com.wanmi.sbc.vas.api.provider.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商品相关性推荐保存服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@FeignClient(value = "${application.vas.name}", contextId = "GoodsRelatedRecommendProvider")
public interface GoodsRelatedRecommendProvider {

	/**
	 * 新增商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendAddRequest 商品相关性推荐新增参数结构 {@link GoodsRelatedRecommendAddRequest}
	 * @return 新增的商品相关性推荐信息 {@link GoodsRelatedRecommendAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/add")
    BaseResponse<GoodsRelatedRecommendAddResponse> add(@RequestBody @Valid GoodsRelatedRecommendAddRequest goodsRelatedRecommendAddRequest);

	/**
	 * 批量新增商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendAddListRequest 商品相关性推荐新增参数结构 {@link GoodsRelatedRecommendAddListRequest}
	 * @return 新增的商品相关性推荐信息 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/add-list")
    BaseResponse addList(@RequestBody @Valid GoodsRelatedRecommendAddListRequest goodsRelatedRecommendAddListRequest);

	/**
	 * 修改商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendModifyRequest 商品相关性推荐修改参数结构 {@link GoodsRelatedRecommendModifyRequest}
	 * @return 修改的商品相关性推荐信息 {@link GoodsRelatedRecommendModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/modify")
    BaseResponse<GoodsRelatedRecommendModifyResponse> modify(@RequestBody @Valid GoodsRelatedRecommendModifyRequest goodsRelatedRecommendModifyRequest);

	/**
	 * 单个删除商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param request 单个删除参数结构 {@link GoodsRelatedRecommendDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/update-weight")
    BaseResponse updateWeight(@RequestBody @Valid GoodsRelatedRecommendUpdateWeightRequest request);

	/**
	 * 单个删除商品相关性推荐API
	 *
	 * @author lvzhenwei
	 * @param goodsRelatedRecommendDelByIdRequest 单个删除参数结构 {@link GoodsRelatedRecommendDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodsrelatedrecommend/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid GoodsRelatedRecommendDelByIdRequest goodsRelatedRecommendDelByIdRequest);

}

