package com.wanmi.sbc.employee;

import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/***
 * 平台员工Service
 * 防腐层
 * @className EmployeeService
 * @author zhengyang
 * @date 2021/7/29 14:03
 **/
@Slf4j
@Service
public class EmployeeCacheService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 是否可以发送验证码
     *
     * @param mobile 要发送短信的手机号码
     * @return true:可以发送，false:不可以
     */
    public boolean isSendSms(String redisKey, String mobile) {
        String timeStr = redisUtil.hget(redisKey, mobile);
        if (StringUtils.isBlank(timeStr)) {
            return true;
        }
        //如果当前时间 > 上一次发送时间+1分钟
        return LocalDateTime.now().isAfter(DateUtil.parse(timeStr, DateUtil.FMT_TIME_1).plusMinutes(1));
    }
}
