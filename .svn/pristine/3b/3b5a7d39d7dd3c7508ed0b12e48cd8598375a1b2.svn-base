package com.wanmi.sbc.setting.api.request.helpcenterarticle;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除帮助中心文章信息请求参数</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleDelByIdRequest extends SettingBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @Schema(description = "主键Id")
    @NotNull
    private Long id;

    /**
     * 更新人
     */
    @Schema(description = "更新人", hidden = true)
    private String updatePerson;
}
