package com.wanmi.sbc.setting.api.request.presetsearch;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>预置搜索词</p>
 * @author weiwenhao
 * @date 2020-04-16
 */

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Data
public class PresetSearchTermsRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 预置搜索词
     */
    @Schema(description = "预置搜索词")
    @Length(max = 10)
    private String presetSearchKeyword;

}
