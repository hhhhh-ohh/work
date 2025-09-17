package com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName CateRelatedRecommendUpdateWeightRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/26 16:35
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendUpdateWeightRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

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
