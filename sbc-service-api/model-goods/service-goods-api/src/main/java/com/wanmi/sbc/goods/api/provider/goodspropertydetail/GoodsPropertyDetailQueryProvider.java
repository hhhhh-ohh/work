package com.wanmi.sbc.goods.api.provider.goodspropertydetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailPageRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailPageResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailListRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailListResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailByIdRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商品属性值查询服务Provider</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsPropertyDetailQueryProvider")
public interface GoodsPropertyDetailQueryProvider {

	/**
	 * 分页查询商品属性值API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailPageReq 分页请求参数和筛选对象 {@link GoodsPropertyDetailPageRequest}
	 * @return 商品属性值分页列表信息 {@link GoodsPropertyDetailPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetail/page")
	BaseResponse<GoodsPropertyDetailPageResponse> page(@RequestBody @Valid GoodsPropertyDetailPageRequest goodsPropertyDetailPageReq);

	/**
	 * 列表查询商品属性值API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailListReq 列表请求参数和筛选对象 {@link GoodsPropertyDetailListRequest}
	 * @return 商品属性值的列表信息 {@link GoodsPropertyDetailListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetail/list")
	BaseResponse<GoodsPropertyDetailListResponse> list(@RequestBody @Valid GoodsPropertyDetailListRequest goodsPropertyDetailListReq);

	/**
	 * 单个查询商品属性值API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailByIdRequest 单个查询商品属性值请求参数 {@link GoodsPropertyDetailByIdRequest}
	 * @return 商品属性值详情 {@link GoodsPropertyDetailByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetail/get-by-id")
	BaseResponse<GoodsPropertyDetailByIdResponse> getById(@RequestBody @Valid GoodsPropertyDetailByIdRequest goodsPropertyDetailByIdRequest);

}

