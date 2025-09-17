package com.wanmi.sbc.goods.api.provider.buycyclegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.buycyclegoods.*;
import com.wanmi.sbc.goods.api.response.buycyclegoods.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>周期购spu表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@FeignClient(value = "${application.goods.name}", contextId = "BuyCycleGoodsQueryProvider")
public interface BuyCycleGoodsQueryProvider {

	/**
	 * 分页查询周期购spu表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsPageReq 分页请求参数和筛选对象 {@link BuyCycleGoodsPageRequest}
	 * @return 周期购spu表分页列表信息 {@link BuyCycleGoodsPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/page")
	BaseResponse<BuyCycleGoodsPageResponse> page(@RequestBody @Valid BuyCycleGoodsPageRequest buyCycleGoodsPageReq);

	/**
	 * 列表查询周期购spu表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsListReq 列表请求参数和筛选对象 {@link BuyCycleGoodsListRequest}
	 * @return 周期购spu表的列表信息 {@link BuyCycleGoodsListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/list")
	BaseResponse<BuyCycleGoodsListResponse> list(@RequestBody @Valid BuyCycleGoodsListRequest buyCycleGoodsListReq);

	/**
	 * 单个查询周期购spu表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsByIdRequest 单个查询周期购spu表请求参数 {@link BuyCycleGoodsByIdRequest}
	 * @return 周期购spu表详情 {@link BuyCycleGoodsByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/get-by-id")
	BaseResponse<BuyCycleGoodsByIdResponse> getById(@RequestBody @Valid BuyCycleGoodsByIdRequest buyCycleGoodsByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link BuyCycleGoodsExportRequest}
	 * @return 周期购spu表数量 {@link Long}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid BuyCycleGoodsExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link BuyCycleGoodsExportRequest}
	 * @return 周期购spu表列表 {@link BuyCycleGoodsExportResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/export/page")
	BaseResponse<BuyCycleGoodsExportResponse> exportBuyCycleGoodsRecord(@RequestBody @Valid BuyCycleGoodsExportRequest request);

	/**
	 * 单个查询周期购spu表API
	 *
	 * @author xuyunpeng
	 * @param request 单个查询周期购spu表请求参数 {@link BuyCycleGoodsBySkuIdRequest}
	 * @return 周期购spu表详情 {@link BuyCycleGoodsBySkuIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/get-by-sku-id")
	BaseResponse<BuyCycleGoodsBySkuIdResponse> getBySkuId(@RequestBody @Valid BuyCycleGoodsBySkuIdRequest request);


	/**
	 * 单个查询周期购spu表API
	 *
	 * @author xuyunpeng
	 * @param request 单个查询周期购spu表请求参数 {@link BuyCycleGoodsBySpuIdRequest}
	 * @return 周期购spu表详情 {@link BuyCycleGoodsBySpuIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/get-by-spu-id")
	BaseResponse<BuyCycleGoodsBySpuIdResponse> getBySpuId(@RequestBody @Valid BuyCycleGoodsBySpuIdRequest request);

}

