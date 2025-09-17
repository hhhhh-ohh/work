package com.wanmi.sbc.vas.provider.impl.recommend.caterelatedrecommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.caterelatedrecommend.CateRelatedRecommendQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.caterelatedrecommend.*;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendDetailVO;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendVO;
import com.wanmi.sbc.vas.recommend.caterelatedrecommend.model.root.CateRelatedRecommend;
import com.wanmi.sbc.vas.recommend.caterelatedrecommend.service.CateRelatedRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>分类相关性推荐查询服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@RestController
@Validated
public class CateRelatedRecommendQueryController implements CateRelatedRecommendQueryProvider {
	@Autowired
	private CateRelatedRecommendService cateRelatedRecommendService;

	@Override
	public BaseResponse<CateRelatedRecommendPageResponse> page(@RequestBody @Valid CateRelatedRecommendPageRequest cateRelatedRecommendPageReq) {
		CateRelatedRecommendQueryRequest queryReq = KsBeanUtil.convert(cateRelatedRecommendPageReq, CateRelatedRecommendQueryRequest.class);
		Page<CateRelatedRecommend> cateRelatedRecommendPage = cateRelatedRecommendService.page(queryReq);
		Page<CateRelatedRecommendVO> newPage = cateRelatedRecommendPage.map(entity -> cateRelatedRecommendService.wrapperVo(entity));
		MicroServicePage<CateRelatedRecommendVO> microPage = new MicroServicePage<>(newPage, cateRelatedRecommendPageReq.getPageable());
		CateRelatedRecommendPageResponse finalRes = new CateRelatedRecommendPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<CateRelatedRecommendListResponse> list(@RequestBody @Valid CateRelatedRecommendListRequest cateRelatedRecommendListReq) {
		CateRelatedRecommendQueryRequest queryReq = KsBeanUtil.convert(cateRelatedRecommendListReq, CateRelatedRecommendQueryRequest.class);
		List<CateRelatedRecommend> cateRelatedRecommendList = cateRelatedRecommendService.list(queryReq);
		List<CateRelatedRecommendVO> newList = cateRelatedRecommendList.stream().map(entity -> cateRelatedRecommendService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new CateRelatedRecommendListResponse(newList));
	}

	@Override
	public BaseResponse<CateRelatedRecommendInfoListResponse> getCateRelateRecommendInfoList(@Valid CateRelatedRecommendInfoListRequest cateRelatedRecommendInfoListReq) {
		List<CateRelatedRecommendInfoVO> cateRelatedRecommendInfoVOList = cateRelatedRecommendService.getCateRelateRecommendInfoList(cateRelatedRecommendInfoListReq);
		return BaseResponse.success(new CateRelatedRecommendInfoListResponse(cateRelatedRecommendInfoVOList));
	}

	@Override
	public BaseResponse<CateRelatedRecommendDetailListResponse> getCateRelateRecommendDetailList(@Valid CateRelatedRecommendDetailListRequest request) {
		Page<CateRelatedRecommendDetailVO> recommendGoodsManageInfoVOPage = cateRelatedRecommendService.getCateRelateRecommendDetailList(request);
		MicroServicePage<CateRelatedRecommendDetailVO> microPage = new MicroServicePage<>(recommendGoodsManageInfoVOPage, request.getPageable());
		return BaseResponse.success(new CateRelatedRecommendDetailListResponse(microPage));
	}

	@Override
	public BaseResponse<CateRelatedRecommendByIdResponse> getById(@RequestBody @Valid CateRelatedRecommendByIdRequest cateRelatedRecommendByIdRequest) {
		CateRelatedRecommend cateRelatedRecommend =
		cateRelatedRecommendService.getOne(cateRelatedRecommendByIdRequest.getId());
		return BaseResponse.success(new CateRelatedRecommendByIdResponse(cateRelatedRecommendService.wrapperVo(cateRelatedRecommend)));
	}

}

