package com.wanmi.sbc.vas;


import com.wanmi.sbc.common.configure.CompositePropertySourceFactory;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;

@SpringBootApplication(scanBasePackages = {"com.wanmi.sbc"})
//@EnableTransactionManagement
@EnableAsync
@MapperScan(basePackages = {"com.wanmi.sbc.vas.*.*.mapper"})
@EnableDiscoveryClient
@Slf4j
@EnableFeignClients(basePackages = {"com.wanmi.sbc"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@EnableJpaAuditing
public class VasServiceApplication {

    public static void main(String[] args) throws Exception {
//        JSON.config(JSONWriter.Feature.ReferenceDetection);
        Environment env = SpringApplication.run(VasServiceApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8090");
        String actPort = env.getProperty("management.server.port", "8091");
        String localHostAddr = InetAddress.getLocalHost().getHostAddress();
        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/act/health\n----------------------------------------------------------",
                port,
                localHostAddr,
                port,
                localHostAddr,
                actPort
        );
    }
}
