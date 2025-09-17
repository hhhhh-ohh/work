package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 11:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ProviderTradeCountCriteriaResponse extends BasicResponse {

    /**
     * 总条数
     */
    @Schema(description = "总条数")
    private Long count;

}
