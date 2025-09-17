package com.wanmi.sbc.message.task;

import com.wanmi.sbc.message.smssend.service.SmsSendService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @Author: zgl
 * @Description:用于xxl-job调度定时发送短信
 * @Date: 2019-11-12
 */
@Component
public class SmsSendTaskGenerate {

    @Resource
    private SmsSendService smsSendService;

    @XxlJob(value = "smsSendTaskGenerate")
    public void execute() throws Exception {
        smsSendService.scanSendTask();
    }
}
