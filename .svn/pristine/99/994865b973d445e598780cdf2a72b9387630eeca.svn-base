package com.wanmi.sbc.order.optimization.trade1.snapshot.suitbuy.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsValidRequest;
import com.wanmi.sbc.marketing.api.response.marketingsuits.MarketingSuitsValidResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsSkuVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.QueryDataInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyAssembleInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyCheckInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl.TradeBuyService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className SuitBuyService
 * @description TODO
 * @date 2022/3/29 15:10
 */
@Service("suitBuy")
public class SuitsBuyService extends TradeBuyService {

    @Autowired
    @Qualifier("queryDataService")
    private QueryDataInterface queryDataInterface;

    @Autowired
    @Qualifier("suitsBuyAssembleService")
    private TradeBuyAssembleInterface tradeBuyAssembleInterface;

    @Autowired
    @Qualifier("tradeBuyCheckService")
    private TradeBuyCheckInterface tradeBuyCheckInterface;

    @Autowired private MarketingSuitsQueryProvider marketingSuitsQueryProvider;

    @Autowired private TradeItemSnapshotService tradeItemSnapshotService;

    @Override
    public void queryData(ParamsDataVO paramsDataVO) {


        TradeBuyRequest request = paramsDataVO.getRequest();
        // 查询会员信息
        CustomerVO customerVO = queryDataInterface.getCustomerInfo(request.getCustomerId());
        paramsDataVO.setCustomerVO(customerVO);

        MarketingSuitsValidRequest marketingRequest = new MarketingSuitsValidRequest();
        marketingRequest.setMarketingId(request.getMarketingId());
        marketingRequest.setBaseStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        marketingRequest.setUserId(request.getCustomerId());
        // 验证并获取活动信息
        MarketingSuitsValidResponse marketingRespons =
                marketingSuitsQueryProvider
                        .validSuitOrderBeforeCommit(marketingRequest)
                        .getContext();
        paramsDataVO.setMarketingResponse(marketingRespons);
        Map<String, List<MarketingPluginLabelDetailVO>> skuMarketingLabelMap = new HashMap<>();
        List<TradeItemRequest> tradeItemRequests = new ArrayList<>();
        marketingRespons
                .getMarketingSuitsSkuVOList()
                .forEach(
                        marketingSuitsSkuVO -> {
                            MarketingPluginLabelDetailVO marketingPluginLabelDetailVO =
                                    new MarketingPluginLabelDetailVO();
                            marketingPluginLabelDetailVO.setMarketingId(request.getMarketingId());
                            marketingPluginLabelDetailVO.setDetail(marketingSuitsSkuVO);
                            marketingPluginLabelDetailVO.setMarketingType(
                                    MarketingPluginType.SUITS.getId());
                            marketingPluginLabelDetailVO.setPluginPrice(
                                    marketingSuitsSkuVO.getDiscountPrice());
                            skuMarketingLabelMap.put(
                                    marketingSuitsSkuVO.getSkuId(),
                                    Collections.singletonList(marketingPluginLabelDetailVO));
                            TradeItemRequest tradeItemRequest = new TradeItemRequest();
                            tradeItemRequest.setSkuId(marketingSuitsSkuVO.getSkuId());
                            tradeItemRequest.setNum(marketingSuitsSkuVO.getNum());
                            tradeItemRequests.add(tradeItemRequest);
                        });
        request.setTradeItemRequests(tradeItemRequests);
        GoodsTradePluginResponse response = new GoodsTradePluginResponse();
        response.setSkuMarketingLabelMap(skuMarketingLabelMap);
        paramsDataVO.setGoodsTradePluginResponse(response);

        List<String> skuIds =
                marketingRespons.getMarketingSuitsSkuVOList().stream()
                        .map(MarketingSuitsSkuVO::getSkuId)
                        .collect(Collectors.toList());
        TradeConfirmGoodsRequest goodsRequest =
                TradeConfirmGoodsRequest.builder()
                        .skuIds(skuIds)
                        .isHavSpecText(Boolean.TRUE)
                        .isHavIntervalPrice(Boolean.TRUE)
                        .showLabelFlag(Boolean.FALSE)
                        .showSiteLabelFlag(Boolean.FALSE)
                        .isHavRedisStock(Boolean.FALSE)
                        .build();
        GoodsInfoResponse goodsInfoResponse = queryDataInterface.getGoodsInfo(goodsRequest);
        paramsDataVO.setGoodsInfoResponseSourceData(goodsInfoResponse);
        GoodsInfoResponse infoResponse =
                KsBeanUtil.convert(goodsInfoResponse, GoodsInfoResponse.class);
        paramsDataVO.setGoodsInfoResponse(infoResponse);

        // 是否都是实体商品
        boolean isRealGoods = goodsInfoResponse.getGoodsInfos().stream().allMatch(g -> g.getGoodsType() == 0);
        // 第三方商家商品库存需要省市区信息
        if (isRealGoods && StringUtils.isBlank(paramsDataVO.getRequest().getAddressId())) {
            CustomerDeliveryAddressResponse customerDeliveryAddressResponse =
                    queryDataInterface.getDefaultAddress(paramsDataVO);
            String provinceId = customerDeliveryAddressResponse.getProvinceId().toString();
            String cityId = customerDeliveryAddressResponse.getCityId().toString();
            String areaId = customerDeliveryAddressResponse.getAreaId().toString();
            String streetId = "";
            if (customerDeliveryAddressResponse.getStreetId() != null) {
                streetId = customerDeliveryAddressResponse.getStreetId().toString();
            }
            paramsDataVO
                    .getRequest()
                    .setAddressId(provinceId + "|" + cityId + "|" + areaId + "|" + streetId);
        }

        // 查询店铺
        StoreVO storeVO =
                queryDataInterface.getStoreInfo(
                        goodsInfoResponse.getGoodsInfos().get(0).getStoreId(),
                        request.getCustomerId());
        paramsDataVO.setStoreVO(storeVO);

        // 查询等级
        if (BoolFlag.YES.equals(storeVO.getCompanyType())){
            String joinLevel = marketingRespons.getMarketingVO().getJoinLevel();
            if (!Objects.equals(joinLevel, "-1") ){
                List<StoreCustomerRelaVO> storeCustomerRelaVOS =
                        queryDataInterface.listByCondition(
                                request.getCustomerId(), storeVO.getStoreId());
                paramsDataVO.setStoreCustomerRelaVOS(storeCustomerRelaVOS);
            }
        }
    }

