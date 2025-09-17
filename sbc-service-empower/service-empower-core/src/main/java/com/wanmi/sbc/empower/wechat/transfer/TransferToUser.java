package com.wanmi.sbc.empower.wechat.transfer;

import com.wanmi.sbc.empower.wechat.WXPayUtility;

import java.security.PrivateKey;
import java.security.PublicKey;

public class TransferToUser {

    private final String mchid;
    private final String certificateSerialNo;
    private final PrivateKey privateKey;
    private final String wechatPayPublicKeyId;
    private final PublicKey wechatPayPublicKey;

    public TransferToUser(String mchid, String certificateSerialNo, String privateKey, String wechatPayPublicKeyId, String wechatPayPublicKey) {
        this.mchid = mchid;
        this.certificateSerialNo = certificateSerialNo;
        this.privateKey = WXPayUtility.loadPrivateKeyFromString(privateKey);
        this.wechatPayPublicKeyId = wechatPayPublicKeyId;
        this.wechatPayPublicKey = WXPayUtility.loadPublicKeyFromString(wechatPayPublicKey);
    }
}
