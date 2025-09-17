package com.wanmi.sbc.job;

import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.empower.api.provider.channel.vop.token.VopTokenProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @className JdTokenJobHandler
 * @description 定时刷新token
 * @author    张文昌
 * @date      2021/5/10 下午5:30
 */
@Component
@Slf4j
public class JdTokenJobHandler {

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private VopTokenProvider vopTokenProvider;

    @XxlJob(value = "jdTokenJobHandler")
    public void execute() throws Exception {
        // 获取token
        String accessToken = redisService.getString(CacheKeyConstant.JDVOP_ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            // 为空直接获取
            vopTokenProvider.accessToken();
        } else {
            // 不为空刷新
            vopTokenProvider.refreshToken();
        }
    }
}
