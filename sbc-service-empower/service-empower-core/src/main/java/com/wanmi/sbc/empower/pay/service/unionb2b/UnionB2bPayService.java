package com.wanmi.sbc.empower.pay.service.unionb2b;


import com.alibaba.fastjson2.JSONObject;
import com.chinapay.secss.SecssUtil;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayCloseOrderRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.unionb2b.UnionB2bPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unionb2b.UnionDirectRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.bean.enums.TradeType;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.repository.ChannelItemRepository;
import com.wanmi.sbc.empower.pay.repository.GatewayConfigRepository;
import com.wanmi.sbc.empower.pay.sdk.AcpService;
import com.wanmi.sbc.empower.pay.sdk.SDKConfig;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.order.api.provider.payingmemberpayrecord.PayingMemberPayRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesByIdRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesModifyRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberPayRecordVO;

import io.seata.spring.annotation.GlobalTransactional;

import jodd.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class UnionB2bPayService implements PayBaseService {

    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private ChannelItemRepository channelItemRepository;

    @Autowired
    private GatewayConfigRepository gatewayConfigRepository;

    @Autowired
    private PayingMemberPayRecordQueryProvider payingMemberPayRecordQueryProvider;

    @Autowired
    private PayTimeSeriesQueryProvider payTimeSeriesQueryProvider;

    @Autowired
    private PayTimeSeriesProvider payTimeSeriesProvider;

    @Value("${new.version}")
    private String newVersion;

    @Value("${china.pay.security.path}")
    private String url;

    @Value("${china.pay.request.front.url}")
    private String requestFrontUrl;

    @Value("${union.refund.url}")
    private String unionRefundUrl;


    /**
     * 新银联企业支付
     *
     * @param basePayRequest
     * @return
     */
    @Override
    public BaseResponse pay(BasePayRequest basePayRequest) {
        UnionPayRequest unionPay = basePayRequest.getUnionPayRequest();
        String businessId = basePayRequest.getTradeId();
        if (businessId.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
            PayingMemberPayRecordVO payingMemberPayRecordVO = payingMemberPayRecordQueryProvider.getTradeRecordByOrderCode(new TradeRecordByOrderCodeRequest
                    (businessId)).getContext().getPayingMemberPayRecordVO();
            if (!Objects.isNull(payingMemberPayRecordVO)) {
                // 如果重复支付，判断状态，已成功状态则做异常提示
                if (payingMemberPayRecordVO.getStatus() == TradeStatus.SUCCEED.toValue()) {
                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
                }
            }
        } else {
            //是否重复支付
            PayTradeRecordResponse record = payTradeRecordQueryProvider.getTradeRecordByOrderCode(TradeRecordByOrderCodeRequest.builder()
                    .orderId(businessId)
                    .build()).getContext();
            if (!Objects.isNull(record) && record.getStatus() == TradeStatus.SUCCEED) {
                //如果重复支付，判断状态，已成功状态则做异常提示
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
            }
        }

//        PayTradeRecord record = recordRepository.findByBusinessId(unionPay.getBusinessId());
        PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
        payTradeRecordRequest.setApplyPrice(unionPay.getAmount());
        payTradeRecordRequest.setBusinessId(businessId);
        payTradeRecordRequest.setClientIp(unionPay.getClientIp());
        payTradeRecordRequest.setChannelItemId(unionPay.getChannelItemId());
        payTradeRecordRequest.setCreateTime(LocalDateTime.now());
        payTradeRecordRequest.setTradeType(TradeType.PAY);
        payTradeRecordRequest.setStatus(TradeStatus.PROCESSING);
        payTradeRecordRequest.setPayNo(unionPay.getOutTradeNo());
        payTradeRecordProvider.queryAndSave(payTradeRecordRequest);

        return BaseResponse.success(newCreateUnionHtml(unionPay));
    }



    /**
     * 新银联企业支付参数拼接
     *
     * @param unionPay
     * @return
     */
    private String newCreateUnionHtml(UnionPayRequest unionPay) {

        //前台页面传过来的
        /***商户接入参数***/
        String merId = unionPay.getApiKey();
        BigDecimal txnAmt = unionPay.getAmount();

        Map<String, String> requestData = new HashMap<>();

        requestData.put("Version", newVersion);  //版本号，认证支付和快捷支付：20150922其余：20140728

        requestData.put("TranType", "0002");                          //交易类型 ，0002 企业网银支付

        requestData.put("BusiType", "0001");                      //业务类型 000202: B2B

        requestData.put("MerId", merId);                              //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("AccessType", "0");                          //接入类型，0：直连商户
        requestData.put("MerOrderNo", unionPay.getOutTradeNo());             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("TranDate", getTranDate());           // 商户提交交易的日期
        requestData.put("TranTime", getTranTime());            //商户提交交易的时间 格式:hhmmss
        requestData.put("OrderAmt", txnAmt.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP)
                .toString());                              //交易金额，单位分，不要带小数点
        requestData.put("MerPageUrl", unionPay.getFrontUrl());

        requestData.put("MerBgUrl", unionPay.getNotifyUrl());

        // 订单超时时间。
        requestData.put("PayTimeOut", "60");
        requestData.put("RemoteAddr",unionPay.getClientIp());   // 防钓鱼客户浏  览器 IP

        /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
        SecssUtil secssUtil = getSecssUtil();
        //签名
        secssUtil.sign(requestData);
        String signature = secssUtil.getSign();
        log.info("=== {}",secssUtil.getSign());
        requestData.put("Signature",signature);
//
        secssUtil.verify(requestData);
        log.info("=== {}  : {}",secssUtil.getErrCode(),secssUtil.getErrMsg());
        String html = AcpService.createAutoFormHtml(requestFrontUrl, requestData, "UTF-8");   //生成自动跳转的Html表单

        log.info("打印请求HTML，此为请求报文，为联调排查问题的依据：" + html);

        return html;
    }

    /**
     * 新银联退款
     * @param request
     * @return
     */
    @Override
    public BaseResponse payRefund(PayRefundBaseRequest request) {
        UnionB2bPayRefundRequest refundRequest = request.getUnionB2bPayRefundRequest();
        Map<String, String> data = getStringStringMap(refundRequest);
        /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
//        SecssUtil secssUtil = new SecssUtil();
        SecssUtil secssUtil1 = getSecssUtil();
        secssUtil1.sign(data);
        String signature = secssUtil1.getSign();
        data.put("Signature",signature);
        log.info("数据源======={}",data);
        String sendMap = AcpService.send(unionRefundUrl, data);
        // 解析同步应答字段
        String[] strs = sendMap.split("&", -1);
        Map<String, String> resultMap = new TreeMap<String, String>();
        for (String str : strs) {
            String[] keyValues = str.split("=", -1);
            if (keyValues.length < 2) {
                continue;
            }
            String key = keyValues[0];
            String value = keyValues[1];
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            resultMap.put(key, value);
        }
        return BaseResponse.success(resultMap);

    }

    private Map<String, String> getStringStringMap(UnionB2bPayRefundRequest unionB2bPayRefundRequest) {
//        SDKConfig.getConfig().loadPropertiesFromSrc();
        String txnAmt = unionB2bPayRefundRequest.getApplyPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP)
                .toString();
        Map<String, String> data = new HashMap<String, String>();
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        data.put("Version", newVersion);                  //版本号，
        data.put("TranType", "0401");                           //交易类型 0401退款
        data.put("BusiType", "0001");                       //业务类型
        data.put("MerId", unionB2bPayRefundRequest.getApiKey());                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("AccessType", "0");                         //接入类型，商户接入固定填0，不需修改
        data.put("MerOrderNo", unionB2bPayRefundRequest.getBusinessId());          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
        data.put("TranDate", getTranDate());      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        data.put("TranTime", getTranTime());      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        data.put("OriOrderNo",unionB2bPayRefundRequest.getOriBusinessId());  //原始交易订单号
        data.put("OriTranDate",unionB2bPayRefundRequest.getCreateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")));  //原始交易时间
        data.put("RefundAmt", txnAmt);                          //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额
        data.put("MerBgUrl", unionB2bPayRefundRequest.getBossBackUrl() + "/tradeCallback/newUnionRefundCallBack");
        return data;
    }

    public UnionB2bPayRefundRequest buildUnionB2bPayRefundRequest(PayTradeRecordResponse record, PayGatewayConfig gatewayConfig,
                                                                  PayTradeRecordResponse payRecord){
        UnionB2bPayRefundRequest unionPayRefundRequest = new UnionB2bPayRefundRequest();
        unionPayRefundRequest.setApiKey(gatewayConfig.getApiKey());
        unionPayRefundRequest.setBossBackUrl(gatewayConfig.getBossBackUrl());
        unionPayRefundRequest.setBusinessId(record.getBusinessId());
        unionPayRefundRequest.setApplyPrice(record.getApplyPrice());
        unionPayRefundRequest.setTradeNo(record.getTradeNo());
//        unionPayRefundRequest.setOriBusinessId(payRecord.getBusinessId());
        unionPayRefundRequest.setOriBusinessId(payRecord.getPayNo());
        unionPayRefundRequest.setCreateTime(payRecord.getCreateTime());
        return unionPayRefundRequest;
    }

    // 商户提交交易的日期 格式:YYYYMMDD
    private static String getTranDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }
    // 商户提交交易的时间 格式:hhmmss
    private static String getTranTime() {
        return new SimpleDateFormat("HHmmss").format(new Date());
    }

    protected SecssUtil getSecssUtil() {
        //   String path = "D:\\workspace\\jew\\back\\sbc-service-pay\\service-pay-api\\src\\main\\resources\\security.properties";
        SecssUtil secssUtil = new SecssUtil();

        secssUtil.init(url);
        // secssUtil.init(path);
        return secssUtil;
    }


    /**
     * 银联企业支付同步回调 添加 数据
     *
     * @param resMap
     */
    public void unionCallBack(Map<String, String> resMap, String businessId) {
        if (businessId.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
            PayingMemberPayRecordVO payingMemberPayRecordVO = payingMemberPayRecordQueryProvider.getTradeRecordByOrderCode(new TradeRecordByOrderCodeRequest
                    (businessId)).getContext().getPayingMemberPayRecordVO();
            if (Constants.STR_00.equals(resMap.get("respCode"))) {
                payingMemberPayRecordVO.setStatus(TradeStatus.SUCCEED.toValue());
            } else {
                payingMemberPayRecordVO.setStatus(TradeStatus.FAILURE.toValue());
            }
            payingMemberPayRecordVO.setChargeId(resMap.get("queryId"));
            payingMemberPayRecordVO.setCallbackTime(payingMemberPayRecordVO.getCallbackTime() == null ? LocalDateTime.now() : payingMemberPayRecordVO.getCallbackTime());
            payingMemberPayRecordVO.setPracticalPrice(BigDecimal.valueOf(Double.valueOf(resMap.get("txnAmt"))).
                    divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
            payingMemberPayRecordVO.setFinishTime(LocalDateTime.now());
            payTradeRecordProvider.saveAndFlush(KsBeanUtil.convert(payingMemberPayRecordVO, PayTradeRecordRequest.class));
        } else {
            PayTradeRecordResponse record = payTradeRecordQueryProvider.getTradeRecordByOrderCode(TradeRecordByOrderCodeRequest.builder()
                    .orderId(businessId)
                    .build()).getContext();
            record.setTradeNo(resMap.get("queryId"));
            if (Constants.STR_00.equals(resMap.get("respCode"))) {
                record.setStatus(TradeStatus.SUCCEED);
            } else {
                record.setStatus(TradeStatus.FAILURE);
            }
            record.setCallbackTime(record.getCallbackTime() == null ? LocalDateTime.now() : record.getCallbackTime());
            record.setPracticalPrice(BigDecimal.valueOf(Double.valueOf(resMap.get("txnAmt"))).
                    divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
            record.setFinishTime(LocalDateTime.now());
            payTradeRecordProvider.saveAndFlush(KsBeanUtil.convert(record, PayTradeRecordRequest.class));
        }
    }

    public void unionCallBackByTimeSeries(Map<String, String> resMap, String payNo) {
        if (payNo.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
            PayingMemberPayRecordVO payingMemberPayRecordVO = payingMemberPayRecordQueryProvider.getTradeRecordByOrderCode(new TradeRecordByOrderCodeRequest
                    (payNo)).getContext().getPayingMemberPayRecordVO();
            if (Constants.STR_00.equals(resMap.get("respCode"))) {
                payingMemberPayRecordVO.setStatus(TradeStatus.SUCCEED.toValue());
            } else {
                payingMemberPayRecordVO.setStatus(TradeStatus.FAILURE.toValue());
            }
            payingMemberPayRecordVO.setChargeId(resMap.get("queryId"));
            payingMemberPayRecordVO.setCallbackTime(payingMemberPayRecordVO.getCallbackTime() == null ? LocalDateTime.now() : payingMemberPayRecordVO.getCallbackTime());
            payingMemberPayRecordVO.setPracticalPrice(BigDecimal.valueOf(Double.valueOf(resMap.get("txnAmt"))).
                    divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
            payingMemberPayRecordVO.setFinishTime(LocalDateTime.now());
            payTradeRecordProvider.saveAndFlush(KsBeanUtil.convert(payingMemberPayRecordVO, PayTradeRecordRequest.class));
        } else {
            PayTimeSeriesVO payTimeSeries = payTimeSeriesQueryProvider.getById(PayTimeSeriesByIdRequest.builder().payNo(payNo).build()).getContext().getPayTimeSeriesVO();


            if (Constants.STR_00.equals(resMap.get("respCode"))) {
                payTimeSeries.setStatus(TradeStatus.SUCCEED);
            } else {
                payTimeSeries.setStatus(TradeStatus.FAILURE);
            }
            payTimeSeries.setCallbackTime(payTimeSeries.getCallbackTime() == null ? LocalDateTime.now() : payTimeSeries.getCallbackTime());
            payTimeSeries.setPracticalPrice(BigDecimal.valueOf(Double.valueOf(resMap.get("txnAmt"))).
                    divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
            payTimeSeries.setTradeNo(resMap.get("queryId"));
            payTimeSeriesProvider.modify(KsBeanUtil.convert(payTimeSeries, PayTimeSeriesModifyRequest.class));
            PayTradeRecordResponse record = payTradeRecordQueryProvider.getTradeRecordByOrderCode(TradeRecordByOrderCodeRequest.builder()
                    .orderId(payTimeSeries.getBusinessId())
                    .build()).getContext();
            record.setChargeId( payTimeSeries.getChargeId() );
            record.setApplyPrice( payTimeSeries.getApplyPrice() );
            record.setPracticalPrice( payTimeSeries.getPracticalPrice() );
            record.setStatus( payTimeSeries.getStatus() );
            record.setChannelItemId( payTimeSeries.getChannelItemId() );
            record.setCreateTime( payTimeSeries.getCreateTime() );
            record.setCallbackTime( payTimeSeries.getCallbackTime() );
            record.setClientIp( payTimeSeries.getClientIp() );
            record.setTradeNo( payTimeSeries.getTradeNo() );
            record.setPayNo( payTimeSeries.getPayNo() );
            record.setFinishTime(LocalDateTime.now());
            record.setFinishTime(LocalDateTime.now());
            payTradeRecordProvider.saveAndFlush(KsBeanUtil.convert(record, PayTradeRecordRequest.class));
        }
    }


    /**
     * 银联直接退款
     *
     * @param request
     */
    @GlobalTransactional
    @Transactional(noRollbackFor = SbcRuntimeException.class)
    public Object unionDirectRefund(UnionDirectRefundRequest request) {
//        PayTradeRecordResponse record = payTradeRecordQueryProvider.getTradeRecordByOrderCode(TradeRecordByOrderCodeRequest.builder()
//                .orderId(request.getBusinessId())
//                .build()).getContext();
        PayTimeSeriesVO payTimeSeriesVO = payTimeSeriesQueryProvider.getById(PayTimeSeriesByIdRequest.builder().payNo(request.getBusinessId()).build()).getContext().getPayTimeSeriesVO();
//        PayTradeRecord record = recordRepository.findByBusinessId(request.getBusinessId());
        PayChannelItem payChannelItem = channelItemRepository.getOne(payTimeSeriesVO.getChannelItemId());
        PayGatewayConfig gatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(payChannelItem.getGatewayName(), request.getStoreId());
        Map<String, String> resultMap = unionRefund(payTimeSeriesVO, gatewayConfig);
        log.info(">>>>>>>>>>>>>>>>>>respCode:{} respMsg:{}", resultMap.get("respCode"), resultMap.get("respMsg"));
        if (Constants.STR_00.equals(resultMap.get("respCode"))) {
            payTimeSeriesVO.setRefundPayNo(resultMap.get("orderId"));
            payTimeSeriesVO.setRefundStatus(TradeStatus.SUCCEED);
            payTimeSeriesVO.setRefundTime(LocalDateTime.now());
            payTimeSeriesProvider.modify(KsBeanUtil.convert(payTimeSeriesVO, PayTimeSeriesModifyRequest.class));
            PayTradeRecordRequest recordRequest = new PayTradeRecordRequest();
            recordRequest.setPayNo(payTimeSeriesVO.getPayNo());
            payTradeRecordProvider.deleteByPayNo(recordRequest);
        } else {
            //提交退款申请失败
            log.error("银联直接退款失败,request:{},resultMap:{}", JSONObject.toJSONString(request), JSONObject.toJSONString(resultMap));
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return resultMap;
    }


    private Map<String, String> unionRefund(PayTimeSeriesVO payTimeSeriesVO, PayGatewayConfig gatewayConfig) {

//        SDKConfig.getConfig().loadPropertiesFromSrc();
        String origQryId = payTimeSeriesVO.getTradeNo();
        String txnAmt = payTimeSeriesVO.getApplyPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP)
                .toString();
        String encoding = "UTF-8";
        Map<String, String> data = new HashMap<String, String>();

        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        data.put("version", SDKConfig.getConfig().getVersion());                  //版本号，全渠道默认值
        data.put("encoding", encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
        data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
        data.put("txnType", "04");                           //交易类型 04-退货
        data.put("txnSubType", "00");                        //交易子类型  默认00
        data.put("bizType", "000202");                       //业务类型
        data.put("channelType", "07");                       //渠道类型，07-PC，08-手机

        /***商户接入参数***/
        data.put("merId", gatewayConfig.getApiKey());                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("accessType", "0");                         //接入类型，商户接入固定填0，不需修改
        data.put("orderId", payTimeSeriesVO.getPayNo());          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
        data.put("txnTime", getCurrentTime());      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        data.put("currencyCode", "156");                     //交易币种（境内商户一般是156 人民币）
        data.put("txnAmt", txnAmt);                          //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额
        data.put("backUrl", gatewayConfig.getBossBackUrl() + "/tradeCallback/unionRefundCallBack");
        //后台通知地址，后台通知参数详见open.unionpay
        // .com帮助中心 下载  产品接口规范
        // 网关支付产品接口规范 退货交易 商户通知,
        // 其他说明同消费交易的后台通知

        /***要调通交易以下字段必须修改***/
        data.put("origQryId", origQryId);      //****原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取

        // 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		data.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		data.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));

        /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/

        Map<String, String> reqData = AcpService.sign(data, encoding);//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String url = SDKConfig.getConfig().getBackTransUrl();                                //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        Map<String, String> rspData = AcpService.post(reqData, url, encoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        /**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
        //应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
//        if(!rspData.isEmpty()){
//            return  rspData.get("respCode");
//        }
//        return null;
        return rspData;

    }


    // 商户发送交易时间 格式:YYYYMMDDhhmmss
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    @Override
    public BaseResponse getPayOrderDetail(PayOrderDetailRequest request) {
        // https://open.unionpay.com/tjweb/acproduct/APIList?acpAPIId=808&apiservId=452&version=V2.2&bussType=0
        // TODO B2B提供对应接口后实现
        return null;
    }

    @Override
    public BaseResponse payCloseOrder(PayCloseOrderRequest request) {
        // https://open.unionpay.com/tjweb/acproduct/APIList?acpAPIId=808&apiservId=452&version=V2.2&bussType=0
        // TODO B2B提供对应接口后实现
        return null;
    }

}
