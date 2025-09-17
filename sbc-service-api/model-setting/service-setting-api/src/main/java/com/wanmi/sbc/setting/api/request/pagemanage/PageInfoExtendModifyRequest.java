package com.wanmi.sbc.setting.api.request.pagemanage;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema
public class PageInfoExtendModifyRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * pageId
     */
    @Schema(description = "页面id")
    @NotBlank
    private String pageId;

    /**
     * 页面背景图
     */
    @Schema(description = "页面背景图")
    private String backGroundPic;

    /**
     * 使用类型  0:小程序 1:二维码
     */
    @Schema(description = "使用类型  0:小程序 1:二维码 2:H5")
    private Integer useType;

    /**
     * 来源
     */
    @Schema(description = "来源")
    private List<String> sources;


}
