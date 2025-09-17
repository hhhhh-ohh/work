package com.wanmi.sbc.goods.api.provider.goodscommission;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigStatusUpdateRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigUpdateRequest;
import com.wanmi.sbc.goods.api.response.goodscommission.GoodsCommissionPriceConfigQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description   商品代销智能设价加价比例设置
 * @author  wur
 * @date: 2021/9/10 14:58
 **/
@FeignClient(value = "${application.goods.name}", contextId = "GoodsCommissionPriceConfigProvider")
public interface GoodsCommissionPriceConfigProvider {

	/**
	 * 代销智能设价加价比例跟新
	 *
	 * @author 
	 * @param goodsCommissionConfigUpdateRequest 平台类目和第三方平台类目映射新增参数结构 {@link GoodsCommissionPriceConfigStatusUpdateRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/goodscommissionpriceconfig/update")
	BaseResponse update(@RequestBody @Valid GoodsCommissionPriceConfigUpdateRequest goodsCommissionConfigUpdateRequest);

	/**
	 * 代销智能设价加价比例查询
	 *
	 * @author 
	 * @param goodsCommissionPriceConfigQueryRequest 平台类目和第三方平台类目映射修改参数结构 {@link GoodsCommissionPriceConfigQueryRequest}
	 * @return 修改的平台类目和第三方平台类目映射信息 {@link GoodsCommissionPriceConfigQueryResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodscommissionpriceconfig/query")
	BaseResponse<GoodsCommissionPriceConfigQueryResponse> query(@RequestBody @Valid GoodsCommissionPriceConfigQueryRequest goodsCommissionPriceConfigQueryRequest);

	/**
	 * 代销智能设价加价比例配置开启状态更新
	 *
	 * @author
	 * @param commissionPriceConfigStatusUpdateRequest 平台类目和第三方平台类目映射修改参数结构 {@link GoodsCommissionPriceConfigStatusUpdateRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/goodscommissionpriceconfig/updateStatus")
	BaseResponse updateStatus(@RequestBody @Valid GoodsCommissionPriceConfigStatusUpdateRequest commissionPriceConfigStatusUpdateRequest);

}

