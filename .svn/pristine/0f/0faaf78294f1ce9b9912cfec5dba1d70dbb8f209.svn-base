package com.wanmi.sbc.empower.pay.service.wechat;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayCloseOrderRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.PayGatewayUploadPayCertificateRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.*;
import com.wanmi.sbc.empower.api.request.sellplatform.order.PlatformOrderRequest;
import com.wanmi.sbc.empower.api.response.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformCompanyResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformOrderPayParamResponse;
import com.wanmi.sbc.empower.bean.constant.PayServiceConstants;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.WxPayTradeType;
import com.wanmi.sbc.empower.bean.vo.sellplatform.order.PlatformCompanyVO;
import com.wanmi.sbc.empower.miniprogramset.service.MiniProgramSetService;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.model.root.WechatConfig;
import com.wanmi.sbc.empower.pay.repository.GatewayConfigRepository;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.empower.pay.service.PayDataService;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformOrderService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import com.wanmi.sbc.empower.wechat.WechatApiUtil;
import com.wanmi.sbc.empower.wechat.constant.WechatApiConstant;
import com.wanmi.sbc.empower.wechatloginset.service.WechatLoginSetService;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.wxpayuploadshippinginfo.WxPayUploadShippingInfoProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 微信支付
 */
@Slf4j
@Service(PayServiceConstants.WECHAT_SERVICE)
public class WechatPayServiceImpl implements PayBaseService {

    /***
     * 连接超时时间,默认10秒
     */
    private static int socketTimeout = 10000;
    /***
     * 传输超时时间,默认30秒
     */
    private static int connectTimeout = 30000;
    /***
     * 请求器的配置
     */
    private static RequestConfig requestConfig;
    /***
     * HTTP请求器
     */
    private static CloseableHttpClient httpClient;
    /***
     *  微信支付类型--为app，对应调用参数对应开放平台参数
     */
    private static final String WXPAYAPPTYPE = "APP";
    /***
     * 微信退款调用微信接口地址
     */
    private static final String WXPAYREFUNDURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /***
     * 微信支付调用微信接口地址
     */
    private static final String WXUNIFIEDORDERURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /***
     * 微信企业付款到零钱接口地址
     */
    private static final String WXPAYCOMPANYPAYMENTURL = "https://api.mch.weixin.qq" +
            ".com/mmpaymkttransfers/promotion/transfers";

    /***
     * 查询订单支付单详情
     */
    private static final String WXPAYORDERQUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

    /***
     * 查询订单支付单详情
     */
    private static final String WXPAYCLOSEORDER = "https://api.mch.weixin.qq.com/pay/closeorder";

    /***
     * 查询退款
     */
    private static final String WXPAYREFUNDQUERY = "https://api.mch.weixin.qq.com/pay/refundquery";

    /**
     * 微信退款成功回调地址
     */
    private static final String WXREFUNDSUCCCALLBACK = "/tradeCallback/WXPayRefundSuccessCallBack";

    /**
     * 微信支付成功回调地址
     */
    private static final String WXPAYSUCCCALLBACK = "/tradeCallback/WXPaySuccessCallBack";

    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private GatewayConfigRepository gatewayConfigRepository;

    @Autowired
    private PayDataService payDataService;

    @Autowired
    private PlatformContext thirdPlatformContext;

    @Autowired
    private WechatLoginSetService wechatLoginSetService;

    @Autowired
    private MiniProgramSetService miniProgramSetService;

    @Autowired
    private WechatApiUtil wechatApiUtil;

    @Autowired
    private WxPayUploadShippingInfoProvider wxPayUploadShippingInfoProvider;

