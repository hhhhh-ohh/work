package com.wanmi.sbc.goods.api.provider.flashsalegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.flashsalegoods.*;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsByActivityIdResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsListResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsPageResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsStoreCountResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.IsFlashSaleResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.IsInProgressResp;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsSimpleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>抢购商品表查询服务Provider</p>
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@FeignClient(value = "${application.goods.name}", contextId = "FlashSaleGoodsQueryProvider")
public interface FlashSaleGoodsQueryProvider {

	/**
	 * 分页查询抢购商品表API
	 *
	 * @author bob
	 * @param flashSaleGoodsPageReq 分页请求参数和筛选对象 {@link FlashSaleGoodsPageRequest}
	 * @return 抢购商品表分页列表信息 {@link FlashSaleGoodsPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/page")
	BaseResponse<FlashSaleGoodsPageResponse> page(@RequestBody @Valid FlashSaleGoodsPageRequest flashSaleGoodsPageReq);

	/**
	 * 列表查询抢购商品表API
	 *
	 * @author bob
	 * @param flashSaleGoodsListReq 列表请求参数和筛选对象 {@link FlashSaleGoodsListRequest}
	 * @return 抢购商品表的列表信息 {@link FlashSaleGoodsListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/list")
	BaseResponse<FlashSaleGoodsListResponse> list(@RequestBody @Valid FlashSaleGoodsListRequest flashSaleGoodsListReq);

	/**
	 * 单个查询抢购商品表API
	 *
	 * @author bob
	 * @param flashSaleGoodsByIdRequest 单个查询抢购商品表请求参数 {@link FlashSaleGoodsByIdRequest}
	 * @return 抢购商品表详情 {@link FlashSaleGoodsByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/get-by-id")
	BaseResponse<FlashSaleGoodsByIdResponse> getById(@RequestBody @Valid FlashSaleGoodsByIdRequest flashSaleGoodsByIdRequest);

	/**
	 * 商品是否正在抢购API
	 *
	 * @author bob
	 * @param isInProgressReq 单个查询抢购商品表请求参数 {@link IsInProgressReq}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/isInProgress")
	BaseResponse<IsInProgressResp> isInProgress(@RequestBody @Valid IsInProgressReq isInProgressReq);

	/**
	 * 根据skuId判断商品是否正在抢购中
	 * @param isInProgressByGoodsInfoIdReq
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/isInProgressByGoodsInfoId")
	BaseResponse<IsInProgressResp> isInProgressByGoodsInfoId(@RequestBody @Valid IsInProgressByGoodsInfoIdReq
																	 isInProgressByGoodsInfoIdReq);

	/**
	 * 获取参与商家数量API
	 *
	 * @author yxz
	 * @return 参与商家数量 {@link FlashSaleGoodsStoreCountResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/storeCount")
	BaseResponse<FlashSaleGoodsStoreCountResponse> storeCount();

	/**
	 * 是否有未结束活动关联商品
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/isFlashSale")
	BaseResponse<IsFlashSaleResponse> isFlashSale(@RequestBody @Valid IsFlashSaleRequest isFlashSaleRequest);

	/**
	 * 根据请求参数返回精简的实体对象
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/query-simple-by-ids")
	BaseResponse<List<FlashSaleGoodsSimpleVO>> querySimpleByIds(@RequestBody @Valid FlashSaleGoodsListRequest request);

	/**
	 * 单个查询抢购商品表API
	 *
	 * @author bob
	 * @param flashSaleGoodsByIdRequest 单个查询抢购商品表请求参数 {@link FlashSaleGoodsByIdRequest}
	 * @return 抢购商品表详情 {@link FlashSaleGoodsByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/get-by-id-not-del")
	BaseResponse<FlashSaleGoodsByIdResponse> getByIdNotDel(@RequestBody @Valid FlashSaleGoodsByIdRequest flashSaleGoodsByIdRequest);


	/**
	 * 查询需要刷新缓存的数据
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/syc-cache")
	BaseResponse<FlashSaleGoodsPageResponse> sycCache(@RequestBody @Valid FlashSaleGoodsQueryRequest request);

	/**
	 * 根据skuId集合判断商品是否正在抢购中
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/isInProgressBySkuList")
	BaseResponse<IsInProgressResp> isInProgressBySkuList(@RequestBody IsInProgressRequest
																	 request);

	/**
	 * 单个查询抢购活动表API
	 *
	 * @author xufeng
	 * @param flashSaleGoodsByActivityIdRequest 单个查询抢购商品表请求参数 {@link FlashSaleGoodsByActivityIdRequest}
	 * @return 抢购商品表详情 {@link FlashSaleGoodsByActivityIdRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/get-by-activityid")
	BaseResponse<FlashSaleGoodsByActivityIdResponse> getByActivityId(@RequestBody @Valid FlashSaleGoodsByActivityIdRequest flashSaleGoodsByActivityIdRequest);

	/**
	 * 商品是否正在抢购API
	 *
	 * @author xufeng
	 * @param promotionIsInProgress 单个查询抢购商品表请求参数 {@link IsInProgressReq}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/promotionIsInProgress")
	BaseResponse<IsInProgressResp> promotionIsInProgress(@RequestBody @Valid IsInProgressReq promotionIsInProgress);

	/**
	 * 互斥验证
	 *
	 * @author dyt
	 * @param request 互斥请求参数 {@link FlashSaleGoodsValidateRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/flashsalegoods/validate")
	BaseResponse validate(@RequestBody @Valid FlashSaleGoodsValidateRequest request);
}

