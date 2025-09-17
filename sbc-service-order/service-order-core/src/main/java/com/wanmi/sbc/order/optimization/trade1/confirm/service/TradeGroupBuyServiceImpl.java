package com.wanmi.sbc.order.optimization.trade1.confirm.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.cache.DistributionCacheService;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmInterface;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.optimization.trade1.confirm.service.query.TradeConfirmQueryService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;
import com.wanmi.sbc.order.trade.service.VerifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liguang
 * @description 拼团提交
 * @date 2022/3/23 10:30
 */
@Service(value = "groupBuy")
@Slf4j
public class TradeGroupBuyServiceImpl implements TradeConfirmInterface {
    @Autowired
    private TradeConfirmQueryService tradeConfirmQueryService;
    @Autowired
    private TradeItemSnapshotService tradeItemSnapshotService;
    @Autowired
    private VerifyService verifyService;

    @Autowired
    private DistributionCacheService distributionCacheService;

    @Override
    public void getParams(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        // 获取会员信息
        CustomerVO customerVO = tradeConfirmQueryService.getCustomer(request.getCustomerId());
        param.setCustomerVO(customerVO);
        Map<String, TradeItem> tradeItemMap = Maps.newHashMap();
        tradeItemMap.put(request.getSkuId(), TradeItem.builder().skuId(request.getSkuId()).num(request.getNum()).build());
        param.setTradeItemMap(tradeItemMap);

        TradeItemRequest tradeItemRequest = new TradeItemRequest();
        tradeItemRequest.setSkuId(request.getSkuId());
        tradeItemRequest.setNum(request.getNum());
        param.getTradeBuyRequest().setTradeItemRequests(Lists.newArrayList(tradeItemRequest));

        // 获取商品信息
        TradeConfirmGoodsRequest goodsRequest = TradeConfirmGoodsRequest.builder()
                .skuIds(Lists.newArrayList(request.getSkuId()))
                .isHavSpecText(Boolean.TRUE)
                .isHavRedisStock(Boolean.TRUE)
                .build();
        this.tradeConfirmQueryService.getGoodsInfos(param, goodsRequest);
        // 拼团商品只有一种商品
        if (CollectionUtils.isNotEmpty(param.getGoodses())) {
            param.setStoreIds(Lists.newArrayList(param.getGoodses().get(0).getStoreId()));
        }
        param.setStores(this.tradeConfirmQueryService.getStoreByStoreIds(param.getStoreIds()));
        // 校验店铺状态
        if (!verifyService.checkStore(param.getStores())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }
        // 是否是分销
        param.setIsDistributFlag(this.tradeConfirmQueryService.querySystemDistributionConfig() && !ChannelType.PC_MALL.equals(request.getDistributeChannel().getChannelType()));
        // 获取店铺是否开启分销开关
        param.setStoreOpenFlag(this.distributionCacheService.queryStoreListOpenFlag(param.getStoreIds()));
        // 系统积分开关
        param.setSystemPointFlag(this.tradeConfirmQueryService.querySystemPointConfig());
        // 判断分销开关，如果系统分销或者店铺分销开关开启设置商品为普通商品
        this.tradeConfirmQueryService.checkDistributFlag(param, true, false);

        // 获取第三方渠道开关
        this.tradeConfirmQueryService.getChanelFlag(param);
    }

    @Override
    public void check(TradeConfirmParam param) {
        // 校验地址，拼团没有默认地址 直接查询
        this.tradeConfirmQueryService.checkAddress(param);
        // 商品校验
        this.tradeConfirmQueryService.checkGoodsStock(param);
        // 周期购校验
        this.tradeConfirmQueryService.checkBuyCycle(param);
        // 渠道校验
        this.tradeConfirmQueryService.checkChanelGoods(param);
    }

    @Override
    public void assembleTrade(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        String customerId = request.getCustomerId();
        GoodsInfoVO goodsInfo = param.getGoodsInfos().get(0);
        StoreVO store = param.getStores().get(0);
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

        // 订单标签
        OrderTag orderTag = OrderTag.builder()
                .virtualFlag(GoodsType.VIRTUAL_GOODS.toValue() == goodsInfo.getGoodsType())
                .electronicCouponFlag(GoodsType.ELECTRONIC_COUPON_GOODS.toValue() == goodsInfo.getGoodsType())
                .build();
        itemGroup.setOrderTag(orderTag);

        // 填充tradeItems
        Map<String, String> specDetailMap = param.getGoodsInfoSpecDetailRelVOList().stream().collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));
        TradeItem tradeItem = new TradeItem();
        tradeItem.setMarketingIds(Lists.newArrayList());
        tradeItem.setMarketingLevelIds(Lists.newArrayList());
        GoodsVO goodsVO = param.getGoodses().get(0);
        TradeItemRequest tradeItemRequest = request.getTradeItemRequests().get(0);
        this.tradeConfirmQueryService.fillBaseGoodsInfo(goodsInfo, goodsVO, specDetailMap);
        this.tradeConfirmQueryService.fillBaseTradeItem(tradeItem, goodsInfo, tradeItemRequest, request.getBuyType());
        // 拼团信息
        itemGroup.setGrouponForm(this.tradeConfirmQueryService.dealGroupon(tradeItem, request));
        itemGroup.setTradeItems(Lists.newArrayList(tradeItem));

        //生成快照
        TradeItemSnapshot snapshot = TradeItemSnapshot.builder()
                .id(UUIDUtil.getUUID())
                .buyerId(customerId)
                .itemGroups(Lists.newArrayList(itemGroup))
                .terminalToken(request.getTerminalToken())
                .purchaseBuy(Boolean.FALSE)
                .shareUserId(request.getShareUserId())
                .build();
        param.setTradeItemSnapshot(snapshot);
    }

    @Override
    public void fillPriceAndStock(TradeConfirmParam param) {

    }

    @Override
    public void calcMarketing(TradeConfirmParam param) {

    }

    @Override
    public void calcPrice(TradeConfirmParam param) {
    }

    @Override
    public void snapshot(TradeConfirmParam param) {
        this.tradeItemSnapshotService.addTradeItemSnapshot(param.getTradeItemSnapshot());
    }
}
