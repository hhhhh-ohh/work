package com.wanmi.sbc.empower.api.request.pay.lakala;

import lombok.Data;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaPayVerifySign
 * @description TODO
 * @date 2022/7/6 19:37
 **/
@Data
public class LakalaPayVerifySignRequest implements Serializable {
    private String timestamp;
    private String nonce;
    private String signature;
    private String body;
}
