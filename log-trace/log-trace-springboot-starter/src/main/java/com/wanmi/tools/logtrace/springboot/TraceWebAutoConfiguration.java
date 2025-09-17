package com.wanmi.tools.logtrace.springboot;

import com.wanmi.tools.logtrace.channel.web.TraceWebConfig;
import com.wanmi.tools.logtrace.channel.web.TraceWebFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author zhanggaolei
 * @className TraceWebAutoConfiguration
 * @description TODO
 * @date 2021/12/15 5:59 下午
 */
@Configuration
@ConditionalOnClass(
        name = {
            "org.springframework.web.servlet.config.annotation.WebMvcConfigurer",
            "org.springframework.boot.web.servlet.FilterRegistrationBean"
        })
public class TraceWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TraceWebConfig.class)
    public TraceWebConfig traceWebConfig() {
        return new TraceWebConfig();
    }

    @Bean
    public FilterRegistrationBean traceWebFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setName("traceWebFilter");
        filter.setFilter(new TraceWebFilter());
        // 指定优先级
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filter;
    }
}
