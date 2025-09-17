package com.wanmi.sbc.job;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.order.api.provider.trade.TradeSettingProvider;
import com.wanmi.sbc.order.api.request.trade.ReturnOrderModifyAutoAuditRequest;
import com.wanmi.sbc.order.api.request.trade.ReturnOrderModifyAutoReceiveRequest;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务Handler（Bean模式）
 * 订单定时任务
 */
@Component
@Slf4j
public class OrderJobHandler {
    @Autowired
    private TradeSettingProvider tradeSettingProvider;


    @Autowired
    private AuditQueryProvider auditQueryProvider;

    /**
     * 凌晨一点执行 考虑使用分布式定时任务
     */
    @XxlJob(value = "orderJobHandler")
    public void execute() throws Exception {
        XxlJobHelper.log("订单定时任务执行 " + LocalDateTime.now());
        log.info("订单定时任务执行 {}", LocalDateTime.now());
        // 订单自动确认收货
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_AUTO_RECEIVE);
        Integer autoReceive = auditQueryProvider
                .getTradeConfigByType(request).getContext().getStatus();
        // 退单自动收货
        request.setConfigType(ConfigType.ORDER_SETTING_REFUND_AUTO_RECEIVE);
        ConfigVO returnOrderAutoReceiveConfig = auditQueryProvider
                .getTradeConfigByType(request).getContext();
        // 退单自动审核
        request.setConfigType(ConfigType.ORDER_SETTING_REFUND_AUTO_AUDIT);
        ConfigVO autoAuditConfig = auditQueryProvider
                .getTradeConfigByType(request).getContext();
        // 开关只有开的时候才执行
        if (Integer.valueOf(1).equals(autoReceive)) {
            tradeSettingProvider.orderAutoReceive();
        }

        // 退单自动确认收货
        if (Integer.valueOf(1).equals(returnOrderAutoReceiveConfig.getStatus())) {
            Integer day = Integer.valueOf(JSON.parseObject(returnOrderAutoReceiveConfig.getContext()).get("day").toString());
            tradeSettingProvider.modifyReturnOrderAutoReceive(new ReturnOrderModifyAutoReceiveRequest(day));
        }

        // 退单自动审核
        if (Integer.valueOf(1).equals(autoAuditConfig.getStatus())) {
            Integer day = Integer.valueOf(JSON.parseObject(autoAuditConfig.getContext()).get("day").toString());
            tradeSettingProvider.modifyReturnOrderAutoAudit(new ReturnOrderModifyAutoAuditRequest(day));
        }
    }
}
