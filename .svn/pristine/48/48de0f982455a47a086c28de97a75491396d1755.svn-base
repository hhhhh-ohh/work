package com.wanmi.sbc.vas.provider.impl.recommend.recommendpositionconfiguration;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendpositionconfiguration.RecommendPositionConfigurationQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationPageRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationQueryRequest;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationPageResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendPositionConfigurationVO;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.model.root.RecommendPositionConfiguration;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.service.RecommendPositionConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>推荐坑位设置查询服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:14:13
 */
@RestController
@Validated
public class RecommendPositionConfigurationQueryController implements RecommendPositionConfigurationQueryProvider {
	@Autowired
	private RecommendPositionConfigurationService recommendPositionConfigurationService;

	@Override
	public BaseResponse<RecommendPositionConfigurationPageResponse> page(@RequestBody @Valid RecommendPositionConfigurationPageRequest recommendPositionConfigurationPageReq) {
		RecommendPositionConfigurationQueryRequest queryReq = KsBeanUtil.convert(recommendPositionConfigurationPageReq, RecommendPositionConfigurationQueryRequest.class);
		Page<RecommendPositionConfiguration> recommendPositionConfigurationPage = recommendPositionConfigurationService.page(queryReq);
		Page<RecommendPositionConfigurationVO> newPage = recommendPositionConfigurationPage.map(entity -> recommendPositionConfigurationService.wrapperVo(entity));
		MicroServicePage<RecommendPositionConfigurationVO> microPage = new MicroServicePage<>(newPage, recommendPositionConfigurationPageReq.getPageable());
		RecommendPositionConfigurationPageResponse finalRes = new RecommendPositionConfigurationPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<RecommendPositionConfigurationListResponse> list(@RequestBody @Valid RecommendPositionConfigurationListRequest recommendPositionConfigurationListReq) {
		RecommendPositionConfigurationQueryRequest queryReq = KsBeanUtil.convert(recommendPositionConfigurationListReq, RecommendPositionConfigurationQueryRequest.class);
		List<RecommendPositionConfiguration> recommendPositionConfigurationList = recommendPositionConfigurationService.list(queryReq);
		List<RecommendPositionConfigurationVO> newList = recommendPositionConfigurationList.stream().map(entity -> recommendPositionConfigurationService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new RecommendPositionConfigurationListResponse(newList));
	}

	@Override
	public BaseResponse<RecommendPositionConfigurationByIdResponse> getById(@RequestBody @Valid RecommendPositionConfigurationByIdRequest recommendPositionConfigurationByIdRequest) {
		RecommendPositionConfiguration recommendPositionConfiguration =
		recommendPositionConfigurationService.getOne(recommendPositionConfigurationByIdRequest.getId());
		return BaseResponse.success(new RecommendPositionConfigurationByIdResponse(recommendPositionConfigurationService.wrapperVo(recommendPositionConfiguration)));
	}

}

