package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.configure.CompositePropertySourceFactory;
import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import java.net.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-21 14:13
 */

@SpringBootApplication(scanBasePackages = {"com.wanmi.sbc"}, exclude = SeataAutoConfiguration.class)
@EnableAsync
@EnableDiscoveryClient
@Slf4j
@EnableFeignClients(basePackages = {"com.wanmi.sbc"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@ImportResource(locations = {"classpath:spring-plugin.xml"})
@EnableJpaAuditing
@EnableCaching
//@EnablePluginRouting
public class MarketingServiceApplication {

    public static void main(String[] args) throws Exception {
        Environment env = SpringApplication.run(MarketingServiceApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8095");
        String healthPort = env.getProperty("management.server.port", "9095");
        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/act/health\n----------------------------------------------------------",
                port,
                hostAddress,
                port,
                hostAddress,
                healthPort
        );
    }
}
