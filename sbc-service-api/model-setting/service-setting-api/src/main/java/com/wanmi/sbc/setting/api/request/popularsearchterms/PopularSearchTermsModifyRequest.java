package com.wanmi.sbc.setting.api.request.popularsearchterms;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema
public class PopularSearchTermsModifyRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 热门搜索词
     */
    @Schema(description = "popular_search_keyword")
    @NotBlank
    @Length(max = 10)
    private String popularSearchKeyword;

    /**
     * 移动端落地页
     */
    @Schema(description = "移动端落地页")
    private String relatedLandingPage;

    /**
     * PC落地页
     */
    @Schema(description = "PC落地页")
    private String pcLandingPage;

    /**
     * 排序号
     */
    @Schema(description ="sort_number")
    private Long sortNumber;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;


}
