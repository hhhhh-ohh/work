package com.wanmi.sbc.common.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhanggaolei
 * @className CacheMessage
 * @description
 * @date 2022/5/9 14:28
 */
@Data
@NoArgsConstructor
public class CacheMessage {

    /** 缓存名称 */
    private String cacheName;
    /** 缓存key */
    private Object key;

    private String traceId;

    private Integer type = 0;

    public CacheMessage(String cacheName, Object key,Integer type) {
        this.cacheName = cacheName;
        this.key = key;
        this.type = type;
    }

    public String getBean(){
        switch (type){
            case 0:
                return CacheType.LOCAL;
            case 1:
                return CacheType.REDIS;
            case 2:
                return CacheType.MULTI;
            default:
                return CacheType.LOCAL;

        }
    }
}
