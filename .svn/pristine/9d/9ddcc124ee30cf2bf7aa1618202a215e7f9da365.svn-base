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
import java.util.List;


/**
 * @program: sbc-micro-service
 * @description: 代理商返回结果
 * @create: 2020-04-01 15:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class GetUserAreaIdResponse extends BasicResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "区")
    private List<Long> areaIdList;

}
