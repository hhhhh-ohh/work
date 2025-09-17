package com.wanmi.sbc.order.optimization.trade1.commit.service;

import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.trade.model.entity.PickSettingInfo;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;

import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className TradeCommitCheckServiceInterface
 * @description TODO
 * @date 2022/2/21 3:11 下午
 */
public interface Trade1CommitCheckInterface {

    /**
     * 校验支付类型
     * @param payType
     */
    void checkPayType(PayType payType);


    /**
     * 校验用户
     * @param
     */
    void checkCustomerAndPoint(Trade1CommitParam param, TradeCommitRequest request);

    /**
     * 校验组合购
     *
     * @param
     */
    void checkSuitMarketing( TradeItemSnapshot itemSnapshot,String customerId,boolean isForceCommit);

    /**
     * 校验开店礼包
     *
     * @param
     */
    void checkStoreBags( TradeItemSnapshot itemSnapshot);

    /**
     * 校验营销
     *
     * @param param
     * @param request
     */
    void checkMarketing(Trade1CommitParam param,TradeCommitRequest request);

    /**
     * 校验限售
     *
     * @param param
     * @param request
     */
    void checkRestricted(Trade1CommitParam param, TradeCommitRequest request);

    /**
     * 校验自提
     * @param request
     * @param param
     */
    Map<Long, PickSettingInfo> checkPickUp(
            TradeCommitRequest request,
            Trade1CommitParam param);

    /**
     * 校验开票信息
     * @param storeCommitInfoList
     * @param storeVO
     */
    void checkInvoice(List<StoreCommitInfoDTO> storeCommitInfoList, List<StoreVO> storeVO);

    /**
     * 校验优惠券
     */
    void checkCoupon(TradeCommitRequest request, GoodsTradePluginResponse goodsTradePluginResponse, Boolean bargain);

    /**
     * 校验商品
     * @param goodsInfoList
     * @param tradeItemSnapshot
     */
    void checkGoods(
            List<GoodsInfoTradeVO> goodsInfoList, TradeItemSnapshot tradeItemSnapshot);

    void checkBargain(Trade1CommitParam param);

    void chekGiftCard(Trade1CommitParam param, TradeCommitRequest request);

    void checkGoodsChannelPrice(Trade1CommitParam param, TradeCommitRequest request);

    /**
     * @description 社区团购活动信息校验
     * @author  edz
     * @date: 2023/7/26 17:56
     * @param param
     * @return void
     */
    void checkCommunityActivity(Trade1CommitParam param);

    /**
     * @description 校验社区团购团长信息
     * @author  edz
     * @date: 2023/7/27 15:58
     * @param param
     * @return void
     */
    void checkCommunityLeader(Trade1CommitParam param);

    /**
     * @description 校验社区团购自提点信息
     * @author  edz
     * @date: 2023/7/27 15:58
     * @param param
     * @return void
     */
    void checkCommunityPickup(Trade1CommitParam param);

}
