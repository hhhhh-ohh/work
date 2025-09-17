package com.wanmi.sbc;

import com.wanmi.sbc.common.annotation.EnabledRequestLog;
import com.wanmi.sbc.common.configure.CompositePropertySourceFactory;
import com.wanmi.sbc.common.plugin.annotation.EnablePluginRouting;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.config.EnableWithStateMachine;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>Boss Bootstrap</p>
 * Created by of628-wenzhi on 2017-06-01-上午11:10.
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.wanmi.sbc"})
@EnableWithStateMachine
@EnableAsync
@Slf4j
@PropertySource(value = {"classpath:manage-base-application.properties", "classpath:api-application.properties"},
        factory = CompositePropertySourceFactory.class)
@EnableFeignClients(basePackages = {"com.wanmi.sbc", "com.wanmi.ares.provider"})
@ServletComponentScan
@EnablePluginRouting
@EnabledRequestLog
public class SupplierApplication {

    public static void main(String[] args) throws UnknownHostException {
//        JSON.config(JSONWriter.Feature.ReferenceDetection);
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        Environment env = SpringApplication.run(SupplierApplication.class, args).getEnvironment();

        String port = env.getProperty("server.port", "8087");

        String healthPort = env.getProperty("management.server.port", "9004");

        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/act/health\n----------------------------------------------------------",
                port,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                InetAddress.getLocalHost().getHostAddress(),
                healthPort
        );
    }
}
