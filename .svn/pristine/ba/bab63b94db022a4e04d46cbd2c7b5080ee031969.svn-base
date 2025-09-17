package com.wanmi.sbc.vas.provider.impl.recommend.recommendcatemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendcatemanage.RecommendCateManageQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageInfoListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManageListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.RecommendCateManagePageResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendCateManageInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendCateManageVO;
import com.wanmi.sbc.vas.recommend.recommendcatemanage.model.root.RecommendCateManage;
import com.wanmi.sbc.vas.recommend.recommendcatemanage.service.RecommendCateManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>分类推荐管理查询服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@RestController
@Validated
public class RecommendCateManageQueryController implements RecommendCateManageQueryProvider {
	@Autowired
	private RecommendCateManageService recommendCateManageService;

	@Override
	public BaseResponse<RecommendCateManagePageResponse> page(@RequestBody @Valid RecommendCateManagePageRequest recommendCateManagePageReq) {
		RecommendCateManageQueryRequest queryReq = KsBeanUtil.convert(recommendCateManagePageReq, RecommendCateManageQueryRequest.class);
		Page<RecommendCateManage> recommendCateManagePage = recommendCateManageService.page(queryReq);
		Page<RecommendCateManageVO> newPage = recommendCateManagePage.map(entity -> recommendCateManageService.wrapperVo(entity));
		MicroServicePage<RecommendCateManageVO> microPage = new MicroServicePage<>(newPage, recommendCateManagePageReq.getPageable());
		RecommendCateManagePageResponse finalRes = new RecommendCateManagePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<RecommendCateManageListResponse> list(@RequestBody @Valid RecommendCateManageListRequest recommendCateManageListReq) {
		RecommendCateManageQueryRequest queryReq = KsBeanUtil.convert(recommendCateManageListReq, RecommendCateManageQueryRequest.class);
		List<RecommendCateManage> recommendCateManageList = recommendCateManageService.list(queryReq);
		List<RecommendCateManageVO> newList = recommendCateManageList.stream().map(entity -> recommendCateManageService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new RecommendCateManageListResponse(newList));
	}

	@Override
	public BaseResponse<RecommendCateManageInfoListResponse> getRecommendCateInfoList(@RequestBody @Valid RecommendCateManageInfoListRequest recommendCateManageListReq) {
		List<RecommendCateManageInfoVO> recommendCateManageInfoVOList = recommendCateManageService.getRecommendCateInfoList(recommendCateManageListReq);
		return BaseResponse.success(new RecommendCateManageInfoListResponse(recommendCateManageInfoVOList));
	}

	@Override
	public BaseResponse<RecommendCateManageByIdResponse> getById(@RequestBody @Valid RecommendCateManageByIdRequest recommendCateManageByIdRequest) {
		RecommendCateManage recommendCateManage =
		recommendCateManageService.getOne(recommendCateManageByIdRequest.getId());
		return BaseResponse.success(new RecommendCateManageByIdResponse(recommendCateManageService.wrapperVo(recommendCateManage)));
	}

}

