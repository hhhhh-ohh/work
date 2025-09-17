package com.wanmi.sbc.setting.api.request.systemresourcecate;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>店铺资源资源分类表新增参数</p>
 *
 * @author lq
 * @date 2019-11-05 16:13:19
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceCateInitRequest extends SettingBaseRequest {

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识")
    @NotNull
    private Long storeId;

    /**
     * 商家标识
     */
    @Schema(description = "商家标识")
    @NotNull
    private Long companyInfoId;

    /**
     * 父分类ID
     */
    @Schema(description = "父分类ID")
    @Max(9223372036854775807L)
    private Long cateParentId;
}