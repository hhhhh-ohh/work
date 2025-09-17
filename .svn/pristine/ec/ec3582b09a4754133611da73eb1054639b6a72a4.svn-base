package com.wanmi.sbc.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhanggaolei
 * @className DefaultIntercaptor
 * @description TODO
 * @date 2021/4/20 10:42
 **/
@Slf4j
public class DefaultInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        log.info(request.getPathInfo());
        return true;
    }

}
