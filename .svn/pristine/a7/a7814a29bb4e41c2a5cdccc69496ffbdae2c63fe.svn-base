package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 结算单待结算统计响应请求
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementCountResponse extends BasicResponse {

    private static final long serialVersionUID = -5141981870356343243L;
    /**
     * 待结算数量
     */
    @Schema(description = "待结算数量")
    private Long count;
}
