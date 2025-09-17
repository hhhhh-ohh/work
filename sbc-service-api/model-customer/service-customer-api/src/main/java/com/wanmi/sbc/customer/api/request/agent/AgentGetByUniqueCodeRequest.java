package com.wanmi.sbc.customer.api.request.agent;


import com.wanmi.sbc.common.base.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class AgentGetByUniqueCodeRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 系统唯一码（不可重复）
     */
    @Schema(description = "系统唯一码（不可重复）")
    private String agentUniqueCode;



}
