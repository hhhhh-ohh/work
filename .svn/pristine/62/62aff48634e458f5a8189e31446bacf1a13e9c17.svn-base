package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: sbc-micro-service
 * @description: 微信小程序用户信息
 * @create: 2020-05-22 15:44
 **/
@Data
@Schema
public class WeChatQuickLoginVo extends BasicResponse {


    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private String Id;

    @Schema(description = "应用内用户唯一标示")
    private String openId;

    @Schema(description = "同一开放平台下唯一")
    private String unionId;

    @Schema(description = "删除标示")
    private DeleteFlag delFlag;

    @Schema(description = "绑定时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
}