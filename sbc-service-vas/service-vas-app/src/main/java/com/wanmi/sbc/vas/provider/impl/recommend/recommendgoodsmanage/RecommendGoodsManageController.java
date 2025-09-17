package com.wanmi.sbc.vas.provider.impl.recommend.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendgoodsmanage.RecommendGoodsManageProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageModifyResponse;
import com.wanmi.sbc.vas.recommend.recommendgoodsmanage.model.root.RecommendGoodsManage;
import com.wanmi.sbc.vas.recommend.recommendgoodsmanage.service.RecommendGoodsManageService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>商品推荐管理保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@RestController
@Validated
public class RecommendGoodsManageController implements RecommendGoodsManageProvider {
	@Autowired
	private RecommendGoodsManageService recommendGoodsManageService;

	@Override
	public BaseResponse<RecommendGoodsManageAddResponse> add(@RequestBody @Valid RecommendGoodsManageAddRequest recommendGoodsManageAddRequest) {
		RecommendGoodsManage recommendGoodsManage = KsBeanUtil.convert(recommendGoodsManageAddRequest, RecommendGoodsManage.class);
		return BaseResponse.success(new RecommendGoodsManageAddResponse(
				recommendGoodsManageService.wrapperVo(recommendGoodsManageService.add(recommendGoodsManage))));
	}

	@Override
	public BaseResponse addList(@Valid RecommendGoodsManageAddListRequest recommendGoodsManageAddListRequest) {
		recommendGoodsManageService.addList(recommendGoodsManageAddListRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<RecommendGoodsManageModifyResponse> modify(@RequestBody @Valid RecommendGoodsManageModifyRequest recommendGoodsManageModifyRequest) {
		RecommendGoodsManage recommendGoodsManage = KsBeanUtil.convert(recommendGoodsManageModifyRequest, RecommendGoodsManage.class);
		return BaseResponse.success(new RecommendGoodsManageModifyResponse(
				recommendGoodsManageService.wrapperVo(recommendGoodsManageService.modify(recommendGoodsManage))));
	}

	@Override
	public BaseResponse updateNoPush(@Valid RecommendGoodsManageUpdateNoPushRequest request) {
		if(CollectionUtils.isNotEmpty(request.getIds())){
			recommendGoodsManageService.updateNoPushForIds(request);
		} else {
			recommendGoodsManageService.updateNoPush(request);
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateWeight(@Valid RecommendGoodsManageUpdateWeightRequest request) {
		recommendGoodsManageService.updateWeight(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid RecommendGoodsManageDelByIdRequest recommendGoodsManageDelByIdRequest) {
		recommendGoodsManageService.deleteById(recommendGoodsManageDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid RecommendGoodsManageDelByIdListRequest recommendGoodsManageDelByIdListRequest) {
		recommendGoodsManageService.deleteByIdList(recommendGoodsManageDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

