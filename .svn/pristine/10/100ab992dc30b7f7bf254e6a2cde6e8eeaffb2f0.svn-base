package com.wanmi.sbc.goods.provider.impl.newcomerpurchasegoods;

import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods.NewcomerPurchaseGoodsProvider;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsAddResponse;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsModifyResponse;
import com.wanmi.sbc.goods.newcomerpurchasegoods.service.NewcomerPurchaseGoodsService;
import com.wanmi.sbc.goods.newcomerpurchasegoods.model.root.NewcomerPurchaseGoods;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>新人购商品表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@RestController
@Validated
public class NewcomerPurchaseGoodsController implements NewcomerPurchaseGoodsProvider {
	@Autowired
	private NewcomerPurchaseGoodsService newcomerPurchaseGoodsService;

	@Override
	public BaseResponse<NewcomerPurchaseGoodsAddResponse> add(@RequestBody @Valid NewcomerPurchaseGoodsAddRequest newcomerPurchaseGoodsAddRequest) {
		NewcomerPurchaseGoods newcomerPurchaseGoods = KsBeanUtil.convert(newcomerPurchaseGoodsAddRequest, NewcomerPurchaseGoods.class);
		return BaseResponse.success(new NewcomerPurchaseGoodsAddResponse(
				newcomerPurchaseGoodsService.wrapperVo(newcomerPurchaseGoodsService.add(newcomerPurchaseGoods))));
	}

	@Override
	public BaseResponse<NewcomerPurchaseGoodsModifyResponse> modify(@RequestBody @Valid NewcomerPurchaseGoodsModifyRequest newcomerPurchaseGoodsModifyRequest) {
		NewcomerPurchaseGoods newcomerPurchaseGoods = KsBeanUtil.convert(newcomerPurchaseGoodsModifyRequest, NewcomerPurchaseGoods.class);
		return BaseResponse.success(new NewcomerPurchaseGoodsModifyResponse(
				newcomerPurchaseGoodsService.wrapperVo(newcomerPurchaseGoodsService.modify(newcomerPurchaseGoods))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid NewcomerPurchaseGoodsDelByIdRequest newcomerPurchaseGoodsDelByIdRequest) {
		NewcomerPurchaseGoods newcomerPurchaseGoods = KsBeanUtil.convert(newcomerPurchaseGoodsDelByIdRequest, NewcomerPurchaseGoods.class);
		newcomerPurchaseGoods.setDelFlag(DeleteFlag.YES);
		newcomerPurchaseGoodsService.deleteById(newcomerPurchaseGoods);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid NewcomerPurchaseGoodsDelByIdListRequest newcomerPurchaseGoodsDelByIdListRequest) {
		newcomerPurchaseGoodsService.deleteByIdList(newcomerPurchaseGoodsDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse saveAll(@RequestBody @Valid NewcomerPurchaseGoodsSaveRequest newcomerPurchaseGoodsSaveRequest) {
		newcomerPurchaseGoodsService.saveAll(newcomerPurchaseGoodsSaveRequest.getGoodsInfoIds());
		return BaseResponse.SUCCESSFUL();
	}
}

