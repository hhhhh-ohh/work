package com.wanmi.sbc.empower.legder.lakala;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaPayBaseResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Objects;


@Slf4j
public class LakalaUtils {

    private LakalaUtils() {}
    /**
     * 字符集固定 utf-8
     */
    public static final String ENCODING = "utf-8";

    /**
     * API schema ,固定 LKLAPI-SHA256withRSA
     */
    public final static String SCHEMA = "LKLAPI-SHA256withRSA";


    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final SecureRandom RANDOM = new SecureRandom();


    public static final String EC_APPLY_URL = "/ec/apply";

    public static final String EC_APPLY_CALL_BACK_URL = "/ledgeCallback/ec/applyCallBack";

    public static final String EC_DOWNLOAD_URL = "/ec/download";

    public static final String EC_Q_STATUS_URL = "/ec/qStatus";

    public static final String UPLOAD_FILE_URL = "/uploadFile";

    public static final String VERIFY_CONTRACT_INFO_URL = "/verifyContractInfo";


    public static final String ADD_MER_URL = "/addMer";

    public static final String ADD_MER_CALL_BACK_URL = "/ledgeCallback/addMerCallBack";


    public static final String RECONSIDER_SUBMIT_URL = "/reconsiderSubmit";

    public static final String QUERY_CONTRACT_URL = "/queryContract";


    public static final String CARD_BIN_SUBMIT_URL = "/cardBin";

    public static final String APPLY_SPLIT_MER_URL = "/ntsa/applySplitMer";

    public static final String QUERY_SPLIT_MER_URL = "/ntsa/querySplitMer";

    public static final String APPLY_SPLIT_MER_CALL_BACK_URL = "/ledgeCallback/applySplitMerCallBack";

    public static final String APPLY_SPLIT_RECEIVER_URL = "/ntsa/applySplitReceiver";

    public static final String APPLY_BIND_URL = "/ntsa/applyBind";

    public static final String APPLY_BIND_CALL_BACK_URL = "/ledgeCallback/applyBindCallBack";

    public static final String LA_KA_LA_SUCCESS_CODE = "000000";

    public static final String PAY_CALLBACK_URL = "/tradeCallback/lakala/pay/callBack";

    public static final String ADD_B2B_BUSI = "/addB2bBusi";

    public static final String ADD_B2B_BUSI_CALL_BACK_URL = "/ledgeCallback/addB2bBusiCallBack";


    /***
     * 线程安全，所有的线程都可以使用它一起发送http请求
     */
    private static final CloseableHttpClient httpClient = HttpUtils.getHttpClient();

    //拉卡拉配置信息
    private static PayGateway payGateway;


//    public static void main(String[] args) {
//        try {
//            BaseRequest baseRequest = BaseRequest.builder()
//                    .reqData(LakalaEcDownloadRequest.builder()
//                            .orgId(1)
//                            .orgCode("1")
//                            .ecApplyId(489929378651758592L)
//                            .orderNo("e0a1d6f8dbca4ee99dddf8a805778d37")
//                            .build())
//                    .build();
//            String body = JSON.toJSONString(baseRequest);
//            String authorization = getAuthorization(body);
//            BaseResponse<EcDownloadResponse> post = post(apiUrl + ecDownloadUrl, body, authorization, EcDownloadResponse.class);
//            log.info("post{}",post);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public static boolean lakalaVerify(HttpResponse response,String responseStr)  {
        String appid = getHeadValue(response, "Lklapi-Appid");
        String lklapiSerial = getHeadValue(response, "Lklapi-Serial");
        String timestamp = getHeadValue(response, "Lklapi-Timestamp");
        String nonce = getHeadValue(response, "Lklapi-Nonce");
        String signature = getHeadValue(response, "Lklapi-Signature");

        String source = appid + "\n" + lklapiSerial + "\n" + timestamp + "\n" + nonce + "\n" + responseStr + "\n";

        log.info("source  " + source);

        X509Certificate lklCertificate;
        try {
            byte[] lklCertificateByte = payGateway.getConfig().getWxOpenPayCertificate();
            lklCertificate = loadCertificate(new ByteArrayInputStream(lklCertificateByte));
            return verify(lklCertificate, source.getBytes(ENCODING), signature);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
    }

    public static <T> BaseResponse<T> post(String url, String message, String authorization, Class<T> clazz) {
        String resp = postForCom(url, message, authorization);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        JSONObject respData = jsonObject.getJSONObject("respData");
        String jsonString = JSON.toJSONString(respData);
        T t = JSON.parseObject(jsonString, clazz);
        if (jsonObject.containsKey("retCode")){
            String retCode = jsonObject.getString("retCode");
            if (LA_KA_LA_SUCCESS_CODE.equals(retCode)) {
                return BaseResponse.success(t);
            }
        }
        log.error("LA_KA_LA_ERR--POST请求url={},请求参数={}", url, message);
        log.error("LA_KA_LA_ERR_MSG:{}",jsonObject);
        return BaseResponse.info(EmpowerErrorCodeEnum.K060029.getCode(), jsonObject.getString("retMsg"));
    }

