package com.wanmi.sbc.init;

import com.wanmi.sbc.account.api.constant.AccountRedisKey;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsProvider;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 会员资金初始化Redis
 *
 * @author: Geek Wang
 * @createDate: 2019/4/25 10:00
 * @version: 1.0
 */
@Slf4j
@Order(1)
@Component
public class CustomerFundsRunner implements CommandLineRunner {

    @Autowired private RedisUtil redisService;

    @Autowired private CustomerFundsProvider customerFundsProvider;

    @Override
    public void run(String... args) {
        String zero = "0.00";
        String accountBalanceTotal =
                Objects.toString(
                        redisService.getValueByKey(AccountRedisKey.ACCOUNT_BALANCE_TOTAL), zero);
        String blockedBalanceTotal =
                Objects.toString(
                        redisService.getValueByKey(AccountRedisKey.BLOCKED_BALANCE_TOTAL), zero);
        String withdrawAmountTotal =
                Objects.toString(
                        redisService.getValueByKey(AccountRedisKey.WITHDRAW_AMOUNT_TOTAL), zero);

        if (!zero.equals(accountBalanceTotal)
                && !zero.equals(blockedBalanceTotal)
                && !zero.equals(withdrawAmountTotal)) {
            log.info("==============会员资金初始化Redis，统计数据存在，无需初始化====================");
            return;
        }
        BaseResponse baseResponse = null;
        try {
            baseResponse = customerFundsProvider.initStatisticsCache();
        } catch (Exception e) {
			log.error("会员资金初始化Redis, 错误信息", e);
        }
        if (Objects.nonNull(baseResponse)) {
            log.info(
                    "========会员资金初始化Redis，是否成功：{}==============",
                    baseResponse.getContext().equals(Boolean.TRUE) ? "成功" : "失败");
        } else {
            log.info("========会员资金初始化Redis，失败==============");
        }
    }
}
