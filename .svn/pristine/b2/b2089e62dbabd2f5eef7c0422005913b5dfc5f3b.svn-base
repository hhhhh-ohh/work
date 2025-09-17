package com.wanmi.sbc.setting.api.request.popularsearchterms;

import com.wanmi.sbc.common.enums.DeleteFlag;
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
public class PopularSearchTermsRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 热门搜索词
     */
    @Schema(description = "popular_search_keyword")
    @Length(max = 10)
    @NotBlank
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
    @NotNull
    private Long sortNumber;

    /**
     * 是否删除 0 否  1 是
     */
    @Schema(description = "是否删除")
    private DeleteFlag delFlag;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;


}
