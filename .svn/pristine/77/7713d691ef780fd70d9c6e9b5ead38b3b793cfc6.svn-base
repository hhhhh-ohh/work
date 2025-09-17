package com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoAddRequest;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoAddResponse;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoModifyRequest;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoModifyResponse;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoDelByIdRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>周期购sku表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@FeignClient(value = "${application.goods.name}", contextId = "BuyCycleGoodsInfoProvider")
public interface BuyCycleGoodsInfoProvider {

	/**
	 * 新增周期购sku表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsInfoAddRequest 周期购sku表新增参数结构 {@link BuyCycleGoodsInfoAddRequest}
	 * @return 新增的周期购sku表信息 {@link BuyCycleGoodsInfoAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/add")
	BaseResponse<BuyCycleGoodsInfoAddResponse> add(@RequestBody @Valid BuyCycleGoodsInfoAddRequest buyCycleGoodsInfoAddRequest);

	/**
	 * 修改周期购sku表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsInfoModifyRequest 周期购sku表修改参数结构 {@link BuyCycleGoodsInfoModifyRequest}
	 * @return 修改的周期购sku表信息 {@link BuyCycleGoodsInfoModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/modify")
	BaseResponse<BuyCycleGoodsInfoModifyResponse> modify(@RequestBody @Valid BuyCycleGoodsInfoModifyRequest buyCycleGoodsInfoModifyRequest);

	/**
	 * 单个删除周期购sku表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsInfoDelByIdRequest 单个删除参数结构 {@link BuyCycleGoodsInfoDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid BuyCycleGoodsInfoDelByIdRequest buyCycleGoodsInfoDelByIdRequest);

	/**
	 * 批量删除周期购sku表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsInfoDelByIdListRequest 批量删除参数结构 {@link BuyCycleGoodsInfoDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoodsinfo/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid BuyCycleGoodsInfoDelByIdListRequest buyCycleGoodsInfoDelByIdListRequest);

}

