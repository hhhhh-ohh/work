package com.wanmi.sbc.vas.api.provider.recommend.manualrecommendgoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.*;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>手动推荐商品管理保存服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@FeignClient(value = "${application.vas.name}", contextId = "ManualRecommendGoodsProvider")
public interface ManualRecommendGoodsProvider {

	/**
	 * 新增手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param manualRecommendGoodsAddRequest 手动推荐商品管理新增参数结构 {@link ManualRecommendGoodsAddRequest}
	 * @return 新增的手动推荐商品管理信息 {@link ManualRecommendGoodsAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/add")
    BaseResponse<ManualRecommendGoodsAddResponse> add(@RequestBody @Valid ManualRecommendGoodsAddRequest manualRecommendGoodsAddRequest);

	/**
	 * 批量新增手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param manualRecommendGoodsAddListRequest 手动推荐商品管理批量新增参数结构 {@link ManualRecommendGoodsAddListRequest}
	 * @return 新增的手动推荐商品管理信息 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/add-list")
    BaseResponse addList(@RequestBody @Valid ManualRecommendGoodsAddListRequest manualRecommendGoodsAddListRequest);

	/**
	 * 修改手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param manualRecommendGoodsModifyRequest 手动推荐商品管理修改参数结构 {@link ManualRecommendGoodsModifyRequest}
	 * @return 修改的手动推荐商品管理信息 {@link ManualRecommendGoodsModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/modify")
    BaseResponse<ManualRecommendGoodsModifyResponse> modify(@RequestBody @Valid ManualRecommendGoodsModifyRequest manualRecommendGoodsModifyRequest);

	/**
	 * 更新对应商品的权重管理API
	 *
	 * @author lvzhenwei
	 * @param request 更新对应商品的权重参数结构 {@link ManualRecommendGoodsUpdateWeightRequest}
	 * @return 修改的手动推荐商品管理信息 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/update-weight")
    BaseResponse updateWeight(@RequestBody @Valid ManualRecommendGoodsUpdateWeightRequest request);

	/**
	 * 单个删除手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param manualRecommendGoodsDelByIdRequest 单个删除参数结构 {@link ManualRecommendGoodsDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid ManualRecommendGoodsDelByIdRequest manualRecommendGoodsDelByIdRequest);

	/**
	 * 批量删除手动推荐商品管理API
	 *
	 * @author lvzhenwei
	 * @param manualRecommendGoodsDelByIdListRequest 批量删除参数结构 {@link ManualRecommendGoodsDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/manualrecommendgoods/delete-by-id-list")
    BaseResponse deleteByIdList(@RequestBody @Valid ManualRecommendGoodsDelByIdListRequest manualRecommendGoodsDelByIdListRequest);

}

