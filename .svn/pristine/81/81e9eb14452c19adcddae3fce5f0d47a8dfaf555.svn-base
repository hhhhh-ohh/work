package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainSaveProvider;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.cache.DistributionCacheService;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.optimization.trade1.commit.service.*;
import com.wanmi.sbc.order.trade.model.entity.TradeCommitResult;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 社区团购订单
 * @author edz
 * @date 2023/7/26 15:34
 */
@Service("communityCommitService")
public class CommunityTradeCommitService implements Trade1CommitInterface {

    @Autowired Trade1CommitCheckInterface checkInterface;

    @Autowired Trade1CommitRequestInterface requestInterface;

    @Autowired Trade1CommitGetDataInterface getDataInterface;

    @Autowired Trade1CommitCreateInterface createInterface;

    @Autowired Trade1CommitProcessInterface communityCommitProcessService;

    @Autowired Trade1CommitAfterInterface afterInterface;

    @Autowired BargainSaveProvider bargainSaveProvider;

    @Autowired
    DistributionCacheService distributionCacheService;

    @Override
    public TradeCommitRequest processRequest(TradeCommitRequest request) {
        requestInterface.setRequest(request);
        // 校验
        return requestInterface.checkRequest(request);
    }

    @Override
    public Trade1CommitParam getData(TradeCommitRequest request) {
        // 获取会员
        CustomerVO customerVO = getDataInterface.getCustomer(request.getOperator().getUserId());

        // 获取订单快照
        TradeItemSnapshot snapshot = getDataInterface.getSnapshot(request);
        // 获取收货地址
        CustomerDeliveryAddressVO customerDeliveryAddressVO =
                getDataInterface.getAddress(request, snapshot);
        // 获取商品
        List<GoodsInfoTradeVO> goodsInfoList =
                getDataInterface.getGoodsInfoData(snapshot, customerVO);

        // 获取店铺
        List<StoreVO> storeVOS = getDataInterface.getStoreInfoData(goodsInfoList);
        // 获取库存
        goodsInfoList = getDataInterface.getStock(goodsInfoList, snapshot);

        // 获取积分配置
        SystemPointsConfigQueryResponse systemPointsConfigQueryResponse =
                getDataInterface.getPointSetting(goodsInfoList, request);

        // 获取营销
        GoodsTradePluginResponse goodsTradePluginResponse =
                getDataInterface.getMarketing(goodsInfoList, snapshot, customerVO);
        // 获取配置
        getDataInterface.getSetting();


        // 获取礼品卡信息
        List<UserGiftCardInfoVO> userGiftCardInfoVOList =
                getDataInterface.getUserGiftCardInfo(
                        goodsInfoList, request, snapshot);

        // 社区团购活动信息
        CommunityActivityByIdResponse communityActivityByIdResponse = getDataInterface.getCommunityActivity(snapshot);
        Map<String, BigDecimal> skuIdToActivityPrice =
                communityActivityByIdResponse.getCommunityActivityVO().getSkuList().stream()
                        .collect(HashMap::new,(map, item) -> map.put(item.getSkuId(), item.getPrice()),
                                HashMap::putAll);

        // 社区团购团长信息
        String leaderId = snapshot.getItemGroups().get(0).getCommunityBuyRequest().getLeaderId();
        CommunityLeaderVO communityLeaderVO = getDataInterface.getLeaderInfo(leaderId);
        // 社区团购自提信息
        List<CommunityLeaderPickupPointVO> communityLeaderPickupPointVOS =
                getDataInterface.getCommunityPickupPoint(request.getCommunityPickUpId(), leaderId);
        // 组合返回参数
        return Trade1CommitParam.builder()
                .customerVO(customerVO)
                .goodsInfoTradeVOS(goodsInfoList)
                .goodsTradePluginResponse(goodsTradePluginResponse)
                .snapshot(snapshot)
                .storeVOS(storeVOS)
                .systemPointsConfigQueryResponse(systemPointsConfigQueryResponse)
                .customerDeliveryAddressVO(customerDeliveryAddressVO)
//                .bargainVO(bargainVO)
                .userGiftCardInfoVOList(userGiftCardInfoVOList)
                .communityActivityByIdResponse(communityActivityByIdResponse)
                .communityLeaderPickupPointVOS(communityLeaderPickupPointVOS)
                .communityLeaderVO(communityLeaderVO)
                .skuIdToActivityPrice(skuIdToActivityPrice)
                .build();
    }

