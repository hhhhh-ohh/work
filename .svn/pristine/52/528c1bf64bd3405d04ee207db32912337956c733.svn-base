package com.wanmi.sbc.vo;

import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>支付网关配置项Response</p>
 * Created by of628-wenzhi on 2018-08-10-下午3:25.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PrivatePayGatewayConfig extends PayGatewayConfigVO {

    /**
     * 微信公众平台支付证书或者拉卡拉私钥
     */
    private byte[] wxPayCertificate;

    /**
     * 微信开放平台支付证书或者拉卡拉公钥
     */
    private byte[] wxOpenPayCertificate;
}
