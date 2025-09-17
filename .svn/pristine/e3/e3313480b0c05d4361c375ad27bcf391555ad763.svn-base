package com.wanmi.sbc.goods.api.provider.goodstemplate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodstemplate.*;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateAddResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>GoodsTemplate保存服务Provider</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsTemplateProvider")
public interface GoodsTemplateProvider {

	/**
	 * 新增GoodsTemplateAPI
	 *
	 * @author 黄昭
	 * @param goodsTemplateAddRequest GoodsTemplate新增参数结构 {@link GoodsTemplateAddRequest}
	 * @return 新增的GoodsTemplate信息 {@link GoodsTemplateAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/add")
	BaseResponse<GoodsTemplateAddResponse> add(@RequestBody @Valid GoodsTemplateAddRequest goodsTemplateAddRequest);

	/**
	 * 修改GoodsTemplateAPI
	 *
	 * @author 黄昭
	 * @param goodsTemplateModifyRequest GoodsTemplate修改参数结构 {@link GoodsTemplateModifyRequest}
	 * @return 修改的GoodsTemplate信息 {@link GoodsTemplateModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/modify")
	BaseResponse<GoodsTemplateModifyResponse> modify(@RequestBody @Valid GoodsTemplateModifyRequest goodsTemplateModifyRequest);

	/**
	 * 单个删除GoodsTemplateAPI
	 *
	 * @author 黄昭
	 * @param goodsTemplateDelByIdRequest 单个删除参数结构 {@link GoodsTemplateDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid GoodsTemplateDelByIdRequest goodsTemplateDelByIdRequest);

	/**
	 * 批量删除GoodsTemplateAPI
	 *
	 * @author 黄昭
	 * @param goodsTemplateDelByIdListRequest 批量删除参数结构 {@link GoodsTemplateDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid GoodsTemplateDelByIdListRequest goodsTemplateDelByIdListRequest);

	/**
	 * 关联商品
	 * @param request
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/join/goods")
	BaseResponse joinGoods(@RequestBody @Valid GoodsTemplateJoinRequest request);

	/**
	 * 单个删除关联商品
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/del-by-goods-id")
	BaseResponse deleteByGoodsId(@RequestBody @Valid GoodsTemplateByGoodsIdRequest request);

}

