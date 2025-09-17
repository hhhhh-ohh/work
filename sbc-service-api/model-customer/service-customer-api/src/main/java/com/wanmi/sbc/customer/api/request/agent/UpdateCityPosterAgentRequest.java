package com.wanmi.sbc.customer.api.request.agent;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


/**
 * @program: sbc-micro-service
 * @description: 添加代理商请求参数
 * @create: 2020-04-01 15:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateCityPosterAgentRequest extends BaseRequest {

    @NotNull(message = "市ID不能为空")
    @Schema(description = "市ID")
    private Long cityId;

}
