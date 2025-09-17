package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className TradeEconomicalPageRequest
 * @description
 * @date 2022/5/24 10:43 AM
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeEconomicalPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 2063002341389445823L;

    /**
     * 分页参数
     */
    @Schema(description = "分页参数")
    private TradeQueryDTO tradePageDTO;

    /**
     * 付费等级id
     */
    @Schema(description = "付费等级id")
    @NotNull
    private Integer levelId;
}
