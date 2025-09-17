package com.wanmi.sbc.goods.api.provider.goodspropcaterel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelByIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelListRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelPageRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelQueryRequest;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelByIdResponse;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelListResponse;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelPageResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>商品类目与属性关联查询服务Provider</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsPropCateRelQueryProvider")
public interface GoodsPropCateRelQueryProvider {

	/**
	 * 分页查询商品类目与属性关联API
	 *
	 * @author chenli
	 * @param goodsPropCateRelPageReq 分页请求参数和筛选对象 {@link GoodsPropCateRelPageRequest}
	 * @return 商品类目与属性关联分页列表信息 {@link GoodsPropCateRelPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/page")
	BaseResponse<GoodsPropCateRelPageResponse> page(@RequestBody @Valid GoodsPropCateRelPageRequest goodsPropCateRelPageReq);

	/**
	 * 列表查询商品类目与属性关联API
	 *
	 * @author chenli
	 * @param request 列表请求参数和筛选对象 {@link GoodsPropCateRelListRequest}
	 * @return 商品类目与属性关联的列表信息 {@link GoodsPropCateRelListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/find-cate-prop")
	BaseResponse<GoodsPropCateRelListResponse> findCateProperty(@RequestBody @Valid GoodsPropCateRelListRequest request);

	/**
	 * 列表查询商品类目与属性关联API
	 *
	 * @author chenli
	 * @param request 列表请求参数和筛选对象 {@link GoodsPropCateRelListRequest}
	 * @return 商品类目与属性关联的列表信息 {@link GoodsPropCateRelListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/find-cate-prop-detail")
	BaseResponse<List<GoodsPropertyDetailRelDTO>> findCateDetailProp(@RequestBody @Valid GoodsPropCateRelListRequest request);

	/**
	 * 单个查询商品类目与属性关联API
	 *
	 * @author chenli
	 * @param goodsPropCateRelByIdRequest 单个查询商品类目与属性关联请求参数 {@link GoodsPropCateRelByIdRequest}
	 * @return 商品类目与属性关联详情 {@link GoodsPropCateRelByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/get-by-id")
	BaseResponse<GoodsPropCateRelByIdResponse> getById(@RequestBody @Valid GoodsPropCateRelByIdRequest goodsPropCateRelByIdRequest);

	/**
	 * 根据分类id和属性id查询关联关系
	 * @param goodsPropCateRelQueryRequest
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropcaterel/get-by-cate-and-prop")
	BaseResponse<GoodsPropCateRelByIdResponse> getByCateIdAndPropId(@RequestBody @Valid GoodsPropCateRelQueryRequest goodsPropCateRelQueryRequest);
}

