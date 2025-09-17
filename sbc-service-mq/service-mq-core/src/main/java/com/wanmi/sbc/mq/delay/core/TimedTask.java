package com.wanmi.sbc.mq.delay.core;

/**
 * @author zhanggaolei
 * @className TimeTask
 * @description TODO
 * @date 2021/9/14 4:14 下午
 **/
public class TimedTask {


    /** 延迟多久执行时间 */
    private long delayMs;

    /** 过期时间戳 */
    private long expireTimestamp;

    /** 任务 */
    private Runnable task;

    /** 是否取消 */
    private volatile boolean cancle;

    protected Bucket bucket;

    /**
     * 下一个节点
     */
    protected TimedTask next;

    /**
     * 上一个节点
     */
    protected TimedTask pre;

    /**
     * 描述
     */
    public String desc;

    public TimedTask(long delayMs, Runnable task) {
        this.delayMs = delayMs;
        this.task = task;
        this.bucket = null;
        this.next = null;
        this.pre = null;
        this.expireTimestamp = System.currentTimeMillis() + delayMs;
        this.cancle = false;
    }

    public void cancel() {
        cancle = true;
    }

    public boolean isCancel() {
        return cancle;
    }

    public Runnable getTask() {
        return task;
    }

    public long getDelayMs() {
        return delayMs;
    }

    public long getExpireTimestamp() {
        return expireTimestamp;
    }

    @Override
    public String toString() {
        return desc;
    }
}
