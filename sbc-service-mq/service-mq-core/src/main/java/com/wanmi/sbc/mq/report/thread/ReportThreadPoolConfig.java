package com.wanmi.sbc.mq.report.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xuyunpeng
 * @className ReportThreadPoolConfig
 * @description 报表导出线程池
 * @date 2021/6/2 1:44 下午
 **/
@Data
@ConfigurationProperties(prefix = "report")
public class ReportThreadPoolConfig {

    /**
     * 线程前缀
     */
    private String threadNamePrefix;

    /**
     * 核心线程池大小
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    /**
     * 活跃时间
     */
    private int keepAliveSeconds;

    /**
     * 队列容量
     */
    private int queueCapacity;
}
