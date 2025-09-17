package com.wanmi.tools.logtrace.core.context;

import com.wanmi.tools.logtrace.utils.NetUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.UnknownHostException;

/**
 * @author zhanggaolei
 * @description TODO
 * @date 2021/12/15 2:42 下午
 **/
public class TraceIdGenerator {
    private final static long MAX_SEQUENCE = -1L ^ (-1L << 12);
    private final static String FILL_C = "0";
    private static Integer DATA_CENTER_ID = null;
    private static Integer MACHINE_ID  = null;
    private static Integer PID = null;


    private static long lastStmp = -1L;//上一次时间戳
    private static long sequence = 0L; //序列号


    static {
        init();
    }

    private static void init(){
        setIP();
        getPID();
    }

    private static void setIP()  {
        try {
            String ip = NetUtil.getLocalHostLANAddress().getHostAddress();
            if(StringUtils.isBlank(ip)){
                ip = NetUtil.LOCAL_IP;
            }
            if(StringUtils.isNotEmpty(ip)){
                String[] strs = ip.split("\\.");
                if (strs.length>3) {
                    DATA_CENTER_ID = Integer.valueOf(strs[2]);
                    MACHINE_ID = Integer.valueOf(strs[3]);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (DATA_CENTER_ID == null){
            DATA_CENTER_ID = RandomUtils.nextInt(0,255);
        }
        if(MACHINE_ID == null){
            MACHINE_ID = RandomUtils.nextInt(0,255);
        }
    }

    private static void getPID(){
        String name = ManagementFactory.getRuntimeMXBean().getName();
      //  System.out.println(name);
        // get pid
        String strPid = name.split("@")[0];
        PID = Integer.parseInt(strPid);
    }
    private static long getNewstmp() {
        return System.currentTimeMillis();
    }
    private static long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }
    private static long getNextId(){
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
        return lastStmp;
    }

    protected static String generatorTraceId(){
        String dataId_32 = StringUtils.leftPad(Integer.toString(DATA_CENTER_ID,32),2,FILL_C);
        String macheId_32 = StringUtils.leftPad(Integer.toString(MACHINE_ID,32),2,FILL_C);
        String pid_32 = StringUtils.leftPad(Integer.toString(PID,32),3,FILL_C);
        String time_32 = StringUtils.leftPad(Long.toString(getNextId(),32),11,FILL_C);
        StringBuilder traceId = new StringBuilder();
        traceId.append(dataId_32)
                .append(macheId_32)
                .append(pid_32)
                .append(time_32);

        return traceId.toString();
    }

    public static void main(String[] args) {

        System.out.println(generatorTraceId());

    }
}
