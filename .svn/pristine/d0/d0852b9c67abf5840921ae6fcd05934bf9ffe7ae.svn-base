package com.wanmi.sbc.pointsgoods.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author yang
 * @since 2019/5/21
 */
@Schema
@Data
public class PointsGoodsImportExcelRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "后缀")
    @NotBlank
    private String ext;

    /**
     * 操作员id
     */
    @Schema(description = "操作员id")
    private String userId;
}
