package com.wanmi.sbc.order.optimization.trade1.confirm.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmInterface;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.optimization.trade1.confirm.service.query.TradeConfirmQueryService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className PickupGiftCardServiceImpl
 * @description TODO
 * @date 2023/6/30 11:23
 **/
@Service("pickupCardBuy")
public class PickupGiftCardServiceImpl implements TradeConfirmInterface {
    @Autowired
    private TradeConfirmQueryService tradeConfirmQueryService;
    @Autowired
    private TradeItemSnapshotService tradeItemSnapshotService;

    @Override
    public void getParams(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        List<TradeItemRequest> tradeItems = request.getTradeItemRequests();
        // 获取会员信息
        CustomerVO customerVO = tradeConfirmQueryService.getCustomer(request.getCustomerId());
        param.setCustomerVO(customerVO);
        // 获取skuIds storeIds
        param.setSkuIds(tradeItems.stream().map(TradeItemRequest::getSkuId).collect(Collectors.toList()));
        // 获取第三方渠道开关
        this.tradeConfirmQueryService.getChanelFlag(param);
        // 获取商品信息
        TradeConfirmGoodsRequest goodsRequest = TradeConfirmGoodsRequest.builder()
                .skuIds(param.getSkuIds())
                .isHavSpecText(Boolean.TRUE)
                .isHavIntervalPrice(Boolean.TRUE)
                .isHavRedisStock(Boolean.TRUE)
                .build();
        this.tradeConfirmQueryService.getGoodsInfos(param, goodsRequest);
        // 获取店铺信息
        param.setStoreIds(param.getGoodses().stream().map(GoodsVO::getStoreId).distinct().collect(Collectors.toList()));
        param.setStores(tradeConfirmQueryService.getStoreByStoreIds(param.getStoreIds()));

        UserGiftCardInfoVO userGiftCardInfoVO = tradeConfirmQueryService.getUserGiftCard(request.getUserGiftCardId(),
                request.getCustomerId());
        param.setUserGiftCardInfoVO(userGiftCardInfoVO);

    }

    @Override
    public void check(TradeConfirmParam param) {
        // 收货地址校验
        this.tradeConfirmQueryService.checkAddress(param);
        // 商品校验
        this.tradeConfirmQueryService.checkGoodsStock(param);
        // 渠道校验
        this.tradeConfirmQueryService.checkChanelGoods(param);
        // 提货卡信息校验
        tradeConfirmQueryService.validatePickUpCard(param);
        // 校验店铺
        tradeConfirmQueryService.verifyStore(param);
    }

    @Override
    public void assembleTrade(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        Map<String, TradeItemRequest> tradeItemRequestMap = request.getTradeItemRequests().stream()
                .collect(Collectors.toMap(TradeItemRequest::getSkuId, Function.identity()));
        Map<Long, List<GoodsInfoVO>> goodsInfosMap = param.getGoodsInfos().stream()
                .collect(Collectors.groupingBy(GoodsInfoVO::getStoreId));
        Map<Long, Map<String, GoodsVO>> goodsMap = param.getGoodses().stream()
                .collect(Collectors.groupingBy(GoodsVO::getStoreId, Collectors.toMap(GoodsVO::getGoodsId, Function.identity())));
        Map<String, String> specDetailMap = param.getGoodsInfoSpecDetailRelVOList()
                .stream()
                .collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));
        List<TradeItemGroup> itemGroups = Lists.newArrayList();
        List<StoreVO> stores = param.getStores();
        for (StoreVO store : stores) {
            TradeItemGroup itemGroup = new TradeItemGroup();
            // 店铺信息
            Supplier supplier = Supplier.builder()
                    .storeId(store.getStoreId())
                    .storeName(store.getStoreName())
                    .isSelf(store.getCompanyType() == BoolFlag.NO)
                    .supplierCode(store.getCompanyInfo().getCompanyCode())
                    .supplierId(store.getCompanyInfo().getCompanyInfoId())
                    .supplierName(store.getCompanyInfo().getSupplierName())
                    .freightTemplateType(store.getFreightTemplateType())
                    .storeType(store.getStoreType())
                    .build();
            itemGroup.setSupplier(supplier);

            List<GoodsInfoVO> storeGoodsInfos = goodsInfosMap.getOrDefault(store.getStoreId(), Lists.newArrayList());
            // 填充 tradeItem 信息
            List<TradeItem> tradeItems = storeGoodsInfos.stream()
                    .map(goodsInfo -> {
                        TradeItem tradeItem = new TradeItem();
                        tradeItem.setMarketingIds(Lists.newArrayList());
                        tradeItem.setMarketingLevelIds(Lists.newArrayList());
                        GoodsVO goodsVO = goodsMap.get(store.getStoreId()).get(goodsInfo.getGoodsId());
                        TradeItemRequest tradeItemRequest = tradeItemRequestMap.get(goodsInfo.getGoodsInfoId());
                        this.tradeConfirmQueryService.fillBaseGoodsInfo(goodsInfo, goodsVO, specDetailMap);
                        // 补充基本信息
                        this.tradeConfirmQueryService.fillBaseTradeItem(tradeItem, goodsInfo, tradeItemRequest, request.getBuyType());
                        // 填充价格
                        this.tradeConfirmQueryService.fillTradeItemPrice(tradeItem, goodsInfo, goodsVO, param.getGoodsIntervalPriceVOList());
                        return tradeItem;
                    }).collect(Collectors.toList());
            itemGroup.setTradeItems(tradeItems);
            itemGroup.setPluginType(PluginType.NORMAL);
            itemGroup.setStoreBagsFlag(DefaultFlag.NO);
            itemGroup.setSuitMarketingFlag(Boolean.FALSE);
            itemGroup.setTradeMarketingList(Lists.newArrayList());
            // 订单标签
            itemGroup.setOrderTag(OrderTag.builder().pickupCardFlag(Boolean.TRUE).build());
            itemGroups.add(itemGroup);
        }

        param.setTradeItemMap(itemGroups.stream().map(TradeItemGroup::getTradeItems).flatMap(Collection::stream)
                .collect(Collectors.toMap(TradeItem::getSkuId, Function.identity(), (v1, v2) -> v1)));

        TradeItemSnapshot tradeItemSnapshot = TradeItemSnapshot.builder()
                .id(UUIDUtil.getUUID())
                .buyerId(request.getCustomerId())
                .itemGroups(itemGroups)
                .terminalToken(request.getTerminalToken())
                .purchaseBuy(Boolean.FALSE)
                .userGiftCardId(param.getUserGiftCardInfoVO().getUserGiftCardId())
                .orderTag(OrderTag.builder().pickupCardFlag(Boolean.TRUE).build())
                .build();
        param.setTradeItemSnapshot(tradeItemSnapshot);
    }

    @Override
    public void fillPriceAndStock(TradeConfirmParam param) {

    }

    @Override
    public void calcMarketing(TradeConfirmParam param){

    }

    @Override
    public void calcPrice(TradeConfirmParam param) {

    }

    @Override
    public void snapshot(TradeConfirmParam param) {
        this.tradeItemSnapshotService.addTradeItemSnapshot(param.getTradeItemSnapshot());
    }
}
