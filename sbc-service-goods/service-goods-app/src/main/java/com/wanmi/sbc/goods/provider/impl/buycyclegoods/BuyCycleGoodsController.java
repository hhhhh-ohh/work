package com.wanmi.sbc.goods.provider.impl.buycyclegoods;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.request.buycyclegoods.*;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsProvider;
import com.wanmi.sbc.goods.buycyclegoods.service.BuyCycleGoodsService;
import com.wanmi.sbc.goods.buycyclegoods.model.root.BuyCycleGoods;

import java.util.Objects;
import jakarta.validation.Valid;

/**
 * <p>周期购spu表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@RestController
@Validated
public class BuyCycleGoodsController implements BuyCycleGoodsProvider {
	@Autowired
	private BuyCycleGoodsService buyCycleGoodsService;

	@Override
	public BaseResponse add(@RequestBody @Valid BuyCycleGoodsAddRequest buyCycleGoodsAddRequest) {
		BuyCycleGoods buyCycleGoods = buyCycleGoodsService.findByGoodsIdAndCycleStateAndDelFlag(buyCycleGoodsAddRequest.getGoodsId());
		if (Objects.nonNull(buyCycleGoods)) {
			throw new SbcRuntimeException(OrderErrorCodeEnum.K050158);
		}
		buyCycleGoodsService.add(buyCycleGoodsAddRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid BuyCycleGoodsModifyRequest buyCycleGoodsModifyRequest) {
		buyCycleGoodsService.modify(buyCycleGoodsModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid BuyCycleGoodsDelByIdRequest buyCycleGoodsDelByIdRequest) {
		buyCycleGoodsService.deleteById(buyCycleGoodsDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyState(@RequestBody @Valid BuyCycleGoodsModifyStateRequest modifyStateRequest) {
		buyCycleGoodsService.modifyState(modifyStateRequest.getId(),modifyStateRequest.getCycleState());
		return BaseResponse.SUCCESSFUL();
	}

}

