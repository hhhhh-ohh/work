package com.wanmi.sbc.customer.api.response.agent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @program: sbc-micro-service
 * @description: 代理商返回结果
 * @create: 2020-04-01 15:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CreatePosterResponse extends BasicResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "代理商主键ID")
    private String agentId;

    @Schema(description = "代理商名称")
    private String agentName;

    @Schema(description = "系统唯一码（不可重复）")
    private String agentUniqueCode;

    @Schema(name = "二维码地址")
    private String qrCodeAddress;

    @Schema(description = "海报地址")
    private String posterAddress;


}
