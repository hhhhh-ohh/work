package com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoValidateRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.*;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoPageResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoListResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoByIdResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>周期购sku表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@FeignClient(value = "${application.goods.name}", contextId = "BuyCycleGoodsInfoQueryProvider")
public interface BuyCycleGoodsInfoQueryProvider {

	/**
	 * 分页查询周期购sku表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsInfoPageReq 分页请求参数和筛选对象 {@link BuyCycleGoodsInfoPageRequest}
	 * @return 周期购sku表分页列表信息 {@link BuyCycleGoodsInfoPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/page")
	BaseResponse<BuyCycleGoodsInfoPageResponse> page(@RequestBody @Valid BuyCycleGoodsInfoPageRequest buyCycleGoodsInfoPageReq);

	/**
	 * 列表查询周期购sku表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsInfoListReq 列表请求参数和筛选对象 {@link BuyCycleGoodsInfoListRequest}
	 * @return 周期购sku表的列表信息 {@link BuyCycleGoodsInfoListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/list")
	BaseResponse<BuyCycleGoodsInfoListResponse> list(@RequestBody @Valid BuyCycleGoodsInfoListRequest buyCycleGoodsInfoListReq);

	/**
	 * 单个查询周期购sku表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsInfoByIdRequest 单个查询周期购sku表请求参数 {@link BuyCycleGoodsInfoByIdRequest}
	 * @return 周期购sku表详情 {@link BuyCycleGoodsInfoByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/get-by-id")
	BaseResponse<BuyCycleGoodsInfoByIdResponse> getById(@RequestBody @Valid BuyCycleGoodsInfoByGoodsIdRequest buyCycleGoodsInfoByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link BuyCycleGoodsInfoExportRequest}
	 * @return 周期购sku表数量 {@link Long}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid BuyCycleGoodsInfoExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link BuyCycleGoodsInfoExportRequest}
	 * @return 周期购sku表列表 {@link BuyCycleGoodsInfoExportResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/export/page")
	BaseResponse<BuyCycleGoodsInfoExportResponse> exportBuyCycleGoodsInfoRecord(@RequestBody @Valid BuyCycleGoodsInfoExportRequest request);


	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/find-by-id")
	BaseResponse<BuyCycleGoodsInfoByIdResponse> findById(@RequestBody @Valid BuyCycleGoodsInfoByIdRequest buyCycleGoodsInfoByIdRequest);

	/**
	 * 互斥验证
	 *
	 * @author dyt
	 * @param request 互斥请求参数 {@link BuyCycleGoodsInfoValidateRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/validate")
	BaseResponse validate(@RequestBody @Valid BuyCycleGoodsInfoValidateRequest request);
}

