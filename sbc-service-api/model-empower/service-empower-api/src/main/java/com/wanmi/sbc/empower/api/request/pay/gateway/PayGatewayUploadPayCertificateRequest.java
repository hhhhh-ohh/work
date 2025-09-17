package com.wanmi.sbc.empower.api.request.pay.gateway;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName PayGatewayUploadPayCertificateRequest
 * @Description 上传微信支付证书request类
 * @Author lvzhenwei
 * @Date 2019/5/7 10:00
 **/
@Schema
@Data
public class PayGatewayUploadPayCertificateRequest implements Serializable {

    private static final long serialVersionUID = 6302265727240467442L;
    /**
     * 网关id
     */
    @Schema(description = "网关id")
    private Long id;

    /**
     * 微信支付证书二进制流
     */
    private byte[] payCertificate;

    /**
     * 微信支付证书类型 1:公众平台微信支付证书；2:开放平台微信支付证书 3: 拉卡拉私钥 4.拉卡拉公钥
     */
    private Integer payCertificateType;

}
