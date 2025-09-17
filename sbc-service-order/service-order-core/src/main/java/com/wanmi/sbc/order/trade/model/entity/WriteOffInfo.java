package com.wanmi.sbc.order.trade.model.entity;/**
 * @author 黄昭
 * @create 2021/9/10 10:24
 */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.bean.enums.WriteOffStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @className WriteOffInfo
 * @description TODO
 * @author 黄昭
 * @date 2021/9/10 10:24
 **/
@Data
public class WriteOffInfo {

    @Schema(description = "核销码")
    private String writeOffCode;

    @Schema(description = "核销人")
    private String writeOffPerson;

    @Schema(description = "核销时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime writeOffTime;

    @Schema(description = "核销状态")
    private WriteOffStatus writeOffStatus;

}
