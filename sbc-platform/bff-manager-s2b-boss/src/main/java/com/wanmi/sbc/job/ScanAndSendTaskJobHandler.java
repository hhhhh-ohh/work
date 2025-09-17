package com.wanmi.sbc.job;

import com.wanmi.sbc.job.scantask.ScanAndSendTask;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author malianfeng
 * @description 扫描各业务表，并分发MQ消息定时任务
 * @date 2021/9/8 19:38
 */
@Component
@Slf4j
public class ScanAndSendTaskJobHandler {

    @Autowired
    private List<ScanAndSendTask> scanAndSendTaskList;

    @XxlJob(value = "ScanAndSendTaskJobHandler")
    public void execute() throws Exception {
        log.info("扫描各个业务表，并分发MQ消息定时任务执行 {}", LocalDateTime.now());
        scanAndSendTaskList.forEach(ScanAndSendTask::scanAndSendMQ);
    }
}
