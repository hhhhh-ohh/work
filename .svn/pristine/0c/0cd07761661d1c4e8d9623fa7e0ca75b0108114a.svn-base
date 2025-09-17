package com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.*;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsListResponse;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsMagicPageResponse;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>新人购商品表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@FeignClient(value = "${application.goods.name}", contextId = "NewcomerPurchaseGoodsQueryProvider")
public interface NewcomerPurchaseGoodsQueryProvider {

	/**
	 * 分页查询新人购商品表API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseGoodsPageReq 分页请求参数和筛选对象 {@link NewcomerPurchaseGoodsPageRequest}
	 * @return 新人购商品表分页列表信息 {@link NewcomerPurchaseGoodsPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/page")
	BaseResponse<NewcomerPurchaseGoodsPageResponse> page(@RequestBody @Valid NewcomerPurchaseGoodsPageRequest newcomerPurchaseGoodsPageReq);

	/**
	 * 列表查询新人购商品表API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseGoodsListReq 列表请求参数和筛选对象 {@link NewcomerPurchaseGoodsListRequest}
	 * @return 新人购商品表的列表信息 {@link NewcomerPurchaseGoodsListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/list")
	BaseResponse<NewcomerPurchaseGoodsListResponse> list(@RequestBody @Valid NewcomerPurchaseGoodsListRequest newcomerPurchaseGoodsListReq);

	/**
	 * 单个查询新人购商品表API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseGoodsByIdRequest 单个查询新人购商品表请求参数 {@link NewcomerPurchaseGoodsByIdRequest}
	 * @return 新人购商品表详情 {@link NewcomerPurchaseGoodsByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/get-by-id")
	BaseResponse<NewcomerPurchaseGoodsByIdResponse> getById(@RequestBody @Valid NewcomerPurchaseGoodsByIdRequest newcomerPurchaseGoodsByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link NewcomerPurchaseGoodsExportRequest}
	 * @return 新人购商品表数量 {@link Long}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid NewcomerPurchaseGoodsExportRequest request);

	/**
	 * 魔方分页查询新人购商品表API
	 *
	 * @author xuyunpeng
	 * @param request 分页请求参数和筛选对象 {@link NewcomerPurchaseGoodsPageMagicRequest}
	 * @return 新人购商品表分页列表信息 {@link NewcomerPurchaseGoodsMagicPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/newcomerpurchasegoods/magic-page")
	BaseResponse<NewcomerPurchaseGoodsMagicPageResponse> magicPage(@RequestBody @Valid NewcomerPurchaseGoodsPageMagicRequest request);



}

