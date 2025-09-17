package com.wanmi.sbc.common.cache;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanggaolei
 * @className CacheProperties
 * @description
 * @date 2022/5/6 15:22
 */
@Data
@Component
@ConditionalOnProperty(prefix = "wm.cache", value = "enabled")
@ConfigurationProperties(prefix = "wm.cache")
public class CacheProperties {

    //    private List<String> cacheName;
    private String defaultSpec = "maximumSize=50,expireTime=300";

    /** 是否自动创建 */
    private boolean dynamic = true;

    /** 是否存储空值 */
    private boolean allowNullValues = true;

    private String topic;

    private List<Config> configs;

    @Getter
    @Setter
    public static class Config {
        private List<String> cacheName;
        private String spec;
    }
}
