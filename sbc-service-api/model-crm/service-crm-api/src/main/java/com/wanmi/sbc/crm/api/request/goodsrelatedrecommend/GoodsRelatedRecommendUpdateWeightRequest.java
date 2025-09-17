package com.wanmi.sbc.crm.api.request.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.math.BigDecimal;

/**
 * @ClassName GoodsRelatedRecommendUpdateWeightRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/25 14:07
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendUpdateWeightRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 权重
     */
    @Schema(description = "权重")
    private BigDecimal weight;
}
