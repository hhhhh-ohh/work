package com.wanmi.sbc.vas.api.provider.recommend.recommendsystemconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigPageRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigRequest;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>智能推荐配置查询服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendSystemConfigQueryProvider")
public interface RecommendSystemConfigQueryProvider {

	/**
	 * 分页查询智能推荐配置API
	 *
	 * @author lvzhenwei
	 * @param recommendSystemConfigPageReq 分页请求参数和筛选对象 {@link RecommendSystemConfigPageRequest}
	 * @return 智能推荐配置分页列表信息 {@link RecommendSystemConfigPageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendsystemconfig/page")
    BaseResponse<RecommendSystemConfigPageResponse> page(@RequestBody @Valid RecommendSystemConfigPageRequest recommendSystemConfigPageReq);

	/**
	 * 列表查询智能推荐配置API
	 *
	 * @author lvzhenwei
	 * @param recommendSystemConfigListReq 列表请求参数和筛选对象 {@link RecommendSystemConfigListRequest}
	 * @return 智能推荐配置的列表信息 {@link RecommendSystemConfigListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendsystemconfig/list")
    BaseResponse<RecommendSystemConfigListResponse> list(@RequestBody @Valid RecommendSystemConfigListRequest recommendSystemConfigListReq);

	/**
	 * 单个查询智能推荐配置API
	 *
	 * @author lvzhenwei
	 * @param recommendSystemConfigByIdRequest 单个查询智能推荐配置请求参数 {@link RecommendSystemConfigByIdRequest}
	 * @return 智能推荐配置详情 {@link RecommendSystemConfigByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendsystemconfig/get-by-id")
    BaseResponse<RecommendSystemConfigByIdResponse> getById(@RequestBody @Valid RecommendSystemConfigByIdRequest recommendSystemConfigByIdRequest);

	/**
	 * 单个查询智能推荐配置API
	 *
	 * @author lvzhenwei
	 * @param request 单个查询智能推荐配置请求参数 {@link RecommendSystemConfigRequest}
	 * @return 智能推荐配置详情 {@link RecommendSystemConfigByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendsystemconfig/get-recommend-system-config")
    BaseResponse<RecommendSystemConfigByIdResponse> getRecommendSystemConfig(@RequestBody @Valid RecommendSystemConfigRequest request);

}

