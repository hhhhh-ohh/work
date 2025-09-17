package com.wanmi.sbc.message;

import com.wanmi.sbc.message.configuration.CompositePropertySourceFactory;
import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>Ares应用安装启动类</p>
 * Created by of628-wenzhi on 2017-09-18-上午11:18.
 */
@SpringBootApplication(exclude = {SeataAutoConfiguration.class})
@ComponentScan(basePackages = {"com.wanmi.sbc"})
@EnableAsync
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wanmi.sbc.crm","com.wanmi.ares.provider","com.wanmi.sbc.setting","com.wanmi.sbc.empower","com.wanmi.sbc.mq", "com.wanmi.sbc.elastic.api.provider"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@EnableJpaAuditing
public class SmsServiceApplication {
    public static void main(String[] args) throws UnknownHostException {
        Environment env = SpringApplication.run(com.wanmi.sbc.message.SmsServiceApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8580");
        String healthPort = env.getProperty("management.server.port", "9501");
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
