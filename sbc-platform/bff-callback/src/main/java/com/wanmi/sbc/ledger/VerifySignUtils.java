package com.wanmi.sbc.ledger;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.vo.PrivatePayGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.*;

/**
 * 拉卡拉分账 回调验签
 */
@Slf4j
public class VerifySignUtils {

    private VerifySignUtils(){}

    public static final String CALL_BACK_SUCCESS = "000000";

    public static final String COMPLETED_EC_STATUS = "COMPLETED";


    /**
     * 字符集固定 utf-8
     */
    public static final String ENCODING = "utf-8";

    /**
     * 验证签名
     * @param authorization
     * @param body
     * @return
     */
    public static boolean verify(String authorization,String body) throws UnsupportedEncodingException {
        String[] strings = authorization.replace("LKLAPI-SHA256withRSA ", "").split(",");
        String[] timestampItem = strings[0].split("=");
        String timestamp = timestampItem[1].replaceAll("\"", "");
        String[] nonceItem = strings[1].split("=");
        String nonce = nonceItem[1].replaceAll("\"", "");
        String[] signatureItem = strings[2].split("=");
        String signature = signatureItem[1].replaceAll("\"", "");
        String preSignData = timestamp +"\n" + nonce + "\n" +body + "\n";
        PrivatePayGateway payGateway =
                JSONObject.parseObject(RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_PAY_SETTING),
                        PrivatePayGateway.class);
        byte[] wxOpenPayCertificate = payGateway.getConfig().getWxOpenPayCertificate();
        X509Certificate lklCertificate = loadCertificate(new ByteArrayInputStream(wxOpenPayCertificate));
        boolean verify = verify(lklCertificate, preSignData.getBytes(ENCODING), signature);
        return verify;
    }


    private static boolean verify(X509Certificate certificate, byte[] message, String signature) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(certificate);
            sign.update(message);
            byte[] signatureB = Base64.decodeBase64(signature);
            return sign.verify(signatureB);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名验证过程发生了错误", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的证书", e);
        }
    }

    public static X509Certificate loadCertificate(InputStream inputStream) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inputStream);
            cert.checkValidity();
            return cert;
        } catch (CertificateExpiredException e) {
            throw new RuntimeException("证书已过期", e);
        } catch (CertificateNotYetValidException e) {
            throw new RuntimeException("证书尚未生效", e);
        } catch (CertificateException e) {
            throw new RuntimeException("无效的证书", e);
        }
    }
    /**
     * 获取响应的字符串
     * @param inputStream
     * @return
     */
    public static String getRespStr(InputStream inputStream) {
        StringBuffer bf = new StringBuffer();
        try (InputStreamReader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8)){

            int len;
            char[] chs = new char[1024];
            while ((len = in.read(chs)) != -1) {
                bf.append(new String(chs, 0, len));
            }
        } catch (Exception e) {
            log.error("请求头部取数据异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "无效的请求数据");
        }
        return bf.toString();
    }

    /**
     * 拉卡拉回调成功的返回值
     * @return
     */
    public static JSONObject getSuccessResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","SUCCESS");
        jsonObject.put("message","执行成功");
        return jsonObject;
    }


    /**
     * 拉卡拉回调失败的返回值
     * @return
     */
    public static JSONObject getFailResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","FAIL");
        jsonObject.put("message","执行失败");
        return jsonObject;
    }
}
