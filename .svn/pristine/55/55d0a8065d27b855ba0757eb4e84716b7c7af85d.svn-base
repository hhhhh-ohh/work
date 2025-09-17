package com.wanmi.sbc.goods.provider.impl.buycyclegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoods.*;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoQueryRequest;
import com.wanmi.sbc.goods.api.response.buycyclegoods.*;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.StateDesc;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.buycyclegoods.model.root.BuyCycleGoods;
import com.wanmi.sbc.goods.buycyclegoods.service.BuyCycleGoodsService;
import com.wanmi.sbc.goods.buycyclegoodsinfo.model.root.BuyCycleGoodsInfo;
import com.wanmi.sbc.goods.buycyclegoodsinfo.service.BuyCycleGoodsInfoService;
import com.wanmi.sbc.goods.info.request.GoodsInfoRequest;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>周期购spu表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@RestController
@Validated
public class BuyCycleGoodsQueryController implements BuyCycleGoodsQueryProvider {
	@Autowired
	private BuyCycleGoodsService buyCycleGoodsService;

	@Autowired
	private BuyCycleGoodsInfoService buyCycleGoodsInfoService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsInfoService goodsInfoService;

	@Autowired
	private StoreQueryProvider storeQueryProvider;

	@Override
	public BaseResponse<BuyCycleGoodsPageResponse> page(@RequestBody @Valid BuyCycleGoodsPageRequest buyCycleGoodsPageReq) {
		BuyCycleGoodsQueryRequest queryReq = KsBeanUtil.convert(buyCycleGoodsPageReq, BuyCycleGoodsQueryRequest.class);
		Objects.requireNonNull(queryReq).setDelFlag(DeleteFlag.NO);
		Page<BuyCycleGoods> buyCycleGoodsPage = buyCycleGoodsService.page(queryReq);
		Map<Long, StoreVO> storeMap = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder()
				.storeIds(buyCycleGoodsPage.getContent().stream()
						.map(BuyCycleGoods::getStoreId)
						.collect(Collectors.toList()))
				.build()).getContext().getStoreVOList()
				.stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
		Page<BuyCycleGoodsVO> newPage = buyCycleGoodsPage.map(entity -> {
			BuyCycleGoodsVO buyCycleGoodsVO = buyCycleGoodsService.wrapperVo(entity);
			StoreVO storeVO = storeMap.get(entity.getStoreId());
			String goodsId = entity.getGoodsId();
			buyCycleGoodsVO.setSupplierName(storeVO.getSupplierName());
			GoodsSaveVO goods = goodsService.findGoodsSimple(goodsId).getGoods();
			//设置spu图片
			buyCycleGoodsVO.setGoodsImg(goods.getGoodsImg());
			//设置spu名称
			buyCycleGoodsVO.setGoodsName(goods.getGoodsName());
			List<BuyCycleGoodsInfo> buyCycleGoodsInfos = buyCycleGoodsInfoService.list(BuyCycleGoodsInfoQueryRequest.builder()
					.buyCycleId(entity.getId())
					.delFlag(DeleteFlag.NO)
					.build());
			List<String> skuIds = buyCycleGoodsInfos.parallelStream().map(BuyCycleGoodsInfo::getGoodsInfoId).collect(Collectors.toList());
			GoodsInfoRequest request = new GoodsInfoRequest();
			request.setGoodsInfoIds(skuIds);
			request.setStockViewFlag(Boolean.FALSE);
			//查询sku信息
			List<GoodsInfoSaveVO> goodsInfos = goodsInfoService.findSkuByIds(request).getGoodsInfos();
			List<GoodsInfoSaveVO> deleteSkuList = goodsInfos.stream().filter(goodsInfo -> Objects.equals(goodsInfo.getDelFlag(),DeleteFlag.YES)).collect(Collectors.toList());
			List<GoodsInfoSaveVO> notSaleSkuList = goodsInfos.stream().filter(goodsInfo -> Objects.nonNull(goodsInfo.getProviderId()) && Objects.equals(goodsInfo.getVendibility(), Constants.ZERO)).collect(Collectors.toList());
			List<GoodsInfoSaveVO> addedFlagSkuList = goodsInfos.stream().filter(goodsInfo -> !AddedFlag.YES.equals(AddedFlag.fromValue(goodsInfo.getAddedFlag()))).collect(Collectors.toList());
			Map<String, BuyCycleGoodsInfo> buyCycleGoodsInfoMap = buyCycleGoodsInfos.parallelStream().collect(Collectors.toMap(BuyCycleGoodsInfo::getGoodsInfoId, Function.identity()));
			List<GoodsInfoSaveVO> stockOutSkuList = goodsInfos.stream().filter(goodsInfo -> {
				BuyCycleGoodsInfo buyCycleGoodsInfo = buyCycleGoodsInfoMap.get(goodsInfo.getGoodsInfoId());
				Integer minCycleNum = buyCycleGoodsInfo.getMinCycleNum();
				return minCycleNum > goodsInfo.getStock();
			}).collect(Collectors.toList());
			CheckStatus auditStatus = goods.getAuditStatus();
			if (CollectionUtils.isNotEmpty(deleteSkuList)) {
				if (Objects.equals(deleteSkuList.size(),goodsInfos.size())) {
					buyCycleGoodsVO.setStateDesc(StateDesc.ALREADY_DELETE.getDesc());
				} else {
					buyCycleGoodsVO.setStateDesc(StateDesc.PART_ALREADY_DELETE.getDesc());
				}
			} else if (CollectionUtils.isNotEmpty(notSaleSkuList)){
				if (goodsInfos.size() == notSaleSkuList.size()) {
					buyCycleGoodsVO.setStateDesc(StateDesc.NOT_SALE.getDesc());
				} else {
					buyCycleGoodsVO.setStateDesc(StateDesc.PART_NOT_SALE.getDesc());
				}
			} else if (CheckStatus.FORBADE.equals(auditStatus)) {
				buyCycleGoodsVO.setStateDesc(StateDesc.FORBID_SALE.getDesc());
			}else {
				//不存在下架商品
				if (CollectionUtils.isEmpty(addedFlagSkuList)) {
					if (CollectionUtils.isNotEmpty(stockOutSkuList)) {
						//全部都是库存不足商品
						if (goodsInfos.size() == stockOutSkuList.size()) {
							buyCycleGoodsVO.setStateDesc(StateDesc.OUT_STOCK.getDesc());
						} else {
							buyCycleGoodsVO.setStateDesc(StateDesc.PART_OUT_STOCK.getDesc());
						}
					}
				} else {
					//全部都是下架商品
					if (goodsInfos.size() == addedFlagSkuList.size()) {
						buyCycleGoodsVO.setStateDesc(StateDesc.UNDERCARRIAGE.getDesc());
					} else {
						buyCycleGoodsVO.setStateDesc(StateDesc.PART_UNDERCARRIAGE.getDesc());
					}
				}
			}
			return buyCycleGoodsVO;
		});
		MicroServicePage<BuyCycleGoodsVO> microPage = new MicroServicePage<>(newPage, buyCycleGoodsPageReq.getPageable());
		BuyCycleGoodsPageResponse finalRes = new BuyCycleGoodsPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<BuyCycleGoodsListResponse> list(@RequestBody @Valid BuyCycleGoodsListRequest buyCycleGoodsListReq) {
		BuyCycleGoodsQueryRequest queryReq = KsBeanUtil.convert(buyCycleGoodsListReq, BuyCycleGoodsQueryRequest.class);
		List<BuyCycleGoods> buyCycleGoodsList = buyCycleGoodsService.list(queryReq);
		List<BuyCycleGoodsVO> newList = buyCycleGoodsList.stream().map(entity -> buyCycleGoodsService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new BuyCycleGoodsListResponse(newList));
	}

