package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 第三方关系表
 *
 * @Author: songhanlin
 * @Date: Created In 10:01 AM 2018/8/8
 * @Description: 第三方关系表
 */
@Schema
@Data
public class ThirdLoginRelationVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方登录主键
     */
    @Schema(description = "第三方登录主键")
    private String thirdLoginId;

    /**
     * 第三方关系关联(union)Id
     */
    @Schema(description = "第三方关系关联")
    private String thirdLoginUid;

    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    private String customerId;

    /**
     * 第三方类型 0:wechat
     */
    @Schema(description = "第三方类型")
    private ThirdLoginType thirdLoginType;

    /**
     * 微信授权openId, 该字段只有微信才有, 由于微信登录使用的是unionId,
     * 但是微信模板消息发送需要使用openId, 所以需要union_id, 所以union_id和open_id单独存放
     */
    @Schema(description = "微信授权openId")
    private String thirdLoginOpenId;

    @Schema(description = "头像路径")
    private String headimgurl;

    @Schema(description = "昵称")
    private String nickname;

    /**
     * 绑定时间
     */
    @Schema(description = "绑定时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime bindingTime;
}
