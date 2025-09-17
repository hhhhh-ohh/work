package com.wanmi.sbc.vas.api.provider.recommend.recommendsystemconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigAddRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigDelByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigModifyRequest;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>智能推荐配置保存服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendSystemConfigProvider")
public interface RecommendSystemConfigProvider {

	/**
	 * 新增智能推荐配置API
	 *
	 * @author lvzhenwei
	 * @param recommendSystemConfigAddRequest 智能推荐配置新增参数结构 {@link RecommendSystemConfigAddRequest}
	 * @return 新增的智能推荐配置信息 {@link RecommendSystemConfigAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendsystemconfig/add")
    BaseResponse<RecommendSystemConfigAddResponse> add(@RequestBody @Valid RecommendSystemConfigAddRequest recommendSystemConfigAddRequest);

	/**
	 * 修改智能推荐配置API
	 *
	 * @author lvzhenwei
	 * @param recommendSystemConfigModifyRequest 智能推荐配置修改参数结构 {@link RecommendSystemConfigModifyRequest}
	 * @return 修改的智能推荐配置信息 {@link RecommendSystemConfigModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendsystemconfig/modify")
    BaseResponse<RecommendSystemConfigModifyResponse> modify(@RequestBody @Valid RecommendSystemConfigModifyRequest recommendSystemConfigModifyRequest);

	/**
	 * 单个删除智能推荐配置API
	 *
	 * @author lvzhenwei
	 * @param recommendSystemConfigDelByIdRequest 单个删除参数结构 {@link RecommendSystemConfigDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendsystemconfig/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid RecommendSystemConfigDelByIdRequest recommendSystemConfigDelByIdRequest);

	/**
	 * 批量删除智能推荐配置API
	 *
	 * @author lvzhenwei
	 * @param recommendSystemConfigDelByIdListRequest 批量删除参数结构 {@link RecommendSystemConfigDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendsystemconfig/delete-by-id-list")
    BaseResponse deleteByIdList(@RequestBody @Valid RecommendSystemConfigDelByIdListRequest recommendSystemConfigDelByIdListRequest);

}

