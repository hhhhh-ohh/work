package com.wanmi.sbc.vas.provider.impl.recommend.manualrecommendgoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.manualrecommendgoods.ManualRecommendGoodsProvider;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.*;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsModifyResponse;
import com.wanmi.sbc.vas.recommend.manualrecommendgoods.model.root.ManualRecommendGoods;
import com.wanmi.sbc.vas.recommend.manualrecommendgoods.service.ManualRecommendGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>手动推荐商品管理保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@RestController
@Validated
public class ManualRecommendGoodsController implements ManualRecommendGoodsProvider {
	@Autowired
	private ManualRecommendGoodsService manualRecommendGoodsService;

	@Override
	public BaseResponse<ManualRecommendGoodsAddResponse> add(@RequestBody @Valid ManualRecommendGoodsAddRequest manualRecommendGoodsAddRequest) {
		ManualRecommendGoods manualRecommendGoods = KsBeanUtil.convert(manualRecommendGoodsAddRequest, ManualRecommendGoods.class);
		return BaseResponse.success(new ManualRecommendGoodsAddResponse(
				manualRecommendGoodsService.wrapperVo(manualRecommendGoodsService.add(manualRecommendGoods))));
	}

	@Override
	public BaseResponse addList(@Valid ManualRecommendGoodsAddListRequest manualRecommendGoodsAddListRequest) {
		manualRecommendGoodsService.addList(manualRecommendGoodsAddListRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<ManualRecommendGoodsModifyResponse> modify(@RequestBody @Valid ManualRecommendGoodsModifyRequest manualRecommendGoodsModifyRequest) {
		ManualRecommendGoods manualRecommendGoods = KsBeanUtil.convert(manualRecommendGoodsModifyRequest, ManualRecommendGoods.class);
		return BaseResponse.success(new ManualRecommendGoodsModifyResponse(
				manualRecommendGoodsService.wrapperVo(manualRecommendGoodsService.modify(manualRecommendGoods))));
	}

	@Override
	public BaseResponse updateWeight(@Valid ManualRecommendGoodsUpdateWeightRequest request) {
		manualRecommendGoodsService.updateWeight(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid ManualRecommendGoodsDelByIdRequest manualRecommendGoodsDelByIdRequest) {
		manualRecommendGoodsService.deleteById(manualRecommendGoodsDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid ManualRecommendGoodsDelByIdListRequest manualRecommendGoodsDelByIdListRequest) {
		manualRecommendGoodsService.deleteByIdList(manualRecommendGoodsDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

