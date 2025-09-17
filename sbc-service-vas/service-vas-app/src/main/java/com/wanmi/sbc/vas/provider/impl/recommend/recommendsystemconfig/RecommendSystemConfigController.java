package com.wanmi.sbc.vas.provider.impl.recommend.recommendsystemconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendsystemconfig.RecommendSystemConfigProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigAddRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigDelByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigModifyRequest;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigModifyResponse;
import com.wanmi.sbc.vas.recommend.recommendsystemconfig.model.root.RecommendSystemConfig;
import com.wanmi.sbc.vas.recommend.recommendsystemconfig.service.RecommendSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>智能推荐配置保存服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@RestController
@Validated
public class RecommendSystemConfigController implements RecommendSystemConfigProvider {
	@Autowired
	private RecommendSystemConfigService recommendSystemConfigService;

	@Override
	public BaseResponse<RecommendSystemConfigAddResponse> add(@RequestBody @Valid RecommendSystemConfigAddRequest recommendSystemConfigAddRequest) {
		RecommendSystemConfig recommendSystemConfig = KsBeanUtil.convert(recommendSystemConfigAddRequest, RecommendSystemConfig.class);
		return BaseResponse.success(new RecommendSystemConfigAddResponse(
				recommendSystemConfigService.wrapperVo(recommendSystemConfigService.add(recommendSystemConfig))));
	}

	@Override
	public BaseResponse<RecommendSystemConfigModifyResponse> modify(@RequestBody @Valid RecommendSystemConfigModifyRequest recommendSystemConfigModifyRequest) {
		RecommendSystemConfig recommendSystemConfig = KsBeanUtil.convert(recommendSystemConfigModifyRequest, RecommendSystemConfig.class);
		return BaseResponse.success(new RecommendSystemConfigModifyResponse(
				recommendSystemConfigService.wrapperVo(recommendSystemConfigService.modify(recommendSystemConfig))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid RecommendSystemConfigDelByIdRequest recommendSystemConfigDelByIdRequest) {
		RecommendSystemConfig recommendSystemConfig = KsBeanUtil.convert(recommendSystemConfigDelByIdRequest, RecommendSystemConfig.class);
		recommendSystemConfig.setDelFlag(DeleteFlag.YES);
		recommendSystemConfigService.deleteById(recommendSystemConfig);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid RecommendSystemConfigDelByIdListRequest recommendSystemConfigDelByIdListRequest) {
		List<RecommendSystemConfig> recommendSystemConfigList = recommendSystemConfigDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				RecommendSystemConfig recommendSystemConfig = KsBeanUtil.convert(Id, RecommendSystemConfig.class);
				recommendSystemConfig.setDelFlag(DeleteFlag.YES);
				return recommendSystemConfig;
			}).collect(Collectors.toList());
		recommendSystemConfigService.deleteByIdList(recommendSystemConfigList);
		return BaseResponse.SUCCESSFUL();
	}

}

