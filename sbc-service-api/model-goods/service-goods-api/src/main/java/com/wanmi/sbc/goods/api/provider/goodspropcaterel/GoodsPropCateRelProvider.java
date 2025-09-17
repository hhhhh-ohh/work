package com.wanmi.sbc.goods.api.provider.goodspropcaterel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.*;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商品类目与属性关联保存服务Provider</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsPropCateRelProvider")
public interface GoodsPropCateRelProvider {

	/**
	 * 新增商品类目与属性关联API
	 *
	 * @author chenli
	 * @param goodsPropCateRelAddRequest 商品类目与属性关联新增参数结构 {@link GoodsPropCateRelAddRequest}
	 * @return 新增的商品类目与属性关联信息 {@link GoodsPropCateRelAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/add")
	BaseResponse<GoodsPropCateRelAddResponse> add(@RequestBody @Valid GoodsPropCateRelAddRequest goodsPropCateRelAddRequest);

	/**
	 * 修改商品类目与属性关联API
	 *
	 * @author chenli
	 * @param request 商品类目与属性关联修改参数结构 {@link GoodsPropCateSortRequest}
	 * @return 修改的商品类目与属性关联信息
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/modify-sort")
	BaseResponse modifySort(@RequestBody @Valid GoodsPropCateSortRequest request);

	/**
	 * 单个删除商品类目与属性关联API
	 *
	 * @author chenli
	 * @param goodsPropCateRelDelByIdRequest 单个删除参数结构 {@link GoodsPropCateRelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid GoodsPropCateRelDelByIdRequest goodsPropCateRelDelByIdRequest);

	/**
	 * 批量删除商品类目与属性关联API
	 *
	 * @author chenli
	 * @param goodsPropCateRelDelByIdListRequest 批量删除参数结构 {@link GoodsPropCateRelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid GoodsPropCateRelDelByIdListRequest goodsPropCateRelDelByIdListRequest);

}

