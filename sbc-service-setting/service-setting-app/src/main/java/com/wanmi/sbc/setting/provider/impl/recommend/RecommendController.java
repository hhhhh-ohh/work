package com.wanmi.sbc.setting.provider.impl.recommend;

import com.wanmi.sbc.setting.api.request.recommend.*;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByIdRequest;
import com.wanmi.sbc.setting.api.response.recommend.RecommendAddResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.recommend.RecommendProvider;
import com.wanmi.sbc.setting.api.response.recommend.RecommendModifyResponse;
import com.wanmi.sbc.setting.recommend.service.RecommendService;
import com.wanmi.sbc.setting.recommend.model.root.Recommend;
import jakarta.validation.Valid;

/**
 * <p>种草信息表保存服务接口实现</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@RestController
@Validated
public class RecommendController implements RecommendProvider {
	@Autowired
	private RecommendService recommendService;

	@Override
	public BaseResponse<RecommendAddResponse> add(@RequestBody @Valid RecommendAddRequest recommendAddRequest) {
		Recommend recommend = KsBeanUtil.convert(recommendAddRequest, Recommend.class);
		return BaseResponse.success(new RecommendAddResponse(
				recommendService.wrapperVo(recommendService.add(recommend,recommendAddRequest.getUserId()))));
	}

	@Override
	public BaseResponse updateTop(RecommendByIdRequest request) {
		recommendService.updateTop(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateStatus(RecommendByIdRequest request) {
		recommendService.updateStatus(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse delById(RecommendByIdRequest request) {
		recommendService.delById(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<RecommendModifyResponse> modify(@RequestBody @Valid RecommendModifyRequest recommendModifyRequest) {
		Recommend recommend = KsBeanUtil.convert(recommendModifyRequest, Recommend.class);
		return BaseResponse.success(new RecommendModifyResponse(
				recommendService.wrapperVo(recommendService.modify(recommend))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid RecommendDelByIdRequest recommendDelByIdRequest) {
		Recommend recommend = KsBeanUtil.convert(recommendDelByIdRequest, Recommend.class);
		recommend.setDelFlag(DeleteFlag.YES);
		recommendService.deleteById(recommend);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid RecommendDelByIdListRequest recommendDelByIdListRequest) {
		recommendService.deleteByIdList(recommendDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse recommendSync(@RequestBody @Valid RecommendByPageCodeRequest recommendByPageCodeRequest) {
		recommendService.recommendSync(recommendByPageCodeRequest.getPageCode());
		return BaseResponse.SUCCESSFUL();
	}

}

