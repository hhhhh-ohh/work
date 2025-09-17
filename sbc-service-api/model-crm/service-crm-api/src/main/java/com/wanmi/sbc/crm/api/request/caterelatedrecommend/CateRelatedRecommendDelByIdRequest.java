package com.wanmi.sbc.crm.api.request.caterelatedrecommend;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除分类相关性推荐请求参数</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendDelByIdRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long id;
}
