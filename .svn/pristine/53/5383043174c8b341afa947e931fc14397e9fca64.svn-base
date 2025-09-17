package com.wanmi.sbc.empower;

import com.wanmi.sbc.common.configure.CompositePropertySourceFactory;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

/**
 * @Author: songhanlin
 * @Date: Created In 下午4:15 2021/3/27
 * @Description: TODO
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.wanmi.sbc", "com.qianmi.wechat.proxy.service.customizetrade"})
@EnableAsync
@EnableDiscoveryClient
@Slf4j
@EnableFeignClients(basePackages = {"com.wanmi.sbc"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@EnableJpaAuditing
public class EmpowerServiceApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplateLoadBalanced(){
        return new RestTemplate();
    }

    public static void main(String[] args) throws Exception {
//        JSON.config(JSONWriter.Feature.ReferenceDetection);
        Environment env = SpringApplication.run(EmpowerServiceApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8090");
        String actPort = env.getProperty("management.server.port", "8091");
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
