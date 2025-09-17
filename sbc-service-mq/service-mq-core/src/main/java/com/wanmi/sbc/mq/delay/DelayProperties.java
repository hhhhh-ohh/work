package com.wanmi.sbc.mq.delay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhanggaolei
 * @className DelayProperties
 * @description
 * @date 2022/6/2 11:14
 **/
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "wm.delay")
public class DelayProperties {

    private Boolean isLocal = false;

    private FlushMode flushMode = FlushMode.MYSQL;

    private Boolean startedReload = false;


    public static enum FlushMode {
        /** mysql存储模式 */
        MYSQL,
        /** redis存储模式 */
        REDIS,
        /** 本地文件存储模式 */
        LOCAL_FILE;

        public boolean isMysql() {
            return this.equals(FlushMode.MYSQL);
        }

        public boolean isRedis() {
            return this.equals(FlushMode.REDIS);
        }

        public boolean isLocalFile() {
            return this.equals(FlushMode.LOCAL_FILE);
        }
    }}
