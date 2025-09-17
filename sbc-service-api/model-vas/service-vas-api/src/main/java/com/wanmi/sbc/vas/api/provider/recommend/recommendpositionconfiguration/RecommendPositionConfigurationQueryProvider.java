package com.wanmi.sbc.vas.api.provider.recommend.recommendpositionconfiguration;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationPageRequest;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>推荐坑位设置查询服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:04:35
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendPositionConfigurationQueryProvider")
public interface RecommendPositionConfigurationQueryProvider {

	/**
	 * 分页查询推荐坑位设置API
	 *
	 * @author lvzhenwei
	 * @param recommendPositionConfigurationPageReq 分页请求参数和筛选对象 {@link RecommendPositionConfigurationPageRequest}
	 * @return 推荐坑位设置分页列表信息 {@link RecommendPositionConfigurationPageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendpositionconfiguration/page")
    BaseResponse<RecommendPositionConfigurationPageResponse> page(@RequestBody @Valid RecommendPositionConfigurationPageRequest recommendPositionConfigurationPageReq);

	/**
	 * 列表查询推荐坑位设置API
	 *
	 * @author lvzhenwei
	 * @param recommendPositionConfigurationListReq 列表请求参数和筛选对象 {@link RecommendPositionConfigurationListRequest}
	 * @return 推荐坑位设置的列表信息 {@link RecommendPositionConfigurationListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendpositionconfiguration/list")
    BaseResponse<RecommendPositionConfigurationListResponse> list(@RequestBody @Valid RecommendPositionConfigurationListRequest recommendPositionConfigurationListReq);

	/**
	 * 单个查询推荐坑位设置API
	 *
	 * @author lvzhenwei
	 * @param recommendPositionConfigurationByIdRequest 单个查询推荐坑位设置请求参数 {@link RecommendPositionConfigurationByIdRequest}
	 * @return 推荐坑位设置详情 {@link RecommendPositionConfigurationByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendpositionconfiguration/get-by-id")
    BaseResponse<RecommendPositionConfigurationByIdResponse> getById(@RequestBody @Valid RecommendPositionConfigurationByIdRequest recommendPositionConfigurationByIdRequest);

}

