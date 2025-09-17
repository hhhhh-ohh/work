package com.wanmi.sbc.vas.provider.impl.recommend.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendgoodsmanage.RecommendGoodsManageQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.RecommendGoodsManageByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.RecommendGoodsManageListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.RecommendGoodsManagePageRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.RecommendGoodsManageQueryRequest;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageInfoListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManageListResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.RecommendGoodsManagePageResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendGoodsManageInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendGoodsManageVO;
import com.wanmi.sbc.vas.recommend.recommendgoodsmanage.model.root.RecommendGoodsManage;
import com.wanmi.sbc.vas.recommend.recommendgoodsmanage.service.RecommendGoodsManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商品推荐管理查询服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@RestController
@Validated
public class RecommendGoodsManageQueryController implements RecommendGoodsManageQueryProvider {
	@Autowired
	private RecommendGoodsManageService recommendGoodsManageService;

	@Override
	public BaseResponse<RecommendGoodsManagePageResponse> page(@RequestBody @Valid RecommendGoodsManagePageRequest recommendGoodsManagePageReq) {
		RecommendGoodsManageQueryRequest queryReq = KsBeanUtil.convert(recommendGoodsManagePageReq, RecommendGoodsManageQueryRequest.class);
		Page<RecommendGoodsManage> recommendGoodsManagePage = recommendGoodsManageService.page(queryReq);
		Page<RecommendGoodsManageVO> newPage = recommendGoodsManagePage.map(entity -> recommendGoodsManageService.wrapperVo(entity));
		MicroServicePage<RecommendGoodsManageVO> microPage = new MicroServicePage<>(newPage, recommendGoodsManagePageReq.getPageable());
		RecommendGoodsManagePageResponse finalRes = new RecommendGoodsManagePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<RecommendGoodsManageListResponse> list(@RequestBody @Valid RecommendGoodsManageListRequest recommendGoodsManageListReq) {
		RecommendGoodsManageQueryRequest queryReq = KsBeanUtil.convert(recommendGoodsManageListReq, RecommendGoodsManageQueryRequest.class);
		List<RecommendGoodsManage> recommendGoodsManageList = recommendGoodsManageService.list(queryReq);
		List<RecommendGoodsManageVO> newList = recommendGoodsManageList.stream().map(entity -> recommendGoodsManageService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new RecommendGoodsManageListResponse(newList));
	}

	@Override
	public BaseResponse<RecommendGoodsManageInfoListResponse> getRecommendGoodsInfoList(@RequestBody @Valid RecommendGoodsManageListRequest request) {
		Page<RecommendGoodsManageInfoVO> recommendGoodsManageInfoVOPage = recommendGoodsManageService.getRecommendGoodsInfoList(request);
		MicroServicePage<RecommendGoodsManageInfoVO> microPage = new MicroServicePage<>(recommendGoodsManageInfoVOPage, request.getPageable());
		return BaseResponse.success(new RecommendGoodsManageInfoListResponse(microPage));
	}


	@Override
	public BaseResponse<RecommendGoodsManageByIdResponse> getById(@RequestBody @Valid RecommendGoodsManageByIdRequest recommendGoodsManageByIdRequest) {
		RecommendGoodsManage recommendGoodsManage =
		recommendGoodsManageService.getOne(recommendGoodsManageByIdRequest.getId());
		return BaseResponse.success(new RecommendGoodsManageByIdResponse(recommendGoodsManageService.wrapperVo(recommendGoodsManage)));
	}

}

