package com.wanmi.sbc.goods.api.provider.buycyclegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.buycyclegoods.*;
import com.wanmi.sbc.goods.api.response.buycyclegoods.BuyCycleGoodsAddResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoods.BuyCycleGoodsModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>周期购spu表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@FeignClient(value = "${application.goods.name}", contextId = "BuyCycleGoodsProvider")
public interface BuyCycleGoodsProvider {

	/**
	 * 新增周期购spu表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsAddRequest 周期购spu表新增参数结构 {@link BuyCycleGoodsAddRequest}
	 * @return 新增的周期购spu表信息 {@link BuyCycleGoodsAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/add")
	BaseResponse add(@RequestBody @Valid BuyCycleGoodsAddRequest buyCycleGoodsAddRequest);

	/**
	 * 修改周期购spu表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsModifyRequest 周期购spu表修改参数结构 {@link BuyCycleGoodsModifyRequest}
	 * @return 修改的周期购spu表信息 {@link BuyCycleGoodsModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/modify")
	BaseResponse modify(@RequestBody @Valid BuyCycleGoodsModifyRequest buyCycleGoodsModifyRequest);

	/**
	 * 单个删除周期购spu表API
	 *
	 * @author zhanghao
	 * @param buyCycleGoodsDelByIdRequest 单个删除参数结构 {@link BuyCycleGoodsDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid BuyCycleGoodsDelByIdRequest buyCycleGoodsDelByIdRequest);

	/**
	 * 修改周期购spu表状态API
	 *
	 * @author zhanghao
	 * @param modifyStateRequest 周期购spu表修改参数 {@link BuyCycleGoodsModifyStateRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/buycyclegoods/modify-state")
	BaseResponse modifyState(@RequestBody @Valid BuyCycleGoodsModifyStateRequest modifyStateRequest);

}