    /**
     * 统一下单接口--native扫码支付（pc扫码支付）
     *
     * @param request
     */
    @Transactional
    public WxPayForNativeResponse wxPayForNative(WxPayForNativeRequest request, String tradeId) {
        WxPayForNativeResponse response = new WxPayForNativeResponse();
        try {
            WxPayForNativeBaseRequest baseRequest = KsBeanUtil.convert(request, WxPayForNativeBaseRequest.class);

            //将订单对象转为xml格式
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayForNativeRequest.class);//根元素名需要是xml
            //添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tradeId);
            payTradeRecordRequest.setPayNo(request.getOut_trade_no());
            payTradeRecordRequest.setApplyPrice(new BigDecimal(request.getTotal_fee()).divide(new BigDecimal(100)).
                    setScale(2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(request.getSpbill_create_ip());
            payTradeRecordRequest.setChannelItemId(14L);
            addTradeRecord(payTradeRecordRequest);
            log.info("wxPayForNative.baseRequest={}", JSON.toJSONString(baseRequest));
            response = wxPayUnifiedOrder(xStream.toXML(baseRequest), WxPayForNativeResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setTime_stamp(String.valueOf(WXPayUtil.getCurrentTimestamp()));
        return response;
    }

    /**
     * 统一下单接口--非微信浏览器h5支付
     *
     * @param request
     */
    @Transactional
    public WxPayForMWebResponse wxPayForMWeb(WxPayForMWebRequest request, String tradeId) {
        WxPayForMWebResponse response = new WxPayForMWebResponse();
        try {
            WxPayForMWebBaseRequest baseRequest = KsBeanUtil.convert(request, WxPayForMWebBaseRequest.class);

            //将订单对象转为xml格式
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayForNativeRequest.class);//根元素名需要是xml
            //添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tradeId);
            payTradeRecordRequest.setPayNo(request.getOut_trade_no());
            payTradeRecordRequest.setApplyPrice(new BigDecimal(request.getTotal_fee()).divide(new BigDecimal(100)).
                    setScale(2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(request.getSpbill_create_ip());
            payTradeRecordRequest.setChannelItemId(15L);
            addTradeRecord(payTradeRecordRequest);
            log.info("wxPayForMWeb.baseRequest={}", JSON.toJSONString(baseRequest));
            response = wxPayUnifiedOrder(xStream.toXML(baseRequest), WxPayForMWebResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 统一下单接口--微信浏览器内JSApi支付
     *
     * @param request
     */
    @Transactional
    public WxPayForJSApiResponse wxPayForJSApi(WxPayForJSApiRequest request, String tradeId) {
        WxPayForJSApiResponse response = new WxPayForJSApiResponse();
        try {
            WxPayForJSApiBaseRequest baseRequest = KsBeanUtil.convert(request, WxPayForJSApiBaseRequest.class);
            //将订单对象转为xml格式
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayForJSApiBaseRequest.class);//根元素名需要是xml
            //添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tradeId);
            payTradeRecordRequest.setPayNo(request.getOut_trade_no());
            payTradeRecordRequest.setApplyPrice(new BigDecimal(request.getTotal_fee()).divide(new BigDecimal(100)).
                    setScale(2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(request.getSpbill_create_ip());
            payTradeRecordRequest.setChannelItemId(16L);
            addTradeRecord(payTradeRecordRequest);
            log.info("wxPayForJSApi.baseRequest={}", JSON.toJSONString(baseRequest));
            response = wxPayUnifiedOrder(xStream.toXML(baseRequest), WxPayForJSApiResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 统一下单接口--微信App支付
     *
     * @param request
     */
    @Transactional
    public WxPayForAppResponse wxPayForApp(WxPayForAppRequest request, String tradeId) {
        WxPayForAppResponse response = new WxPayForAppResponse();
        try {
            WxPayForAppBaseRequest baseRequest = KsBeanUtil.convert(request, WxPayForAppBaseRequest.class);

            //将订单对象转为xml格式
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayForNativeRequest.class);//根元素名需要是xml
            //添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tradeId);
            payTradeRecordRequest.setPayNo(request.getOut_trade_no());
            payTradeRecordRequest.setApplyPrice(new BigDecimal(request.getTotal_fee()).divide(new BigDecimal(100)).
                    setScale(2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(request.getSpbill_create_ip());
            payTradeRecordRequest.setChannelItemId(20L);
            addTradeRecord(payTradeRecordRequest);
            log.info("wxPayForApp.baseRequest={}", JSON.toJSONString(baseRequest));
            response = wxPayUnifiedOrder(xStream.toXML(baseRequest), WxPayForAppResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 调统一下单API
     *
     * @param orderInfo
     * @return
     */
    private <T> T wxPayUnifiedOrder(String orderInfo, Class<T> valueType) throws IllegalAccessException,
            InstantiationException {
        log.info("wxPayUnifiedOrder+++++++orderInfo={}", orderInfo);
        T t = valueType.newInstance();
        BufferedReader reader = null;
        BufferedOutputStream buffOutStr = null;
        InputStreamReader inputStreamReader = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(WXUNIFIEDORDERURL).openConnection();
            //加入数据
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            buffOutStr = new BufferedOutputStream(conn.getOutputStream());
            buffOutStr.write(orderInfo.getBytes(StandardCharsets.UTF_8));
            buffOutStr.flush();
            buffOutStr.close();
            //获取输入流
            inputStreamReader = new InputStreamReader(conn.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
            //将请求返回的内容通过xStream转换为UnifiedOrderResponse对象
            xStream.alias("xml", valueType);
            Class<?>[] classes = new Class[]{valueType};
            xStream.allowTypes(classes);
            t = (T) xStream.fromXML(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != buffOutStr) {
                    buffOutStr.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }


    private <T> T wxPayOrderDetail(String orderInfo, Class<T> valueType, String url) throws IllegalAccessException,
            InstantiationException {
        T t = valueType.newInstance();
        BufferedReader reader = null;
        BufferedOutputStream buffOutStr = null;
        InputStreamReader inputStreamReader = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            //加入数据
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            buffOutStr = new BufferedOutputStream(conn.getOutputStream());
            buffOutStr.write(orderInfo.getBytes(StandardCharsets.UTF_8));
            buffOutStr.flush();
            buffOutStr.close();
            //获取输入流
            inputStreamReader = new InputStreamReader(conn.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String resultXml = sb.toString();
            resultXml = resultXml.replaceAll("<coupon_id_[0-9]{0,11}[^>]*>(.*?)</coupon_id_[0-9]{0,11}>", "");
            resultXml = resultXml.replaceAll("<coupon_type_[0-9]{0,11}[^>]*>(.*?)</coupon_type_[0-9]{0,11}>", "");
            resultXml = resultXml.replaceAll("<coupon_fee_[0-9]{0,11}[^>]*>(.*?)</coupon_fee_[0-9]{0,11}>", "");
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
            //将请求返回的内容通过xStream转换为UnifiedOrderResponse对象
            xStream.alias("xml", valueType);
            Class<?>[] classes = new Class[]{valueType};
            xStream.allowTypes(classes);
            t = (T) xStream.fromXML(resultXml);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != buffOutStr) {
                    buffOutStr.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }


    /**
     * 微信企业付款到零钱
     *
     * @param request
     */
    public WxPayCompanyPaymentRsponse wxPayCompanyPayment(WxPayCompanyPaymentInfoRequest request) {
        PayGatewayConfig payGatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT
                , request.getStoreId());
        Integer sceneType = NumberUtils.INTEGER_ZERO;
        if (request.getPayType() == WxPayTradeType.APP) {
            sceneType = 2;
        }
        WechatConfig wechatConfig = payDataService.findBySceneTypeAndStoreId(sceneType,
                Constants.BOSS_DEFAULT_STORE_ID);
        if (Objects.isNull(wechatConfig) || Objects.isNull(payGatewayConfig)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }

        String appId = wechatConfig.getAppId();
        String apiKey = payGatewayConfig.getApiKey();
        String account = payGatewayConfig.getAccount();
        if (Objects.equals(request.getPayType(), WxPayTradeType.JSAPI)) { //小程序提现
            appId = request.getMiniAppId();
        }
        WxPayCompanyPaymentRequest companyPaymentRequest = new WxPayCompanyPaymentRequest();
        companyPaymentRequest.setPartner_trade_no(request.getPartner_trade_no());
        companyPaymentRequest.setOpenid(request.getOpenid());
        companyPaymentRequest.setCheck_name(request.getCheck_name());
        companyPaymentRequest.setRe_user_name(request.getRe_user_name());
        companyPaymentRequest.setAmount(request.getAmount());
        companyPaymentRequest.setDesc(request.getDesc());
        companyPaymentRequest.setSpbill_create_ip(request.getSpbill_create_ip());
        companyPaymentRequest.setMch_appid(appId);
        companyPaymentRequest.setMchid(account);
        companyPaymentRequest.setNonce_str(WXPayUtil.generateNonceStr());
        try {
            Map<String, String> companyPaymentMap = WXPayUtil.objectToMap(companyPaymentRequest);
            //获取签名
            String sign = WXPayUtil.generateSignature(companyPaymentMap, apiKey);
            companyPaymentRequest.setSign(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将对象转为xml格式
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.alias("xml", WxPayForNativeRequest.class);//根元素名需要是xml
        String refundXmlStr = xStream.toXML(companyPaymentRequest);
        WxPayCompanyPaymentRsponse wxPayCompanyPaymentRsponse = new WxPayCompanyPaymentRsponse();
        //带证书的post
        // 加载证书
        try {
            initCert(account, request.getPayType().toString(), request.getStoreId());
            String resXml = postData(WXPAYCOMPANYPAYMENTURL, refundXmlStr);
            //解析xml为集合,请打断点查看resXml详细信息
            Map<String, String> refundResultMap = WXPayUtil.xmlToMap(resXml);
            wxPayCompanyPaymentRsponse = (WxPayCompanyPaymentRsponse) WXPayUtil.mapToObject(refundResultMap,
                    WxPayCompanyPaymentRsponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxPayCompanyPaymentRsponse;
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return
     */
    public static String postData(String url, String xmlObj) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        // 得指明使用UTF-8编码,否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        // 根据默认超时限制初始化requestConfig
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                log.info("wxPayRefund=======>>>httpClient={}", httpClient);
                log.info("wxPayRefund=======>>>httpPost={}", httpPost);
                response = httpClient.execute(httpPost);
                log.info("wxPayRefund=======>>>httpClient-after={}", httpPost);
            } catch (IOException e) {
                log.error("httpClient.execute",e);
                e.printStackTrace();
            } catch (Exception e){
                log.error("httpClient.execute-1",e);
                throw e;
            }
            HttpEntity entity = null;
            log.info("wxPayRefund=======>>>HttpResponse={}", response);
            if (Objects.nonNull(response)) {
                entity = response.getEntity();
            }
            try {
                result = EntityUtils.toString(entity, "UTF-8");
                log.info("wxPayRefund=======>>>result={}", result);
            } catch (IOException e) {
                e.printStackTrace();
            }
         }catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }
        return result;
    }

    /**
     * 加载证书
     *
     * @param machId
     * @throws Exception
     */
    private void initCert(String machId, String type, Long storeId) throws Exception {
        PayGatewayConfig payGatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT
                , storeId);
        // 证书密码,默认为商户ID
        String key = machId;
        // 指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 读取PKCS12证书文件
        InputStream instream = new ByteArrayInputStream(payGatewayConfig.getWxPayCertificate());
        //如果退款为app支付，则证书用对应微信开放平台参数
        if (type.equals(WXPAYAPPTYPE)) {
            instream = new ByteArrayInputStream(payGatewayConfig.getWxOpenPayCertificate());
        }
        try {
            // 指定PKCS12的密码(商户ID)
            keyStore.load(instream, key.toCharArray());
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray())
                .build();
        // 指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1.2"},
                null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        // 设置httpclient的SSLSocketFactory
        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    /**
     * 添加订单支付--交易记录
     *
     * @param request
     */
    private void addTradeRecord(PayTradeRecordRequest request) {
        payTradeRecordProvider.queryAndSave(request);
    }

    /**
     * @return
     * @Description 微信支付查询退款单
     * @Date 14:27 2020/9/17
     * @Param [request]
     **/
    public WxRefundOrderDetailReponse wxRefundQueryOrder(WxRefundOrderDetailRequest request) {
        WxRefundOrderDetailReponse wxPayCloseOrderResponse = new WxRefundOrderDetailReponse();
        try {
            PayGatewayConfig payGatewayConfig =
                    gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT, request.getStoreId());

            WechatConfig wechatConfig = payDataService.findBySceneTypeAndStoreId(NumberUtils.INTEGER_ZERO,
                    Constants.BOSS_DEFAULT_STORE_ID);
            if (Objects.isNull(wechatConfig) || Objects.isNull(payGatewayConfig)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
            String appId = wechatConfig.getAppId();
            String apiKey = payGatewayConfig.getApiKey();
            String account = payGatewayConfig.getAccount();
            PayTradeRecordResponse payTradeRecord = payTradeRecordQueryProvider.getTradeRecordByOrderCode(
                    TradeRecordByOrderCodeRequest.builder().orderId(request.getBusinessId()).build()).getContext();
            if (Objects.nonNull(payTradeRecord) && Objects.nonNull(payTradeRecord.getChannelItemId())) {
                PayChannelItem channelItem = payDataService.queryItemById(payTradeRecord.getChannelItemId());
                if ("wx_app".equals(channelItem.getCode())) {
                    wechatConfig = payDataService.findBySceneTypeAndStoreId(2, Constants.BOSS_DEFAULT_STORE_ID);
                    if (Objects.isNull(wechatConfig)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                    }
                    appId = wechatConfig.getAppId();
                } else if ("js_api".equals(channelItem.getCode())) { //如果是jsapi支付
                    wechatConfig = payDataService.findBySceneTypeAndStoreId(NumberUtils.INTEGER_ONE,
                            Constants.BOSS_DEFAULT_STORE_ID);
                    if (Objects.isNull(wechatConfig)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                    }
                    appId = wechatConfig.getAppId();
                }
            }

            WxRefundQueryOrderBaseRequest refundQueryOrderBaseRequest = new WxRefundQueryOrderBaseRequest();
            refundQueryOrderBaseRequest.setAppid(appId);
            refundQueryOrderBaseRequest.setMch_id(account);
            refundQueryOrderBaseRequest.setOut_refund_no(request.getBusinessId());
            refundQueryOrderBaseRequest.setNonce_str(WXPayUtil.generateNonceStr());
            Map<String, String> wxPayCloseOrderMap = null;
            wxPayCloseOrderMap = WXPayUtil.objectToMap(refundQueryOrderBaseRequest);
            //获取签名
            String sign = WXPayUtil.generateSignature(wxPayCloseOrderMap, apiKey);
            refundQueryOrderBaseRequest.setSign(sign);
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayForNativeRequest.class);//根元素名需要是xml
            String refundXmlStr = xStream.toXML(refundQueryOrderBaseRequest);
            wxPayCloseOrderResponse = wxRefundOrderDetail(refundXmlStr, WxRefundOrderDetailReponse.class,
                    WXPAYCLOSEORDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxPayCloseOrderResponse;
    }

    private <T> T wxRefundOrderDetail(String orderInfo, Class<T> valueType, String url) throws IllegalAccessException
            , InstantiationException {
        T t = valueType.newInstance();
        BufferedReader reader = null;
        BufferedOutputStream buffOutStr = null;
        InputStreamReader inputStreamReader = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            //加入数据
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            buffOutStr = new BufferedOutputStream(conn.getOutputStream());
            buffOutStr.write(orderInfo.getBytes(StandardCharsets.UTF_8));
            buffOutStr.flush();
            buffOutStr.close();
            //获取输入流
            inputStreamReader = new InputStreamReader(conn.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String resultXml = sb.toString();
            resultXml = resultXml.replaceAll("<coupon_refund_fee_[0-9]{0,11}[^>]*>(.*?)</coupon_refund_fee_[0-9]{0," +
                    "11}>", "");
            resultXml = resultXml.replaceAll("<coupon_refund_count_[0-9]{0,11}[^>]*>(.*?)" +
                    "</coupon_refund_count_[0-9]{0,11}>", "");
            resultXml = resultXml.replaceAll("<coupon_refund_id_[0-9]{0,11}_[0-9]{0,11}[^>]*>(.*?)" +
                    "</coupon_refund_id_[0-9]{0,11}_[0-9]{0,11}>", "");
            resultXml = resultXml.replaceAll("<coupon_type_[0-9]{0,11}_[0-9]{0,11}[^>]*>(.*?)</coupon_type_[0-9]{0," +
                    "11}_[0-9]{0,11}>", "");
            resultXml = resultXml.replaceAll("<coupon_refund_fee_[0-9]{0,11}_[0-9]{0,11}[^>]*>(.*?)" +
                    "</coupon_refund_fee_[0-9]{0,11}_[0-9]{0,11}>", "");
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
            //将请求返回的内容通过xStream转换为UnifiedOrderResponse对象
            xStream.alias("xml", valueType);
            Class<?>[] classes = new Class[]{valueType};
            xStream.allowTypes(classes);
            t = (T) xStream.fromXML(resultXml);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != buffOutStr) {
                    buffOutStr.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    @Override
    public BaseResponse getPayOrderDetail(PayOrderDetailRequest request) {
        PayOrderDetailResponse payOrderDetailResponse = new PayOrderDetailResponse();
        try {
            PayGatewayConfig payGatewayConfig =
                    gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT, request.getStoreId());

            WechatConfig wechatConfig = payDataService.findBySceneTypeAndStoreId(NumberUtils.INTEGER_ZERO,
                    Constants.BOSS_DEFAULT_STORE_ID);
            if (Objects.isNull(wechatConfig) || Objects.isNull(payGatewayConfig)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
            String appId = wechatConfig.getAppId();
            String apiKey = payGatewayConfig.getApiKey();
            String account = payGatewayConfig.getAccount();
            PayTradeRecordResponse payTradeRecord = payTradeRecordQueryProvider.getTradeRecordByOrderCode(
                    TradeRecordByOrderCodeRequest.builder().orderId(request.getBusinessId()).build()).getContext();
            if (Objects.nonNull(payTradeRecord) && Objects.nonNull(payTradeRecord.getChannelItemId())) {
                PayChannelItem channelItem = payDataService.queryItemById(payTradeRecord.getChannelItemId());
                if ("wx_app".equals(channelItem.getCode())) {
                    wechatConfig = payDataService.findBySceneTypeAndStoreId(2, Constants.BOSS_DEFAULT_STORE_ID);
                    if (Objects.isNull(wechatConfig)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                    }
                    appId = wechatConfig.getAppId();
                } else if ("js_api".equals(channelItem.getCode())) { //如果是jsapi支付
                    wechatConfig = payDataService.findBySceneTypeAndStoreId(NumberUtils.INTEGER_ONE,
                            Constants.BOSS_DEFAULT_STORE_ID);
                    if (Objects.isNull(wechatConfig)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                    }
                    appId = wechatConfig.getAppId();
                }
            }
            WxPayOrderDetailBaseRequest wxPayOrderDetailBaseRequest = new WxPayOrderDetailBaseRequest();
            wxPayOrderDetailBaseRequest.setAppid(appId);
            wxPayOrderDetailBaseRequest.setMch_id(account);
            wxPayOrderDetailBaseRequest.setNonce_str(WXPayUtil.generateNonceStr());
            wxPayOrderDetailBaseRequest.setOut_trade_no(request.getBusinessId());
            Map<String, String> wxPayOrderDetailMap = null;

            wxPayOrderDetailMap = WXPayUtil.objectToMap(wxPayOrderDetailBaseRequest);

            //获取签名
            String sign = WXPayUtil.generateSignature(wxPayOrderDetailMap, apiKey);
            wxPayOrderDetailBaseRequest.setSign(sign);
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayForNativeRequest.class);//根元素名需要是xml
            Class<?>[] classes = new Class[]{WxPayForNativeRequest.class};
            xStream.allowTypes(classes);
            String refundXmlStr = xStream.toXML(wxPayOrderDetailBaseRequest);
            payOrderDetailResponse = wxPayOrderDetail(refundXmlStr, PayOrderDetailResponse.class, WXPAYORDERQUERY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.success(payOrderDetailResponse);
    }

    @Override
    public BaseResponse payCloseOrder(PayCloseOrderRequest request) {
        PayCloseOrderResponse payCloseOrderResponse = new PayCloseOrderResponse();
        try {
            PayGatewayConfig payGatewayConfig =
                    gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT, request.getStoreId());

            WechatConfig wechatConfig = payDataService.findBySceneTypeAndStoreId(NumberUtils.INTEGER_ZERO,
                    Constants.BOSS_DEFAULT_STORE_ID);
            if (Objects.isNull(wechatConfig) || Objects.isNull(payGatewayConfig)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
            String appId = wechatConfig.getAppId();
            String apiKey = payGatewayConfig.getApiKey();
            String account = payGatewayConfig.getAccount();
            PayTradeRecordResponse payTradeRecord = payTradeRecordQueryProvider.getTradeRecordByOrderCode(
                    TradeRecordByOrderCodeRequest.builder().orderId(request.getBusinessId()).build()).getContext();
            if (Objects.nonNull(payTradeRecord) && Objects.nonNull(payTradeRecord.getChannelItemId())) {
                PayChannelItem channelItem = payDataService.queryItemById(payTradeRecord.getChannelItemId());
                if ("wx_app".equals(channelItem.getCode())) {
                    wechatConfig = payDataService.findBySceneTypeAndStoreId(2, Constants.BOSS_DEFAULT_STORE_ID);
                    if (Objects.isNull(wechatConfig)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                    }
                    appId = wechatConfig.getAppId();
                } else if ("js_api".equals(channelItem.getCode())) { //如果是jsapi支付
                    wechatConfig = payDataService.findBySceneTypeAndStoreId(NumberUtils.INTEGER_ONE,
                            Constants.BOSS_DEFAULT_STORE_ID);
                    if (Objects.isNull(wechatConfig)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                    }
                    appId = wechatConfig.getAppId();
                }
            }
            WxPayCloseOrderBaseRequest wxPayCloseOrderBaseRequest = new WxPayCloseOrderBaseRequest();
            wxPayCloseOrderBaseRequest.setAppid(appId);
            wxPayCloseOrderBaseRequest.setMch_id(account);
            wxPayCloseOrderBaseRequest.setOut_trade_no(request.getBusinessId());
            wxPayCloseOrderBaseRequest.setNonce_str(WXPayUtil.generateNonceStr());
            Map<String, String> wxPayCloseOrderMap = null;
            wxPayCloseOrderMap = WXPayUtil.objectToMap(wxPayCloseOrderBaseRequest);
            //获取签名
            String sign = WXPayUtil.generateSignature(wxPayCloseOrderMap, apiKey);
            wxPayCloseOrderBaseRequest.setSign(sign);
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayForNativeRequest.class);//根元素名需要是xml
            String refundXmlStr = xStream.toXML(wxPayCloseOrderBaseRequest);
            payCloseOrderResponse = wxPayOrderDetail(refundXmlStr, PayCloseOrderResponse.class, WXPAYCLOSEORDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.success(payCloseOrderResponse);
    }

    @Override
    public BaseResponse payRefund(PayRefundBaseRequest payBaseRequest) {
        WxPayRefundRequest refundRequest = getWxPayRefundRequest(payBaseRequest.getWxPayRefundRequest());
        //WxPayRefundRequest request = (WxPayRefundRequest) refundRequest;
        log.info("wxPayRefund=======>>refundRequest:{},type:{},店铺ID:{}", refundRequest, refundRequest.getPayType(),
                refundRequest.getStoreId());
        PayRefundResponse payRefundResponse = new PayRefundResponse();
        try {
            //带证书的post
            // 加载证书
            log.info("wxPayRefund=======>>加载证书开始");
            initCert(refundRequest.getMch_id(), refundRequest.getPayType(), refundRequest.getStoreId());
            log.info("wxPayRefund=======>>加载证书结束");
            //将订单对象转为xml格式
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayForNativeRequest.class);//根元素名需要是xml
            // 去除微信退款不需要的参数
            refundRequest.setStoreId(null);
            refundRequest.setPayType(null);
            refundRequest.setPay_type(null);
            refundRequest.setRefund_type(null);
            String refundXmlStr = xStream.toXML(refundRequest);
            log.info("wxPayRefund=======>>返回结果-refundXmlStr：{}", refundXmlStr);
            String resXml = postData(WXPAYREFUNDURL, refundXmlStr);
            log.info("wxPayRefund=======>>返回结果-resXml：{}", resXml);
            //解析xml为集合,请打断点查看resXml详细信息
            Map<String, String> refundResultMap = WXPayUtil.xmlToMap(resXml);
            payRefundResponse = (PayRefundResponse) WXPayUtil.mapToObject(refundResultMap, PayRefundResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.success(payRefundResponse);
    }

    private WxPayRefundRequest getWxPayRefundRequest(WxPayRefundRequest refundInfoRequest) {
        PayGatewayConfig payGatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT,
                refundInfoRequest.getStoreId());
        //默认是   0：扫码 ，H5，微信网页-JSAPI
        WechatConfig wechatConfig = payDataService.findBySceneTypeAndStoreId(NumberUtils.INTEGER_ZERO,
                refundInfoRequest.getStoreId());
        if (Objects.isNull(wechatConfig) || Objects.isNull(payGatewayConfig)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        String appId = wechatConfig.getAppId();
        String apiKey = payGatewayConfig.getApiKey();
        String account = payGatewayConfig.getAccount();
        if (refundInfoRequest.getPay_type().equals("JSAPI")) {  //小程序
            wechatConfig = payDataService.findBySceneTypeAndStoreId(NumberUtils.INTEGER_ONE,
                    refundInfoRequest.getStoreId());
            if (Objects.isNull(wechatConfig) || Objects.isNull(payGatewayConfig)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
            appId = wechatConfig.getAppId();
        } else if (refundInfoRequest.getPay_type().equals("APP")) {  // APP
            wechatConfig = payDataService.findBySceneTypeAndStoreId(2, refundInfoRequest.getStoreId());
            if (Objects.isNull(wechatConfig) || Objects.isNull(payGatewayConfig)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
            appId = wechatConfig.getAppId();
        }
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        refundRequest.setAppid(appId);
        refundRequest.setMch_id(account);
        refundRequest.setNonce_str(WXPayUtil.generateNonceStr());
        refundRequest.setOut_refund_no(refundInfoRequest.getOut_refund_no());
        refundRequest.setOut_trade_no(refundInfoRequest.getOut_trade_no());
        refundRequest.setTotal_fee(refundInfoRequest.getTotal_fee());
        refundRequest.setRefund_fee(refundInfoRequest.getRefund_fee());
        //重复支付退款不需要异步回调地址
        if (StringUtils.isNotBlank(refundInfoRequest.getRefund_type()) && !refundInfoRequest.getRefund_type().equals(
                "REPEATPAY")) {
            refundRequest.setNotify_url(payGatewayConfig.getBossBackUrl() + WXREFUNDSUCCCALLBACK);
        }
        try {
            Map<String, String> refundMap = WXPayUtil.objectToMap(refundRequest);
            //获取签名
            String sign = WXPayUtil.generateSignature(refundMap, apiKey);
            refundRequest.setSign(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        refundRequest.setPayType(refundInfoRequest.getPay_type());
        refundRequest.setStoreId(refundInfoRequest.getStoreId());
        return refundRequest;
    }

    @Override
    public BaseResponse pay(BasePayRequest basePayRequest) {
        PayGatewayConfig payGatewayConfig;
        Integer wxPayType = basePayRequest.getWxPayType();
        WechatConfig wechatConfig = payDataService.findByWxPayTypeAndStoreId(wxPayType, null);
        if (Objects.isNull(wechatConfig)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        switch (wxPayType) {
            case Constants.ZERO:
                WxPayForNativeRequest request = basePayRequest.getWxPayForNativeRequest();
                payGatewayConfig = payDataService.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT,
                        request.getStoreId());
                //由于各个环境
//                request.setAppid(wechatLoginSet.getPcAppId());
                request.setAppid(wechatConfig.getAppId());
                request.setMch_id(payGatewayConfig.getAccount());
                request.setNonce_str(WXPayUtil.generateNonceStr());
                request.setNotify_url(getNotifyUrl(payGatewayConfig));
                try {
                    Map<String, String> nativeMap = WXPayUtil.objectToMap(request);
                    nativeMap.remove("storeId");
                    //获取签名
                    String sign = WXPayUtil.generateSignature(nativeMap, payGatewayConfig.getApiKey());
                    request.setSign(sign);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 调用统一下单接口
                WxPayForNativeResponse wxPayForNativeResponse =
                        wxPayForNative(request, basePayRequest.getTradeId());
                log.info("微信扫码支付请求结果:{}", wxPayForNativeResponse);
                if (Constants.SUCCESS.equals(wxPayForNativeResponse.getReturn_code()) && Constants.SUCCESS.equals(wxPayForNativeResponse.getResult_code())) {
                    return BaseResponse.success(wxPayForNativeResponse);
                }
                this.throwErrMsg(wxPayForNativeResponse.getErr_code(), wxPayForNativeResponse.getErr_code_des());
                return BaseResponse.error(wxPayForNativeResponse.getErr_code_des());
            case Constants.ONE:
                WxPayForMWebRequest mWebRequest = basePayRequest.getWxPayForMWebRequest();
                payGatewayConfig = payDataService.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT,
                        mWebRequest.getStoreId());
                mWebRequest.setAppid(wechatConfig.getAppId());
                mWebRequest.setMch_id(payGatewayConfig.getAccount());
                mWebRequest.setNonce_str(WXPayUtil.generateNonceStr());
                mWebRequest.setNotify_url(getNotifyUrl(payGatewayConfig));
                try {
                    Map<String, String> mwebMap = WXPayUtil.objectToMap(mWebRequest);
                    mwebMap.remove("storeId");
                    //获取签名
                    String sign = WXPayUtil.generateSignature(mwebMap, payGatewayConfig.getApiKey());
                    mWebRequest.setSign(sign);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 调用统一下单接口
                WxPayForMWebResponse wxPayForMWebResponse =
                        wxPayForMWeb(mWebRequest, basePayRequest.getTradeId());
                if (Constants.SUCCESS.equals(wxPayForMWebResponse.getReturn_code()) && Constants.SUCCESS.equals(wxPayForMWebResponse.getResult_code())) {
                    return BaseResponse.success(wxPayForMWebResponse);
                }
                this.throwErrMsg(wxPayForMWebResponse.getErr_code(), wxPayForMWebResponse.getErr_code_des());
                return BaseResponse.error(wxPayForMWebResponse.getErr_code_des());
            case Constants.TWO:
                WxPayForJSApiRequest wxPayForJSApiRequest = basePayRequest.getWxPayForJSApiRequest();
                payGatewayConfig = payDataService.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT,
                        wxPayForJSApiRequest.getStoreId());
                wxPayForJSApiRequest.setAppid(wechatConfig.getAppId());
                wxSignCommon(wxPayForJSApiRequest, payGatewayConfig);
                log.info("微信支付[JSApi]统一下单接口入参:{}", wxPayForJSApiRequest);
                // 调用统一下单接口
                WxPayForJSApiResponse wxPayForJSApiResponse =
                        wxPayForJSApi(wxPayForJSApiRequest, basePayRequest.getTradeId());
                if (Constants.SUCCESS.equals(wxPayForJSApiResponse.getReturn_code()) && Constants.SUCCESS.equals(wxPayForJSApiResponse.getResult_code())) {
                    return getSignResultCommon(wxPayForJSApiRequest.getAppid(), payGatewayConfig.getApiKey(),
                            wxPayForJSApiResponse.getPrepay_id());
                }
                log.error("微信支付[JSApi]统一下单接口调用失败,入参:{},返回结果为:{}", wxPayForJSApiRequest, wxPayForJSApiResponse);
                this.throwErrMsg(wxPayForJSApiResponse.getErr_code(), wxPayForJSApiResponse.getErr_code_des());
                return BaseResponse.error(wxPayForJSApiResponse.getErr_code_des());
            case Constants.THREE:
                WxPayForJSApiRequest jsApiRequest = basePayRequest.getWxPayForJSApiRequest();
                payGatewayConfig = payDataService.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT,
                        jsApiRequest.getStoreId());
                jsApiRequest.setAppid(wechatConfig.getAppId());
//                payGatewayConfig.setApiKey(miniProgramSet.getAppSecret()) ;
                wxSignCommon(jsApiRequest, payGatewayConfig);
                log.info("小程序支付[JSApi]统一下单接口入参:{}", jsApiRequest);
                // 调用统一下单接口
                WxPayForJSApiResponse jsApiResponse =
                        wxPayForJSApi(jsApiRequest, basePayRequest.getTradeId());
                if (Constants.SUCCESS.equals(jsApiResponse.getReturn_code()) && Constants.SUCCESS.equals(jsApiResponse.getResult_code())) {
                    return getSignResultCommon(jsApiRequest.getAppid(), payGatewayConfig.getApiKey(),
                            jsApiResponse.getPrepay_id());
                }
                log.error("小程序支付[小程序]统一下单接口调用失败,入参:{},返回结果为:{}", jsApiRequest, jsApiResponse);
                this.throwErrMsg(jsApiResponse.getErr_code(), jsApiResponse.getErr_code_des());
                return BaseResponse.error(jsApiResponse.getErr_code_des());
            case Constants.FOUR:
                WxPayForAppRequest appRequest = basePayRequest.getWxPayForAppRequest();
                payGatewayConfig = payDataService.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT,
                        appRequest.getStoreId());
                appRequest.setAppid(wechatConfig.getAppId());
                appRequest.setMch_id(payGatewayConfig.getOpenPlatformAccount());
                appRequest.setNonce_str(WXPayUtil.generateNonceStr());
                appRequest.setNotify_url(getNotifyUrl(payGatewayConfig));
                try {
                    Map<String, String> appMap = WXPayUtil.objectToMap(appRequest);
                    appMap.remove("storeId");
                    //获取签名
                    String sign = WXPayUtil.generateSignature(appMap, payGatewayConfig.getOpenPlatformApiKey());
                    appRequest.setSign(sign);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                }
                log.info("微信支付[App]统一下单接口入参:{}", appRequest);
                // 调用统一下单接口
                WxPayForAppResponse appResponse =
                        wxPayForApp(appRequest, basePayRequest.getTradeId());
                Map<String, String> resultMap = new HashMap<>();
                if (Constants.SUCCESS.equals(appResponse.getReturn_code()) && Constants.SUCCESS.equals(appResponse.getResult_code())) {
                    resultMap.put("appId", wechatConfig.getAppId());
                    resultMap.put("partnerId", payGatewayConfig.getOpenPlatformAccount());
                    resultMap.put("prepayId", appResponse.getPrepay_id());
                    resultMap.put("packageValue", "Sign=WXPay");
                    resultMap.put("nonceStr", appRequest.getNonce_str());
                    resultMap.put("timestamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
                    try {
                        resultMap.put("sign", WXPayUtil.generateSignature(resultMap,
                                payGatewayConfig.getOpenPlatformApiKey()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                    }
                    log.info("微信支付[App]统一下单接口入参:{}", JSON.toJSONString(resultMap));
                    return BaseResponse.success(resultMap);
                }
                log.error("微信支付[app]统一下单接口调用失败,入参:{},返回结果为:{}", appRequest, appResponse);
                this.throwErrMsg(appResponse.getErr_code(), appResponse.getErr_code_des());
                return BaseResponse.success(resultMap);
            case Constants.FIVE:
                //视频号获取支付参数处理
                WxChannelsPayRequest wxChannelsPayRequest = basePayRequest.getWxChannelsPayRequest();
                PlatformOrderRequest channelsOrderRequest = PlatformOrderRequest.builder()
                        .order_id(wxChannelsPayRequest.getThirdOrderId())
                        .out_order_id(wxChannelsPayRequest.getOrderId())
                        .openid(wxChannelsPayRequest.getOpenid())
                        .build();
                //添加交易记录
                PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
                payTradeRecordRequest.setBusinessId(basePayRequest.getTradeId());
                payTradeRecordRequest.setPayNo(wxChannelsPayRequest.getOrderId());
                payTradeRecordRequest.setApplyPrice(new BigDecimal(wxChannelsPayRequest.getTotalPrice()).divide(new BigDecimal(100)).
                        setScale(2, BigDecimal.ROUND_DOWN));
                payTradeRecordRequest.setClientIp(wxChannelsPayRequest.getClientIp());
                payTradeRecordRequest.setChannelItemId(30L);
                addTradeRecord(payTradeRecordRequest);

                PlatformOrderService orderService =
                        thirdPlatformContext.getPlatformService(SellPlatformType.WECHAT_VIDEO,
                                PlatformServiceType.ORDER_SERVICE);

                BaseResponse<PlatformOrderPayParamResponse> channelsBaseResponse =
                        orderService.getOrderPayParam(channelsOrderRequest);
                if (!CommonErrorCodeEnum.K000000.getCode().equals(channelsBaseResponse.getCode())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                }
                PlatformOrderPayParamResponse payParamResponse = channelsBaseResponse.getContext();
                return BaseResponse.success(WxChannelsPayParamResponse.builder()
                        .payPackage(payParamResponse.getPay_package())
                        .paySign(payParamResponse.getPaySign())
                        .signType(payParamResponse.getSignType())
                        .nonceStr(payParamResponse.getNonceStr())
                        .timeStamp(payParamResponse.getTimeStamp())
                        .build());
            default:
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 上传微信支付证书
     * @Date 10:21 2019/5/7
     * @Param [request]
     **/
    public void uploadPayCertificate(PayGatewayUploadPayCertificateRequest request) {
        PayGatewayConfig payGatewayConfig = gatewayConfigRepository.getOne(request.getId());
        if (Objects.isNull(payGatewayConfig)) {
            payGatewayConfig = new PayGatewayConfig();
        }
        if (PayGatewayEnum.LAKALA.equals(payGatewayConfig.getPayGateway().getName())) {
            if (Constants.THREE == request.getPayCertificateType()) {
                payGatewayConfig.setWxPayCertificate(request.getPayCertificate());
            }
            if (Constants.FOUR == request.getPayCertificateType()) {
                payGatewayConfig.setWxOpenPayCertificate(request.getPayCertificate());
            }
            payDataService.updateLakalaPayCache(request.getPayCertificateType(), request.getPayCertificate());
        } else if (PayGatewayEnum.LAKALACASHIER.equals(payGatewayConfig.getPayGateway().getName())) {
            if (Constants.THREE == request.getPayCertificateType()) {
                payGatewayConfig.setWxPayCertificate(request.getPayCertificate());
            }
            if (Constants.FOUR == request.getPayCertificateType()) {
                payGatewayConfig.setWxOpenPayCertificate(request.getPayCertificate());
            }
            payDataService.updateLakalaCasherPayCache(request.getPayCertificateType(), request.getPayCertificate());
        } else {
            payGatewayConfig.setWxPayCertificate(request.getPayCertificate());
            payGatewayConfig.setWxOpenPayCertificate(request.getPayCertificate());//只有一个商户号，二个证书同一样数据
        }
        gatewayConfigRepository.save(payGatewayConfig);
    }

    private String getNotifyUrl(PayGatewayConfig payGatewayConfig) {
        StringBuilder notify_url = new StringBuilder();
        notify_url.append(payGatewayConfig.getBossBackUrl());
        notify_url.append(WXPAYSUCCCALLBACK);
        return notify_url.toString();
    }


    /**
     * 微信支付公共异常方法
     *
     * @param code
     * @param msg
     */
    private void throwErrMsg(String code, String msg) throws SbcRuntimeException {
        if ("ORDERPAID".equals(code)) {
            //订单已支付
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060010);
        } else if ("NOTENOUGH".equalsIgnoreCase(code)
                || "ORDERCLOSED".equalsIgnoreCase(code)
                || "OUT_TRADE_NO_USED".equalsIgnoreCase(code)) {
            //订单超时、关闭、单号重复
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{msg});
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
    }


    /**
     * 微信浏览器内,小程序内签名参数公共方法
     *
     * @param jsApiRequest
     * @param payGatewayConfig
     */
    private void wxSignCommon(@RequestBody WxPayForJSApiRequest jsApiRequest, PayGatewayConfig payGatewayConfig) {
        jsApiRequest.setMch_id(payGatewayConfig.getAccount());
        jsApiRequest.setNonce_str(WXPayUtil.generateNonceStr());
        jsApiRequest.setNotify_url(getNotifyUrl(payGatewayConfig));
        try {
            Map<String, String> mwebMap = WXPayUtil.objectToMap(jsApiRequest);
            mwebMap.remove("storeId");
            //获取签名
            String sign = WXPayUtil.generateSignature(mwebMap, payGatewayConfig.getApiKey());
            jsApiRequest.setSign(sign);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }


    /**
     * 微信浏览器内,小程序内支付,获取返回对象公共方法
     *
     * @param appId
     * @param apiKey
     * @param prepayId
     * @return
     */
    private BaseResponse<Map<String, String>> getSignResultCommon(String appId, String apiKey, String prepayId) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("appId", appId);
        resultMap.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
        resultMap.put("nonceStr", WXPayUtil.generateNonceStr());
        resultMap.put("package", "prepay_id=" + prepayId);
        resultMap.put("signType", "MD5");
        try {
            resultMap.put("paySign", WXPayUtil.generateSignature(resultMap, apiKey));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return BaseResponse.success(resultMap);
    }

    /**
     * @description 微信支付--发货信息录入接口
     * @author
     * @date
     * @return
     **/
    public String wxPayUploadShippingInfo(WxPayUploadShippingInfoRequest request) {
        String resultStr = "{\"errcode\":\"-99\",\"errmsg\":\"网络请求失败\"}";
        try {
            // 获取access_token
            String accessToken = wechatApiUtil.getAccessTokenPlus("PUBLIC");
            // 发送订阅消息url
            String wxSubscribeSendUrl = String.format(WechatApiConstant.UPLOAD_SHIPPING_INFO, accessToken);
            log.info("wxSubscribeSendUrl请求参数：{}", wxSubscribeSendUrl);
            log.info("wxPayUploadShippingInfo请求参数：{}", request);
            JSONObject paramObjects = (JSONObject) JSON.toJSON(request);
            if (paramObjects.containsKey("all_delivered")) {
                paramObjects.remove("all_delivered");
            }
            if (paramObjects.containsKey("_all_delivered")) {
                paramObjects.remove("_all_delivered");
            }
//            paramObjects.put("is_all_delivered", request.is_all_delivered());
            log.info("paramObjects请求参数：{}", paramObjects);
            log.info("wxPayUploadShippingInfo请求参数JSONStr：{}", JSONObject.toJSONString(paramObjects));
            JSONObject jsonObject = wechatApiUtil.doPost(wxSubscribeSendUrl, JSONObject.toJSONString(paramObjects));
            resultStr = JSONObject.toJSONString(jsonObject);
            log.info("wxPayUploadShippingInfo返回结果resultStr：{}", JSONObject.toJSONString(resultStr));
        } catch (Exception e) {
            log.error("微信支付发货信息录入接口：", e);
        }
        return resultStr;
    }

    /**
     * @description 微信支付--发货信息录入查询接口
     * @author
     * @date
     * @return
     **/
    public Integer wxPayUploadShippingGetOrder(WxPayUploadShippingInfoGetOrderRequest request) {
        // 获取access_token
        String accessToken = wechatApiUtil.getAccessTokenPlus("PUBLIC");
        // 发送订阅消息url
        String wxSubscribeSendUrl = String.format(WechatApiConstant.GET_ORDER, accessToken);
        JSONObject jsonObject = wechatApiUtil.doPost(wxSubscribeSendUrl, JSONObject.toJSONString(request), "UTF-8");
        Integer errCode = jsonObject.getInteger("errcode");
        if (Objects.equals(errCode, Constants.ZERO)) {
            //订单状态枚举：(1) 待发货；(2) 已发货；(3) 确认收货；(4) 交易完成；(5) 已退款；(6) 资金待结算。
            return jsonObject.getJSONObject("order").getInteger("order_state");
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{jsonObject.getString("errmsg")});
        }
    }

    /**
     * 获取运力列表
     * @author dyt
     */
    public PlatformCompanyResponse wxPayGetDeliveryList() {
        String type = "PUBLIC";
        //获取安稳版token
        String stableToken = wechatApiUtil.getStableTokenPlus(type);
        String url = String.format(WechatApiConstant.GET_DELIVERY_LIST, stableToken);
        JSONObject jsonObject = this.wechatApiUtil.doPost(url, "{}", "UTF-8");
        log.info("微信物流服务调用-获取运力id列表get_delivery_list接口url:{}，返回结果：{}", url, jsonObject);
        if (null != jsonObject) {
            log.info("微信物流服务调用-获取运力id列表get_delivery_list返回信息：{}", jsonObject.toJSONString());
            String errCode = jsonObject.getString("errcode");
            if("0".equals(errCode) && jsonObject.containsKey("delivery_list")) {
                PlatformCompanyResponse response = new PlatformCompanyResponse();
                response.setCompany_list(jsonObject.getList("delivery_list", PlatformCompanyVO.class));
                return response;
            }
            if(Arrays.asList(Constants.STR_42001, Constants.STR_40001).contains(errCode)) {
                //token失效
                wechatApiUtil.getStableTokenCleanRedis(type);
                return wxPayGetDeliveryList();
            }
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
    }

    /**
     * @description 微信支付--发货信息录入查询接口
     * @author
     * @date
     * @return
     **/
    public void setMsgJumpPath(WxPayMsgJumpPathSetRequest request) {
        // 获取access_token
        String accessToken = wechatApiUtil.getAccessTokenPlus("PUBLIC");
        String url = String.format(WechatApiConstant.SET_MSG_JUMP_PATH, accessToken);
        JSONObject jsonObject = wechatApiUtil.doPost(url, JSONObject.toJSONString(request), "UTF-8");
        log.info("微信物流服务调用-消息跳转路径设置接口url:{}，返回结果：{}", url, jsonObject);
        Integer errCode = jsonObject.getInteger("errcode");
        if (!Objects.equals(errCode, Constants.ZERO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{jsonObject.getString("errmsg")});
        }
    }
}
