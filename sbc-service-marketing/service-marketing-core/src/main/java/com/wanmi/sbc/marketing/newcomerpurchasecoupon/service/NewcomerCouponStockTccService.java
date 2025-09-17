package com.wanmi.sbc.marketing.newcomerpurchasecoupon.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.marketing.bean.constant.CouponErrorCode;
import com.wanmi.sbc.marketing.bean.dto.NewcomerCouponStockDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.model.root.NewcomerPurchaseCoupon;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.repository.NewcomerPurchaseCouponRepository;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author xuyunpeng
 * @className NewcomerCouponStockTccService
 * @description
 * @date 2022/8/26 11:32 AM
 **/
@Slf4j
@Service
public class NewcomerCouponStockTccService implements NewcomerCouponStockTccInterface{

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private NewcomerPurchaseCouponRepository newcomerPurchaseCouponRepository;

    @Override
    @Transactional
    public boolean subStock(List<NewcomerCouponStockDTO> dtoList) {
        String xid = RootContext.getXID();
        String key = RedisKeyConstant.NEWCOMER_STOCK_TCC+xid;
        dtoList.forEach(dto -> subStock(dto.getCouponId(), dto.getStock(), key));
        return true;
    }

    @Override
    public boolean subCommit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        String key = RedisKeyConstant.NEWCOMER_STOCK_TCC+xid;
        redisUtil.delete(key);
        return true;
    }

    @Override
    @Transactional
    public boolean subRollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        String key = RedisKeyConstant.NEWCOMER_STOCK_TCC+xid;
        this.subRollback(key);
        return true;
    }

    @Override
    @Transactional
    public boolean addStock(List<NewcomerCouponStockDTO> dtoList) {
        String xid = RootContext.getXID();
        String key = RedisKeyConstant.NEWCOMER_STOCK_TCC + xid;
        dtoList.forEach(dto -> addStock(dto.getCouponId(), dto.getStock(), key));
        return true;
    }

    @Override
    public boolean addCommit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        String key = RedisKeyConstant.NEWCOMER_STOCK_TCC+xid;
        redisUtil.delete(key);
        return true;
    }

    @Override
    @Transactional
    public boolean addRollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        String key = RedisKeyConstant.NEWCOMER_STOCK_TCC+xid;
        this.addRollback(key);
        return true;
    }

    private void subStock(String couponId, Long stock, String key) {
        checkStock(couponId);
        //redis扣减
        Long count = redisUtil.decrByKey(RedisKeyConstant.NEW_CUSTOMER_COUPON_STOCK.concat(couponId), stock);
        if (count < 0) {
            redisUtil.incrByKey(RedisKeyConstant.NEW_CUSTOMER_COUPON_STOCK.concat(couponId), stock);
            log.info("redis中库存不足，返还redis库存, couponId={}，stock={}", couponId, stock);
            throw new RuntimeException(MarketingErrorCodeEnum.K080062.getCode());
        }

        //数据库扣减
        newcomerPurchaseCouponRepository.subCouponStock(couponId, stock);
        log.info("扣减新人券库存结束：couponId={}，扣减数量：{}，扣减后库存：{}", couponId, stock, count);

        redisUtil.hset(key, couponId, stock.toString());
    }

    /**
     * 扣减库存回滚
     * @param key
     */
    private void subRollback(String key) {
        Map<String, String> stockMap = redisUtil.hgetall(key);
        if (MapUtils.isNotEmpty(stockMap)) {
            stockMap.entrySet().forEach(entry -> {
                String redisKey = RedisKeyConstant.NEW_CUSTOMER_COUPON_STOCK.concat(entry.getKey());
                Long stock = Long.parseLong(entry.getValue());
                redisUtil.incrByKey(redisKey, stock);
            });
            redisUtil.delete(key);
        }
    }

    private void addStock(String couponId, Long stock, String key) {
        checkStock(couponId);
        Long count = redisUtil.incrByKey(RedisKeyConstant.NEW_CUSTOMER_COUPON_STOCK.concat(couponId), stock);
//        newcomerPurchaseCouponRepository.addCouponStock(couponId, stock);
        log.info("增加新人券库存结束：couponId={}，增加数量：{}，增加后库存：{}", couponId, stock, count);
        redisUtil.hset(key, couponId, stock.toString());
    }

    /**
     * 增加后回滚
     * @param key
     */
    private void addRollback(String key) {
        Map<String, String> stockMap = redisUtil.hgetall(key);
        if (MapUtils.isNotEmpty(stockMap)) {
            stockMap.entrySet().forEach(entry -> {
                String redisKey = RedisKeyConstant.NEW_CUSTOMER_COUPON_STOCK.concat(entry.getKey());
                Long stock = Long.parseLong(entry.getValue());
                redisUtil.decrByKey(redisKey, stock);
            });
            redisUtil.delete(key);
        }
    }

    private void checkStock(String couponId) {
        Object stockObject = redisUtil.getValueByKey(RedisKeyConstant.NEW_CUSTOMER_COUPON_STOCK.concat(couponId));

        if (stockObject == null) {
            log.info("redis中无新人券{}的库存，准备重载新人券库存...", couponId);
            NewcomerPurchaseCoupon coupon = newcomerPurchaseCouponRepository.findByCouponIdAndDelFlag(couponId, DeleteFlag.NO);
            if (coupon == null) {
                log.error("新人券{}不存在", couponId);
                throw new RuntimeException(MarketingErrorCodeEnum.K080046.getCode());
            }
            Long stock = coupon.getCouponStock() == null ? NumberUtils.LONG_ZERO : coupon.getCouponStock();
            redisUtil.incrByKey(RedisKeyConstant.NEW_CUSTOMER_COUPON_STOCK.concat(couponId), coupon.getCouponStock());
            log.info("redis中新人券{}的库存重载完毕，重载后的库存为{}...", couponId, stock);
        }
    }
}
