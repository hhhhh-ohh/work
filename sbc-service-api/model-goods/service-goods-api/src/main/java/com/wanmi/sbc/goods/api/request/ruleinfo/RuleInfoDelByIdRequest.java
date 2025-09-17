package com.wanmi.sbc.goods.api.request.ruleinfo;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除规则说明请求参数</p>
 * @author zxd
 * @date 2020-05-25 18:55:56
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfoDelByIdRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;
}
