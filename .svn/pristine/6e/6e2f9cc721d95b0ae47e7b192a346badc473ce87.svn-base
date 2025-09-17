package com.wanmi.sbc.empower.pay.service.wechat;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.request.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayBossTransferRsponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPaySupplierTransferQueryDetailResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPaySupplierTransferQueryResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPaySupplierTransferRsponse;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.WxPayTradeType;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.repository.GatewayConfigRepository;
import com.wanmi.sbc.empower.wechat.WXPayUtility;
import com.wanmi.sbc.empower.wechat.transfer.TransferSceneReportInfo;
import com.wanmi.sbc.empower.wechat.transfer.TransferToUser;
import com.wanmi.sbc.empower.wechat.transfer.TransferToUserRequest;
import com.wanmi.sbc.empower.wechat.transfer.TransferToUserResponse;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName WechatTransferServiceImpl
 * @Description 商家转账到零钱
 * @Author qiyuanzhao
 * @Date 2022/8/18 17:53
 **/
@Slf4j
@Service
public class WechatTransferServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(WechatTransferServiceImpl.class);

    /***
     * 连接超时时间,默认10秒
     */
    private static int socketTimeout = 10000;

    /***
     * 传输超时时间,默认30秒
     */
    private static int connectTimeout = 30000;

    /***
     * HTTP请求器
     */
    private static CloseableHttpClient httpClient;

    private static final String BATCHNAME = "余额提现";

    /***
     * 商家转账到零钱接口地址
     */
    private static final String WXPAYSUPPLIERTRANSFERURL = "https://api.mch.weixin.qq.com/v3/transfer/batches";

    /***
     * 查询商家转账到零钱明细接口地址
     */
    private static final String WXPAYSUPPLIERTRANSFERQUERYURL = "https://api.mch.weixin.qq.com/v3/transfer/batches/batch-id";

    /***
     * 发起转账接口地址
     */
    private static final String WXPAYBOSSTRANSFERURL = "https://api.mch.weixin.qq.com//v3/fund-app/mch-transfer/transfer-bills";

    /***
     * 请求器的配置
     */
    private static RequestConfig requestConfig;


    @Autowired
    private GatewayConfigRepository gatewayConfigRepository;


    /**
     * 初始化httpclient
     **/
    private void initHttpClient(Long storeId) throws Exception{
        if (httpClient == null) {
            PayGatewayConfig payGatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT, storeId);
            String merchantId = payGatewayConfig.getOpenPlatformAccount();

            //私钥
            String privateKey = payGatewayConfig.getPrivateKey();

            //apiV3Key
            String apiV3Key = payGatewayConfig.getApiV3Key();

            //证书序列号
            String merchantSerialNumber = payGatewayConfig.getMerchantSerialNumber();

            // 加载商户私钥（privateKey：私钥字符串）
            PrivateKey merchantPrivateKey = PemUtil
                    .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));

            // 获取证书管理器实例
            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            // 向证书管理器增加需要自动更新平台证书的商户信息
            certificatesManager.putMerchant(merchantId, new WechatPay2Credentials(merchantId,
                    new PrivateKeySigner(merchantSerialNumber, merchantPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));

            // 从证书管理器中获取verifier
            Verifier verifier = certificatesManager.getVerifier(merchantId);

            WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                    .withMerchant(merchantId, merchantSerialNumber, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier));

            // 初始化httpClient
            setHttpClient(builder.build());
        }

    }

    /**
     * @description   静态变量赋值
     * @author  wur
     * @date: 2022/9/13 18:22
     * @param httpClient
     * @return
     **/
    private static void setHttpClient(CloseableHttpClient httpClient) {
        WechatTransferServiceImpl.httpClient = httpClient;
    }


    /**
     * 商家转账到零钱
     *
     * @Author qiyuanzhao
     **/
    public WxPaySupplierTransferRsponse wxPaySupplierTransfer(WxPayCompanyPaymentInfoRequest request)  {

        PayGatewayConfig payGatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT
                , request.getStoreId());
        String appId = payGatewayConfig.getAppId();
        if (request.getPayType() == WxPayTradeType.APP) {
            appId = payGatewayConfig.getOpenPlatformAppId();
        } else if (Objects.equals(request.getPayType(), WxPayTradeType.JSAPI)) { //小程序提现
            appId = request.getMiniAppId();
        }

        WxPaySupplierTransferInfoRequest wxPaySupplierTransferInfoRequest = WxPaySupplierTransferInfoRequest
                .builder()
                .out_detail_no(request.getPartner_trade_no())
                .transfer_amount(Integer.valueOf(request.getAmount()))
                .transfer_remark(request.getDesc())
                .openid(request.getOpenid())
                .build();

        WxPaySupplierTransferRequest supplierTransferRequest = WxPaySupplierTransferRequest
                .builder()
                .appid(appId)
                .out_batch_no(request.getPartner_trade_no())
                .batch_name(BATCHNAME)
                .batch_remark(request.getDesc())
                .total_amount(Integer.valueOf(request.getAmount()))
                .total_num(Constants.ONE)
                .transfer_detail_list(Collections.singletonList(wxPaySupplierTransferInfoRequest))
                .build();


        WxPaySupplierTransferRsponse wxPaySupplierTransferRsponse = new WxPaySupplierTransferRsponse();

        // 加载证书
        try {
            logger.info("初始化httpclient....");
            initHttpClient(request.getStoreId());

            logger.info("请求商家转账到零钱接口....");
            String jsonResponse = doPostData(WXPAYSUPPLIERTRANSFERURL, JSONObject.toJSONString(supplierTransferRequest));

            logger.info("商家转账到零钱接口返回值:{}", JSON.toJSONString(jsonResponse));
            wxPaySupplierTransferRsponse = JSONObject.parseObject(jsonResponse, WxPaySupplierTransferRsponse.class);

        } catch (Exception e) {
            wxPaySupplierTransferRsponse.setMessage(e.getMessage());
            e.printStackTrace();
        }

        //处理状态
        handelStatus(wxPaySupplierTransferRsponse);

        return wxPaySupplierTransferRsponse;
    }

    /**
     * @ClassName WechatTransferServiceImpl
     * @Description 处理调用转账接口的请求状态
     * @Author qiyuanzhao
     * @Date 2022/9/19 19:32
     **/
    private void handelStatus(WxPaySupplierTransferRsponse wxPaySupplierTransferRsponse) {
        if (StringUtils.isNotBlank(wxPaySupplierTransferRsponse.getMessage())){
            wxPaySupplierTransferRsponse.setSuccess(Boolean.FALSE);
        }else {
            wxPaySupplierTransferRsponse.setSuccess(Boolean.TRUE);
        }
    }

    /**
     * 查询转账状态
     *
     */
    public WxPaySupplierTransferQueryResponse wxPaySupplierTransferQuery(WxPaySupplierTransferQueryRequest queryRequest) {

        //转账微信批次单号
        String batchId = queryRequest.getBatchId();

        //https://api.mch.weixin.qq.com/v3/transfer/batches/batch-id/{batch_id}
        String url = WXPAYSUPPLIERTRANSFERQUERYURL +
                "/" +
                batchId +
                "?" +
                "need_query_detail=" +
                queryRequest.getNeed_query_detail() +
                "&" +
                "detail_status=" +
                queryRequest.getDetail_status();

        WxPaySupplierTransferQueryResponse response = new WxPaySupplierTransferQueryResponse();
        try {
            logger.info("初始化httpclient....");
            initHttpClient(queryRequest.getStoreId());

            logger.info("请求查询转账状态接口....");
            String jsonResponse = doGetData(url);

            logger.info("查询转账状态接口返回值:{}", JSON.toJSONString(jsonResponse));
            response = JSONObject.parseObject(jsonResponse, WxPaySupplierTransferQueryResponse.class);


        }catch (Exception e){
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return response;
    }


    /**
     * 查询转账明细
     *
     */
    public WxPaySupplierTransferQueryDetailResponse wxPaySupplierTransferDetailQuery(WxPaySupplierTransferQueryRequest transferQueryRequest) {
        //转账微信批次单号
        String batchId = transferQueryRequest.getBatchId();
        String detailId = transferQueryRequest.getDetailId();

        //https://api.mch.weixin.qq.com/v3/transfer/batches/batch-id/{batch_id}/details/detail-id/{detail_id}
        String url = WXPAYSUPPLIERTRANSFERQUERYURL + "/" + batchId + "/details/detail-id/" + detailId;

        WxPaySupplierTransferQueryDetailResponse response = new WxPaySupplierTransferQueryDetailResponse();
        try {
            logger.info("初始化httpclient....");
            initHttpClient(transferQueryRequest.getStoreId());

            logger.info("请求查询转账明细接口....");
            String jsonResponse = doGetData(url);

            logger.info("查询转账明细返回值:{}", JSON.toJSONString(jsonResponse));
            response = JSONObject.parseObject(jsonResponse, WxPaySupplierTransferQueryDetailResponse.class);

        }catch (Exception e){
            e.printStackTrace();
        }

        return response;

    }




    /**
     * 通过Https往API post json数据
     *
     * @param url    API地址
     * @return
     */
    public static String doGetData(String url) {

        String result = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpGet.addHeader("Accept", "application/json");

        // 根据默认超时限制初始化requestConfig
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        // 设置请求器的配置
        httpGet.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = null;
            if (Objects.nonNull(response)) {
                entity = response.getEntity();
            }
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpGet.abort();
        }
        return result;
    }

    /**
     * 通过Https往API post json数据
     *
     * @param url    API地址
     * @param jsonString 要提交的jsonString数据对象
     * @return
     */
    public static String doPostData(String url, String jsonString, Map<String, String> headers) {

        String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.addHeader("Accept", "application/json");
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 得指明使用UTF-8编码
        StringEntity postEntity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(postEntity);

        // 根据默认超时限制初始化requestConfig
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = null;
            if (Objects.nonNull(response)) {
                entity = response.getEntity();
            }
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpPost.abort();
        }
        return result;
    }


    /**
     * 通过Https往API post json数据
     *
     * @param url    API地址
     * @param jsonString 要提交的jsonString数据对象
     * @return
     */
    public static String doPostData(String url, String jsonString) {

        String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.addHeader("Accept", "application/json");

        // 得指明使用UTF-8编码
        StringEntity postEntity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(postEntity);

        // 根据默认超时限制初始化requestConfig
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = null;
            if (Objects.nonNull(response)) {
                entity = response.getEntity();
            }
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpPost.abort();
        }
        return result;
    }



    /**
     * 商家转账到零钱
     *
     * @Author qiyuanzhao
     **/
    public WxPayBossTransferRsponse wxPayBossTransfer(WxPayCompanyPaymentInfoRequest wxPayCompanyPaymentInfoRequest)  {
        PayGatewayConfig payGatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT
                , wxPayCompanyPaymentInfoRequest.getStoreId());

        // 判断数据是否有效
        if (Objects.isNull(payGatewayConfig)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        if (payGatewayConfig.getPayGateway().getIsOpen().equals(IsOpen.NO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        if (null == payGatewayConfig.getOpenPlatformAccount()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        if (null == payGatewayConfig.getMerchantSerialNumber()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        if (null == payGatewayConfig.getPublicKeyId()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        if (null == payGatewayConfig.getPublicKey()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        if (null == payGatewayConfig.getPrivateKey()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }


        // 构建签名
        String publicKeyString = payGatewayConfig.getPublicKey();
        PublicKey publicKey = WXPayUtility.loadPublicKeyFromString(publicKeyString);



        TransferToUserRequest request = new TransferToUserRequest();
        request.appid = "wxf636efh567hg4356";
        request.outBillNo = "plfk2020042013";
        request.transferSceneId = "1000";
        request.openid = "o-MYE42l80oelYMDE34nYD456Xoy";
        request.userName = encrypt(publicKey,"user_name");
        request.transferAmount = 1L;
        request.transferRemark = "新会员开通有礼";
        request.notifyUrl = "https://www.weixin.qq.com/wxpay/pay.php";
        request.userRecvPerception = "现金奖励";
        request.transferSceneReportInfos = new ArrayList<>();
        {
            TransferSceneReportInfo item0 = new TransferSceneReportInfo();
            item0.infoType = "活动名称";
            item0.infoContent = "新会员有礼";
            request.transferSceneReportInfos.add(item0);
            TransferSceneReportInfo item1 = new TransferSceneReportInfo();
            item1.infoType = "奖励说明";
            item1.infoContent = "注册会员抽奖一等奖";
            request.transferSceneReportInfos.add(item1);
        };
        try {
            TransferToUserResponse response = run(request, payGatewayConfig);
            // TODO: 请求成功，继续业务逻辑
            log.info("请求成功: response ", JSONObject.toJSONString( response));
        } catch (WXPayUtility.ApiException e) {
            // TODO: 请求失败，根据状态码执行不同的逻辑
            e.printStackTrace();
            log.error("请求失败，状态码：{}", e.getStatusCode());
            log.error("错误信息：{}", e.getErrorMessage());
        }



        // 构建返回体


        WxPayBossTransferRsponse wxPayBossTransferRsponse = new WxPayBossTransferRsponse();
        return wxPayBossTransferRsponse;
    }





    // TODO: 请准备商户开发必要参数，参考：https://pay.weixin.qq.com/doc/v3/merchant/4013070756
//    TransferToUser client = new TransferToUser(
//            "19xxxxxxxx",                    // 商户号，是由微信支付系统生成并分配给每个商户的唯一标识符，商户号获取方式参考 https://pay.weixin.qq.com/doc/v3/merchant/4013070756
//            "1DDE55AD98Exxxxxxxxxx",         // 商户API证书序列号，如何获取请参考 https://pay.weixin.qq.com/doc/v3/merchant/4013053053
//            "/path/to/apiclient_key.pem",    // 商户API证书私钥文件路径，本地文件路径
//            "PUB_KEY_ID_xxxxxxxxxxxxx",      // 微信支付公钥ID，如何获取请参考 https://pay.weixin.qq.com/doc/v3/merchant/4013038816
//            "/path/to/wxp_pub.pem"           // 微信支付公钥文件路径，本地文件路径
//    );
//
//    // 商户号，是由微信支付系统生成并分配给每个商户的唯一标识符，商户号获取方式参考 https://pay.weixin.qq.com/doc/v3/merchant/4013070756
//    private final String mchid;
//    // 商户API证书序列号，如何获取请参考 https://pay.weixin.qq.com/doc/v3/merchant/4013053053
//    private final String certificateSerialNo;
//    // 商户API证书私钥文件路径，本地文件路径
//    private final PrivateKey privateKey;
//    // 微信支付公钥ID，如何获取请参考 https://pay.weixin.qq.com/doc/v3/merchant/4013038816
//    private final String wechatPayPublicKeyId;
//    // 微信支付公钥文件路径，本地文件路径
//    private final PublicKey wechatPayPublicKey;

    private static String HOST = "https://api.mch.weixin.qq.com";
    private static String METHOD = "POST";
    private static String PATH = "/v3/fund-app/mch-transfer/transfer-bills";

    public String encrypt(PublicKey publicKey, String plainText) {
        return WXPayUtility.encrypt(publicKey, plainText);
    }
    public TransferToUserResponse run(TransferToUserRequest request, PayGatewayConfig payGatewayConfig) {


        // 构建请求
        String uri = PATH;
        String reqBody = WXPayUtility.toJson(request);

        // 构建签名
        String mchid = payGatewayConfig.getOpenPlatformAccount();
        String certificateSerialNo = payGatewayConfig.getMerchantSerialNumber();
        String wechatPayPublicKeyId = payGatewayConfig.getPublicKeyId();
        String publicKeyString = payGatewayConfig.getPublicKey();
        PublicKey publicKey = WXPayUtility.loadPublicKeyFromString(publicKeyString);
        String privateKeyString = payGatewayConfig.getPrivateKey();
        PrivateKey privateKey = WXPayUtility.loadPrivateKeyFromString(privateKeyString);
        String wechatpaySerial = payGatewayConfig.getMerchantSerialNumber();


        Request.Builder reqBuilder = new Request.Builder().url(HOST + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatpaySerial);
        reqBuilder.addHeader("Authorization", WXPayUtility.buildAuthorization(mchid, certificateSerialNo, privateKey, METHOD, uri, reqBody));
        reqBuilder.addHeader("Content-Type", "application/json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
        reqBuilder.method(METHOD, requestBody);
        Request httpRequest = reqBuilder.build();

        // 发送HTTP请求
        OkHttpClient client = new OkHttpClient.Builder().build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WXPayUtility.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                // 2XX 成功，验证应答签名
                WXPayUtility.validateResponse(wechatPayPublicKeyId, publicKey,
                        httpResponse.headers(), respBody);

                // 从HTTP应答报文构建返回数据
                return WXPayUtility.fromJson(respBody, TransferToUserResponse.class);
            } else {
                throw new WXPayUtility.ApiException(httpResponse.code(), respBody, httpResponse.headers());
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }


}
