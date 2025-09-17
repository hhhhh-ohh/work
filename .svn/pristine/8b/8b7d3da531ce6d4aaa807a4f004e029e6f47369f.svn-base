package com.wanmi.tools.logtrace.springboot;

import com.wanmi.tools.logtrace.channel.web.TraceWebConfig;
import com.wanmi.tools.logtrace.channel.xxljob.TraceXxlJobAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanggaolei
 * @className TraceXxlJobAutoConfiguration
 * @description TODO
 * @date 2021/12/15 5:59 下午
 */

@ConditionalOnClass(name = {"com.xxl.job.core.executor.XxlJobExecutor"})
@Configuration
public class TraceXxlJobAutoConfiguration {

    @Bean
    public TraceXxlJobAop traceXxlJobAop() {
        return new TraceXxlJobAop();
    }

}
