package com.wanmi.sbc.pay.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.vo.PrivatePayGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.*;
import java.util.Objects;

/**
 * @author edz
 * @className PayVerifySignService
 * @description TODO
 * @date 2022/7/6 20:18
 **/
@Slf4j
public class PayVerifySignUtils {
    public static boolean lakalaCallBackVerify(String timestamp, String nonce, String signature, String body) {
        String preSignData = timestamp + "\n" + nonce + "\n" + body + "\n";
        log.info("preSignData:{}", preSignData);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            PrivatePayGateway payGatewayVO = getPayGateway();
            byte[] lklCertificateByte = payGatewayVO.getConfig().getWxOpenPayCertificate();
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(lklCertificateByte));
            cert.checkValidity();

            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(cert);
            sign.update(preSignData.getBytes(StandardCharsets.UTF_8));
            byte[] signatureB = Base64.decodeBase64(signature);
            return sign.verify(signatureB);
        } catch (CertificateExpiredException e) {
            log.error("证书已过期", e);
            throw new RuntimeException("证书已过期", e);
        } catch (CertificateNotYetValidException e) {
            log.error("证书尚未生效", e);
            throw new RuntimeException("证书尚未生效", e);
        } catch (CertificateException | InvalidKeyException e) {
            log.error("无效的证书", e);
            throw new RuntimeException("无效的证书", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("当前Java环境不支持SHA256withRSA", e);
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            log.error("签名验证过程发生了错误", e);
            throw new RuntimeException("签名验证过程发生了错误", e);
        }
    }

    public static PrivatePayGateway getPayGateway() {
        PrivatePayGateway payGateway =
                JSONObject.parseObject(RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_PAY_SETTING),
                        PrivatePayGateway.class);
        if (Objects.isNull(payGateway)) {
            log.info("拉卡拉配置错误，请检查！");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return payGateway;
    }

    public static boolean lakalaCasherCallBackVerify(String timestamp, String nonce, String signature, String body) {
        String preSignData = timestamp + "\n" + nonce + "\n" + body + "\n";
        log.info("lakala casher preSignData:{}", preSignData);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            PrivatePayGateway payGatewayVO = getLklCasherPayGateway();
            byte[] lklCertificateByte = payGatewayVO.getConfig().getWxOpenPayCertificate();
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(lklCertificateByte));
            cert.checkValidity();

            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(cert);
            sign.update(preSignData.getBytes(StandardCharsets.UTF_8));
            byte[] signatureB = Base64.decodeBase64(signature);
            return sign.verify(signatureB);
        } catch (CertificateExpiredException e) {
            log.error("证书已过期", e);
            throw new RuntimeException("证书已过期", e);
        } catch (CertificateNotYetValidException e) {
            log.error("证书尚未生效", e);
            throw new RuntimeException("证书尚未生效", e);
        } catch (CertificateException | InvalidKeyException e) {
            log.error("无效的证书", e);
            throw new RuntimeException("无效的证书", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("当前Java环境不支持SHA256withRSA", e);
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            log.error("签名验证过程发生了错误", e);
            throw new RuntimeException("签名验证过程发生了错误", e);
        }
    }

    public static PrivatePayGateway getLklCasherPayGateway() {
        PrivatePayGateway payGateway =
                JSONObject.parseObject(RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING),
                        PrivatePayGateway.class);
        if (Objects.isNull(payGateway)) {
            log.info("拉卡拉收银台配置错误，请检查！");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return payGateway;
    }
}
