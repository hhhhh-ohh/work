package com.wanmi.sbc.setting.api.request.pagemanage;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author houshuai
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema
public class GoodsInfoExtendDeleteByIdRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    @Schema(description = "skuId")
    @NotBlank
    private String goodsInfoId;

    @Schema(description = "spuId")
    @NotBlank
    private String goodsId;

}
