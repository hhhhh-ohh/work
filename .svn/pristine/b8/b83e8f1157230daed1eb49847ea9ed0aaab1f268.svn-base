package com.wanmi.sbc.vas.provider.impl.commodityscoringalgorithm;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.commodityscoringalgorithm.CommodityScoringAlgorithmQueryProvider;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmByIdRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmListRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmQueryRequest;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmByIdResponse;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmListResponse;
import com.wanmi.sbc.vas.bean.vo.CommodityScoringAlgorithmVO;
import com.wanmi.sbc.vas.commodityscoringalgorithm.model.root.CommodityScoringAlgorithm;
import com.wanmi.sbc.vas.commodityscoringalgorithm.service.CommodityScoringAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商品评分算法查询服务接口实现</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@RestController
@Validated
public class CommodityScoringAlgorithmQueryController implements CommodityScoringAlgorithmQueryProvider {
	@Autowired
	private CommodityScoringAlgorithmService commodityScoringAlgorithmService;

	@Override
	public BaseResponse<CommodityScoringAlgorithmListResponse> list(@RequestBody @Valid CommodityScoringAlgorithmListRequest commodityScoringAlgorithmListReq) {
		CommodityScoringAlgorithmQueryRequest queryReq = KsBeanUtil.convert(commodityScoringAlgorithmListReq, CommodityScoringAlgorithmQueryRequest.class);
		List<CommodityScoringAlgorithm> commodityScoringAlgorithmList = commodityScoringAlgorithmService.list(queryReq);
		List<CommodityScoringAlgorithmVO> newList = commodityScoringAlgorithmList.stream().map(entity -> commodityScoringAlgorithmService.wrapperVo(entity)).collect(Collectors.toList());
		// 标签ID是冗余字段。ID值是初始化数据，暂且写死
		return BaseResponse.success(CommodityScoringAlgorithmListResponse.builder().commodityScoringAlgorithmVOList(newList).build());
	}

	@Override
	public BaseResponse<CommodityScoringAlgorithmByIdResponse> getById(@RequestBody @Valid CommodityScoringAlgorithmByIdRequest commodityScoringAlgorithmByIdRequest) {
		CommodityScoringAlgorithm commodityScoringAlgorithm =
		commodityScoringAlgorithmService.getOne(commodityScoringAlgorithmByIdRequest.getId());
		return BaseResponse.success(new CommodityScoringAlgorithmByIdResponse(commodityScoringAlgorithmService.wrapperVo(commodityScoringAlgorithm)));
	}

}

