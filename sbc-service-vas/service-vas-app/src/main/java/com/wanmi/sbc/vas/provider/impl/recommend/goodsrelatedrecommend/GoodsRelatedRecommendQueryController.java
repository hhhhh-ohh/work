package com.wanmi.sbc.vas.provider.impl.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.goodsrelatedrecommend.GoodsRelatedRecommendQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendInfoPageResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendListResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.GoodsRelatedRecommendPageResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsRelatedRecommendInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsRelatedRecommendVO;
import com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.model.root.GoodsRelatedRecommend;
import com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.service.GoodsRelatedRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商品相关性推荐查询服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@RestController
@Validated
public class GoodsRelatedRecommendQueryController implements GoodsRelatedRecommendQueryProvider {
	@Autowired
	private GoodsRelatedRecommendService goodsRelatedRecommendService;

	@Override
	public BaseResponse<GoodsRelatedRecommendPageResponse> page(@RequestBody @Valid GoodsRelatedRecommendPageRequest goodsRelatedRecommendPageReq) {
		GoodsRelatedRecommendQueryRequest queryReq = KsBeanUtil.convert(goodsRelatedRecommendPageReq, GoodsRelatedRecommendQueryRequest.class);
		Page<GoodsRelatedRecommend> goodsRelatedRecommendPage = goodsRelatedRecommendService.page(queryReq);
		Page<GoodsRelatedRecommendVO> newPage = goodsRelatedRecommendPage.map(entity -> goodsRelatedRecommendService.wrapperVo(entity));
		MicroServicePage<GoodsRelatedRecommendVO> microPage = new MicroServicePage<>(newPage, goodsRelatedRecommendPageReq.getPageable());
		GoodsRelatedRecommendPageResponse finalRes = new GoodsRelatedRecommendPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsRelatedRecommendListResponse> list(@RequestBody @Valid GoodsRelatedRecommendListRequest goodsRelatedRecommendListReq) {
		GoodsRelatedRecommendQueryRequest queryReq = KsBeanUtil.convert(goodsRelatedRecommendListReq, GoodsRelatedRecommendQueryRequest.class);
		List<GoodsRelatedRecommend> goodsRelatedRecommendList = goodsRelatedRecommendService.list(queryReq);
		List<GoodsRelatedRecommendVO> newList = goodsRelatedRecommendList.stream().map(entity -> goodsRelatedRecommendService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GoodsRelatedRecommendListResponse(newList));
	}

	@Override
	public BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendInfoList(@Valid GoodsRelatedRecommendInfoListRequest goodsRelatedRecommendInfoListRequest) {
		Page<GoodsRelatedRecommendInfoVO> goodsRelatedRecommendPage = goodsRelatedRecommendService.getGoodsRelatedRecommendInfoList(goodsRelatedRecommendInfoListRequest);
		MicroServicePage<GoodsRelatedRecommendInfoVO> microPage = new MicroServicePage<>(goodsRelatedRecommendPage, goodsRelatedRecommendInfoListRequest.getPageable());
		GoodsRelatedRecommendInfoPageResponse finalRes = new GoodsRelatedRecommendInfoPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendDetailInfoList(@Valid GoodsRelatedRecommendInfoListRequest goodsRelatedRecommendInfoListRequest) {
		Page<GoodsRelatedRecommendInfoVO> goodsRelatedRecommendPage = goodsRelatedRecommendService.getGoodsRelatedRecommendDetailInfoList(goodsRelatedRecommendInfoListRequest);
		MicroServicePage<GoodsRelatedRecommendInfoVO> microPage = new MicroServicePage<>(goodsRelatedRecommendPage, goodsRelatedRecommendInfoListRequest.getPageable());
		GoodsRelatedRecommendInfoPageResponse finalRes = new GoodsRelatedRecommendInfoPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendDataInfoList(@Valid GoodsRelatedRecommendInfoListRequest goodsRelatedRecommendInfoListRequest) {
		Page<GoodsRelatedRecommendInfoVO> goodsRelatedRecommendPage = goodsRelatedRecommendService.getGoodsRelatedRecommendDataInfoList(goodsRelatedRecommendInfoListRequest);
		MicroServicePage<GoodsRelatedRecommendInfoVO> microPage = new MicroServicePage<>(goodsRelatedRecommendPage, goodsRelatedRecommendInfoListRequest.getPageable());
		GoodsRelatedRecommendInfoPageResponse finalRes = new GoodsRelatedRecommendInfoPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendChooseList(@Valid GoodsRelatedRecommendInfoListRequest goodsRelatedRecommendInfoListRequest) {
		Page<GoodsRelatedRecommendInfoVO> goodsRelatedRecommendPage = goodsRelatedRecommendService.getGoodsRelatedRecommendChooseList(goodsRelatedRecommendInfoListRequest);
		MicroServicePage<GoodsRelatedRecommendInfoVO> microPage = new MicroServicePage<>(goodsRelatedRecommendPage, goodsRelatedRecommendInfoListRequest.getPageable());
		GoodsRelatedRecommendInfoPageResponse finalRes = new GoodsRelatedRecommendInfoPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsRelatedRecommendByIdResponse> getById(@RequestBody @Valid GoodsRelatedRecommendByIdRequest goodsRelatedRecommendByIdRequest) {
		GoodsRelatedRecommend goodsRelatedRecommend =
		goodsRelatedRecommendService.getOne(goodsRelatedRecommendByIdRequest.getId());
		return BaseResponse.success(new GoodsRelatedRecommendByIdResponse(goodsRelatedRecommendService.wrapperVo(goodsRelatedRecommend)));
	}

}

