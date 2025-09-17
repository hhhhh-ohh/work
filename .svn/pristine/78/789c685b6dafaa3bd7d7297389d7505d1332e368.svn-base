package com.wanmi.sbc.goods.api.provider.goodscommission;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigUpdateRequest;
import com.wanmi.sbc.goods.api.response.goodscatethirdcaterel.GoodsCateThirdCateRelAddResponse;
import com.wanmi.sbc.goods.api.response.goodscommission.GoodsCommissionConfigQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description   商品代销配置
 * @author  wur
 * @date: 2021/9/10 14:58
 **/
@FeignClient(value = "${application.goods.name}", contextId = "GoodsCommissionConfigProvider")
public interface GoodsCommissionConfigProvider {

	/**
	 * 商品代销配置
	 *
	 * @author 
	 * @param goodsCommissionConfigUpdateRequest 平台类目和第三方平台类目映射新增参数结构 {@link GoodsCommissionConfigUpdateRequest}
	 * @return 新增的平台类目和第三方平台类目映射信息 {@link GoodsCateThirdCateRelAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodscommissionconfig/update")
	BaseResponse update(@RequestBody @Valid GoodsCommissionConfigUpdateRequest goodsCommissionConfigUpdateRequest);

	/**
	 * 修改默认加价比例
	 *
	 * @author
	 * @param goodsCommissionConfigUpdateRequest 平台类目和第三方平台类目映射新增参数结构 {@link GoodsCommissionConfigUpdateRequest}
	 * @return 新增的平台类目和第三方平台类目映射信息 {@link GoodsCateThirdCateRelAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodscommissionconfig/updateAddRate")
	BaseResponse updateAddRate(@RequestBody @Valid GoodsCommissionConfigUpdateRequest goodsCommissionConfigUpdateRequest);

	/**
	 * 商品代销配置
	 *
	 * @author 
	 * @param goodsCommissionConfigQueryRequest 平台类目和第三方平台类目映射修改参数结构 {@link GoodsCommissionConfigQueryRequest}
	 * @return 修改的平台类目和第三方平台类目映射信息 {@link GoodsCommissionConfigQueryResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodscommissionconfig/query")
	BaseResponse<GoodsCommissionConfigQueryResponse> query(@RequestBody @Valid GoodsCommissionConfigQueryRequest goodsCommissionConfigQueryRequest);

}

