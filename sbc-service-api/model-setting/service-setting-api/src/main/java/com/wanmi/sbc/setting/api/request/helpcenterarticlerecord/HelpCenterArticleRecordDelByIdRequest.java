package com.wanmi.sbc.setting.api.request.helpcenterarticlerecord;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除帮助中心文章记录请求参数</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleRecordDelByIdRequest extends SettingBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @Schema(description = "主键Id")
    @NotNull
    private Long id;
}
