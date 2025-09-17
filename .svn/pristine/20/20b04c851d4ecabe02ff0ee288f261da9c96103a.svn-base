package com.wanmi.sbc.setting.api.request.recommendcate;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除笔记分类表请求参数</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateDelByIdRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 8960801565590120541L;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    @NotNull
    private Long cateId;
}
