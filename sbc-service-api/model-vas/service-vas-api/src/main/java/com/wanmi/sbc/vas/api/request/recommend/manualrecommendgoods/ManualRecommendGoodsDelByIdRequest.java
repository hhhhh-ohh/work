package com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods;

import com.wanmi.sbc.vas.api.request.VasBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除手动推荐商品管理请求参数</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsDelByIdRequest extends VasBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long id;
}
