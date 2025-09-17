package com.wanmi.sbc.setting.provider.impl.recommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.recommend.RecommendQueryProvider;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByIdRequest;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByPageCodeRequest;
import com.wanmi.sbc.setting.api.request.recommend.RecommendListRequest;
import com.wanmi.sbc.setting.api.request.recommend.RecommendPageRequest;
import com.wanmi.sbc.setting.api.request.recommend.RecommendQueryRequest;
import com.wanmi.sbc.setting.api.response.recommend.RecommendByIdResponse;
import com.wanmi.sbc.setting.api.response.recommend.RecommendListResponse;
import com.wanmi.sbc.setting.api.response.recommend.RecommendPageResponse;
import com.wanmi.sbc.setting.bean.vo.RecommendVO;
import com.wanmi.sbc.setting.recommend.model.root.Recommend;
import com.wanmi.sbc.setting.recommend.service.RecommendService;
import com.wanmi.sbc.setting.recommendcate.model.root.RecommendCate;
import com.wanmi.sbc.setting.recommendcate.repository.RecommendCateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>种草信息表查询服务接口实现</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@RestController
@Validated
public class RecommendQueryController implements RecommendQueryProvider {
	@Autowired
	private RecommendService recommendService;

	@Autowired
	private RecommendCateRepository recommendCateRepository;

	@Override
	public BaseResponse<RecommendPageResponse> page(@RequestBody @Valid RecommendPageRequest recommendPageReq) {
		RecommendQueryRequest queryReq = new RecommendQueryRequest();
		KsBeanUtil.copyPropertiesThird(recommendPageReq, queryReq);
		Page<Recommend> recommendPage = recommendService.page(queryReq);
		Page<RecommendVO> newPage = recommendPage.map(entity -> recommendService.wrapperVo(entity,
				recommendPageReq.getUserId(), Objects.nonNull(recommendPageReq.getIsTop())));
		Set<Long> cateIds = new HashSet<>();
		newPage.getContent().forEach(v->{
			cateIds.add(v.getCateId());
			cateIds.add(v.getNewCateId());
		});
		Map<Long, String> map = recommendCateRepository
				.findAllById(cateIds)
				.stream()
				.collect(Collectors.toMap(RecommendCate::getCateId, RecommendCate::getCateName));
		newPage.getContent().forEach(v->{
			v.setCateName(map.get(v.getCateId()));
			v.setNewCateName(map.get(v.getNewCateId()));
		});
		MicroServicePage<RecommendVO> microPage = new MicroServicePage<>(newPage, recommendPageReq.getPageable());
		RecommendPageResponse finalRes = new RecommendPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<RecommendListResponse> list(@RequestBody @Valid RecommendListRequest recommendListReq) {
		RecommendQueryRequest queryReq = KsBeanUtil.convert(recommendListReq, RecommendQueryRequest.class);
		List<Recommend> recommendList = recommendService.list(queryReq);
		List<RecommendVO> newList = recommendList.stream().map(entity -> recommendService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new RecommendListResponse(newList));
	}

	@Override
	public BaseResponse<RecommendByIdResponse> getById(@RequestBody @Valid RecommendByIdRequest recommendByIdRequest) {
		Recommend recommend = recommendService.getOne(recommendByIdRequest.getId());
		return BaseResponse.success(new RecommendByIdResponse(recommendService.wrapperVo(recommend)));
	}

	@Override
	public BaseResponse<RecommendByIdResponse> getByPageCode(@RequestBody @Valid RecommendByPageCodeRequest recommendByPageCodeRequest) {
		Recommend recommend = recommendService.getByPageCode(recommendByPageCodeRequest.getPageCode());
		if(Objects.isNull(recommend.getStatus()) || Constants.ZERO == recommend.getStatus()){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "种草内容不存在");
		}
		if(Objects.isNull(recommend.getSaveStatus()) || Constants.ONE == recommend.getSaveStatus()){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "种草内容不存在");
		}
		return BaseResponse.success(new RecommendByIdResponse(recommendService.wrapperVo(recommend,
				recommendByPageCodeRequest.getUserId(), true)));
	}
}

