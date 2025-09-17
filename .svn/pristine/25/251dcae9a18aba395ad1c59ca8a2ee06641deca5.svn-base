package com.wanmi.perseus;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.wanmi.sbc.common.annotation.EnabledRequestLog;
import com.wanmi.sbc.common.configure.CompositePropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>Perseus应用安装启动类</p>
 * Created by of628-wenzhi on 2017-09-22-下午2:06.
 */
@SpringBootApplication(scanBasePackages = {"com.wanmi.sbc.mq.api.provider","com.wanmi.perseus.statistics","com.wanmi.sbc.mq.api"})
@EnableAsync
@Slf4j
@EnabledRequestLog
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wanmi.sbc"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@ComponentScan(basePackages = {"com.wanmi.sbc.mq.api.provider","com.wanmi.sbc.mq.api","com.wanmi.perseus","com.wanmi.perseus.statistics"})
public class PerseusBootstrap {
    public static void main(String[] args) throws UnknownHostException {
        JSON.config(JSONWriter.Feature.ReferenceDetection);

        Environment env = SpringApplication.run(PerseusBootstrap.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8480");
        String actPort = env.getProperty("management.server.port", "8481");

        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/act/health\n----------------------------------------------------------",
                port,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                InetAddress.getLocalHost().getHostAddress(),
                actPort
        );
    }
}
