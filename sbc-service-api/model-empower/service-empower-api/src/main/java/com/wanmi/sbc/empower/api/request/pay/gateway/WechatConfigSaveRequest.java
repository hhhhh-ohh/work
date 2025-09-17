package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sunkun on 2017/8/9.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class WechatConfigSaveRequest extends BaseRequest {

    private static final long serialVersionUID = 4304124969782172682L;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    @Schema(description = "appId")
    @NotNull
    private String appId;

    /**
     * 微信secret
     */
    @Schema(description = "微信secret")
    @NotNull
    private String secret;

    /**
     * 场景；H5微信设置（0：扫码 ，H5，微信网页-JSAPI），1：小程序（miniJSAPI），2：APP
     */
    @Schema(description = "场景；H5微信设置（0：扫码 ，H5，微信网页-JSAPI），1：小程序（miniJSAPI），2：APP")
    @NotNull
    private Integer sceneType;
}
