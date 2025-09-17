package com.wanmi.sbc.job;

import com.wanmi.sbc.order.api.provider.pointstrade.PointsTradeQueryProvider;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName PointsOrderAutoReceiveJobHandle
 * @Description 积分订单自动确认收货定时任务
 * @Author lvzhenwei
 * @Date 2019/5/28 9:51
 **/
@Component
@Slf4j
public class PointsOrderAutoReceiveJobHandle {

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private PointsTradeQueryProvider pointsTradeQueryProvider;

    @XxlJob(value = "pointsOrderAutoReceiveJobHandle")
    public void execute() throws Exception {
        // 订单自动确认收货
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_AUTO_RECEIVE);
        Integer autoReceive = auditQueryProvider
                .getTradeConfigByType(request).getContext().getStatus();
        // 开关只有开的时候才执行
        if (Integer.valueOf(1).equals(autoReceive)) {
            pointsTradeQueryProvider.pointsOrderAutoReceive();
        }
    }
}
