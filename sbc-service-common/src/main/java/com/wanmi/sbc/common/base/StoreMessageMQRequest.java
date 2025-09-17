package com.wanmi.sbc.common.base;

import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 消息mq实体类
 */
@Data
@Schema
public class StoreMessageMQRequest {

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long storeId;

    /**
     * 内容参数列表
     */
    @Schema(description = "内容参数")
    private List<Object> contentParams;

    /**
     * 跳转路由参数Map
     */
    @Schema(description = "跳转路由参数")
    private Map<String, Object> routeParams;

    /**
     * 节点标识
     */
    @Schema(description = "节点标识")
    private String nodeCode;

    /**
     * 产生时间
     */
    @Schema(description = "产生时间")
    private LocalDateTime produceTime;
}
