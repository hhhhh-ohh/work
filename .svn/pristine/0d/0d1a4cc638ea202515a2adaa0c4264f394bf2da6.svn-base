package com.wanmi.sbc.setting.api.request.searchterms;


import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>搜索词VO</p>
 * @author weiwenhao
 * @date 2020-04-16
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Data
public class AssociationLongTailWordLikeRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 搜索词
     */
    @Schema(description = "搜索词")
    @NotNull
    private String associationalWord;


}
