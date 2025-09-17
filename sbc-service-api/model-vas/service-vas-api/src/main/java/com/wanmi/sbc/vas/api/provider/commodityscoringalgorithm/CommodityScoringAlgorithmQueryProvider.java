package com.wanmi.sbc.vas.api.provider.commodityscoringalgorithm;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmByIdRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmListRequest;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmByIdResponse;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商品评分算法查询服务Provider</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@FeignClient(value = "${application.vas.name}", contextId = "CommodityScoringAlgorithmQueryProvider")
public interface CommodityScoringAlgorithmQueryProvider {

	/**
	 * 列表查询商品评分算法API
	 *
	 * @author Bob
	 * @param commodityScoringAlgorithmListReq 列表请求参数和筛选对象 {@link CommodityScoringAlgorithmListRequest}
	 * @return 商品评分算法的列表信息 {@link CommodityScoringAlgorithmListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/commodityscoringalgorithm/list")
	BaseResponse<CommodityScoringAlgorithmListResponse> list(@RequestBody @Valid CommodityScoringAlgorithmListRequest commodityScoringAlgorithmListReq);

	/**
	 * 单个查询商品评分算法API
	 *
	 * @author Bob
	 * @param commodityScoringAlgorithmByIdRequest 单个查询商品评分算法请求参数 {@link CommodityScoringAlgorithmByIdRequest}
	 * @return 商品评分算法详情 {@link CommodityScoringAlgorithmByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/commodityscoringalgorithm/get-by-id")
	BaseResponse<CommodityScoringAlgorithmByIdResponse> getById(@RequestBody @Valid CommodityScoringAlgorithmByIdRequest commodityScoringAlgorithmByIdRequest);

}

