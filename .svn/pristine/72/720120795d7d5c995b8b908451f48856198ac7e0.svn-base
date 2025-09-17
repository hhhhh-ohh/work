package com.wanmi.sbc.empower.pay.service.unioncloud;

import static com.wanmi.sbc.empower.pay.service.PayService.getCurrentTime;
import static com.wanmi.sbc.empower.pay.service.PayService.getOrderId;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayCloseOrderRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionCloudPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.bean.constant.PayServiceConstants;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.bean.enums.TradeType;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.repository.ChannelItemRepository;
import com.wanmi.sbc.empower.pay.repository.GatewayConfigRepository;
import com.wanmi.sbc.empower.pay.repository.GatewayRepository;
import com.wanmi.sbc.empower.pay.sdk.AcpService;
import com.wanmi.sbc.empower.pay.sdk.LogUtil;
import com.wanmi.sbc.empower.pay.sdk.SDKConfig;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.empower.pay.utils.PayValidates;
import com.wanmi.sbc.order.api.provider.payingmemberpayrecord.PayingMemberPayRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.bean.vo.PayTradeRecordVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberPayRecordVO;

import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: shiyuan-service
 * @description: 银联云闪付service
 * @author: zhangyong
 * @create: 2021-03-09 11:11
 **/
@Validated
@Slf4j
@Service(PayServiceConstants.UNION_CLOUD_SERVICE)
public class UnionCloudPayServiceImpl implements PayBaseService {

    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;
    @Resource
    private ChannelItemRepository channelItemRepository;

    @Resource
    private GatewayRepository gatewayRepository;
    @Resource
    private GatewayConfigRepository gatewayConfigRepository;

