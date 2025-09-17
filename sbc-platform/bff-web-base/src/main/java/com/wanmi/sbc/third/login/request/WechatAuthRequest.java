package com.wanmi.sbc.third.login.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @program: sbc-micro-service
 * @description:
 * @create: 2020-05-08 10:36
 **/
@Data
@Schema
public class WechatAuthRequest extends BaseRequest {
    @Schema(description = "微信临时授权码")
    @NotBlank(message = "授权失败, 微信授权码为空, 请重新授权!")
    private String code;

    @Schema(description = "解密密钥")
    @NotBlank(message = "参数不能为空！")
    private String iv;

    @Schema(description = "微信加密数据")
    @NotBlank(message = "授权失败, 获取不到手机信息, 请重新授权!")
    private String encryptedData;
}