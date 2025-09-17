package com.wanmi.sbc.vas.api.provider.recommend.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商品推荐管理保存服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendGoodsManageProvider")
public interface RecommendGoodsManageProvider {

	/**
	 * 新增商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManageAddRequest 商品推荐管理新增参数结构 {@link RecommendGoodsManageAddRequest}
	 * @return 新增的商品推荐管理信息 {@link RecommendGoodsManageAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/add")
    BaseResponse<RecommendGoodsManageAddResponse> add(@RequestBody @Valid RecommendGoodsManageAddRequest recommendGoodsManageAddRequest);

	/**
	 * 批量新增商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManageAddListRequest 商品推荐管理批量新增参数结构 {@link RecommendGoodsManageAddListRequest}
	 * @return 批量新增的商品推荐管理信息 {@link RecommendGoodsManageAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/add-list")
    BaseResponse addList(@RequestBody @Valid RecommendGoodsManageAddListRequest recommendGoodsManageAddListRequest);

	/**
	 * 修改商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManageModifyRequest 商品推荐管理修改参数结构 {@link RecommendGoodsManageModifyRequest}
	 * @return 修改的商品推荐管理信息 {@link RecommendGoodsManageModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/modify")
    BaseResponse<RecommendGoodsManageModifyResponse> modify(@RequestBody @Valid RecommendGoodsManageModifyRequest recommendGoodsManageModifyRequest);

	/**
	 * 更新商品管理状态
	 *
	 * @author lvzhenwei
	 * @param request 更新商品管理状态结构 {@link RecommendGoodsManageUpdateNoPushRequest}
	 * @return 更新商品管理状态信息 {@link RecommendGoodsManageUpdateNoPushRequest}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/update-no-push")
    BaseResponse updateNoPush(@RequestBody @Valid RecommendGoodsManageUpdateNoPushRequest request);

	/**
	 * 更新商品管理权重
	 *
	 * @author lvzhenwei
	 * @param request 更新商品管理权重结构 {@link RecommendGoodsManageUpdateWeightRequest}
	 * @return 更新商品管理权重信息 {@link RecommendGoodsManageUpdateWeightRequest}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/update-weight")
    BaseResponse updateWeight(@RequestBody @Valid RecommendGoodsManageUpdateWeightRequest request);

	/**
	 * 单个删除商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManageDelByIdRequest 单个删除参数结构 {@link RecommendGoodsManageDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid RecommendGoodsManageDelByIdRequest recommendGoodsManageDelByIdRequest);

	/**
	 * 批量删除商品推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendGoodsManageDelByIdListRequest 批量删除参数结构 {@link RecommendGoodsManageDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendgoodsmanage/delete-by-id-list")
    BaseResponse deleteByIdList(@RequestBody @Valid RecommendGoodsManageDelByIdListRequest recommendGoodsManageDelByIdListRequest);

}

