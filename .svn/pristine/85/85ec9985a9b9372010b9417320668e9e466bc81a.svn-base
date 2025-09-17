package com.wanmi.sbc.marketing.util.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MarketingSnowFlake
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/3/17 17:38
 * @Version 1.0
 **/
@Slf4j
public class MarketingSnowFlake {

    /**
     * 起始的时间戳 从2021-01-01 00:00:00起
     */
    private final static long START_STMP = 1609430405000L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 6;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 4;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public MarketingSnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

//    public static void main(String[] args) {
//        //        Executor executor = new ThreadPoolExecutor(20, 20, 100L, TimeUnit.MILLISECONDS,
//        // new LinkedBlockingDeque<Runnable>());
//        //        System.out.println(DATACENTER_LEFT);
//        //        System.out.println(TIMESTMP_LEFT);
//        //
//        //        for (int i = 0; i < 8; i++) {
//        //
//        //            MarketingSnowFlake snowFlake = new MarketingSnowFlake(1, i+1);
//        //            executor.execute(new Test(snowFlake));
//        //        }
//        System.out.printf(toBinaryStringWithSplitSymbol(263313533448765467L));
//    }
//    static class Test implements Runnable {
//        MarketingSnowFlake snowFlake;
//
//        public Test(MarketingSnowFlake snowFlake){
//            this.snowFlake = snowFlake;
//        }
//
//        @Override
//        public void run() {
//            for (int i = 0; i < (1 << 10); i++) {
//                log.info("dataId:{}-mid:{}-id:{}",snowFlake.datacenterId, snowFlake.machineId,snowFlake.nextId());
//
//            }
//        }
//    }
//
//    static String toBinaryStringWithSplitSymbol(long snowId) {
//
//        char[] bId = Long.toBinaryString(snowId).toCharArray();
//        StringBuilder sb = new StringBuilder();
//        //标志位
//        sb.append("0").append('-');
//
//        //时间戳
//        long timestamp = bId.length - (MACHINE_BIT + DATACENTER_BIT + SEQUENCE_BIT);
//        long zero = 41 - timestamp;
//        //时间戳前面补全0
//        for (int i = 0; i < zero; i++) {
//            sb.append(0);
//        }
//        for (int i = 0; i < timestamp; i++) {
//            sb.append(bId[i]);
//        }
//        sb.append('-');
//
//        //data center + work
//        long workLength = MACHINE_BIT + DATACENTER_BIT;
//        for (long i = timestamp; i < timestamp + workLength; i++) {
//            sb.append(bId[(int) i]);
//        }
//        sb.append('-');
//        //num seq
//        for (long i = timestamp + workLength; i < bId.length; i++) {
//            sb.append(bId[(int) i]);
//        }
//        return sb.toString();
//    }



}
