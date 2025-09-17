package com.wanmi.sbc.vas.api.request.commodityscoringalgorithm;

import com.wanmi.sbc.vas.api.request.VasBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商品评分算法请求参数</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityScoringAlgorithmDelByIdRequest extends VasBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private Long id;
}
