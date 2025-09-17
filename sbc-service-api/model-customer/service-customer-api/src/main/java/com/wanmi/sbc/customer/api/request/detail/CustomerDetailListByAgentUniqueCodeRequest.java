package com.wanmi.sbc.customer.api.request.detail;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员详情查询参数
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailListByAgentUniqueCodeRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * agentUniqueCode
     */
    @Schema(description = "agentUniqueCode")
    private String agentUniqueCode;



}
