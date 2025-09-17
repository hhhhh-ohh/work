package com.wanmi.sbc.common.redis.bean;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 适用于redis hset 结构的对象
 * Created by daiyitian on 2016/12/24.
 */
@Schema
@Data
public class RedisHsetBean {

    @Schema(description = "key")
    private String key;

    @Schema(description = "field")
    private String field;

    @Schema(description = "value")
    private String value;

}
