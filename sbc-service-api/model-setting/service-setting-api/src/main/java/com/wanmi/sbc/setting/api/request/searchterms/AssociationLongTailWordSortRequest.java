package com.wanmi.sbc.setting.api.request.searchterms;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>联想词VO</p>
 * @author weiwenhao
 * @date 2020-04-17
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Data
public class AssociationLongTailWordSortRequest extends SettingBaseRequest {



    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long associationLongTailWordId;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    @NotNull
    private Long sortNumber;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

}
