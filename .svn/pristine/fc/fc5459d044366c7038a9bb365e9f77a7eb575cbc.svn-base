package com.wanmi.sbc.empower.pay.service.ali;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.google.common.base.Joiner;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayCloseOrderRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.AliPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.PayExtraRequest;
import com.wanmi.sbc.empower.api.response.pay.ali.AliPayFormResponse;
import com.wanmi.sbc.empower.api.response.pay.ali.AliPayRefundResponse;
import com.wanmi.sbc.empower.bean.constant.PayServiceConstants;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.repository.ChannelItemRepository;
import com.wanmi.sbc.empower.pay.repository.GatewayRepository;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.empower.pay.service.PayDataService;
import com.wanmi.sbc.empower.pay.utils.PayValidates;
import com.wanmi.sbc.empower.util.JsonUtils;
import com.wanmi.sbc.order.api.provider.payingmemberpayrecord.PayingMemberPayRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.bean.vo.PayingMemberPayRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * @author EDZ
 * @program: service-pay
 * @description: 支付宝
 * @create: 2019-01-28 16:37
 **/
@Service(PayServiceConstants.ALIPAY_SERVICE)
@Slf4j
public class AliPayServiceImpl implements PayBaseService {

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;
    @Resource
    private ChannelItemRepository channelItemRepository;
    @Resource
    private GatewayRepository gatewayRepository;
    @Autowired
    PayDataService payDataService;
    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;
    @Autowired
    private PayingMemberPayRecordQueryProvider payingMemberPayRecordQueryProvider;

    //    String payDev = "https://openapi.alipaydev.com/gateway.do";
    /***
     * 支付宝网关
     */
    private static final String PAY = "https://openapi.alipay.com/gateway.do";

    /***
     * 回调地址
     */
    private static final String CALLBACK = "/tradeCallback/aliPayCallBack";

    /**
     * @Description: 支付宝支付接口。调用sdk生成支付数据
     * @Author: Bob
     * @Date: 2019-02-26 17:11
     */
    @Override
    @Transactional(noRollbackFor = SbcRuntimeException.class)
    public BaseResponse pay(@Valid BasePayRequest basePayRequest) {
        PayExtraRequest request = basePayRequest.getPayExtraRequest();
        PayChannelItem item = getPayChannelItem(request.getChannelItemId(), request.getStoreId());
        //该付款方式是否支持该渠道
        if (Objects.isNull(item) || item.getTerminal() != request.getTerminal()) {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060002);
        }

        PayValidates.verifyGateway(item.getGateway());

