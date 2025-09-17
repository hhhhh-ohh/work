package com.wanmi.sbc.vas.provider.impl.recommend.caterelatedrecommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.caterelatedrecommend.CateRelatedRecommendProvider;
import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.caterelatedrecommend.CateRelatedRecommendAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.caterelatedrecommend.CateRelatedRecommendModifyResponse;
import com.wanmi.sbc.vas.recommend.caterelatedrecommend.model.root.CateRelatedRecommend;
import com.wanmi.sbc.vas.recommend.caterelatedrecommend.service.CateRelatedRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>分类相关性推荐保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@RestController
@Validated
public class CateRelatedRecommendController implements CateRelatedRecommendProvider {
	@Autowired
	private CateRelatedRecommendService cateRelatedRecommendService;

	@Override
	public BaseResponse<CateRelatedRecommendAddResponse> add(@RequestBody @Valid CateRelatedRecommendAddRequest cateRelatedRecommendAddRequest) {
		CateRelatedRecommend cateRelatedRecommend = KsBeanUtil.convert(cateRelatedRecommendAddRequest, CateRelatedRecommend.class);
		return BaseResponse.success(new CateRelatedRecommendAddResponse(
				cateRelatedRecommendService.wrapperVo(cateRelatedRecommendService.add(cateRelatedRecommend))));
	}

	@Override
	public BaseResponse addList(@Valid CateRelatedRecommendAddListRequest cateRelatedRecommendAddListRequest) {
		cateRelatedRecommendService.addList(cateRelatedRecommendAddListRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<CateRelatedRecommendModifyResponse> modify(@RequestBody @Valid CateRelatedRecommendModifyRequest cateRelatedRecommendModifyRequest) {
		CateRelatedRecommend cateRelatedRecommend = KsBeanUtil.convert(cateRelatedRecommendModifyRequest, CateRelatedRecommend.class);
		return BaseResponse.success(new CateRelatedRecommendModifyResponse(
				cateRelatedRecommendService.wrapperVo(cateRelatedRecommendService.modify(cateRelatedRecommend))));
	}

	@Override
	public BaseResponse updateWeight(@Valid CateRelatedRecommendUpdateWeightRequest request) {
		cateRelatedRecommendService.updateWeight(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid CateRelatedRecommendDelByIdRequest cateRelatedRecommendDelByIdRequest) {
		cateRelatedRecommendService.deleteById(cateRelatedRecommendDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

}

