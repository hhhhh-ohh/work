package com.wanmi.sbc.vas.provider.impl.recommend.manualrecommendgoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.manualrecommendgoods.ManualRecommendGoodsQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.*;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsInfoListResponse;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsListResponse;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.ManualRecommendGoodsPageResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.ManualRecommendGoodsInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.ManualRecommendGoodsVO;
import com.wanmi.sbc.vas.recommend.manualrecommendgoods.model.root.ManualRecommendGoods;
import com.wanmi.sbc.vas.recommend.manualrecommendgoods.service.ManualRecommendGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>手动推荐商品管理查询服务接口实现</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@RestController
@Validated
public class ManualRecommendGoodsQueryController implements ManualRecommendGoodsQueryProvider {
	@Autowired
	private ManualRecommendGoodsService manualRecommendGoodsService;

	@Override
	public BaseResponse<ManualRecommendGoodsPageResponse> page(@RequestBody @Valid ManualRecommendGoodsPageRequest manualRecommendGoodsPageReq) {
		ManualRecommendGoodsQueryRequest queryReq = KsBeanUtil.convert(manualRecommendGoodsPageReq, ManualRecommendGoodsQueryRequest.class);
		Page<ManualRecommendGoods> manualRecommendGoodsPage = manualRecommendGoodsService.page(queryReq);
		Page<ManualRecommendGoodsVO> newPage = manualRecommendGoodsPage.map(entity -> manualRecommendGoodsService.wrapperVo(entity));
		MicroServicePage<ManualRecommendGoodsVO> microPage = new MicroServicePage<>(newPage, manualRecommendGoodsPageReq.getPageable());
		ManualRecommendGoodsPageResponse finalRes = new ManualRecommendGoodsPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<ManualRecommendGoodsListResponse> list(@RequestBody @Valid ManualRecommendGoodsListRequest manualRecommendGoodsListReq) {
		ManualRecommendGoodsQueryRequest queryReq = KsBeanUtil.convert(manualRecommendGoodsListReq, ManualRecommendGoodsQueryRequest.class);
		List<ManualRecommendGoods> manualRecommendGoodsList = manualRecommendGoodsService.list(queryReq);
		List<ManualRecommendGoodsVO> newList = manualRecommendGoodsList.stream().map(entity -> manualRecommendGoodsService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new ManualRecommendGoodsListResponse(newList));
	}

	@Override
	public BaseResponse<ManualRecommendGoodsInfoListResponse> getManualRecommendGoodsInfoList(@Valid ManualRecommendGoodsInfoListRequest manualRecommendGoodsListReq) {
		Page<ManualRecommendGoodsInfoVO> manualRecommendGoodsPage = manualRecommendGoodsService.getManualRecommendGoodsInfoList(manualRecommendGoodsListReq);
		MicroServicePage<ManualRecommendGoodsInfoVO> microPage = new MicroServicePage<>(manualRecommendGoodsPage, manualRecommendGoodsListReq.getPageable());
		ManualRecommendGoodsInfoListResponse finalRes = new ManualRecommendGoodsInfoListResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<ManualRecommendGoodsInfoListResponse> getManualRecommendChooseGoodsList(@Valid ManualRecommendGoodsInfoListRequest request) {
		Page<ManualRecommendGoodsInfoVO> manualRecommendGoodsPage = manualRecommendGoodsService.getManualRecommendChooseGoodsList(request);
		MicroServicePage<ManualRecommendGoodsInfoVO> microPage = new MicroServicePage<>(manualRecommendGoodsPage, request.getPageable());
		ManualRecommendGoodsInfoListResponse finalRes = new ManualRecommendGoodsInfoListResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<ManualRecommendGoodsByIdResponse> getById(@RequestBody @Valid ManualRecommendGoodsByIdRequest manualRecommendGoodsByIdRequest) {
		ManualRecommendGoods manualRecommendGoods =
		manualRecommendGoodsService.getOne(manualRecommendGoodsByIdRequest.getId());
		return BaseResponse.success(new ManualRecommendGoodsByIdResponse(manualRecommendGoodsService.wrapperVo(manualRecommendGoods)));
	}

}