    @Autowired
    private PayingMemberPayRecordQueryProvider payingMemberPayRecordQueryProvider;
    /**
     *银联在线网关支付
     *
     * @date 18:00 2021/3/11
     * @author zhangyong
      * @param basePayRequest
     * @return   {@link String}
     */
    @Override
    @Transactional(noRollbackFor = SbcRuntimeException.class)
    public BaseResponse pay(BasePayRequest basePayRequest) {
        UnionPayRequest unionPay = basePayRequest.getUnionPayRequest();
        PayChannelItem item = getPayChannelItem(unionPay.getChannelItemId(), Constants.BOSS_DEFAULT_STORE_ID);
        //该付款方式是否支持该渠道
        if (item.getTerminal() != unionPay.getTerminal()) {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060002);
        }
        PayTradeRecordResponse response = payTradeRecordQueryProvider.getTradeRecordByOrderCode(TradeRecordByOrderCodeRequest.builder()
                .orderId(basePayRequest.getTradeId())
                .build()).getContext();
        String html = "";
        if (!Objects.isNull(response) && response.getStatus() == TradeStatus.SUCCEED) {
            //如果重复支付，判断状态，已成功状态则做异常提示
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
        } else {
            PayTradeRecordRequest request = new PayTradeRecordRequest();
            request.setApplyPrice(unionPay.getAmount());
            request.setBusinessId(basePayRequest.getTradeId());
            request.setClientIp(unionPay.getClientIp());
            request.setChannelItemId(unionPay.getChannelItemId());
            request.setCreateTime(LocalDateTime.now());
            request.setTradeType(TradeType.PAY);
            request.setStatus(TradeStatus.PROCESSING);
            request.setPayNo(unionPay.getOutTradeNo());
            payTradeRecordProvider.queryAndSave(request);
            //创建银联页面html
            html = createUnionCloudHtml(unionPay);
        }
        return BaseResponse.success(html);
    }

    private PayChannelItem getPayChannelItem(Long channelItemId, Long storeId) {
        PayChannelItem item = channelItemRepository.findById(channelItemId).get();
        PayValidates.verfiyPayChannelItem(item);
        // 获取网关
        PayGateway gateway = gatewayRepository.queryByNameAndStoreId(item.getGatewayName(), storeId);
        item.setGateway(gateway);
        return item;
    }

    /**
     * 银联支付退款接口
     *
     * @param payBaseRequest
     * @return
     */
    @Override
    public BaseResponse payRefund(PayRefundBaseRequest payBaseRequest) {
        UnionCloudPayRefundRequest unionPay = payBaseRequest.getUnionCloudPayRefundRequest();
        boolean needCallback = unionPay.isNeedCallback();
        String businessId = unionPay.getBusinessId();
        PayTradeRecordResponse response;
        if (businessId.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
            PayingMemberPayRecordVO payingMemberPayRecordVO = payingMemberPayRecordQueryProvider.getTradeRecordByOrderCode(new TradeRecordByOrderCodeRequest
                    (businessId)).getContext().getPayingMemberPayRecordVO();
            String returnId = businessId.replace(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID,"");
            returnId = "PMR".concat(returnId);
            response = new PayTradeRecordResponse();
            response.setBusinessId(returnId);
            response.setApplyPrice(unionPay.getApplyPrice());
            response.setTradeNo(payingMemberPayRecordVO.getChargeId());
        } else {
            response = payTradeRecordQueryProvider.getTradeRecordByOrderCode(TradeRecordByOrderCodeRequest.builder()
                    .orderId(unionPay.getBusinessId())
                    .build()).getContext();
        }

//        PayTradeRecord record = recordRepository.findByBusinessId(unionPay.getBusinessId());
        PayGatewayConfig gatewayConfig =
                gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.UNIONPAY, Constants.BOSS_DEFAULT_STORE_ID);
        UnionCloudPayRefundRequest unionPayRefundRequest = buildUnionPayRefundRequest(response,
                gatewayConfig,needCallback);
        Map<String, String> resultMap = (Map<String, String>) dealPayRefund(unionPayRefundRequest).getContext();
        log.info(">>>>>>>>>>>>>>>>>>respCode:" + resultMap.get("respCode") + "respMsg:" + resultMap.get("respMsg"));
        if (Constants.STR_00.equals(resultMap.get("respCode"))) {
            response.setTradeNo(resultMap.get("orderId"));
        }else{
            //退款失败
            log.info(">>>>>>>>>>>>>>>>>>银联支付退款失败:respCode" + resultMap.get("respCode") + "respMsg:" + resultMap.get("respMsg"));
            String errMsg = "退款失败原因：";
            errMsg = errMsg +resultMap.get("respMsg") + ";";
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060014, new Object[]{errMsg});
        }
        unionPayRefundRequest = buildUnionPayRefundRequest(response, gatewayConfig,needCallback);
        return BaseResponse.success((Map<String, String>)dealPayRefund(unionPayRefundRequest).getContext());
    }

    /**
     * 银联支付退款接口
     *
     * @param refundRequest
     * @return
     */
    public BaseResponse dealPayRefund(UnionCloudPayRefundRequest refundRequest) {
        //初始化sdkconfig
        log.info("=================初始化SDKConfig开始==============");
//        SDKConfig.getConfig().loadPropertiesFromSrc();
        log.info("=================初始化SDKConfig成功==============");
        Map<String, String> requestData = getRefundRequestData(refundRequest);
        //这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        String url = SDKConfig.getConfig().getBackTransUrl();
        Map<String, String> rspData = AcpService.post(requestData, url, "UTF-8");
        log.info("银联云闪付退货接口返回数据rspData：{}", rspData);
        return BaseResponse.success(rspData);
    }

    public UnionCloudPayRefundRequest buildUnionPayRefundRequest(PayTradeRecordResponse record, PayGatewayConfig gatewayConfig,
                                                                 boolean needCallback){
        UnionCloudPayRefundRequest unionPayRefundRequest = new UnionCloudPayRefundRequest();
        unionPayRefundRequest.setApiKey(gatewayConfig.getApiKey());
        if (needCallback) {
            unionPayRefundRequest.setBossBackUrl(gatewayConfig.getBossBackUrl());
        }
        unionPayRefundRequest.setBusinessId(record.getBusinessId());
        unionPayRefundRequest.setApplyPrice(record.getApplyPrice());
        unionPayRefundRequest.setTradeNo(record.getTradeNo());
        return unionPayRefundRequest;
    }


    /**
     * 生成自动跳转的Html表单
     *
     * @param unionPay
     * @return
     */
    private String createUnionCloudHtml(UnionPayRequest unionPay) {
        //初始化sdkconfig
        log.info("=================初始化SDKConfig开始============== 传入参数unionPay{}", unionPay.toString());
//        SDKConfig.getConfig().loadPropertiesFromSrc();
        log.info("=================初始化SDKConfig成功==============");
        Map<String, String> submitFromData = getSubmitFromData(unionPay);
        //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String requestFrontUrl = SDKConfig.getConfig().getFrontTransUrl();
        //生成自动跳转的Html表单
        String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, "UTF-8");
        log.info("打印请求HTML，此为请求报文，为联调排查问题的依据：" + html);
        //将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
        return html;
    }

    /**
     * app对接获取tn
     * @param basePayRequest
     * @return
     */
    @Transactional(noRollbackFor = SbcRuntimeException.class)
    public String getTn(BasePayRequest basePayRequest) {
        UnionPayRequest unionPay = basePayRequest.getUnionPayRequest();
        PayChannelItem item = getPayChannelItem(unionPay.getChannelItemId(), Constants.BOSS_DEFAULT_STORE_ID);
        //该付款方式是否支持该渠道
        if (item.getTerminal() != unionPay.getTerminal()) {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060002);
        }
        //是否重复支付
        PayTradeRecordResponse response = payTradeRecordQueryProvider.getTradeRecordByOrderCode(TradeRecordByOrderCodeRequest.builder()
                .orderId(basePayRequest.getTradeId())
                .build()).getContext();
        if (!Objects.isNull(response) && response.getStatus() == TradeStatus.SUCCEED) {
            //如果重复支付，判断状态，已成功状态则做异常提示
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
        } else {
            PayTradeRecordRequest request = new PayTradeRecordRequest();
            request.setApplyPrice(unionPay.getAmount());
            request.setBusinessId(basePayRequest.getTradeId());
            request.setClientIp(unionPay.getClientIp());
            request.setChannelItemId(unionPay.getChannelItemId());
            request.setCreateTime(LocalDateTime.now());
            request.setTradeType(TradeType.PAY);
            request.setStatus(TradeStatus.PROCESSING);
            request.setPayNo(unionPay.getOutTradeNo());
            payTradeRecordProvider.queryAndSave(request);
        }
        //初始化sdkconfig
        log.info("=================初始化SDKConfig开始============== 传入参数unionPay{}", unionPay.toString());
