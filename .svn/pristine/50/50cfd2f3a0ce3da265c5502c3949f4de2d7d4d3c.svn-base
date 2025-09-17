package com.wanmi.sbc.empower.pay.sdk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhanggaolei
 * @type UnionPayProperties.java
 * @desc
 * @date 2023/2/28 10:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "union.acpsdk")
public class UnionPayProperties {
    /** 前台请求URL. */
    private String frontTransUrl;
    /** 后台请求URL. */
    private String backTransUrl;
    /** 单笔查询 */
    private String singleQueryUrl;
    /** 批量查询 */
    private String batchQueryUrl;
    /** 批量交易 */
    private String batchTransUrl;
    /** 文件传输 */
    private String fileTransUrl;
    /** 签名证书路径. */
    private String signCertPath;
    /** 签名证书密码. */
    private String signCertPwd;
    /** 签名证书类型. */
    private String signCertType;
    /** 加密公钥证书路径. */
    private String encryptCertPath;
    /** 验证签名公钥证书目录. */
    private String validateCertDir;
    /** 按照商户代码读取指定签名证书目录. */
    private String signCertDir;
    /** 磁道加密证书路径. */
    private String encryptTrackCertPath;
    /** 磁道加密公钥模数. */
    private String encryptTrackKeyModulus;
    /** 磁道加密公钥指数. */
    private String encryptTrackKeyExponent;
    /** 有卡交易. */
    private String cardTransUrl;
    /** app交易 */
    private String appTransUrl;
    /** 证书使用模式(单证书/多证书) */
    private String singleMode;
    /** 安全密钥(SHA256和SM3计算时使用) */
    private String secureKey;
    /** 中级证书路径  */
    private String middleCertPath;
    /** 根证书路径  */
    private String rootCertPath;
    /** 是否验证验签证书CN，除了false都验  */
    private boolean ifValidateCNName = true;
    /** 是否验证https证书，默认都不验  */
    private boolean ifValidateRemoteCert = false;
    /** signMethod，没配按01吧  */
    private String signMethod = "01";
    /** version，没配按5.0.0  */
    private String version = "5.0.0";
    /** frontUrl  */
    private String frontUrl;
    /** backUrl  */
    private String backUrl;

    /*缴费相关地址*/
    private String jfFrontTransUrl;
    private String jfBackTransUrl;
    private String jfSingleQueryUrl;
    private String jfCardTransUrl;
    private String jfAppTransUrl;

    private String qrcBackTransUrl;
    private String qrcB2cIssBackTransUrl;
    private String qrcB2cMerBackTransUrl;
}
