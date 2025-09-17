package com.wanmi.sbc.setting.provider.impl.recommendcate;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.recommendcate.RecommendCateQueryProvider;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCatePageRequest;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateQueryRequest;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCatePageResponse;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateListRequest;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateListResponse;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateByIdRequest;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateByIdResponse;
import com.wanmi.sbc.setting.bean.vo.RecommendCateVO;
import com.wanmi.sbc.setting.recommendcate.service.RecommendCateService;
import com.wanmi.sbc.setting.recommendcate.model.root.RecommendCate;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>笔记分类表查询服务接口实现</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@RestController
@Validated
public class RecommendCateQueryController implements RecommendCateQueryProvider {
	@Autowired
	private RecommendCateService recommendCateService;

	@Override
	public BaseResponse<RecommendCatePageResponse> page(@RequestBody @Valid RecommendCatePageRequest recommendCatePageReq) {
		RecommendCateQueryRequest queryReq = KsBeanUtil.convert(recommendCatePageReq, RecommendCateQueryRequest.class);
		Page<RecommendCate> recommendCatePage = recommendCateService.page(queryReq);
		Page<RecommendCateVO> newPage = recommendCatePage.map(entity -> recommendCateService.wrapperVo(entity));
		MicroServicePage<RecommendCateVO> microPage = new MicroServicePage<>(newPage, recommendCatePageReq.getPageable());
		RecommendCatePageResponse finalRes = new RecommendCatePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<RecommendCateListResponse> list(@RequestBody @Valid RecommendCateListRequest recommendCateListReq) {
		RecommendCateQueryRequest queryReq = KsBeanUtil.convert(recommendCateListReq, RecommendCateQueryRequest.class);
		if (Objects.nonNull(queryReq)){
			queryReq.putSort("cateSort", "ASC");
		}
		List<RecommendCate> recommendCateList = recommendCateService.list(queryReq);
		List<RecommendCateVO> newList = recommendCateList.stream().map(entity -> recommendCateService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new RecommendCateListResponse(newList));
	}

	@Override
	public BaseResponse<RecommendCateByIdResponse> getById(@RequestBody @Valid RecommendCateByIdRequest recommendCateByIdRequest) {
		RecommendCate recommendCate =
		recommendCateService.getOne(recommendCateByIdRequest.getCateId());
		return BaseResponse.success(new RecommendCateByIdResponse(recommendCateService.wrapperVo(recommendCate)));
	}
}

