package com.wanmi.sbc.vas.provider.impl.recommend.recommendcatemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendcatemanage.RecommendCateManageProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageModifyResponse;
import com.wanmi.sbc.vas.recommend.recommendcatemanage.model.root.RecommendCateManage;
import com.wanmi.sbc.vas.recommend.recommendcatemanage.service.RecommendCateManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>分类推荐管理保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@RestController
@Validated
public class RecommendCateManageController implements RecommendCateManageProvider {
	@Autowired
	private RecommendCateManageService recommendCateManageService;

	@Override
	public BaseResponse<RecommendCateManageAddResponse> add(@RequestBody @Valid RecommendCateManageAddRequest recommendCateManageAddRequest) {
		RecommendCateManage recommendCateManage = KsBeanUtil.convert(recommendCateManageAddRequest, RecommendCateManage.class);
		return BaseResponse.success(new RecommendCateManageAddResponse(
				recommendCateManageService.wrapperVo(recommendCateManageService.add(recommendCateManage))));
	}

	@Override
	public BaseResponse addList(@RequestBody @Valid RecommendCateManageAddListRequest recommendCateManageAddListRequest) {
		recommendCateManageService.addList(recommendCateManageAddListRequest);
		return BaseResponse.SUCCESSFUL();
	}


	@Override
	public BaseResponse<RecommendCateManageModifyResponse> modify(@RequestBody @Valid RecommendCateManageModifyRequest recommendCateManageModifyRequest) {
		RecommendCateManage recommendCateManage = KsBeanUtil.convert(recommendCateManageModifyRequest, RecommendCateManage.class);
		return BaseResponse.success(new RecommendCateManageModifyResponse(
				recommendCateManageService.wrapperVo(recommendCateManageService.modify(recommendCateManage))));
	}

	@Override
	public BaseResponse updateCateWeight(@Valid RecommendCateManageUpdateWeightRequest request) {
		recommendCateManageService.updateCateWeight(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateCateNoPushType(@Valid RecommendCateManageUpdateNoPushTypeRequest request) {
		recommendCateManageService.updateCateNoPushType(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid RecommendCateManageDelByIdRequest recommendCateManageDelByIdRequest) {
		recommendCateManageService.deleteById(recommendCateManageDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid RecommendCateManageDelByIdListRequest recommendCateManageDelByIdListRequest) {
		recommendCateManageService.deleteByIdList(recommendCateManageDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

