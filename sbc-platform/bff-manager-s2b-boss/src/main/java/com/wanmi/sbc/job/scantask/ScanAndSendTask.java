package com.wanmi.sbc.job.scantask;

/**
 * @description 扫描分发MQ消息定时任务接口
 * @author malianfeng
 * @date 2021/9/9 14:26
 */
public interface ScanAndSendTask {

    /**
     * 扫描业务表并发送消息至MQ
     */
    void scanAndSendMQ();
}