	@Override
	public BaseResponse<BuyCycleGoodsByIdResponse> getById(@RequestBody @Valid BuyCycleGoodsByIdRequest buyCycleGoodsByIdRequest) {
		BuyCycleGoods buyCycleGoods =
			buyCycleGoodsService.getOne(buyCycleGoodsByIdRequest.getId());
		String goodsId = buyCycleGoods.getGoodsId();
		List<BuyCycleGoodsInfo> buyCycleGoodsInfos = buyCycleGoodsInfoService.list(BuyCycleGoodsInfoQueryRequest.builder()
				.buyCycleId(buyCycleGoodsByIdRequest.getId())
				.delFlag(DeleteFlag.NO)
				.build());
		BuyCycleGoodsVO buyCycleGoodsVO = buyCycleGoodsService.wrapperVo(buyCycleGoods);
		GoodsSaveVO goods = goodsService.findGoodsSimple(goodsId).getGoods();
		//设置spu图片
		buyCycleGoodsVO.setGoodsImg(goods.getGoodsImg());
		//设置spu名称
		buyCycleGoodsVO.setGoodsName(goods.getGoodsName());
		buyCycleGoodsVO.setBuyCycleGoodsInfos(KsBeanUtil.convert(buyCycleGoodsInfos, BuyCycleGoodsInfoVO.class));
		return BaseResponse.success(BuyCycleGoodsByIdResponse.builder()
				.buyCycleGoodsVO(buyCycleGoodsVO)
				.build());
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid BuyCycleGoodsExportRequest request) {
		BuyCycleGoodsQueryRequest queryReq = KsBeanUtil.convert(request, BuyCycleGoodsQueryRequest.class);
		Long total = buyCycleGoodsService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<BuyCycleGoodsExportResponse> exportBuyCycleGoodsRecord(@RequestBody @Valid BuyCycleGoodsExportRequest request) {
		BuyCycleGoodsQueryRequest queryReq = KsBeanUtil.convert(request, BuyCycleGoodsQueryRequest.class);
		List<BuyCycleGoodsPageVO> data = KsBeanUtil.convert(buyCycleGoodsService.page(queryReq).getContent(),BuyCycleGoodsPageVO.class);
		return BaseResponse.success(new BuyCycleGoodsExportResponse(data));
	}

	@Override
	public BaseResponse<BuyCycleGoodsBySkuIdResponse> getBySkuId(@RequestBody @Valid BuyCycleGoodsBySkuIdRequest request) {
		BuyCycleVO buyCycleVO = buyCycleGoodsService.getBySkuId(request.getSkuId());
		return BaseResponse.success(new BuyCycleGoodsBySkuIdResponse(buyCycleVO));
	}


	@Override
	public BaseResponse<BuyCycleGoodsBySpuIdResponse> getBySpuId(@RequestBody @Valid BuyCycleGoodsBySpuIdRequest request) {
		BuyCycleGoods buyCycleGoods = buyCycleGoodsService.findByGoodsIdAndCycleStateAndDelFlag(request.getSpuId());
		return BaseResponse.success(new BuyCycleGoodsBySpuIdResponse(buyCycleGoodsService.wrapperVo(buyCycleGoods)));
	}
}

