package com.wanmi.sbc.vas.api.provider.recommend.recommendpositionconfiguration;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.RecommendPositionConfigurationModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>推荐坑位设置保存服务Provider</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:04:35
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendPositionConfigurationProvider")
public interface RecommendPositionConfigurationProvider {

	/**
	 * 新增推荐坑位设置API
	 *
	 * @author lvzhenwei
	 * @param recommendPositionConfigurationAddRequest 推荐坑位设置新增参数结构 {@link RecommendPositionConfigurationAddRequest}
	 * @return 新增的推荐坑位设置信息 {@link RecommendPositionConfigurationAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendpositionconfiguration/add")
    BaseResponse<RecommendPositionConfigurationAddResponse> add(@RequestBody @Valid RecommendPositionConfigurationAddRequest recommendPositionConfigurationAddRequest);

	/**
	 * 修改推荐坑位设置API
	 *
	 * @author lvzhenwei
	 * @param recommendPositionConfigurationModifyRequest 推荐坑位设置修改参数结构 {@link RecommendPositionConfigurationModifyRequest}
	 * @return 修改的推荐坑位设置信息 {@link RecommendPositionConfigurationModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendpositionconfiguration/modify")
    BaseResponse<RecommendPositionConfigurationModifyResponse> modify(@RequestBody @Valid RecommendPositionConfigurationModifyRequest recommendPositionConfigurationModifyRequest);

	/**
	 * 修改推荐坑位开关设置
	 *
	 * @author lvzhenwei
	 * @param request 修改推荐坑位开关设置参数结构 {@link RecommendPositionConfigurationModifyIsOpenRequest}
	 * @return 修改推荐坑位开关设置信息 {@link RecommendPositionConfigurationModifyIsOpenRequest}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendpositionconfiguration/modify-is-open")
    BaseResponse modifyIsOpen(@RequestBody @Valid RecommendPositionConfigurationModifyIsOpenRequest request);


	/**
	 * 单个删除推荐坑位设置API
	 *
	 * @author lvzhenwei
	 * @param recommendPositionConfigurationDelByIdRequest 单个删除参数结构 {@link RecommendPositionConfigurationDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendpositionconfiguration/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid RecommendPositionConfigurationDelByIdRequest recommendPositionConfigurationDelByIdRequest);

	/**
	 * 批量删除推荐坑位设置API
	 *
	 * @author lvzhenwei
	 * @param recommendPositionConfigurationDelByIdListRequest 批量删除参数结构 {@link RecommendPositionConfigurationDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/recommendpositionconfiguration/delete-by-id-list")
    BaseResponse deleteByIdList(@RequestBody @Valid RecommendPositionConfigurationDelByIdListRequest recommendPositionConfigurationDelByIdListRequest);

}

