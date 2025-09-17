package com.wanmi.sbc.mq.delay.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.util.NamedThreadFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhanggaolei
 * @className Timer
 * @description TODO
 * @date 2021/9/14 4:39 下午
 */
@Slf4j
public class Timer {

    /** 最底层的那个时间轮 */
    private TimeWheel timeWheel;

    /** 对于一个Timer以及附属的时间轮，都只有一个delayQueue */
    private DelayQueue<Bucket> delayQueue = new DelayQueue<>();

    /** 只有一个线程的无限阻塞队列线程池 */
    private ExecutorService workerThreadPool;

    private ExecutorService bossThreadPool;

    private static volatile Timer INSTANCE;

    private AtomicLong consumeCounter = new AtomicLong(0);

    public static Timer getInstance() {
        if (INSTANCE == null) {
            synchronized (Timer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Timer();
                }
            }
        }
        return INSTANCE;
    }

    /** 新建一个Timer，同时新建一个时间轮 */
    public Timer() {
        //        workerThreadPool =
        //                Executors.newFixedThreadPool(
        //                        10,
        //                        new ThreadFactoryBuilder()
        //                                .setPriority(10)
        //                                .setNameFormat("TimeWheelWorker")
        //                                .build());

        workerThreadPool =
                new ThreadPoolExecutor(
                        Runtime.getRuntime().availableProcessors() * 2-1,
                        Runtime.getRuntime().availableProcessors() * 2-1,
                        60L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue(),
                        new NamedThreadFactory("TimeWheelWorker")
                        );

        bossThreadPool =
                Executors.newFixedThreadPool(
                        1,
                        new NamedThreadFactory("TimeWheelBoss"));
        timeWheel = new TimeWheel(1, 20, System.currentTimeMillis(), delayQueue);

        bossThreadPool.submit(
                () -> {
                    while (true) {
                        INSTANCE.advanceClock(20);
                    }
                });
    }

    /** 将任务添加到时间轮 */
    public void addTask(TimedTask timedTask) {
        if (!timeWheel.addTask(timedTask)) {
            if (!timedTask.isCancel()) {
                workerThreadPool.submit(timedTask.getTask());
            }
        }
    }

    /** 推进一下时间轮的指针，并且将delayQueue中的任务取出来再重新扔进去 */
    private void advanceClock(long timeout) {
        try {
            Bucket bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                timeWheel.advanceClock(bucket.getExpire());
                bucket.flush(this::addTask);
         //       bucket = delayQueue.poll();
            }
        } catch (Exception e) {
            log.error("error：",e);
        }
    }
}
