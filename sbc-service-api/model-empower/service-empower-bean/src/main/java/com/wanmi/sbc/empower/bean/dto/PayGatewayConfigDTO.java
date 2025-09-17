package com.wanmi.sbc.empower.bean.dto;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * Created by sunkun on 2017/8/9.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class PayGatewayConfigDTO extends BaseRequest {

    /**
     * 身份标识
     */
    @Schema(description = "身份标识")
    private String apiKey;

    /**
     * 身份标识(apiV3Key)
     */
    @Schema(description = "身份标识(apiV3Key)")
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
    @Length(max = 60)
    private String secret;

    /**
     * 第三方应用标识
     */
    @Schema(description = "第三方应用标识")
    @Length(max = 40)
    private String appId;

    /**
     * 微信app_id
     */
    @Schema(description = "微信app_id")
    private String appId2;

    /**
     * 收款账号
     */
    @Schema(description = "收款账号")
    private String account;

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
     * 微信开放平台apiV3key---微信app支付使用
     */
    private String openPlatformApiV3Key;

    /**
     * 微信开放平台商户号---微信app支付使用
     */
    private String openPlatformAccount;
}
