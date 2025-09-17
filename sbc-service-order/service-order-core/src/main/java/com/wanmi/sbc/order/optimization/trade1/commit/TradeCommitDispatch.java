package com.wanmi.sbc.order.optimization.trade1.commit;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardTransRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardUseResponse;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBusinessType;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitGetDataInterface;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitInterface;
import com.wanmi.sbc.order.trade.model.entity.TradeCommitResult;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author zhanggaolei
 * @className TradeCommitDispatch
 * @description TODO
 * @date 2022/2/17 3:11 下午
 */
@Service
@Slf4j
public class TradeCommitDispatch {
    @Autowired private RedisUtil redisUtil;

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    @Autowired private Trade1CommitGetDataInterface getDataInterface;

    private final static String COMMIT_SERVICE ="commitService";
    private final static String PICKUP_CARD_COMMIT_SERVICE ="pickupCardCommitService";
    private final static String COMMUNITY_COMMIT_SERVICE ="communityCommitService";

    @Transactional
    @GlobalTransactional
    public List<TradeCommitResult> commit(TradeCommitRequest request) {
        Trade1CommitInterface commitInterface = SpringContextHolder.getBean(COMMIT_SERVICE);
        // 获取订单快照
        TradeItemSnapshot snapshot = getDataInterface.getSnapshot(request);
        if (Objects.nonNull(snapshot.getOrderTag()) && snapshot.getOrderTag().getPickupCardFlag()){
            commitInterface = SpringContextHolder.getBean(PICKUP_CARD_COMMIT_SERVICE);
        } else if (Objects.nonNull(snapshot.getOrderTag()) && snapshot.getOrderTag().getCommunityFlag()) {
            commitInterface = SpringContextHolder.getBean(COMMUNITY_COMMIT_SERVICE);
        }
        request = commitInterface.processRequest(request);

        Trade1CommitParam param = commitInterface.getData(request);

        commitInterface.check(param, request);

        List<Trade> tradeList = commitInterface.process(param, request);

        commitInterface.create(tradeList, request);

        return commitInterface.afterCommit(tradeList, request, param.getSnapshot());
    }

    /**
     * @description   订单提交成功
     * @author  wur
     * @date: 2022/12/16 13:38
     * @param request
     * @return
     **/
    public void commitSuccessDelayProcess(TradeCommitRequest request) {
        // 礼品卡缓存删除处理
        String key = CacheKeyConstant.ORDER_COMMIT_GIFT_CARD_SUCCESS+request.getTerminalToken();
        redisUtil.delete(key);
    }

    /**
     * @description   订单提交失败
     * @author  wur
     * @date: 2022/12/16 13:39
     * @param request
     * @return
     **/
    public void commitErrorDelayProcess(TradeCommitRequest request) {
        // 退还礼品卡处理
        String key = CacheKeyConstant.ORDER_COMMIT_GIFT_CARD_SUCCESS+request.getTerminalToken();
        List<UserGiftCardUseResponse> userGiftCardUseList = redisUtil.getList(key, UserGiftCardUseResponse.class);
        if (CollectionUtils.isEmpty(userGiftCardUseList)) {
            return;
        }
        for (UserGiftCardUseResponse useResponse : userGiftCardUseList) {
            try{
                //封装请求
                UserGiftCardTransRequest transRequest = new UserGiftCardTransRequest();
                transRequest.setGiftCardNo(useResponse.getGiftCardNo());
                transRequest.setBusinessType(GiftCardBusinessType.ORDER_CANCEL);
                transRequest.setUserGiftCardId(useResponse.getUserGiftCardId());
                transRequest.setSumTradePrice(useResponse.getUsePrice());
                transRequest.setTransBusinessVOList(useResponse.getTransBusinessVOList());
                transRequest.setCustomerId(request.getOperator().getUserId());
                transRequest.setTradePersonType(DefaultFlag.NO);
                transRequest.setRollback(Boolean.TRUE);
                transRequest.setRollbackBusinessType(GiftCardBusinessType.ORDER_DEDUCTION);
                userGiftCardProvider.returnUserGiftCard(transRequest);
            } catch (Exception e) {
                log.error("gift card return error userGiftCardId={}, returnPrice={}", useResponse.getUserGiftCardId(), useResponse.getUsePrice());
            }
        }
    }
}
