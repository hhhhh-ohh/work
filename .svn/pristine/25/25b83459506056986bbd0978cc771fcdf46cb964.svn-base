package com.wanmi.sbc.setting.api.request.searchterms;


import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>搜索词VO</p>
 * @author weiwenhao
 * @date 2020-04-17
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Data
public class SearchAssociationalWordModifyRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long id;

    /**
     * 搜索词
     */
    @Schema(description = "搜索词")
    @Length(max = 10)
    @NotBlank
    private String searchTerms;


    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;


}
