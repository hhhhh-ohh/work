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
public class PopularSearchTermsSortRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long id;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    @NotNull
    private Long sortNumber;


}
