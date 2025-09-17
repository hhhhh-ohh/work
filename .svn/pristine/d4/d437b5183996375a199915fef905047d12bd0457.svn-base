package com.wanmi.sbc.configure;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-04-24 11:31
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("verifyInfo", new ConcurrentHashMap());

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
