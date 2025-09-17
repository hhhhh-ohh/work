package com.wanmi.sbc.job;

import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhanggaolei
 * @className DelayCompensateJob
 * @description TODO
 * @date 2021/9/24 4:12 下午
 **/
@Component
public class DelayCompensateJob {
    @Autowired
    MqSendProvider mqSendProvider;

    @XxlJob(value = "mqDelayCompensateJob")
    public void execute() throws Exception {
        mqSendProvider.delayProcess();
    }
}
