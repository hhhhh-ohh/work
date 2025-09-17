package com.wanmi.sbc.vas.provider.impl.commodityscoringalgorithm;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.commodityscoringalgorithm.CommodityScoringAlgorithmProvider;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmAddRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmDelByIdRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmModifyRequest;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmAddResponse;
import com.wanmi.sbc.vas.commodityscoringalgorithm.model.root.CommodityScoringAlgorithm;
import com.wanmi.sbc.vas.commodityscoringalgorithm.service.CommodityScoringAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商品评分算法保存服务接口实现</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@RestController
@Validated
public class CommodityScoringAlgorithmController implements CommodityScoringAlgorithmProvider {
	@Autowired
	private CommodityScoringAlgorithmService commodityScoringAlgorithmService;

	@Override
	public BaseResponse<CommodityScoringAlgorithmAddResponse> add(@RequestBody @Valid CommodityScoringAlgorithmAddRequest commodityScoringAlgorithmAddRequest) {
		CommodityScoringAlgorithm commodityScoringAlgorithm = KsBeanUtil.convert(commodityScoringAlgorithmAddRequest, CommodityScoringAlgorithm.class);
		return BaseResponse.success(new CommodityScoringAlgorithmAddResponse(
				commodityScoringAlgorithmService.wrapperVo(commodityScoringAlgorithmService.add(commodityScoringAlgorithm))));
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid List<CommodityScoringAlgorithmModifyRequest> commodityScoringAlgorithmModifyRequest) {
		List<CommodityScoringAlgorithm> commodityScoringAlgorithms = new ArrayList<>();
		commodityScoringAlgorithmModifyRequest.forEach(x -> {
			CommodityScoringAlgorithm commodityScoringAlgorithm = KsBeanUtil.convert(x,
					CommodityScoringAlgorithm.class);
			commodityScoringAlgorithms.add(commodityScoringAlgorithm);
		});
		commodityScoringAlgorithmService.modify(commodityScoringAlgorithms);
		return BaseResponse.SUCCESSFUL();

	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid CommodityScoringAlgorithmDelByIdRequest commodityScoringAlgorithmDelByIdRequest) {
		CommodityScoringAlgorithm commodityScoringAlgorithm = KsBeanUtil.convert(commodityScoringAlgorithmDelByIdRequest, CommodityScoringAlgorithm.class);
		commodityScoringAlgorithm.setDelFlag(DeleteFlag.YES);
		commodityScoringAlgorithmService.deleteById(commodityScoringAlgorithm);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid CommodityScoringAlgorithmDelByIdListRequest commodityScoringAlgorithmDelByIdListRequest) {
		List<CommodityScoringAlgorithm> commodityScoringAlgorithmList = commodityScoringAlgorithmDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				CommodityScoringAlgorithm commodityScoringAlgorithm = KsBeanUtil.convert(Id, CommodityScoringAlgorithm.class);
				commodityScoringAlgorithm.setDelFlag(DeleteFlag.YES);
				return commodityScoringAlgorithm;
			}).collect(Collectors.toList());
		commodityScoringAlgorithmService.deleteByIdList(commodityScoringAlgorithmList);
		return BaseResponse.SUCCESSFUL();
	}

}

