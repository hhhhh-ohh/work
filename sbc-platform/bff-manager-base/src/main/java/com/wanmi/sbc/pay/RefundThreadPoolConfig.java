package com.wanmi.sbc.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 退款回调线程配置类
 */
@Data
@ConfigurationProperties(prefix = "refund.call.back")
public class RefundThreadPoolConfig {

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
