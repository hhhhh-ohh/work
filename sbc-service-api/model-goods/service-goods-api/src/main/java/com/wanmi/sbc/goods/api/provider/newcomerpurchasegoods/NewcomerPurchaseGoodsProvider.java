package com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.*;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsAddResponse;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>新人购商品表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@FeignClient(value = "${application.goods.name}", contextId = "NewcomerPurchaseGoodsProvider")
public interface NewcomerPurchaseGoodsProvider {

	/**
	 * 新增新人购商品表API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseGoodsAddRequest 新人购商品表新增参数结构 {@link NewcomerPurchaseGoodsAddRequest}
	 * @return 新增的新人购商品表信息 {@link NewcomerPurchaseGoodsAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/add")
	BaseResponse<NewcomerPurchaseGoodsAddResponse> add(@RequestBody @Valid NewcomerPurchaseGoodsAddRequest newcomerPurchaseGoodsAddRequest);

	/**
	 * 修改新人购商品表API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseGoodsModifyRequest 新人购商品表修改参数结构 {@link NewcomerPurchaseGoodsModifyRequest}
	 * @return 修改的新人购商品表信息 {@link NewcomerPurchaseGoodsModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/modify")
	BaseResponse<NewcomerPurchaseGoodsModifyResponse> modify(@RequestBody @Valid NewcomerPurchaseGoodsModifyRequest newcomerPurchaseGoodsModifyRequest);

	/**
	 * 单个删除新人购商品表API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseGoodsDelByIdRequest 单个删除参数结构 {@link NewcomerPurchaseGoodsDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid NewcomerPurchaseGoodsDelByIdRequest newcomerPurchaseGoodsDelByIdRequest);

	/**
	 * 批量删除新人购商品表API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseGoodsDelByIdListRequest 批量删除参数结构 {@link NewcomerPurchaseGoodsDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid NewcomerPurchaseGoodsDelByIdListRequest newcomerPurchaseGoodsDelByIdListRequest);




	/**
	 * 新增新人购商品表API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseGoodsSaveRequest 新人购商品表新增参数结构 {@link NewcomerPurchaseGoodsSaveRequest}
	 * @return 新增的新人购商品表信息 {@link NewcomerPurchaseGoodsAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/saveAll")
	BaseResponse saveAll(@RequestBody @Valid NewcomerPurchaseGoodsSaveRequest newcomerPurchaseGoodsSaveRequest);

}

