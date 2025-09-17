package com.wanmi.sbc.dbreplay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class DbReplayApplication {

    public static void main(String[] args) throws UnknownHostException {
        Environment env = SpringApplication.run(DbReplayApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8889");
        String healthPort = env.getProperty("management.server.port", "9889");

        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/health\n----------------------------------------------------------",
                port,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                InetAddress.getLocalHost().getHostAddress(),
                healthPort
        );
    }
}
