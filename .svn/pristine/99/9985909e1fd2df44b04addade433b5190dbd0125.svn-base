package com.wanmi.sbc.vas.provider.impl.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.goodsrelatedrecommend.GoodsRelatedRecommendProvider;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendModifyResponse;
import com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.model.root.GoodsRelatedRecommend;
import com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.service.GoodsRelatedRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>商品相关性推荐保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@RestController
@Validated
public class GoodsRelatedRecommendController implements GoodsRelatedRecommendProvider {
	@Autowired
	private GoodsRelatedRecommendService goodsRelatedRecommendService;

	@Override
	public BaseResponse<GoodsRelatedRecommendAddResponse> add(@RequestBody @Valid GoodsRelatedRecommendAddRequest goodsRelatedRecommendAddRequest) {
		GoodsRelatedRecommend goodsRelatedRecommend = KsBeanUtil.convert(goodsRelatedRecommendAddRequest, GoodsRelatedRecommend.class);
		return BaseResponse.success(new GoodsRelatedRecommendAddResponse(
				goodsRelatedRecommendService.wrapperVo(goodsRelatedRecommendService.add(goodsRelatedRecommend))));
	}

	@Override
	public BaseResponse addList(@Valid GoodsRelatedRecommendAddListRequest goodsRelatedRecommendAddListRequest) {
		goodsRelatedRecommendService.addList(goodsRelatedRecommendAddListRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<GoodsRelatedRecommendModifyResponse> modify(@RequestBody @Valid GoodsRelatedRecommendModifyRequest goodsRelatedRecommendModifyRequest) {
		GoodsRelatedRecommend goodsRelatedRecommend = KsBeanUtil.convert(goodsRelatedRecommendModifyRequest, GoodsRelatedRecommend.class);
		return BaseResponse.success(new GoodsRelatedRecommendModifyResponse(
				goodsRelatedRecommendService.wrapperVo(goodsRelatedRecommendService.modify(goodsRelatedRecommend))));
	}

	@Override
	public BaseResponse updateWeight(@RequestBody @Valid GoodsRelatedRecommendUpdateWeightRequest request) {
		goodsRelatedRecommendService.updateWeight(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid GoodsRelatedRecommendDelByIdRequest goodsRelatedRecommendDelByIdRequest) {
		goodsRelatedRecommendService.deleteById(goodsRelatedRecommendDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

}

