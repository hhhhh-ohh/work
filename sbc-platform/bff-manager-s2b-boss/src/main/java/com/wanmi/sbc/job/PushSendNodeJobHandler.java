package com.wanmi.sbc.job;

import com.wanmi.sbc.message.api.provider.pushsendnode.PushSendNodeProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: sbc-micro-service
 * @description: 通知节任务
 * @create: 2020-01-14 17:45
 **/
@Component
@Slf4j
public class PushSendNodeJobHandler {

    @Autowired
    private PushSendNodeProvider pushSendNodeProvider;

    @XxlJob(value = "PushSendNodeJobHandler")
    public void execute() throws Exception {
        pushSendNodeProvider.nodeTask();
    }
}