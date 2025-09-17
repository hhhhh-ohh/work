package com.wanmi.sbc.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * @ClassName PayCallBackThreadPool
 * @Description 支付回调线处理程池
 * @Author lvzhenwei
 * @Date 2020/6/30 14:12
 **/
@Configuration
@EnableAsync
public class PayCallBackThreadPool {

    @Value("${payCallBackThreadPool.corePoolSize}")
    private Integer corePoolSize;

    @Value("${payCallBackThreadPool.maxiNumSize}")
    private Integer maxiNumSize;

    @Value("${payCallBackThreadPool.linkBlockQueueSize}")
    private Integer linkBlockQueueSize;

    @Value("${payCallBackThreadPool.keepAliveTime}")
    private Integer keepAliveTime;

    private static final String THREAD_NAME = "paycallback";

    @Bean(name = "payCallBack")
    public Executor myExecutor(){
        LinkedBlockingQueue payCallBacklinkedBlockingQueue = new LinkedBlockingQueue<Runnable>(linkBlockQueueSize);
        ThreadFactory factory = new CustomizableThreadFactory(THREAD_NAME);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxiNumSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                payCallBacklinkedBlockingQueue,
                factory,
                new ThreadPoolExecutor.AbortPolicy());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

}
