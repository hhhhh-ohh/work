package com.wanmi.sbc.vas.api.provider.commodityscoringalgorithm;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmAddRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmDelByIdRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmModifyRequest;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmAddResponse;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>商品评分算法保存服务Provider</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@FeignClient(value = "${application.vas.name}", contextId = "CommodityScoringAlgorithmProvider")
public interface CommodityScoringAlgorithmProvider {

	/**
	 * 新增商品评分算法API
	 *
	 * @author Bob
	 * @param commodityScoringAlgorithmAddRequest 商品评分算法新增参数结构 {@link CommodityScoringAlgorithmAddRequest}
	 * @return 新增的商品评分算法信息 {@link CommodityScoringAlgorithmAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/commodityscoringalgorithm/add")
	BaseResponse<CommodityScoringAlgorithmAddResponse> add(@RequestBody @Valid CommodityScoringAlgorithmAddRequest commodityScoringAlgorithmAddRequest);

	/**
	 * 修改商品评分算法API
	 *
	 * @author Bob
	 * @param commodityScoringAlgorithmModifyRequest 商品评分算法修改参数结构 {@link CommodityScoringAlgorithmModifyRequest}
	 * @return 修改的商品评分算法信息 {@link CommodityScoringAlgorithmModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/commodityscoringalgorithm/modify")
	BaseResponse<CommodityScoringAlgorithmModifyResponse> modify(@RequestBody @Valid List<CommodityScoringAlgorithmModifyRequest> commodityScoringAlgorithmModifyRequest);

	/**
	 * 单个删除商品评分算法API
	 *
	 * @author Bob
	 * @param commodityScoringAlgorithmDelByIdRequest 单个删除参数结构 {@link CommodityScoringAlgorithmDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/commodityscoringalgorithm/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid CommodityScoringAlgorithmDelByIdRequest commodityScoringAlgorithmDelByIdRequest);

	/**
	 * 批量删除商品评分算法API
	 *
	 * @author Bob
	 * @param commodityScoringAlgorithmDelByIdListRequest 批量删除参数结构 {@link CommodityScoringAlgorithmDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/commodityscoringalgorithm/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid CommodityScoringAlgorithmDelByIdListRequest commodityScoringAlgorithmDelByIdListRequest);

}

