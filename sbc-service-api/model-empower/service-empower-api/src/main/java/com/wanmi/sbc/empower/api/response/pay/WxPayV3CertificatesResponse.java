package com.wanmi.sbc.empower.api.response.pay;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description  获取微信支付V3 - 微信平台证书公钥
 * @author  wur
 * @date: 2022/12/1 15:58
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxPayV3CertificatesResponse implements Serializable {

    private static final long serialVersionUID = 6491764079121186091L;
    /**
     * 微信平台公钥
     */
    @Schema(description = "微信平台公钥")
    private String publicKey;
}
