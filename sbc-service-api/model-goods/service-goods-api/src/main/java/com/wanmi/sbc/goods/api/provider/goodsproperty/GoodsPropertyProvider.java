package com.wanmi.sbc.goods.api.provider.goodsproperty;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodsproperty.*;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyAddResponse;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商品属性保存服务Provider</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsPropertyProvider")
public interface GoodsPropertyProvider {

	/**
	 * 新增商品属性API
	 *
	 * @author chenli
	 * @param goodsPropertyAddRequest 商品属性新增参数结构 {@link GoodsPropertyAddRequest}
	 * @return 新增的商品属性信息 {@link GoodsPropertyAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodsproperty/add-goods-prop")
	BaseResponse<Long> saveGoodsProperty(@RequestBody @Valid GoodsPropertyAddRequest goodsPropertyAddRequest);

	/**
	 * 修改商品属性API
	 *
	 * @author chenli
	 * @param goodsPropertyModifyRequest 商品属性修改参数结构 {@link GoodsPropertyModifyRequest}
	 * @return 修改的商品属性信息 {@link GoodsPropertyModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodsproperty/modify-goods-prop")
	BaseResponse<GoodsPropertyModifyResponse> modifyGoodsProperty(@RequestBody @Valid GoodsPropertyModifyRequest goodsPropertyModifyRequest);

	/**
	 * 修改 属性索引
	 * @param request  {@link GoodsPropertyIndexRequest}
	 * @return {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodsproperty/modify-goods-prop-index")
	BaseResponse modifyIndexFlag(@RequestBody @Valid GoodsPropertyIndexRequest request);

	/**
	 * 单个删除商品属性API
	 *
	 * @author chenli
	 * @param goodsPropertyDelByIdRequest 单个删除参数结构 {@link GoodsPropertyDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodsproperty/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid GoodsPropertyDelByIdRequest goodsPropertyDelByIdRequest);

	/**
	 * 批量删除商品属性API
	 *
	 * @author chenli
	 * @param goodsPropertyDelByIdListRequest 批量删除参数结构 {@link GoodsPropertyDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodsproperty/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid GoodsPropertyDelByIdListRequest goodsPropertyDelByIdListRequest);

}

