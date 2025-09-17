package com.wanmi.sbc.setting.api.request.pagemanage;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema
public class GoodsInfoExtendByIdRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    @Schema(description = "skuId")
    @NotBlank
    private String goodsInfoId;

    @Schema(description = "spuId")
    @NotBlank
    private String goodsId;

    @Schema(description = "页面所属平台")
    @NotBlank
    private String platform;

    @Schema(description = "店铺id", hidden = true)
    private Long storeId;

    @Schema(description = "商品类型")
    private PluginType pluginType;

    @Schema(description = "推广活动地址")
    private String url;

    @Schema(description = "推广页面id")
    private String pageId;

}
