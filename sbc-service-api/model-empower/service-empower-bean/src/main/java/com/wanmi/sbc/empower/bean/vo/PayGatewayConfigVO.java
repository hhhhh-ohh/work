package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>支付网关配置项Response</p>
 * Created by of628-wenzhi on 2018-08-10-下午3:25.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PayGatewayConfigVO implements Serializable{

    private static final long serialVersionUID = 6917426870422118966L;
    @Schema(description = "支付网关配置项id")
    private Long id;

    @Schema(description = "支付网关")
    private PayGatewayVO payGateway;

    /**
     * 身份标识
     */
    @Schema(description = "身份标识")
    private String apiKey;

    /**
     * 身份标识
     */
    @Schema(description = "身份标识")
    private String apiV3Key;

    /**
     * 微信商户平台-证书编号
     **/
    @Schema(description = "证书编号")
    private String merchantSerialNumber;

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

    /**
     * 微信公众平台支付证书或者拉卡拉私钥
     */
    private byte[] wxPayCertificate;

    /**
     * 微信开放平台支付证书或者拉卡拉公钥
     */
    private byte[] wxOpenPayCertificate;
}
