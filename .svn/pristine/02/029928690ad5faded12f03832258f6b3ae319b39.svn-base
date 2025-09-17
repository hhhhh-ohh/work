package com.wanmi.sbc.mq.report.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * @author edz
 * @className ReportThreadPool
 * @description 报表导出线程池
 * @date 2021/6/2 2:37 下午
 **/
@Configuration
@EnableAsync
@EnableConfigurationProperties(ReportThreadPoolConfig.class)
public class ReportThreadPool {

    @Autowired
    private ReportThreadPoolConfig reportThreadPoolConfig;

    @Bean("reportExecutor")
    public Executor myExecutor(){
        LinkedBlockingQueue reportQueue = new LinkedBlockingQueue<Runnable>(reportThreadPoolConfig.getQueueCapacity());
        ThreadFactory factory = new CustomizableThreadFactory(reportThreadPoolConfig.getThreadNamePrefix());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                reportThreadPoolConfig.getCorePoolSize(),
                reportThreadPoolConfig.getMaxPoolSize(),
                reportThreadPoolConfig.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                reportQueue,
                factory,
                new ThreadPoolExecutor.AbortPolicy());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }
}
