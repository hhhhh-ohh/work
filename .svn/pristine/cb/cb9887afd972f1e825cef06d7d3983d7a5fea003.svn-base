package com.wanmi.sbc.empower.api.response.channel.vop.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 京东推送消息响应参数
 * @author  hanwei
 * @date 2021/5/13
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VopMessageResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 推送id
     */
    private String id;

    /**
     * 具体的Message数据
     */
    private String result;

    /**
     * 推送时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime time;

    /**
     * 消息类型
     */
    private int type;
}
