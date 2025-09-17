package com.wanmi.sbc.vas.provider.impl.recommend.recommendsystemconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendsystemconfig.RecommendSystemConfigQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigPageResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendSystemConfigVO;
import com.wanmi.sbc.vas.recommend.recommendsystemconfig.model.root.RecommendSystemConfig;
import com.wanmi.sbc.vas.recommend.recommendsystemconfig.service.RecommendSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>智能推荐配置查询服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@RestController
@Validated
public class RecommendSystemConfigQueryController implements RecommendSystemConfigQueryProvider {
	@Autowired
	private RecommendSystemConfigService recommendSystemConfigService;

	@Override
	public BaseResponse<RecommendSystemConfigPageResponse> page(@RequestBody @Valid RecommendSystemConfigPageRequest recommendSystemConfigPageReq) {
		RecommendSystemConfigQueryRequest queryReq = KsBeanUtil.convert(recommendSystemConfigPageReq, RecommendSystemConfigQueryRequest.class);
		Page<RecommendSystemConfig> recommendSystemConfigPage = recommendSystemConfigService.page(queryReq);
		Page<RecommendSystemConfigVO> newPage = recommendSystemConfigPage.map(entity -> recommendSystemConfigService.wrapperVo(entity));
		MicroServicePage<RecommendSystemConfigVO> microPage = new MicroServicePage<>(newPage, recommendSystemConfigPageReq.getPageable());
		RecommendSystemConfigPageResponse finalRes = new RecommendSystemConfigPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<RecommendSystemConfigListResponse> list(@RequestBody @Valid RecommendSystemConfigListRequest recommendSystemConfigListReq) {
		RecommendSystemConfigQueryRequest queryReq = KsBeanUtil.convert(recommendSystemConfigListReq, RecommendSystemConfigQueryRequest.class);
		List<RecommendSystemConfig> recommendSystemConfigList = recommendSystemConfigService.list(queryReq);
		List<RecommendSystemConfigVO> newList = recommendSystemConfigList.stream().map(entity -> recommendSystemConfigService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new RecommendSystemConfigListResponse(newList));
	}

	@Override
	public BaseResponse<RecommendSystemConfigByIdResponse> getById(@RequestBody @Valid RecommendSystemConfigByIdRequest recommendSystemConfigByIdRequest) {
		RecommendSystemConfig recommendSystemConfig =
		recommendSystemConfigService.getOne(recommendSystemConfigByIdRequest.getId());
		return BaseResponse.success(new RecommendSystemConfigByIdResponse(recommendSystemConfigService.wrapperVo(recommendSystemConfig)));
	}

	@Override
	public BaseResponse<RecommendSystemConfigByIdResponse> getRecommendSystemConfig(@RequestBody @Valid RecommendSystemConfigRequest request) {
		RecommendSystemConfigQueryRequest queryReq = KsBeanUtil.convert(request, RecommendSystemConfigQueryRequest.class);
		RecommendSystemConfig recommendSystemConfig =
				recommendSystemConfigService.getRecommendSystemConfig(queryReq);
		return BaseResponse.success(new RecommendSystemConfigByIdResponse(recommendSystemConfigService.wrapperVo(recommendSystemConfig)));
	}

}

