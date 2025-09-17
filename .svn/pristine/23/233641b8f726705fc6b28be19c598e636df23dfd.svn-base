package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.wanmi.sbc.customer.bean.vo.CustomerDeliveryAddressVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainSaveProvider;
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

/**
 * @author edz
 * @className TradePickupCardCommitService
 * @description TODO
 * @date 2023/7/3 16:58
 **/
@Service("pickupCardCommitService")
public class TradePickupCardCommitService implements Trade1CommitInterface {
    @Autowired
    Trade1CommitCheckInterface checkInterface;

    @Autowired
    Trade1CommitRequestInterface requestInterface;

    @Autowired
    Trade1CommitGetDataInterface getDataInterface;

    @Autowired
    Trade1CommitCreateInterface createInterface;

    @Autowired
    Trade1CommitProcessInterface packupCardCommitProcessService;

    @Autowired Trade1CommitAfterInterface afterInterface;

    @Autowired
    BargainSaveProvider bargainSaveProvider;

    @Autowired
    DistributionCacheService distributionCacheService;

    /**
     * 请求参数处理
     *
     * @param request
     */
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

        // 获取积分配置
        SystemPointsConfigQueryResponse systemPointsConfigQueryResponse =
                getDataInterface.getPointSetting(goodsInfoList, request);

        // 获取礼品卡信息
        List<UserGiftCardInfoVO> userGiftCardInfoVOList =
                getDataInterface.getUserGiftCardInfo(
                        goodsInfoList, request, snapshot);
        // 组合返回参数
        return Trade1CommitParam.builder()
                .customerVO(customerVO)
                .goodsInfoTradeVOS(goodsInfoList)
                .snapshot(snapshot)
                .systemPointsConfigQueryResponse(systemPointsConfigQueryResponse)
                .storeVOS(storeVOS)
                .customerDeliveryAddressVO(customerDeliveryAddressVO)
                .userGiftCardInfoVOList(userGiftCardInfoVOList)
                .build();
    }

    @Override
    public void check(Trade1CommitParam param, TradeCommitRequest request) {

        // 校验自提
        param.setPickSettingInfoMap(checkInterface.checkPickUp(request, param));

        // 校验渠道商品最新价格
        checkInterface.checkGoodsChannelPrice(param,request);

        // 校验礼品卡
        checkInterface.chekGiftCard(param, request);
    }

    @Override
    public List<Trade> process(Trade1CommitParam param, TradeCommitRequest request) {
        // 构建订单信息
        List<Trade> tradeList = packupCardCommitProcessService.buildTrade(request, param);
        // 订单价格处理
        packupCardCommitProcessService.priceProcess(tradeList);
        // 积分处理
        packupCardCommitProcessService.pointProcess(tradeList, request, param);
        // 运费处理
        packupCardCommitProcessService.freightProcess(tradeList, request, param);
        // 审核状态处理，（拼团、预售、第三方、代客下单不需要审核）
        packupCardCommitProcessService.auditStateProcess(tradeList, request);
        // 礼品卡
        packupCardCommitProcessService.giftCardProcess(tradeList, request, param);
        return tradeList;
    }

    @Override
    public List<Trade> create(List<Trade> tradeList, TradeCommitRequest request) {
        // 创建支付单
        createInterface.createPayOrder(tradeList, request);
        // 创建订单组（仅供平台券使用的区分）
        createInterface.createTradeGroup(tradeList, request);
        // 扣减库存
        createInterface.minusStock(tradeList);
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

        // 移除订单快照
        afterInterface.removeSnapshot(request.getTerminalToken());

        // 返回参数处理
        return afterInterface.returnProcess(tradeList);
    }
}
