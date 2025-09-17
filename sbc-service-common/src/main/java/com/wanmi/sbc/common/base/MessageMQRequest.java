package com.wanmi.sbc.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

/**
 * 消息mq实体类
 */
@Data
@Schema
public class MessageMQRequest {

    /**
     * 内容参数
     */
    @Schema(description = "内容参数")
    private List<String> params;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 跳转路由参数
     */
    @Schema(description = "跳转路由参数")
    private Map<String, Object> routeParam;

    /**
     * 节点类型
     */
    @Schema(description = "节点类型")
    private Integer nodeType;

    /**
     * 节点code
     */
    @Schema(description = "节点code")
    private String nodeCode;

    /**
     * 消息图片
     */
    @Schema(description = "消息图片")
    private String pic;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String mobile;

    /**
     * 是否发送短信
     * @link com.wanmi.sbc.common.util.Constants.yes
     */
    @Schema(description = "是否发送短信")
    private Integer sendSms;
}
