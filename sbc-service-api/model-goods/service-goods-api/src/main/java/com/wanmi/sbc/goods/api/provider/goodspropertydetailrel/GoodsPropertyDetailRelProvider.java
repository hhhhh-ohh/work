package com.wanmi.sbc.goods.api.provider.goodspropertydetailrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelAddRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardImportGoodsRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelAddResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelModifyRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelModifyResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelDelByIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商品与属性值关联保存服务Provider</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsPropertyDetailRelProvider")
public interface GoodsPropertyDetailRelProvider {

	/**
	 * 新增商品与属性值关联API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailRelAddRequest 商品与属性值关联新增参数结构 {@link GoodsPropertyDetailRelAddRequest}
	 * @return 新增的商品与属性值关联信息 {@link GoodsPropertyDetailRelAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/add")
	BaseResponse<GoodsPropertyDetailRelAddResponse> add(@RequestBody @Valid GoodsPropertyDetailRelAddRequest goodsPropertyDetailRelAddRequest);

	/**
	 * 修改商品与属性值关联API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailRelModifyRequest 商品与属性值关联修改参数结构 {@link GoodsPropertyDetailRelModifyRequest}
	 * @return 修改的商品与属性值关联信息 {@link GoodsPropertyDetailRelModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/modify")
	BaseResponse<GoodsPropertyDetailRelModifyResponse> modify(@RequestBody @Valid GoodsPropertyDetailRelModifyRequest goodsPropertyDetailRelModifyRequest);

	/**
	 * 单个删除商品与属性值关联API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailRelDelByIdRequest 单个删除参数结构 {@link GoodsPropertyDetailRelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid GoodsPropertyDetailRelDelByIdRequest goodsPropertyDetailRelDelByIdRequest);

	/**
	 * 批量删除商品与属性值关联API
	 *
	 * @author chenli
	 * @param goodsPropertyDetailRelDelByIdListRequest 批量删除参数结构 {@link GoodsPropertyDetailRelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid GoodsPropertyDetailRelDelByIdListRequest goodsPropertyDetailRelDelByIdListRequest);

	/**
	 * 商品关系导入
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/import-by-goods-id")
	BaseResponse importGoodsDetailRel(@RequestBody StandardImportGoodsRequest request);

}

