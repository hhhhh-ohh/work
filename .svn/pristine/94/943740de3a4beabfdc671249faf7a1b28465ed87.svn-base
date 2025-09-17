package com.wanmi.sbc.goods;


import com.wanmi.sbc.common.configure.CompositePropertySourceFactory;
import com.wanmi.sbc.common.plugin.annotation.EnablePluginRouting;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;

/**
 * @Author: ZhangLingKe
 * @Description: 商品服务启动器
 * @Date: 2018-11-07 10:07
 */
//@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class},scanBasePackages = {"com.wanmi.sbc"})
@SpringBootApplication(scanBasePackages = {"com.wanmi.sbc"})
@EnableAsync
@EnableDiscoveryClient
@Slf4j
@EnableFeignClients(basePackages = {"com.wanmi.sbc"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@EnableJpaAuditing
@EnableCaching
@EnablePluginRouting
@EnableAspectJAutoProxy
public class GoodsServiceApplication {

    public static void main(String[] args) throws Exception {

//        JSON.config(JSONWriter.Feature.ReferenceDetection);
        // 解决SpringBoot的netty和elasticsearch的netty冲突造成无法启动问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        Environment env = SpringApplication.run(GoodsServiceApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8490");
        String actPort = env.getProperty("management.server.port", "8491");
        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/act/health\n----------------------------------------------------------",
                port,
                hostAddress,
                port,
                hostAddress,
                actPort
        );
    }

}