package com.wanmi.sbc.job.credit;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditOverviewProvider;
import com.wanmi.sbc.account.api.response.credit.CreditOverviewResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountStatisticsResponse;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordCreditStatisticsResponse;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 陈莉
 * @date 2021/4/22 09:49
 * @description
 *     <p>授信额度统计对比
 */
@Component
public class CreditStatisticsJobHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private CreditOverviewProvider creditOverviewProvider;

    @Autowired private CreditAccountQueryProvider creditAccountQueryProvider;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @XxlJob(value = "creditStatisticsJobHandler")
    public void execute() throws Exception {
        // 发消息统计的授信数据
        CreditOverviewResponse overviewResponse =
                creditOverviewProvider.findCreditOverview().getContext();

        // 数据库sql语句查询的授信数据
        CreditAccountStatisticsResponse accountStatisticsRes =
                creditAccountQueryProvider.findCustomerCreditAccountStatistics().getContext();
        PayTradeRecordCreditStatisticsResponse payStatisticsRes =
                payTradeRecordQueryProvider.getPayTradeRecordCreditStatistics().getContext();

        // 判断统计数据是否与查询数据相等，一个个数据比对，打印数据有误时的错误日志
        if (overviewResponse.getTotalCustomer() != accountStatisticsRes.getCreditAccountNum().intValue()
                || overviewResponse.getTotalRepayAmount().compareTo(accountStatisticsRes.getCreditRepayAmount()) != 0
                || overviewResponse.getTotalUsableAmount().compareTo(accountStatisticsRes.getCreditUsableAmount()) != 0
                || overviewResponse.getTotalUsedAmount().compareTo(payStatisticsRes.getCreditUsedAmount()) != 0
                || overviewResponse.getTotalRepaidAmount().compareTo(payStatisticsRes.getCreditHasRepaidAmount()) != 0
        ) {
            logger.error(
                    "统计数据与查询数据不相等，统计数据为：{}，查询account服务数据为：{}，查询pay服务数据为：{}",
                    JSONObject.toJSONString(overviewResponse),
                    JSONObject.toJSONString(accountStatisticsRes),
                    JSONObject.toJSONString(payStatisticsRes));
        }
    }
}
