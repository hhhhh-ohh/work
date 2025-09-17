package com.wanmi.sbc.job;

import cn.hutool.core.date.DateUtil;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.AppointmentShipmentQueryRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AppointmentShipmentPushH5 {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;
    @XxlJob(value = "appointmentShipmentPushH5")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        log.info("进件AppointmentShipmentPushH5 start:{}", param);
        Date currentDate = new Date();
        if (StringUtils.isNotEmpty(param)) {
            currentDate = DateUtil.parseDate(param);
        }
        String startTime = DateUtil.format(DateUtil.beginOfDay(currentDate) , "yyyy-MM-dd HH:mm:ss");
        String endTime = DateUtil.format(DateUtil.endOfDay(currentDate) , "yyyy-MM-dd HH:mm:ss");
        AppointmentShipmentQueryRequest appointmentShipmentQueryRequest = new AppointmentShipmentQueryRequest();
        appointmentShipmentQueryRequest.setStartTime(startTime);
        appointmentShipmentQueryRequest.setEndTime(endTime);
        List<String> trades = tradeQueryProvider.findExpiredAppointmentTradeIds(appointmentShipmentQueryRequest).getContext();
        log.info("查询预约发货订单:{},开始时间:{},结束时间:{}", trades, startTime, endTime);
    }
}
