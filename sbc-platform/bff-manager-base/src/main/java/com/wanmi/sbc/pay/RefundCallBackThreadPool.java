package com.wanmi.sbc.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * @ClassName RefundCallBackThreadPool
 * @Description 退款回调线处理程池
 * @Author Geek Wang
 * @Date 2020/6/30 14:12
 **/
@Configuration
@EnableAsync
@EnableConfigurationProperties(RefundThreadPoolConfig.class)
public class RefundCallBackThreadPool {

    @Autowired
    private RefundThreadPoolConfig refundThreadPoolConfig;

    @Bean(name = "refundCallBack")
    public Executor myExecutor(){
        LinkedBlockingQueue refundCallBacklinkedBlockingQueue =
                new LinkedBlockingQueue<Runnable>(refundThreadPoolConfig.getQueueCapacity());
        ThreadFactory factory = new CustomizableThreadFactory(refundThreadPoolConfig.getThreadNamePrefix());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                refundThreadPoolConfig.getCorePoolSize(),
                refundThreadPoolConfig.getMaxPoolSize(),
                refundThreadPoolConfig.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                refundCallBacklinkedBlockingQueue,
                factory,
                new ThreadPoolExecutor.AbortPolicy());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

}