    @Override
    public void check(ParamsDataVO paramsDataVO) {
        // 活动校验
        tradeBuyCheckInterface.validateMarketingSuits(paramsDataVO);
        // 商品校验
        tradeBuyCheckInterface.validateGoodsStock(paramsDataVO);

        // 商品渠道检验
        tradeBuyCheckInterface.verifyChannelGoods(paramsDataVO);
    }

    @Override
    public void assembleTrade(ParamsDataVO paramsDataVO) {
        // <sku, goodsInfo> goodsInfo是数据库查询出来的
        Map<String, GoodsInfoVO> skuGoodsInfoMap =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .collect(
                                Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));

        // <spu, goods> goods是数据库查询出来的
        Map<String, GoodsVO> spuGoodsMap =
                paramsDataVO.getGoodsInfoResponse().getGoodses().stream()
                        .collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        List<TradeItemDTO> tradeItems = new ArrayList<>();
        paramsDataVO
                .getGoodsTradePluginResponse()
                .getSkuMarketingLabelMap()
                .forEach(
                        (k, v) -> {
                            MarketingSuitsSkuVO marketingSuitsSkuVO = v.get(0).getDetail();
                            TradeItemDTO tradeItemDTO = new TradeItemDTO();
                            // 前端传过来的购买数量
                            tradeItemDTO.setNum(marketingSuitsSkuVO.getNum());
                            GoodsInfoVO goodsInfoVO =
                                    skuGoodsInfoMap.get(marketingSuitsSkuVO.getSkuId());
                            // 构建tradeItem基础数据
                            tradeBuyAssembleInterface.tradeItemBaseBuilder(
                                    tradeItemDTO,
                                    goodsInfoVO,
                                    spuGoodsMap.get(goodsInfoVO.getGoodsId()),
                                    paramsDataVO.getStoreVO().getStoreId());

                            tradeBuyAssembleInterface.tradeItemPriceBuilder(
                                    new TradeItemRequest(),
                                    tradeItemDTO,
                                    goodsInfoVO,
                                    spuGoodsMap.get(goodsInfoVO.getGoodsId()),
                                    paramsDataVO.getGoodsInfoResponse().getGoodsIntervalPrices());

                            BigDecimal price =
                                    goodsInfoVO.getMarketPrice() == null
                                            ? BigDecimal.ZERO
                                            : goodsInfoVO.getMarketPrice();
                            tradeItemDTO.setLevelPrice(price);
                            tradeItemDTO.setOriginalPrice(price);
                            tradeItemDTO.setPrice(price);
                            tradeItemDTO.setSplitPrice(
                                    tradeItemDTO
                                            .getLevelPrice()
                                            .multiply(new BigDecimal(tradeItemDTO.getNum()))
                                            .setScale(2, RoundingMode.HALF_UP));
                            tradeItems.add(tradeItemDTO);
                        });
        OrderTag orderTag = new OrderTag();
        boolean virtualFlag =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .anyMatch(
                                goodsInfoVO ->
                                        NumberUtils.INTEGER_ONE.equals(goodsInfoVO.getGoodsType()));
        orderTag.setVirtualFlag(virtualFlag);
        boolean electronicCouponFlag =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .anyMatch(
                                goodsInfoVO ->
                                        Constants.TWO == (goodsInfoVO.getGoodsType()));
        orderTag.setElectronicCouponFlag(electronicCouponFlag);
        paramsDataVO.setTradeItemDTOS(tradeItems);
        paramsDataVO.setOrderTag(orderTag);
    }

    @Override
    public void saveTrade(ParamsDataVO paramsDataVO) {
        List<TradeItemDTO> tradeItems = paramsDataVO.getTradeItemDTOS();
        TradeItemGroup tradeItemGroup = new TradeItemGroup();
        tradeItemGroup.setTradeItems(KsBeanUtil.convert(tradeItems, TradeItem.class));
        StoreVO store = paramsDataVO.getStoreVO();
        Supplier supplier =
                Supplier.builder()
                        .storeId(store.getStoreId())
                        .storeName(store.getStoreName())
                        .isSelf(store.getCompanyType() == BoolFlag.NO)
                        .supplierCode(store.getCompanyInfo().getCompanyCode())
                        .supplierId(store.getCompanyInfo().getCompanyInfoId())
                        .supplierName(store.getCompanyInfo().getSupplierName())
                        .freightTemplateType(store.getFreightTemplateType())
                        .storeType(store.getStoreType())
                        .build();
        tradeItemGroup.setSupplier(supplier);
        tradeItemGroup.setTradeMarketingList(
                paramsDataVO.getTradeMarketingList() == null
                        ? new ArrayList<>()
                        : paramsDataVO.getTradeMarketingList());
        tradeItemGroup.setStoreBagsFlag(DefaultFlag.NO);
        tradeItemGroup.setSuitMarketingFlag(Boolean.TRUE);
        tradeItemGroup.setOrderTag(paramsDataVO.getOrderTag());

        TradeItemSnapshot snapshot =
                TradeItemSnapshot.builder()
                        .id(UUIDUtil.getUUID())
                        .buyerId(paramsDataVO.getCustomerVO().getCustomerId())
                        .itemGroups(Collections.singletonList(tradeItemGroup))
                        .terminalToken(paramsDataVO.getTerminalToken())
                        .purchaseBuy(Boolean.FALSE)
                        .build();
        tradeItemSnapshotService.addTradeItemSnapshot(snapshot);
    }
}
