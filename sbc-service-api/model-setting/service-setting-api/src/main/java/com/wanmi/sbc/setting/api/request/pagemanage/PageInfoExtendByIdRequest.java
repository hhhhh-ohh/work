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
public class PageInfoExtendByIdRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    @Schema(description = "页面id")
    @NotBlank
    private String pageId;

    @Schema(description = "页面code")
    @NotBlank
    private String pageCode;

    @Schema(description = "页面类型")
    @NotBlank
    private String pageType;

    @Schema(description = "页面所属平台")
    @NotBlank
    private String platform;

    @Schema(description = "店铺id", hidden = true)
    private Long storeId;

    @Schema(description = "首页小程序二维码", hidden = true)
    private String mainMiniQrCode;

    @Schema(description = "首页二维码", hidden = true)
    private String mainQrCode;

    @Schema(description = "优惠卷推广状态, yes 表示推广请求")
    private String couponRecommend;

    @Schema(description = "优惠卷推广ID/活动ID")
    private String activityId;

    @Schema(description = "0 小程序码 1 二维码")
    private Integer useType;

    @Schema(description = "访问地址")
    private String url;

    @Schema(description = "背景图片")
    private String backgroundPic;

    @Schema(description = "0拼团 1秒杀 2预约 3预售 4打包一口价 5第二件半价 10抽奖活动 11加价购")
    private Integer marketingType;

    @Schema(description = "商品id")
    private String goodsInfoId;

    @Schema(description = "商品类型")
    private PluginType pluginType;

}
