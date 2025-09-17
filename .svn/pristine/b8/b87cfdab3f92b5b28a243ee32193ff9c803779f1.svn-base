package com.wanmi.sbc.setting.api.request.baseconfig;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by feitingting on 2019/11/8.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigSaveRopRequest extends BaseRequest {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Integer baseConfigId;

    /**
     * PC端商城网址
     */
    @Schema(description = "PC端商城网址")
    @NotBlank
    private String pcWebsite;

    /**
     * 移动端商城网址
     */
    @Schema(description = "移动端商城网址")
    @NotBlank
    private String mobileWebsite;

    /**
     * PC商城logo
     */
    @Schema(description = "PC商城logo")
    private String pcLogo;
    
    /**
     * 商城加载图
     */
    @Schema(description = "商城加载图")
    private String loadingUrl;

    /**
     * PC商城登录页banner,最多可添加5个,多个图片间以"|"隔开
     */
    @Schema(description = "PC商城登录页banner,最多可添加5个,多个图片间以\"|\"隔开")
    private String pcBanner;

    /**
     * PC商城首页banner,最多可添加5个,多个图片间以"|"隔开
     */
    @Schema(description = "PC商城首页banner,最多可添加5个,多个图片间以\"|\"隔开")
    private String pcMainBanner;

    /**
     * 移动商城banner,最多可添加5个,多个图片间以"|"隔开
     */
    @Schema(description = "移动商城banner,最多可添加5个,多个图片间以\"|\"隔开")
    private String mobileBanner;

    /**
     * 商城图标，最多添加一个
     */
    @Schema(description = "商城图标，最多添加一个")
    private String pcIco;

    /**
     * 商城首页标题
     */
    @Schema(description = "商城首页标题")
    private String pcTitle;

    /**
     * 商家网址
     */
    @Schema(description = "商家网址")
    private String supplierWebsite;

    /**
     * 会员注册协议
     */
    @Schema(description = "会员注册协议")
    private String registerContent;

    /**
     * 会员注销协议
     */
    @Schema(description = "会员注销协议")
    private String cancellationContent;

    /**
     * 版权信息
     */
    @Schema(description = "版权信息")
    @Length(max = 40)
    @NotBlank
    private String copyright;

    /**
     * 版本号
     */
    @Schema(description = "版本号")
    private String version;
}
