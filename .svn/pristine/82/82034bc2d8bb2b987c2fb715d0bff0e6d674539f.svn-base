package com.wanmi.sbc.vas.provider.impl.recommend.recommendpositionconfiguration;

import com.google.common.base.Joiner;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendpositionconfiguration.RecommendPositionConfigurationProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationModifyResponse;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.model.root.RecommendPositionConfiguration;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.service.RecommendPositionConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>推荐坑位设置保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:14:13
 */
@RestController
@Validated
public class RecommendPositionConfigurationController implements RecommendPositionConfigurationProvider {
	@Autowired
	private RecommendPositionConfigurationService recommendPositionConfigurationService;

	@Override
	public BaseResponse<RecommendPositionConfigurationAddResponse> add(@RequestBody @Valid RecommendPositionConfigurationAddRequest recommendPositionConfigurationAddRequest) {
		RecommendPositionConfiguration recommendPositionConfiguration = KsBeanUtil.convert(recommendPositionConfigurationAddRequest, RecommendPositionConfiguration.class);
		return BaseResponse.success(new RecommendPositionConfigurationAddResponse(
				recommendPositionConfigurationService.wrapperVo(recommendPositionConfigurationService.add(recommendPositionConfiguration))));
	}

	@Override
	public BaseResponse<RecommendPositionConfigurationModifyResponse> modify(@RequestBody @Valid RecommendPositionConfigurationModifyRequest recommendPositionConfigurationModifyRequest) {
		RecommendPositionConfiguration recommendPositionConfiguration = KsBeanUtil.convert(recommendPositionConfigurationModifyRequest, RecommendPositionConfiguration.class);
		recommendPositionConfiguration.setContent(Joiner.on(",").join(recommendPositionConfigurationModifyRequest.getContent()));
		return BaseResponse.success(new RecommendPositionConfigurationModifyResponse(
				recommendPositionConfigurationService.wrapperVo(recommendPositionConfigurationService.modify(recommendPositionConfiguration))));
	}

	@Override
	public BaseResponse modifyIsOpen(@Valid RecommendPositionConfigurationModifyIsOpenRequest request) {
		recommendPositionConfigurationService.modifyIsOpen(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid RecommendPositionConfigurationDelByIdRequest recommendPositionConfigurationDelByIdRequest) {
		recommendPositionConfigurationService.deleteById(recommendPositionConfigurationDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid RecommendPositionConfigurationDelByIdListRequest recommendPositionConfigurationDelByIdListRequest) {
		recommendPositionConfigurationService.deleteByIdList(recommendPositionConfigurationDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

