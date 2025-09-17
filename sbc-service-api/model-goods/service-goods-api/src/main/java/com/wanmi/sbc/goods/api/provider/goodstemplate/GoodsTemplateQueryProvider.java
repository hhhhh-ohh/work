package com.wanmi.sbc.goods.api.provider.goodstemplate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodstemplate.*;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplatePageResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateListResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateByIdResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.goodsTemplateJoinResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>GoodsTemplate查询服务Provider</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsTemplateQueryProvider")
public interface GoodsTemplateQueryProvider {

	/**
	 * 分页查询GoodsTemplateAPI
	 *
	 * @author 黄昭
	 * @param goodsTemplatePageReq 分页请求参数和筛选对象 {@link GoodsTemplatePageRequest}
	 * @return GoodsTemplate分页列表信息 {@link GoodsTemplatePageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/page")
	BaseResponse<GoodsTemplatePageResponse> page(@RequestBody @Valid GoodsTemplatePageRequest goodsTemplatePageReq);

	/**
	 * 列表查询GoodsTemplateAPI
	 *
	 * @author 黄昭
	 * @param goodsTemplateListReq 列表请求参数和筛选对象 {@link GoodsTemplateListRequest}
	 * @return GoodsTemplate的列表信息 {@link GoodsTemplateListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/list")
	BaseResponse<GoodsTemplateListResponse> list(@RequestBody @Valid GoodsTemplateListRequest goodsTemplateListReq);

	/**
	 * 单个查询GoodsTemplateAPI
	 *
	 * @author 黄昭
	 * @param goodsTemplateByIdRequest 单个查询GoodsTemplate请求参数 {@link GoodsTemplateByIdRequest}
	 * @return GoodsTemplate详情 {@link GoodsTemplateByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/get-by-id")
	BaseResponse<GoodsTemplateByIdResponse> getById(@RequestBody @Valid GoodsTemplateByIdRequest goodsTemplateByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author 黄昭
	 * @param request {tableDesc}导出数量查询请求 {@link GoodsTemplateExportRequest}
	 * @return GoodsTemplate数量 {@link Long}
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid GoodsTemplateExportRequest request);

	/**
	 * 关联商品详情
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/join-goods/details")
	BaseResponse<goodsTemplateJoinResponse> joinGoodsDetails(@RequestBody @Valid GoodsTemplateByIdRequest request);

	/**
	 * 根据商品Id查询商品模版信息
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goodstemplate/get-by-goods-id")
	BaseResponse<GoodsTemplateByIdResponse> getByGoodsId(@RequestBody @Valid GoodsTemplateByGoodsIdRequest request);
}

