package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>保存支付网关配置request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:36.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayConfigSaveRequest extends PayBaseRequest {
    
    private static final long serialVersionUID = 6602907582694970973L;
    
    private Long id;

    /**
     * 网关id
     */
    @Schema(description = "网关id")
    @NotNull
    private Long gatewayId;

    /**
     * 身份标识
     */
    @NotBlank
    @Schema(description = "身份标识")
    private String apiKey;

    /**
     * secret key
     */
    @Schema(description = "secret key")
    private String secret;

    /**
     * 收款账户
     */
    @Schema(description = "收款账户")
    private String account;

    /**
     * 第三方应用标识
     */
    @Schema(description = "第三方应用标识")
    private String appId;

    /**
     * 微信app_id
     */
    @Schema(description = "微信app_id")
    private String appId2;

    /**
     * 私钥
     */
    @Schema(description = "私钥")
    private String privateKey;

    /**
     * 公钥
     */
    @Schema(description = "公钥")
    private String publicKey;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;


    /**
     * PC前端后台接口地址
     */
    @Schema(description = "PC前端后台接口地址")
    private String pcBackUrl;

    /**
     * PC前端web地址
     */
    @Schema(description = "PC前端web地址")
    private String pcWebUrl;

    /**
     * boss后台接口地址
     */
    @Schema(description = "boss后台接口地址")
    private String bossBackUrl;

    /**
     * 微信开放平台app_id---微信app支付使用
     */
    private String openPlatformAppId;

    /**
     * 微信开放平台secret---微信app支付使用
     */
    private String openPlatformSecret;

    /**
     * 微信开放平台api key---微信app支付使用
     */
    private String openPlatformApiKey;

    /**
     * 微信开放平台商户号---微信app支付使用
     */
    private String openPlatformAccount;

}
