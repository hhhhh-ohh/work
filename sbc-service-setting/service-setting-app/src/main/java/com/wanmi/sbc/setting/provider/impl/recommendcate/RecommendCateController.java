package com.wanmi.sbc.setting.provider.impl.recommendcate;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.request.recommendcate.*;
import com.wanmi.sbc.setting.recommendcate.model.entity.RecommendCateSort;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.recommendcate.RecommendCateProvider;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateAddResponse;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateModifyResponse;
import com.wanmi.sbc.setting.recommendcate.service.RecommendCateService;
import com.wanmi.sbc.setting.recommendcate.model.root.RecommendCate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>笔记分类表保存服务接口实现</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@RestController
@Validated
public class RecommendCateController implements RecommendCateProvider {
	@Autowired
	private RecommendCateService recommendCateService;

	@Override
	public BaseResponse<RecommendCateAddResponse> add(@RequestBody @Valid RecommendCateAddRequest recommendCateAddRequest) {
		RecommendCate recommendCate = KsBeanUtil.convert(recommendCateAddRequest, RecommendCate.class);
		return BaseResponse.success(new RecommendCateAddResponse(
				recommendCateService.wrapperVo(recommendCateService.add(recommendCate))));
	}

	@Override
	public BaseResponse<RecommendCateModifyResponse> modify(@RequestBody @Valid RecommendCateModifyRequest recommendCateModifyRequest) {
		RecommendCate recommendCate = KsBeanUtil.convert(recommendCateModifyRequest, RecommendCate.class);
		return BaseResponse.success(new RecommendCateModifyResponse(
				recommendCateService.wrapperVo(recommendCateService.modify(recommendCate))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid RecommendCateDelByIdRequest recommendCateDelByIdRequest) {
		recommendCateService.deleteById(recommendCateDelByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid RecommendCateDelByIdListRequest recommendCateDelByIdListRequest) {
		recommendCateService.deleteByIdList(recommendCateDelByIdListRequest.getCateIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse dragSort(RecommendCateSortRequest recommendCateSortRequest) {
		if (Objects.isNull(recommendCateSortRequest) || CollectionUtils.isEmpty(recommendCateSortRequest.getRecommendCateSortVOS())) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		List<RecommendCateSort> grouponCateSorts = recommendCateSortRequest.getRecommendCateSortVOS().stream().map(cateSort ->
				recommendCateService.wrapperSortVo(cateSort)).collect(Collectors.toList());
		recommendCateService.dragSort(grouponCateSorts);
		return BaseResponse.SUCCESSFUL();
	}
}

