package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.wanmi.sbc.customer.bean.vo.CustomerDeliveryAddressVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainSaveProvider;
import com.wanmi.sbc.marketing.api.request.bargain.UpdateTradeRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zhanggaolei
 * @className TradeCommitServiceImpl
 * @description TODO
 * @date 2022/2/21 2:55 下午
 */
@Service("commitService")
public class Trade1CommitService implements Trade1CommitInterface {

    @Autowired Trade1CommitCheckInterface checkInterface;

    @Autowired Trade1CommitRequestInterface requestInterface;

    @Autowired Trade1CommitGetDataInterface getDataInterface;

    @Autowired Trade1CommitCreateInterface createInterface;

    @Autowired Trade1CommitProcessInterface trade1CommitProcessService;

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
        goodsInfoList =
                getDataInterface.getStock(
                        goodsInfoList, snapshot);
        if (!Objects.equals(Boolean.TRUE, snapshot.getBargain())) {
            // 获取分销配置
            goodsInfoList = getDataInterface.getDistribution(goodsInfoList, request);
        }

        // 获取积分配置
        SystemPointsConfigQueryResponse systemPointsConfigQueryResponse =
                getDataInterface.getPointSetting(goodsInfoList, request);

        // 获取营销
        GoodsTradePluginResponse goodsTradePluginResponse =
                getDataInterface.getMarketing(goodsInfoList, snapshot, customerVO);
        // 获取配置
        getDataInterface.getSetting();

        //获取砍价信息
        BargainVO bargainVO = getDataInterface.getBargain(snapshot);

        // 获取礼品卡信息
        List<UserGiftCardInfoVO> userGiftCardInfoVOList =
                getDataInterface.getUserGiftCardInfo(
                        goodsInfoList, request, snapshot);
        // 组合返回参数
        return Trade1CommitParam.builder()
                .customerVO(customerVO)
                .goodsInfoTradeVOS(goodsInfoList)
                .goodsTradePluginResponse(goodsTradePluginResponse)
                .snapshot(snapshot)
                .storeVOS(storeVOS)
                .systemPointsConfigQueryResponse(systemPointsConfigQueryResponse)
                .customerDeliveryAddressVO(customerDeliveryAddressVO)
                .bargainVO(bargainVO)
                .userGiftCardInfoVOList(userGiftCardInfoVOList)
                .build();
    }

    @Override
    public void check(Trade1CommitParam param, TradeCommitRequest request) {
        // 校验会员和积分
        checkInterface.checkCustomerAndPoint(param, request);

        // 校验自提
        param.setPickSettingInfoMap(checkInterface.checkPickUp(request, param));

        boolean buyCycleFlag = param.getSnapshot().getItemGroups().stream()
                .anyMatch(tradeItemGroup -> tradeItemGroup.getOrderTag() != null
                        && Boolean.TRUE.equals(tradeItemGroup.getOrderTag().getBuyCycleFlag()));
        //砍价订单处理
        if (!Objects.equals(Boolean.TRUE, param.getSnapshot().getBargain())) {
            if (!buyCycleFlag) {
                // 校验限售
                checkInterface.checkRestricted(param, request);
            }
            // 校验开店礼包
            checkInterface.checkStoreBags(param.getSnapshot());
            // 校验组合购
            checkInterface.checkSuitMarketing(
                    param.getSnapshot(), param.getCustomerVO().getCustomerId(), request.forceCommit);
            // 校验其他营销
            checkInterface.checkMarketing(param, request);
        }

        // 校验渠道商品最新价格
        checkInterface.checkGoodsChannelPrice(param,request);

        // 校验优惠券
        checkInterface.checkCoupon(request, param.getGoodsTradePluginResponse(),  param.getSnapshot().getBargain());

        // 校验开票信息
        checkInterface.checkInvoice(request.getStoreCommitInfoList(), param.getStoreVOS());

        // 校验礼品卡
        checkInterface.chekGiftCard(param, request);
    }

    @Override
    public List<Trade> process(Trade1CommitParam param, TradeCommitRequest request) {
        // 构建订单信息
        List<Trade> tradeList = trade1CommitProcessService.buildTrade(request, param);
        // 订单营销处理
        trade1CommitProcessService.marketingProcess(tradeList, param, request);
        // 订单优惠券处理（店铺券、平台券）
        trade1CommitProcessService.couponProcess(tradeList, request);
        // 订单价格处理
        trade1CommitProcessService.priceProcess(tradeList);
        // 积分处理
        trade1CommitProcessService.pointProcess(tradeList, request, param);
        // 运费处理
        trade1CommitProcessService.freightProcess(tradeList, request, param);
        // 分销处理
        trade1CommitProcessService.distributionProcess(tradeList, request, param);
        // 审核状态处理，（拼团、预售、第三方、代客下单不需要审核）
        trade1CommitProcessService.auditStateProcess(tradeList, request);
        // 配置处理
        trade1CommitProcessService.settingProcess(tradeList);
        // 礼品卡
        trade1CommitProcessService.giftCardProcess(tradeList, request, param);
        // 满返处理，因为需处理价格，所以放到最后
        trade1CommitProcessService.fullReturnProcess(tradeList, param);
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
        // 扣减拼团活动商品数量
        createInterface.minusGrouponStock(tradeList);
        // 扣减预售活动商品数量
        createInterface.minusBookingSaleStock(tradeList);
        // 扣减积分
        createInterface.minusPoint(tradeList);
        // 扣减优惠券，及优惠券核销
        createInterface.minusCoupon(tradeList, request);
        // 创建订单
        createInterface.createTrade(tradeList, request);
        //砍价订单 订单号
        Optional<Trade> optional = tradeList.stream().filter(v -> Boolean.TRUE.equals(v.getBargain())).findFirst();
        if (optional.isPresent()) {
            Trade trade = optional.get();
            bargainSaveProvider.commitTrade(new UpdateTradeRequest(trade.getBargainId(), trade.getId()));
        }
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

        if(Objects.isNull(snapshot.getBargain()) || Objects.equals(Boolean.FALSE, snapshot.getBargain())) {
            // 移除购物车商品
            afterInterface.removePurchaseGoodsInfo(tradeList, snapshot, request);

            // 添加限售记录
            afterInterface.addRestrictedRecord(tradeList);
        }

        //移除周期购缓存
        afterInterface.removeCycleSnapshot(tradeList, request.getTerminalToken());

        // 移除订单快照
        afterInterface.removeSnapshot(request.getTerminalToken());

        // 订单送积分处理
        afterInterface.dealOrderPointsIncrease(tradeList);
        // 返回参数处理
        List<TradeCommitResult> results = afterInterface.returnProcess(tradeList);
        return results;
    }
}
