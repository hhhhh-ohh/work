package com.wanmi.sbc.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 重复提交靠按钮解决不是特别靠谱，后台加mysql锁也是不优（今后分布式了），
 * 使用redis分布式锁(setnx)解决
 * hash值 对象实例相同，默认的hash值相等(配合lombok的@Data对hashcode的重写)，通过hash值作为锁值校验入参(不要随便重写request hash()方法)
 * hash值超时时间（默认 5 s）
 */
@Aspect
@Component
@Slf4j
public class MultiSubmitHandler {
    /**
     * 锁的默认时间为5秒
     */
    private static final long REPEAT_LOCK_TIME = 5L;

    /**
     * 1个入参
     */
    private static final int ONE_PARAM = 1;

    /**
     * 2个入参
     */
    private static final int TWO_PARAM = 2;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private CommonUtil commonUtil;

    @Pointcut("@annotation(com.wanmi.sbc.common.annotation.MultiSubmit)")
    public void pointcut() {
    }

    /**
     * 防止重复提交的请求,请求之前的逻辑
     * 对 用户id + 请求参数的hashcode 加锁
     *
     * @param joinPoint 获取请求Request
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        Object[] objects = joinPoint.getArgs();
        if (objects.length == ONE_PARAM || objects.length == TWO_PARAM) {
            String key = getRedisKey(objects);
            if (redisService.setNx(key, Constants.STR_1, REPEAT_LOCK_TIME)) {
                log.info("submitting repeat check time : {}ms, key:{}", (System.currentTimeMillis() - start), key);
            } else {
                log.error("submitting repeat: {}", joinPoint.toLongString());
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999997);
            }
        }
    }

    /**
     * 防止重复提交的请求,请求之后的逻辑
     * 释放锁
     *
     * @param joinPoint 获取请求Request
     * @param res       暂无用
     */
    @AfterReturning(pointcut = "pointcut()", returning = "res")
    public void afterReturning(JoinPoint joinPoint, Object res) {
        Object[] objects = joinPoint.getArgs();
        if (objects.length == ONE_PARAM || objects.length == TWO_PARAM) {
            String key = getRedisKey(objects);
            redisService.delete(key);
            log.info("submitting repeat lock released, key:{}", key);
        }
    }

    /**
     * 获取防止相同请求重复提交的redis锁的key
     *
     * @param objects 请求参数
     * @return key redis锁的key
     */
    private String getRedisKey(Object[] objects) {
        String key;
        if (objects.length == TWO_PARAM) {
            key = String.valueOf(getObject(objects, 0).hashCode()) + getObject(objects, 1).hashCode();
        } else {
            key = String.valueOf(getObject(objects, 0).hashCode());
        }

        String uid;
        try {
            uid = commonUtil.getOperatorId();
        } catch (Exception e) {
            uid = null;
        }
        if (StringUtils.isNotBlank(uid)) {
            key = "R:" + uid + ":" + key;
        } else {
            key = "R:" + key;
        }
        return key;
    }

    private String getObject(Object[] objects, int index) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //先转json
            String json = objectMapper.writeValueAsString(objects[index]);
            log.info("submitting repeat json:{}",json);
            //再取hashcode
            return json;
        }catch (Exception e){
            log.error("submitting repeat error:{}",e.getMessage());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }
}
