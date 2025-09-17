package com.wanmi.sbc.goods.api.provider.goodspropertydetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailAddRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailAddResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailModifyRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailModifyResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailDelByIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商品属性值保存服务Provider</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsPropertyDetailProvider")
public interface GoodsPropertyDetailProvider {

	/**
	 * 新增商品属性值API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailAddRequest 商品属性值新增参数结构 {@link GoodsPropertyDetailAddRequest}
	 * @return 新增的商品属性值信息 {@link GoodsPropertyDetailAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetail/add")
	BaseResponse<GoodsPropertyDetailAddResponse> add(@RequestBody @Valid GoodsPropertyDetailAddRequest goodsPropertyDetailAddRequest);

	/**
	 * 修改商品属性值API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailModifyRequest 商品属性值修改参数结构 {@link GoodsPropertyDetailModifyRequest}
	 * @return 修改的商品属性值信息 {@link GoodsPropertyDetailModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetail/modify")
	BaseResponse<GoodsPropertyDetailModifyResponse> modify(@RequestBody @Valid GoodsPropertyDetailModifyRequest goodsPropertyDetailModifyRequest);

	/**
	 * 单个删除商品属性值API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailDelByIdRequest 单个删除参数结构 {@link GoodsPropertyDetailDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetail/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid GoodsPropertyDetailDelByIdRequest goodsPropertyDetailDelByIdRequest);

	/**
	 * 批量删除商品属性值API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailDelByIdListRequest 批量删除参数结构 {@link GoodsPropertyDetailDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetail/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid GoodsPropertyDetailDelByIdListRequest goodsPropertyDetailDelByIdListRequest);

}