        //订单重复付款检验
        //PayTradeRecord record = recordRepository.findByBusinessId(request.getBusinessId());
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
            PayTradeRecordResponse payTradeRecord =
                    payTradeRecordQueryProvider.getTradeRecordByOrderCode(new TradeRecordByOrderCodeRequest
                            (businessId)).getContext();
            if (!Objects.isNull(payTradeRecord)) {
                // 如果重复支付，判断状态，已成功状态则做异常提示
                if (payTradeRecord.getStatus() == TradeStatus.SUCCEED) {
                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
                }
            }
        }



        //支付宝的参数
        String appId = item.getGateway().getConfig().getAppId();
        String appPrivateKey = item.getGateway().getConfig().getPrivateKey();
        String aliPayPublicKey = item.getGateway().getConfig().getPublicKey();
        String id = item.getId() + "";

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(PAY, appId, appPrivateKey, "json", StandardCharsets.UTF_8.name(),
                aliPayPublicKey, "RSA2");

        String form = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_trade_no", request.getOutTradeNo());
        jsonObject.put("total_amount", request.getAmount());
        jsonObject.put("subject", request.getSubject());
        jsonObject.put("body", request.getBody());
        //不同的下单渠道走不同的支付接口
        if (TerminalType.PC == request.getTerminal()) {
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(request.getSuccessUrl());
            alipayRequest.setNotifyUrl(getNotifyUrl(item.getGateway().getConfig()));
            jsonObject.put("product_code", "FAST_INSTANT_TRADE_PAY");
            jsonObject.put("passback_params", id);
            alipayRequest.setBizContent(jsonObject.toString());


            try {
                // 调用SDK生成表单
                form = alipayClient.pageExecute(alipayRequest).getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060008, new Object[]{"PC支付"});
            }
        } else if (TerminalType.H5 == request.getTerminal()) {
            // 创建API对应的request
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
            alipayRequest.setReturnUrl(request.getSuccessUrl());
            alipayRequest.setNotifyUrl(getNotifyUrl(item.getGateway().getConfig()));
            jsonObject.put("product_code", "QUICK_WAP_PAY");
            jsonObject.put("passback_params", id);
            alipayRequest.setBizContent(jsonObject.toString());
            try {
                // 调用SDK生成表单
                form = alipayClient.pageExecute(alipayRequest).getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060008, new Object[]{"H5支付"});
            }
        } else if (TerminalType.APP == request.getTerminal()) {
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(request.getBody());
            model.setSubject(request.getSubject());
            model.setOutTradeNo(request.getOutTradeNo());
            model.setTotalAmount(request.getAmount().toString());
            model.setProductCode("QUICK_MSECURITY_PAY");
            model.setPassbackParams(id);
            model.setOutTradeNo(request.getOutTradeNo());
            alipayRequest.setBizModel(model);
            alipayRequest.setNotifyUrl(getNotifyUrl(item.getGateway().getConfig()));
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
                form = response.getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060008, new Object[]{"APP支付"});
            }
        }
        savePayRecord(request,businessId);
        return BaseResponse.success(new AliPayFormResponse(form));
    }

    /**
     * @Description: 新增、更新交易记录
     * @Author: Bob
     * @Date: 2019-03-05 13:54
     */
    private void savePayRecord(PayRequest request,String tradeId) {
        PayTradeRecordRequest record = new PayTradeRecordRequest();
        record.setApplyPrice(request.getAmount());
        record.setBusinessId(tradeId);
        record.setChargeId(request.getOutTradeNo());
        record.setClientIp(request.getClientIp());
        record.setChannelItemId(request.getChannelItemId());
        record.setPayNo(request.getOutTradeNo());
        payTradeRecordProvider.saveAndFlush(record);
    }

    /**
     * @Description: 支付宝退款接口
     * @Author: Bob
     * @Date: 2019-02-26 17:11
     */
    @Override
    public BaseResponse payRefund(PayRefundBaseRequest payBaseRequest) {
        AliPayRefundRequest refundRequest = getAliPayRefundRequest(payBaseRequest.getAliPayRefundRequest());

        AlipayClient alipayClient = new DefaultAlipayClient(PAY, refundRequest.getAppid(),
                refundRequest.getAppPrivateKey(),
                "json", StandardCharsets.UTF_8.name(), refundRequest.getAliPayPublicKey(), "RSA2");

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject jsonObject = new JSONObject();
        // 订单支付时传入的商户订单号,不能和 trade_no同时为空。
        jsonObject.put("out_trade_no", refundRequest.getBusinessId());
        // 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
        jsonObject.put("refund_amount", refundRequest.getAmount());
        // 退款的原因说明
        jsonObject.put("refund_reason", refundRequest.getDescription());
        // 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        jsonObject.put("out_request_no", refundRequest.getRefundBusinessId());
        request.setBizContent(jsonObject.toString());

        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060008, new Object[]{"退款接口"});
        }
        AliPayRefundResponse aliPayRefundResponse = new AliPayRefundResponse();
        aliPayRefundResponse.setPayType("ALIPAY");
        aliPayRefundResponse.setAlipayTradeRefundResponse(response);
        return BaseResponse.success(aliPayRefundResponse);
    }

    private AliPayRefundRequest getAliPayRefundRequest(AliPayRefundRequest payBaseRequest) {
        AliPayRefundRequest refundRequest = payBaseRequest;
        // 兼容PayService,通用退款接口
        if (StringUtils.isEmpty(refundRequest.getAppid())
                || StringUtils.isEmpty(refundRequest.getAliPayPublicKey())
                || StringUtils.isEmpty(refundRequest.getAppPrivateKey())){
            // Todo Saas独立收款
            PayGatewayConfig payGatewayConfig = payDataService.queryConfigByNameAndStoreId(PayGatewayEnum.ALIPAY,
                    Constants.BOSS_DEFAULT_STORE_ID);
            if (Objects.nonNull(payGatewayConfig)) {
                refundRequest.setAppid(payGatewayConfig.getAppId());
                refundRequest.setAliPayPublicKey(payGatewayConfig.getPublicKey());
                refundRequest.setAppPrivateKey(payGatewayConfig.getPrivateKey());
            } else {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060005);
            }
        }
        return refundRequest;
    }

    /**
     * @Description: 获取配置信息
     * @Author: Bob
     * @Date: 2019-03-05 13:55
     */
    private PayChannelItem getPayChannelItem(Long channelItemId, Long storeId) {
        Optional<PayChannelItem> optionalItem = channelItemRepository.findById(channelItemId);
        if (!optionalItem.isPresent()) {
            return null;
        }
        PayChannelItem item = optionalItem.get();
        PayValidates.verfiyPayChannelItem(item);
        // 获取网关
        PayGateway gateway = gatewayRepository.queryByNameAndStoreId(item.getGatewayName(), storeId);
        item.setGateway(gateway);
        return item;
    }


    private String getNotifyUrl(PayGatewayConfig payGatewayConfig) {
        return Joiner.on("").join(payGatewayConfig.getBossBackUrl(), CALLBACK);
    }

    @Override
    public BaseResponse getPayOrderDetail(PayOrderDetailRequest request) {
        AlipayTradeQueryRequest tradeQueryRequest = new AlipayTradeQueryRequest();
        tradeQueryRequest.setBizContent(JsonUtils.buildJsonStr("out_trade_no", request.getBusinessId()));
        AlipayTradeQueryResponse response;
        try {
            response = buildAlipayClient(request.getStoreId()).execute(tradeQueryRequest);
            log.info("getPayOrderDetail response:{}", response);
        } catch (AlipayApiException e) {
            log.error("getPayOrderDetail error:", e);
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060008, new Object[]{"查询支付单"});
        }
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse payCloseOrder(PayCloseOrderRequest request) {
        AlipayTradeCloseRequest tradeQueryRequest = new AlipayTradeCloseRequest();
        tradeQueryRequest.setBizContent(JsonUtils.buildJsonStr("out_trade_no", request.getBusinessId()));
        AlipayTradeCloseResponse response;
        try {
            response = buildAlipayClient(request.getStoreId()).execute(tradeQueryRequest);
        } catch (AlipayApiException e) {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060008, new Object[]{"关闭支付单"});
        }
        return BaseResponse.success(response);
    }
//
//    @Override
//    public BaseResponse pay(BasePayRequest basePayRequest) {
//        return BaseResponse.success(new AliPayFormResponse(pay(basePayRequest)));
//    }

    /***
     * 构建一个Alipay请求客户端
     * @param storeId 店铺ID
     * @return
     */
    private AlipayClient buildAlipayClient(Long storeId) {
        PayGateway gateway = gatewayRepository.queryByNameAndStoreId(PayGatewayEnum.ALIPAY, storeId);

        // 支付宝的参数
        PayGatewayConfig config = gateway.getConfig();
        String appId = config.getAppId();
        String appPrivateKey = config.getPrivateKey();
        String aliPayPublicKey = config.getPublicKey();

        // 获得初始化的AlipayClient
        return new DefaultAlipayClient(PAY, appId, appPrivateKey, "json", StandardCharsets.UTF_8.name(),
                aliPayPublicKey, "RSA2");
    }
}
