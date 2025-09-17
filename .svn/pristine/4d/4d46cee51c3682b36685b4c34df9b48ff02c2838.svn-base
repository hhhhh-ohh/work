package com.wanmi.ares.source.mq;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.report.flow.service.FlowReportService;
import com.wanmi.ares.report.flow.service.FollowMarketingReportService;
import com.wanmi.ares.request.mq.FlowRequest;
import com.wanmi.ares.request.mq.MarketingSkuSource;
import com.wanmi.ares.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息接收处理Bean
 * <p>
 * 为了对具体消息的生产消费情况做更好地监控，消息以业务中最小粒度的操作区分
 * </p>
 * Created by of628-wenzhi on 2017-10-10-下午2:19.
 */
@Slf4j
@Component
public class MessageConsumer {

    @Autowired
    private FlowReportService flowReportService;

    @Autowired
    private FollowMarketingReportService followMarketingReportService;

    /**
     * 流量信息同步
     *
     * @param json
     */
    public void flowSynchronization(String json) {
        log.info("MessageConsumer flowSynchronization call params is {} ", json);
        try {
            FlowRequest request = JSONObject.parseObject(json, FlowRequest.class);
            request.setId(Constants.COMPANY_ID);
            flowReportService.update(request);
        } catch (Exception e) {
            log.error("Activemq consumer execute method [customer.synchronization] error, param={}", json, e);
        }
    }

    /**
     * 营销流量信息同步
     *
     * @param json
     */
    public void flowMarketing(String json) {
        try {
            log.info("营销流量信息同步,Q_FOLLOW_MARKETING_SKU=== {} ",json);
            List<MarketingSkuSource> request = JSON.parseArray(json, MarketingSkuSource.class);
            followMarketingReportService.save(request);
        } catch (Exception e) {
            log.error("Activemq consumer execute method [customer.synchronization] error, param={}", json, e);
        }
    }
}
