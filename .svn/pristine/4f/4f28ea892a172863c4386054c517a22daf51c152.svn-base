package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-04-08 21:58
 */
@Service
public class TradeItemSnapshotService {

    @Autowired
    private RedisUtil redisService;

    /**
     * 新增文档
     * 专门用于数据新增服务,不允许数据修改的时候调用
     *
     * @param tradeItemSnapshot
     */
    public void addTradeItemSnapshot(TradeItemSnapshot tradeItemSnapshot) {
        redisService.setObj(RedisKeyConstant.TRADE_ITME_SNAPSHOT+tradeItemSnapshot.getTerminalToken(),
                tradeItemSnapshot,
                24*60*60L);
    }

    /**
     * 修改文档
     * 专门用于数据修改服务,不允许数据新增的时候调用
     *
     * @param tradeItemSnapshot
     */
    public void updateTradeItemSnapshot(TradeItemSnapshot tradeItemSnapshot) {
        redisService.setObj(RedisKeyConstant.TRADE_ITME_SNAPSHOT+tradeItemSnapshot.getTerminalToken(),
                tradeItemSnapshot,
                24*60*60L);
    }

    /**
     * 删除文档
     *
     * @param terminalToken
     */
    public void deleteTradeItemSnapshot(String terminalToken) {
        redisService.delete(RedisKeyConstant.TRADE_ITME_SNAPSHOT+terminalToken);
    }

    /**
     * 获取文档
     *
     * @param terminalToken
     */
    public TradeItemSnapshot getTradeItemSnapshot(String terminalToken) {
        return redisService.getObj(RedisKeyConstant.TRADE_ITME_SNAPSHOT+terminalToken,TradeItemSnapshot.class);
    }

}
