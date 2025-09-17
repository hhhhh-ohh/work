package com.wanmi.sbc.common.configure;

import com.alibaba.cloud.commons.lang.StringUtils;
import io.seata.core.context.RootContext;
import io.seata.tm.api.GlobalTransactionContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shardingsphere.transaction.base.seata.at.SeataTransactionHolder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SeataFeignInterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SeataFeignInterceptor()).addPathPatterns("/**");
    }

    public static class SeataFeignInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String xid = RootContext.getXID();
            if (xid == null) {
                xid = request.getHeader(RootContext.KEY_XID);
            }

            if (!StringUtils.isBlank(xid) && SeataTransactionHolder.get() == null) {
                RootContext.bind(xid);
                SeataTransactionHolder.set(GlobalTransactionContext.getCurrentOrCreate());
            }

            return true;
        }
    }
}