    @Override
    public void check(Trade1CommitParam param, TradeCommitRequest request) {
        // 校验会员和积分
        checkInterface.checkCustomerAndPoint(param, request);
        // 校验限售
//        checkInterface.checkRestricted(param, request);
        // 校验其他营销
        checkInterface.checkMarketing(param, request);
        // 校验渠道商品最新价格
        checkInterface.checkGoodsChannelPrice(param,request);
        // 校验优惠券
        checkInterface.checkCoupon(request, param.getGoodsTradePluginResponse(),  param.getSnapshot().getBargain());
        // 校验开票信息
        checkInterface.checkInvoice(request.getStoreCommitInfoList(), param.getStoreVOS());
        // 校验礼品卡
        checkInterface.chekGiftCard(param, request);
        // 社区团购活动校验
        checkInterface.checkCommunityActivity(param);
        // 团长信息校验
        checkInterface.checkCommunityLeader(param);
        // 自提点校验
        checkInterface.checkCommunityPickup(param);
    }

    @Override
    public List<Trade> process(Trade1CommitParam param, TradeCommitRequest request) {
        // 构建订单信息
        List<Trade> tradeList = communityCommitProcessService.buildTrade(request, param);
        // 订单营销处理
        communityCommitProcessService.marketingProcess(tradeList, param, request);
        // 订单优惠券处理（店铺券、平台券）
        communityCommitProcessService.couponProcess(tradeList, request);
        // 订单价格处理
        communityCommitProcessService.priceProcess(tradeList);
        // 积分处理
        communityCommitProcessService.pointProcess(tradeList, request, param);
        // 运费处理
        communityCommitProcessService.freightProcess(tradeList, request, param);
        // 审核状态处理，（拼团、预售、第三方、代客下单不需要审核）
        communityCommitProcessService.auditStateProcess(tradeList, request);
        // 配置处理
        communityCommitProcessService.settingProcess(tradeList);
        // 礼品卡
        communityCommitProcessService.giftCardProcess(tradeList, request, param);
        // 社区团购
        communityCommitProcessService.communityCommissionProcess(tradeList, param);
        // 满返处理，因为需处理价格，所以放到最后
        communityCommitProcessService.fullReturnProcess(tradeList, param);
        return tradeList;
    }

    @Override
    public List<Trade> create(List<Trade> tradeList, TradeCommitRequest request) {
        // 创建拼团订单
        createInterface.createGrouponInstance(tradeList);
        // 创建支付单
        createInterface.createPayOrder(tradeList, request);
        // 创建订单组（仅供平台券使用的区分）
        createInterface.createTradeGroup(tradeList, request);
        // 扣减库存
        createInterface.minusStock(tradeList);
        // 扣减赠品库存
        createInterface.minusGiftStock(tradeList);
        // 扣减积分
        createInterface.minusPoint(tradeList);
        // 扣减优惠券，及优惠券核销
        createInterface.minusCoupon(tradeList, request);
        // 创建订单
        createInterface.createTrade(tradeList, request);
        // 创建第三方订单，包含供应商、渠道商品（linkedMall、VOP）
        createInterface.createThirdPlatformTrade(tradeList);
        // 创建订单超时未支付mq，拼团订单默认是五分钟，其他订单按照后台的设置设置超时时间
        createInterface.createTimeOutMQ(tradeList);
        // 同步代销渠道
        createInterface.createSellPlatformTrade(tradeList, request);
        return tradeList;
    }

    @Override
    public List<TradeCommitResult> afterCommit(
            List<Trade> tradeList, TradeCommitRequest request, TradeItemSnapshot snapshot) {
        // 发送短信、站内信、apppush、商家消息
        afterInterface.sendMessage(tradeList);

        // 添加限售记录
//        afterInterface.addRestrictedRecord(tradeList);

        // 移除订单快照
        afterInterface.removeSnapshot(request.getTerminalToken());

        // 订单送积分处理
        afterInterface.dealOrderPointsIncrease(tradeList);

        // 返回参数处理
        return afterInterface.returnProcess(tradeList);
    }
}