    public static <T> LakalaPayBaseResponse<T> postForPay(
            String url, String message, String authorization, Class<T> clazz) {
        String resp = postForCom(url, message, authorization);
        return JSON.parseObject(resp, new TypeReference<LakalaPayBaseResponse<T>>(clazz) {});
    }

    public static String postForCom(String url, String message, String authorization) {
        log.info("lakala--POST请求url={},请求参数={}", url, message);
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            StringEntity myEntity = new StringEntity(message, ENCODING);
            post.setEntity(myEntity);
            post.setHeader("Authorization", SCHEMA + " " + authorization);
            post.setHeader("Content-Type", "application/json");
            log.info("HttpUtils postData==================== {}", JSONObject.toJSONString(post));
            response = httpClient.execute(post);
            log.info("HttpUtils responseData==================== {}", JSONObject.toJSONString(response));
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
            }
            String responseStr = IOUtils.toString(response.getEntity().getContent(), ENCODING);
            log.info("responseStr  " + responseStr);
//            boolean verify = lakalaVerify(response,responseStr);
//            if (!verify) {
//                log.info("验签失败");
//                throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
//            }
            return responseStr;
        } catch (IOException e) {
            log.error("收集服务配置http请求异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("HttpUtils postData 关闭流异常!", e);
            }
        }
    }

    protected  static long generateTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    protected static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public static String getAuthorization(String body) {
        // 获取拉卡拉appId
        String appId = payGateway.getConfig().getAppId();
        // 获取拉卡拉证书编号
        String mchSerialNo = payGateway.getConfig().getAppId2();
        String nonceStr = generateNonceStr();
        long timestamp = generateTimestamp();
        String message = appId + "\n" + mchSerialNo + "\n" + timestamp + "\n" + nonceStr + "\n" + body + "\n";
        log.info("getToken message :  " + message);
        PrivateKey merchantPrivateKey;
        String signature;
        try {
            //获取拉卡拉私钥证书字节数组
            byte[] merchantPrivateKeyByte = payGateway.getConfig().getWxPayCertificate();
            merchantPrivateKey = loadPrivateKey(new ByteArrayInputStream(merchantPrivateKeyByte));
            signature = sign(message.getBytes(ENCODING), merchantPrivateKey);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        String authorization = "appid=\"" + appId + "\"," + "serial_no=\"" + mchSerialNo + "\"," + "timestamp=\""
                + timestamp + "\"," + "nonce_str=\"" + nonceStr + "\"," + "signature=\"" + signature + "\"";
        log.info("authorization message :" + authorization);
        return authorization;
    }

    public  static String sign(byte[] message, PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(message);
            return new String(Base64.encodeBase64(sign.sign()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名计算失败", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的私钥", e);
        }
    }

    private static String getHeadValue(HttpResponse response, String key) {
        return (response.containsHeader(key)) ? response.getFirstHeader(key).getValue() : "";
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

    public static PrivateKey loadPrivateKey(InputStream inputStream) {
        try {
            ByteArrayOutputStream array = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                array.write(buffer, 0, length);
            }

            String privateKey = array.toString("utf-8").replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        } catch (IOException e) {
            throw new RuntimeException("无效的密钥");
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
     * 从redis中获取拉卡拉配置并存在treadLocal中
     * @return
     */
    public static PayGateway getPayGateway() {
        payGateway = JSONObject.parseObject(RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_PAY_SETTING), PayGateway.class);
        if (Objects.isNull(payGateway)) {
            log.info("拉卡拉配置错误，请检查！");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        if (IsOpen.NO.equals(payGateway.getIsOpen())) {
            log.info("拉卡拉支付已关闭，请检查！");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return payGateway;
    }

    public static PayGateway getPayGatewayIgnoreSwitch() {
        payGateway = JSONObject.parseObject(RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING), PayGateway.class);
        if (Objects.isNull(payGateway)) {
            log.info("拉卡拉配置错误，请检查！");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return payGateway;
    }

    /**
     * 从redis中获取拉卡拉收银台配置并存在treadLocal中
     * @return
     */
    public static PayGateway getLklCasherPayGateway() {
        payGateway = JSONObject.parseObject(RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING), PayGateway.class);
        if (Objects.isNull(payGateway)) {
            log.info("拉卡拉配置错误，请检查！");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        if (IsOpen.NO.equals(payGateway.getIsOpen())) {
            log.info("拉卡拉支付已关闭，请检查！");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return payGateway;
    }

    /**
     * 获取支付开关状态
     *
     * @return true 开启
     */
    public static PayGatewayVO getPayGatewayVO() {
        PayGatewayVO payGatewayVO = JSONObject.parseObject(RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_PAY_SETTING), PayGatewayVO.class);
        PayGatewayVO payGatewayVO2 = JSONObject.parseObject(RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING), PayGatewayVO.class);
        if (Objects.nonNull(payGatewayVO) && IsOpen.YES.equals(payGatewayVO.getIsOpen())) {
            return payGatewayVO;
        }
        if (Objects.nonNull(payGatewayVO2) && IsOpen.YES.equals(payGatewayVO2.getIsOpen())) {
            return payGatewayVO2;
        }
        return payGatewayVO2;
    }
}