//        SDKConfig.getConfig().loadPropertiesFromSrc();
        log.info("=================初始化SDKConfig成功==============");
        Map<String, String> submitFromData = getSubmitFromData(unionPay);
        Map<String, String> reqData = AcpService.sign(submitFromData,"UTF-8");
        String requestAppUrl = SDKConfig.getConfig().getAppTransUrl();
        Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,"UTF-8");
        if(!rspData.isEmpty()){
            if(AcpService.validate(rspData, "UTF-8")){
                LogUtil.writeLog("验证签名成功");
                String respCode = rspData.get("respCode") ;
                if((Constants.STR_00).equals(respCode)){
                    //成功,获取tn号
                    String tn = rspData.get("tn");
                    log.info("银联云闪付app对接获取tn----------->:{}",tn);
                    return tn;
                }else{
                    //其他应答码为失败请排查原因或做失败处理
                    LogUtil.writeErrorLog(JSON.toJSONString(respCode));
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                }
            }else{
                LogUtil.writeErrorLog("验证签名失败");
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }else{
            //未返回正确的http状态
            LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 组装消费接口参数
     *
     * @param unionPay
     * @return
     */
    private Map<String, String> getSubmitFromData(UnionPayRequest unionPay) {
        Map<String, String> requestData = new HashMap<String, String>();
        //版本号 全渠道默认值
        requestData.put("version", SDKConfig.getConfig().getVersion());
        //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("encoding", "utf-8");
        //签名方法
        requestData.put("signMethod", SDKConfig.getConfig().getSignMethod());
        //交易类型 ，01：消费
        requestData.put("txnType", "01");
        //交易子类型， 01：自助消费
        requestData.put("txnSubType", "01");
        //业务类型，B2C网关支付，手机wap支付
        requestData.put("bizType", "000201");
        //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板 08：手机
        String channelType = "";
        //因为需支持小程序 和PC 端
        switch (unionPay.getTerminal()) {
            case PC:
                requestData.put("channelType", "07");
                break;
            default:
                requestData.put("channelType", "08");
        }
        /***商户接入参数***/

        //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("merId", unionPay.getApiKey());
        //接入类型，0：直连商户
        requestData.put("accessType", "0");
        //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("orderId", unionPay.getOutTradeNo());
        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("txnTime", getCurrentTime());
        //交易币种（境内商户一般是156 人民币）
        requestData.put("currencyCode", "156");
        //交易金额，单位分，不要带小数点
        requestData.put("txnAmt", doAmount(unionPay.getAmount()));
        //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
        //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
        //异步通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 网关支付产品接口规范 消费交易 商户通知
        requestData.put("frontUrl", unionPay.getFrontUrl());
        //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
        //后台通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 网关支付产品接口规范 消费交易 商户通知
        //注意:1.需设置为外网能访问，否则收不到通知 2.http https均可 3.收单后台通知后需要10秒内返回http200或302状态码
        // 4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
        // 5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
        requestData.put("backUrl", unionPay.getNotifyUrl());
        // 订单超时时间。
        // 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
        // 此时间建议取支付时的北京时间加15分钟。
        // 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
        if(null != unionPay.getOrderTimeOut()){
            requestData.put("payTimeout",
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(unionPay.getOrderTimeOut()));
        }

        /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
        //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        log.info("=====调用银联支付消费接口传入参数加密前requestData：{}", requestData);
        Map<String, String> submitFromData = AcpService.sign(requestData, "UTF-8");
        log.info("=====调用银联支付消费接口传入参数加密后requestData：{}", requestData);
        return submitFromData;
    }

    /**
     * 组装退款接口数据
     *
     * @param payBaseRequest
     * @return
     */
    private Map<String, String> getRefundRequestData(UnionCloudPayRefundRequest payBaseRequest) {
        Map<String, String> data = new HashMap<String, String>();
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        //版本号
        data.put("version", SDKConfig.getConfig().getVersion());
        //字符集编码 可以使用UTF-8,GBK两种方式
        data.put("encoding", "UTF-8");
        //签名方法
        data.put("signMethod", SDKConfig.getConfig().getSignMethod());
        //交易类型 04-退货
        data.put("txnType", "04");
        //交易子类型; 默认00
        data.put("txnSubType", "00");
        //业务类型
        data.put("bizType", "000301");
        //渠道类型，07-PC，08-手机
        data.put("channelType", "07");
        /***商户接入参数***/
        //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("merId", payBaseRequest.getApiKey());
        //接入类型，商户接入固定填0，不需修改
        data.put("accessType", "0");
        //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
        data.put("orderId", payBaseRequest.getBusinessId());
        //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        data.put("txnTime", getCurrentTime());
        //交易币种（境内商户一般是156 人民币）
        data.put("currencyCode", "156");
        //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额
        data.put("txnAmt", doAmount(payBaseRequest.getApplyPrice()));
        //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载; 产品接口规范; 网关支付产品接口规范 退货交易 商户通知,其他说明同消费交易的后台通知
        data.put("backUrl", payBaseRequest.getBossBackUrl() + "/tradeCallback/unionPayRefundCallBack");
        /***要调通交易以下字段必须修改***/
        //****原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
        data.put("origQryId", payBaseRequest.getTradeNo())
        /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/;
        //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        log.info("=====调用银联支付退款接口传入参数加密前requestData：{}", data);
        Map<String, String> reqData = AcpService.sign(data, "UTF-8");
        log.info("=====调用银联支付退款接口传入参数加密后requestData：{}", reqData);
        return reqData;
    }

    /***
     * 银联加密公钥更新查询接口
     *
     * @date 14:49 2021/3/15
     * @author zhangyong
     * @param
     * @return   {@link Map< String, String>}
     */
    public Map<String, String> getUpdateEncryptCer(UnionPayRequest unionPay) {

        //加载classpath下的acp_sdk.properties文件内容
//        SDKConfig.getConfig().loadPropertiesFromSrc();

        Map<String, String> contentData = new HashMap<String, String>();
        contentData.put("version",  SDKConfig.getConfig().getVersion());                  		     //版本号
        contentData.put("encoding", "UTF-8");            		 //字符集编码 可以使用UTF-8,GBK两种方式
        contentData.put("signMethod", SDKConfig.getConfig().getSignMethod());    //签名方法  01:RSA证书方式  11：支持散列方式验证SHA-256 12：支持散列方式验证SM3
        contentData.put("txnType", "95");                              			 //交易类型 95-银联加密公钥更新查询
        contentData.put("txnSubType", "00");                           			 //交易子类型  默认00
        contentData.put("bizType", "000000");                          			 //业务类型  默认
        contentData.put("channelType", "07");                          			 //渠道类型

        contentData.put("certType", "01");							   			 //01：敏感信息加密公钥(只有01可用)
        contentData.put("merId", unionPay.getApiKey());                   			 //商户号码（商户号码777290058110097仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
        contentData.put("accessType", "0");                            			 //接入类型，商户接入固定填0，不需修改
        contentData.put("orderId", getOrderId());             			 //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        contentData.put("txnTime", getCurrentTime());         		     //订单发送时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效

        // 账号类型
        Map<String, String> reqData = AcpService.sign(contentData,"UTF-8");			   //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String requestBackUrl = SDKConfig.getConfig().getBackTransUrl();				 			   //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,"UTF-8");  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

        if (!rspData.isEmpty()) {
            if (AcpService.validate(rspData, "UTF-8")) {
                LogUtil.writeLog("验证签名成功");
                String respCode = rspData.get("respCode");
                if ((Constants.STR_00).equals(respCode)) {
                    int resultCode = AcpService.updateEncryptCert(rspData, "UTF-8");
                    if (resultCode == 1) {
                        LogUtil.writeLog("加密公钥更新成功");
                    } else if (resultCode == 0) {
                        LogUtil.writeLog("加密公钥无更新");
                    } else {
                        LogUtil.writeLog("加密公钥更新失败");
                    }

                } else {
                    //其他应答码为失败请排查原因
                    //TODO
                }
            } else {
                LogUtil.writeErrorLog("验证签名失败");
                //TODO 检查验证签名失败的原因
            }
        } else {
            //未返回正确的http状态
            LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
        }
        return rspData;
    }

    @Override
    public BaseResponse getPayOrderDetail(PayOrderDetailRequest request) {
        // 查询网关初始化配置
        PayGatewayConfig gatewayConfig = gatewayConfigRepository.
                queryConfigByNameAndStoreId(PayGatewayEnum.UNIONPAY, request.getStoreId());
        UnionPayRequest unionPay = new UnionPayRequest();
        unionPay.setOutTradeNo(request.getBusinessId());
        unionPay.setApiKey(gatewayConfig.getApiKey());
        unionPay.setTxnTime(request.getTxnTime());
        // 初始化sdkconfig
        log.info("==================初始化SDKConfig开始============== 传入参数unionPay{}", unionPay.toString());
//        SDKConfig.getConfig().loadPropertiesFromSrc();

        log.info("=================初始化SDKConfig成功==============");
        Map<String, String> resultRequestData = getResultRequestData(unionPay);
        /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
        // 报文中certId,signature的值是在signData
        Map<String, String> reqData  = AcpService.sign(resultRequestData,"UTF-8");
        // 方法中获取并自动赋值的，只要证书配置正确即可。
        // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        String url = SDKConfig.getConfig().getSingleQueryUrl();
        // 这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        Map<String, String> rspData = AcpService.post(reqData,url,"UTF-8");
        return BaseResponse.success(rspData);
    }

    @Override
    public BaseResponse payCloseOrder(PayCloseOrderRequest request) {
        // 查询网关初始化配置
        PayGatewayConfig gatewayConfig = gatewayConfigRepository.
                queryConfigByNameAndStoreId(PayGatewayEnum.UNIONPAY, request.getStoreId());
        // 构建请求参数
        PayTradeRecordVO record = new PayTradeRecordVO();
        record.setBusinessId(request.getBusinessId());
        record.setApplyPrice(request.getApplyPrice());
        record.setTradeNo(request.getOut_trade_no());
        //初始化sdkconfig
        log.info("=================初始化SDKConfig开始==============");
//        SDKConfig.getConfig().loadPropertiesFromSrc();
        log.info("=================初始化SDKConfig成功==============");
        Map<String, String> requestData = getCancelData(record, gatewayConfig);
        //这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        String url = SDKConfig.getConfig().getBackTransUrl();
        Map<String, String> rspData = AcpService.post(requestData, url, "UTF-8");
        log.info("银联云闪付消费撤销接口返回数据rspData：{}",rspData);
        return BaseResponse.success(rspData);
    }

//    @Override
//    public BaseResponse pay(BasePayRequest basePayRequest) {
//        return BaseResponse.success(payUnionCloud(basePayRequest.getUnionPayRequest()));
//    }

    /***
     * 封装请求数据
     * @param unionPay
     * @return
     */
    private Map<String, String> getResultRequestData(UnionPayRequest unionPay){
        Map<String, String> data = new HashMap<String, String>(16);
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        // 版本号
        data.put("version", SDKConfig.getConfig().getVersion());
        // 字符集编码 可以使用UTF-8,GBK两种方式
        data.put("encoding", "UTF-8");
        // 签名方法
        data.put("signMethod", SDKConfig.getConfig().getSignMethod());
        // 交易类型 00-默认
        data.put("txnType", "00");
        // 交易子类型  默认00
        data.put("txnSubType", "00");
        // 业务类型 B2C网关支付，手机wap支付
        data.put("bizType", "000201");

        /***商户接入参数***/
        // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("merId", unionPay.getApiKey());
        // 接入类型，商户接入固定填0，不需修改
        data.put("accessType", "0");

        // 商户订单号，每次发交易测试需修改为被查询的交易的订单号
        data.put("orderId", unionPay.getOutTradeNo());

        // 订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
        data.put("txnTime", unionPay.getTxnTime());
        return data;
    }

    /**
     * 交易金额转化为分
     *
     * @param txnAmt
     * @return
     */
    private String doAmount(BigDecimal txnAmt) {
        return txnAmt.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP)
                .toString();
    }

    /***
     * 消费撤销接口
     *
     * @date  2021/3/18
     * @author zhangyong
     * @param record
     * @param gatewayConfig
     * @return   {@link Map< String, String>}
     */
    private Map<String, String> getCancelData(PayTradeRecordVO record, PayGatewayConfig gatewayConfig) {
        Map<String, String> data = new HashMap<String, String>(16);
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        //版本号
        data.put("version", SDKConfig.getConfig().getVersion());
        //字符集编码 可以使用UTF-8,GBK两种方式
        data.put("encoding", "UTF-8");
        //签名方法
        data.put("signMethod", SDKConfig.getConfig().getSignMethod());
        //交易类型 31-消费撤销
        data.put("txnType", "31");
        //交易子类型  默认00
        data.put("txnSubType", "00");
        //业务类型
        data.put("bizType", "000201");
        //渠道类型，07-PC，08-手机
        data.put("channelType", "07");
        /***商户接入参数***/
        //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("merId",  gatewayConfig.getApiKey());
        //接入类型，商户接入固定填0，不需修改
        data.put("accessType", "0");
        //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
        data.put("orderId", record.getBusinessId());
        //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        data.put("txnTime", getCurrentTime());
        //【撤销金额】，消费撤销时必须和原消费金额相同
        data.put("txnAmt", doAmount(record.getApplyPrice()));
        //交易币种(境内商户一般是156 人民币)
        data.put("currencyCode", "156");
        //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范
        //网关支付产品接口规范 消费撤销交易 商户通知,其他说明同消费交易的商户通知
        data.put("backUrl", gatewayConfig.getBossBackUrl() + "/tradeCallback/unionPayRefundCallBack");
        /***要调通交易以下字段必须修改***/
        //【原始交易流水号】，原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
        data.put("origQryId", record.getTradeNo());
        Map<String, String> reqData  = AcpService.sign(data,"UTF-8");
        return reqData;
    }
}
