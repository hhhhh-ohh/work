package com.wanmi.sbc.vas.api.provider.recommend.manualrecommendgoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsInfoListRequest;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsListRequest;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsPageRequest;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsInfoListResponse;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsListResponse;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>手动推荐商品管理查询服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@FeignClient(value = "${application.vas.name}", contextId = "ManualRecommendGoodsQueryProvider")
public interface ManualRecommendGoodsQueryProvider {

	/**
	 * 分页查询手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param manualRecommendGoodsPageReq 分页请求参数和筛选对象 {@link ManualRecommendGoodsPageRequest}
	 * @return 手动推荐商品管理分页列表信息 {@link ManualRecommendGoodsPageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/page")
    BaseResponse<ManualRecommendGoodsPageResponse> page(@RequestBody @Valid ManualRecommendGoodsPageRequest manualRecommendGoodsPageReq);

	/**
	 * 列表查询手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param manualRecommendGoodsListReq 列表请求参数和筛选对象 {@link ManualRecommendGoodsListRequest}
	 * @return 手动推荐商品管理的列表信息 {@link ManualRecommendGoodsListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/list")
    BaseResponse<ManualRecommendGoodsListResponse> list(@RequestBody @Valid ManualRecommendGoodsListRequest manualRecommendGoodsListReq);

	/**
	 * 列表查询手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param request 列表请求参数和筛选对象 {@link ManualRecommendGoodsInfoListRequest}
	 * @return 手动推荐商品管理的列表信息 {@link ManualRecommendGoodsInfoListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/get-manual-recommend-goods-info-list")
    BaseResponse<ManualRecommendGoodsInfoListResponse> getManualRecommendGoodsInfoList(@RequestBody @Valid ManualRecommendGoodsInfoListRequest request);

	/**
	 * 列表查询手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param request 列表请求参数和筛选对象 {@link ManualRecommendGoodsInfoListRequest}
	 * @return 手动推荐商品管理的选择列表信息 {@link ManualRecommendGoodsInfoListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/get-manual-recommend-choose-goods-list")
    BaseResponse<ManualRecommendGoodsInfoListResponse> getManualRecommendChooseGoodsList(@RequestBody @Valid ManualRecommendGoodsInfoListRequest request);


	/**
	 * 单个查询手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param manualRecommendGoodsByIdRequest 单个查询手动推荐商品管理请求参数 {@link ManualRecommendGoodsByIdRequest}
	 * @return 手动推荐商品管理详情 {@link ManualRecommendGoodsByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/get-by-id")
    BaseResponse<ManualRecommendGoodsByIdResponse> getById(@RequestBody @Valid ManualRecommendGoodsByIdRequest manualRecommendGoodsByIdRequest);

}

