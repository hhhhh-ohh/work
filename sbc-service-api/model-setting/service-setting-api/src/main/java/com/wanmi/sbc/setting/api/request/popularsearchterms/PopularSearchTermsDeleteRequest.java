package com.wanmi.sbc.setting.api.request.popularsearchterms;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema
public class PopularSearchTermsDeleteRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

}
