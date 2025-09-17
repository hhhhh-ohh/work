package com.wanmi.sbc.crm.api.request.manualrecommendgoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.math.BigDecimal;

/**
 * @ClassName ManualRecommendGoodsUpdateWeightRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/23 13:47
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsUpdateWeightRequest extends BaseRequest {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long id;

    /**
     * 权重
     */
    @Schema(description = "权重")
    private BigDecimal weight;
}
