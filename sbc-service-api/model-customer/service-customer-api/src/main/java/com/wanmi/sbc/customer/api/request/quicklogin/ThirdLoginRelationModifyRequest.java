package com.wanmi.sbc.customer.api.request.quicklogin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 第三方关系表更新
 *
 * @Author: songhanlin
 * @Date: Created In 10:01 AM 2018/8/8
 * @Description: 第三方关系表
 */
@Schema
@Data
public class ThirdLoginRelationModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方登录主键
     */
    @Schema(description = "第三方登录主键")
    @NotNull
    private String thirdLoginId;

    /**
     * 第三方关系关联(union)Id
     */
    @Schema(description = "第三方关系关联(union)Id")
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
    @Schema(description = "微信授权openI")
    private String thirdLoginOpenId;

    /**
     * 绑定时间
     */
    @Schema(description = "绑定时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime bindingTime;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;
}
