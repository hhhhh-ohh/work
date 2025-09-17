package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.util.ValidateUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.Validate;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:06 2018/11/19
 * @Description:
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class AppShareSettingModifyRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * app分享开关
     */
    @Schema(description = "app分享开关")
    private boolean enabled;

    /**
     * app分享标题
     */
    @Schema(description = "app分享标题")
    private String title;

    /**
     * app分享描述
     */
    @Schema(description = "app分享描述")
    private String desc;

    /**
     * app图标
     */
    @Schema(description = "app图标")
    private String icon;

    /**
     * ios下载包地址
     */
    @Schema(description = "ios下载包地址")
    private String iosUrl;

    /**
     * android下载包地址
     */
    @Schema(description = "android下载包地址")
    private String androidUrl;

    /**
     * app下载页面链接
     */
    @Schema(description = "app下载页面链接")
    private String downloadUrl;

    /**
     * app下载二维码
     */
    @Schema(description = "app下载二维码")
    private String downloadImg;

    /**
     * app分享页背景图
     */
    @Schema(description = "app分享页背景图")
    private String shareImg;

    public void checkParam() {
        if (enabled) {
            Validate.notBlank(title, ValidateUtil.BLANK_EX_MESSAGE, "title");
            Validate.notBlank(desc, ValidateUtil.BLANK_EX_MESSAGE, "desc");
            Validate.notBlank(icon, ValidateUtil.BLANK_EX_MESSAGE, "icon");
            Validate.notBlank(iosUrl, ValidateUtil.BLANK_EX_MESSAGE, "iosUrl");
            Validate.notBlank(androidUrl, ValidateUtil.BLANK_EX_MESSAGE, "androidUrl");
            Validate.notBlank(downloadUrl, ValidateUtil.BLANK_EX_MESSAGE, "downloadUrl");
            Validate.notBlank(downloadImg, ValidateUtil.BLANK_EX_MESSAGE, "downloadImg");
            Validate.notBlank(shareImg, ValidateUtil.BLANK_EX_MESSAGE, "shareImg");
        }
    }
}
