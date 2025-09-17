package com.wanmi.sbc.goods.provider.impl.newcomerpurchasegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods.NewcomerPurchaseGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.*;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsListResponse;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsMagicPageResponse;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsPageResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSaveVO;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsMagicVO;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsVO;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.newcomerpurchasegoods.model.root.NewcomerPurchaseGoods;
import com.wanmi.sbc.goods.newcomerpurchasegoods.service.NewcomerPurchaseGoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>新人购商品表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@RestController
@Validated
public class NewcomerPurchaseGoodsQueryController implements NewcomerPurchaseGoodsQueryProvider {
	@Autowired
	private NewcomerPurchaseGoodsService newcomerPurchaseGoodsService;

	@Autowired
	private GoodsInfoService goodsInfoService;

	@Autowired
	private StoreQueryProvider storeQueryProvider;

	@Override
	public BaseResponse<NewcomerPurchaseGoodsPageResponse> page(@RequestBody @Valid NewcomerPurchaseGoodsPageRequest newcomerPurchaseGoodsPageReq) {
		NewcomerPurchaseGoodsQueryRequest queryReq = KsBeanUtil.convert(newcomerPurchaseGoodsPageReq, NewcomerPurchaseGoodsQueryRequest.class);
		Page<NewcomerPurchaseGoods> newcomerPurchaseGoodsPage = newcomerPurchaseGoodsService.page(queryReq);
		Page<NewcomerPurchaseGoodsVO> newPage = newcomerPurchaseGoodsPage.map(entity -> newcomerPurchaseGoodsService.wrapperVo(entity));
		MicroServicePage<NewcomerPurchaseGoodsVO> microPage = new MicroServicePage<>(newPage, newcomerPurchaseGoodsPageReq.getPageable());
		NewcomerPurchaseGoodsPageResponse finalRes = new NewcomerPurchaseGoodsPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<NewcomerPurchaseGoodsListResponse> list(@RequestBody @Valid NewcomerPurchaseGoodsListRequest newcomerPurchaseGoodsListReq) {
		NewcomerPurchaseGoodsQueryRequest queryReq = KsBeanUtil.convert(newcomerPurchaseGoodsListReq, NewcomerPurchaseGoodsQueryRequest.class);
		List<NewcomerPurchaseGoods> newcomerPurchaseGoodsList = newcomerPurchaseGoodsService.list(queryReq);
		List<NewcomerPurchaseGoodsVO> newList = newcomerPurchaseGoodsList.stream().map(entity -> newcomerPurchaseGoodsService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new NewcomerPurchaseGoodsListResponse(newList));
	}

	@Override
	public BaseResponse<NewcomerPurchaseGoodsByIdResponse> getById(@RequestBody @Valid NewcomerPurchaseGoodsByIdRequest newcomerPurchaseGoodsByIdRequest) {
		NewcomerPurchaseGoods newcomerPurchaseGoods =
		newcomerPurchaseGoodsService.getOne(newcomerPurchaseGoodsByIdRequest.getId());
		return BaseResponse.success(new NewcomerPurchaseGoodsByIdResponse(newcomerPurchaseGoodsService.wrapperVo(newcomerPurchaseGoods)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid NewcomerPurchaseGoodsExportRequest request) {
		NewcomerPurchaseGoodsQueryRequest queryReq = KsBeanUtil.convert(request, NewcomerPurchaseGoodsQueryRequest.class);
		Long total = newcomerPurchaseGoodsService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<NewcomerPurchaseGoodsMagicPageResponse> magicPage(@RequestBody @Valid NewcomerPurchaseGoodsPageMagicRequest request) {
		NewcomerPurchaseGoodsMagicPageResponse response = new NewcomerPurchaseGoodsMagicPageResponse();
		List<String> goodsInfoIds = newcomerPurchaseGoodsService.findGoodsInfoIds();
		if (CollectionUtils.isEmpty(goodsInfoIds)) {
			response.setNewcomerPurchaseGoodsVOS(new MicroServicePage<>());
			return BaseResponse.success(response);
		}

		GoodsInfoQueryRequest goodsInfoQueryRequest = new GoodsInfoQueryRequest();
		if (StringUtils.isNotBlank(request.getStoreName())) {
			List<StoreVO> storeVOList = storeQueryProvider
					.listByName(ListStoreByNameRequest.builder().storeName(request.getStoreName()).build())
					.getContext().getStoreVOList();
			if(CollectionUtils.isEmpty(storeVOList)) {
				response.setNewcomerPurchaseGoodsVOS(new MicroServicePage<>());
				return BaseResponse.success(response);
			}
			goodsInfoQueryRequest.setStoreIds(storeVOList.stream().map(StoreVO::getStoreId).collect(Collectors.toList()));
		}
		goodsInfoQueryRequest.setLikeGoodsName(request.getGoodsInfoName());
		goodsInfoQueryRequest.setGoodsInfoIds(goodsInfoIds);
		goodsInfoQueryRequest.setPageNum(request.getPageNum());
		goodsInfoQueryRequest.setPageSize(request.getPageSize());
		MicroServicePage<GoodsInfoSaveVO> page = goodsInfoService.pageView(goodsInfoQueryRequest).getGoodsInfoPage();

		List<Long> storeIds = page.getContent().stream().map(GoodsInfoSaveVO::getStoreId).collect(Collectors.toList());
		Map<Long, StoreVO> storeVOMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(storeIds)) {
			List<StoreVO> storeVOList = storeQueryProvider
					.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build())
					.getContext().getStoreVOList();
			storeVOMap = storeVOList.stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
		}

		Map<Long, StoreVO> finalStoreVOMap = storeVOMap;
		Page<NewcomerPurchaseGoodsMagicVO> newPage = page.map(entity -> {
			NewcomerPurchaseGoodsMagicVO magicVO = KsBeanUtil.convert(entity, NewcomerPurchaseGoodsMagicVO.class);
			StoreVO storeVO = finalStoreVOMap.get(entity.getStoreId());
			if (storeVO != null) {
				magicVO.setStoreName(storeVO.getStoreName());
			}
			return magicVO;
		});
		MicroServicePage<NewcomerPurchaseGoodsMagicVO> microPage = new MicroServicePage<>(newPage, page.getPageable());
		response.setNewcomerPurchaseGoodsVOS(microPage);

		return BaseResponse.success(response);
	}

}

