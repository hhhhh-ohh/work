package com.wanmi.sbc.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求基类
 * Created by aqlu on 15/11/30.
 */
@Schema
@Data
@Slf4j
public class BaseRequest implements Serializable {

    /**
     * 登录用户Id
     */
    @Schema(description = "登录用户Id", hidden = true)
    private String userId;

    /**
     * 店铺ID
     * 由于前面有很多继承它的类已经定义了storeId，作用与storeId一样，继续它没有必要再起
     */
    @Schema(description = "店铺ID", hidden = true)
    private Long baseStoreId;

    /**
     * 扩展数据
     */
    private Map<String, String> extendMap = new HashMap<>();

    /**
     * 统一参数校验入口
     */
    @Schema(description = "统一参数校验入口", hidden = true)
    public void checkParam(){
        log.info("统一参数校验入口");
    }

    /**
     * 统一参数校验敏感词入口
     */
    @Schema(description = "统一参数校验敏感词入口", hidden = true)
    public String checkSensitiveWord(){return null;}
}
