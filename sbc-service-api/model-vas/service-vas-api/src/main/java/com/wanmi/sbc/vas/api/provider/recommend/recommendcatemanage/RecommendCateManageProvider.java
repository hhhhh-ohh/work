package com.wanmi.sbc.vas.api.provider.recommend.recommendcatemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分类推荐管理保存服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendCateManageProvider")
public interface RecommendCateManageProvider {

	/**
	 * 新增分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManageAddRequest 分类推荐管理新增参数结构 {@link RecommendCateManageAddRequest}
	 * @return 新增的分类推荐管理信息 {@link RecommendCateManageAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/add")
    BaseResponse<RecommendCateManageAddResponse> add(@RequestBody @Valid RecommendCateManageAddRequest recommendCateManageAddRequest);

	/**
	 * 新增分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManageAddListRequest 分类推荐管理新增参数结构 {@link RecommendCateManageAddListRequest}
	 * @return 新增的分类推荐管理信息 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/add-list")
    BaseResponse addList(@RequestBody @Valid RecommendCateManageAddListRequest recommendCateManageAddListRequest);

	/**
	 * 修改分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManageModifyRequest 分类推荐管理修改参数结构 {@link RecommendCateManageModifyRequest}
	 * @return 修改的分类推荐管理信息 {@link RecommendCateManageModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/modify")
    BaseResponse<RecommendCateManageModifyResponse> modify(@RequestBody @Valid RecommendCateManageModifyRequest recommendCateManageModifyRequest);

	/**
	 * 修改分类推荐权重API
	 *
	 * @author lvzhenwei
	 * @param request 分类推荐管理修改参数结构 {@link RecommendCateManageUpdateWeightRequest}
	 * @return 修改的分类推荐管理信息 {@link RecommendCateManageModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/update-cate-weight")
    BaseResponse updateCateWeight(@RequestBody @Valid RecommendCateManageUpdateWeightRequest request);

	/**
	 * 修改分类推荐禁推API
	 *
	 * @author lvzhenwei
	 * @param request 分类推荐管理修改参数结构 {@link RecommendCateManageUpdateNoPushTypeRequest}
	 * @return 修改的分类推荐管理信息 {@link RecommendCateManageModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/update-cate-no-push-type")
    BaseResponse updateCateNoPushType(@RequestBody @Valid RecommendCateManageUpdateNoPushTypeRequest request);

	/**
	 * 单个删除分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManageDelByIdRequest 单个删除参数结构 {@link RecommendCateManageDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid RecommendCateManageDelByIdRequest recommendCateManageDelByIdRequest);

	/**
	 * 批量删除分类推荐管理API
	 *
	 * @author lvzhenwei
	 * @param recommendCateManageDelByIdListRequest 批量删除参数结构 {@link RecommendCateManageDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendcatemanage/delete-by-id-list")
    BaseResponse deleteByIdList(@RequestBody @Valid RecommendCateManageDelByIdListRequest recommendCateManageDelByIdListRequest);

}

