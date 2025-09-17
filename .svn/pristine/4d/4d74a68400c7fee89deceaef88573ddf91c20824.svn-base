package com.wanmi.sbc.customer.api.request.quicklogin;

import com.wanmi.sbc.common.base.BaseRequest;
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
 * @description:
 * @create: 2020-05-22 16:20
 **/
@Data
@Schema
public class WeChatQuickLoginAddReq extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "应用内用户唯一标示")
    private String openId;

    @Schema(description = "同一开放平台下唯一")
    private String unionId;

    @Schema(description = "删除标示")
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
}