package com.wanmi.sbc.order.optimization.trade1.commit.service;

import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.trade.model.root.Trade;

import java.util.List;

/**
 * @author zhanggaolei
 */
public interface Trade1CommitProcessInterface {
    /**
     * 拼装订单数据
     */
    List<Trade> buildTrade(TradeCommitRequest tradeCommitRequest, Trade1CommitParam param);

    /**
     * 价格处理
     * @param tradeList
     */
    void priceProcess(List<Trade> tradeList);

    /**
     * 营销处理
     * @param tradeList
     * @param param
     */
    void marketingProcess(List<Trade> tradeList, Trade1CommitParam param, TradeCommitRequest request);

    /**
     * 优惠券处理
     * @param tradeList
     * @param request
     */
    void couponProcess(List<Trade> tradeList,TradeCommitRequest request);

    /**
     * 积分处理
     * @param tradeList
     * @param request
     * @param param
     */
    void pointProcess(List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param);

    /**
     * 运费处理
     * @param tradeList
     */
    void freightProcess(List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param);

    /**
     * @description     礼品卡处理
     * @author  wur
     * @date: 2022/12/14 9:14
     * @param tradeList
     * @param request
     * @param param
     * @return
     **/
    void giftCardProcess(List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param);

    /**
     * 分销处理
     * @param tradeList
     * @param request
     * @param param
     */
    void distributionProcess(List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param);

    /**
     * 审核状态处理
     * @param tradeList
     * @param request
     */
    void auditStateProcess(List<Trade> tradeList, TradeCommitRequest request);

    /**
     * 配置处理
     * @param tradeList
     */
    void settingProcess(List<Trade> tradeList);

    /**
     * 满返处理
     * @param tradeList
     * @param param
     */
    void fullReturnProcess(List<Trade> tradeList, Trade1CommitParam param);

    /**
     * @description 社区团购佣金信息处理
     * @author  edz
     * @date: 2023/7/27 11:11
     * @param tradeList
     * @param param
     * @return void
     */
    void communityCommissionProcess(List<Trade> tradeList, Trade1CommitParam param);

}
