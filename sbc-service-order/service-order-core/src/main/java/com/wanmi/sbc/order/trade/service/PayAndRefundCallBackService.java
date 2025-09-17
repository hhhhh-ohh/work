package com.wanmi.sbc.order.trade.service;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CustomerCreditRepayProvider;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayByRepayCodeRequest;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayModifyRequest;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayAndOrdersByRepayCodeResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.redis.util.RedisPayUtil;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.unionb2b.UnionB2BProvider;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.AliPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemSaveRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionCloudPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayRefundRequest;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemListResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayResultResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultQueryRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.paycallbackresult.model.root.PayCallBackResult;
import com.wanmi.sbc.order.paycallbackresult.service.PayCallBackResultService;
import com.wanmi.sbc.order.payorder.model.root.PayOrder;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.request.ReturnQueryRequest;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.entity.CreditPayInfo;
import com.wanmi.sbc.order.trade.model.entity.PayCallBackOnlineBatch;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.util.GeneratorUtils;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayConstants;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayUtil;

import io.seata.spring.annotation.GlobalTransactional;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 支付/退款回调服务
 */
@Slf4j
@Deprecated
@Service
public class PayAndRefundCallBackService {

    private static final String ALIPAY_GET_WAY_INFO_KEY = "ALI_PAY_GETWAY_INFO_KEY:";

    private static final String WECHAT_PAY_GET_WAY_INFO_KEY = "WECHAT_PAY_GETWAY_INFO_KEY:";

    private static final String UNION_PAY_GET_WAY_INFO_KEY = "UNION_PAY_GETWAY_INFO_KEY:";


    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private PayCallBackResultService payCallBackResultService;

    @Autowired
    private PaySettingProvider paySettingProvider;

    @Autowired
    private PayProvider payProvider;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private CustomerCreditRepayProvider customerCreditRepayProvider;

    @Autowired
    private CreditRepayQueryProvider customerCreditRepayQueryProvider;

    @Autowired
    private UnionB2BProvider unionB2BProvider;

    @Autowired
    private PayTradeRecordService payTradeRecordService;

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 支付回调处理，将原有逻辑迁移到order处理
     * @Date 14:56 2020/7/2
     * @Param [tradePayOnlineCallBackRequest]
     **/
    @Transactional
    @GlobalTransactional
    public void wxPayOnlineCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        String businessId = "";
        try {
            PayGatewayConfigResponse payGatewayConfig = getGatewayConfigByGateway(WECHAT_PAY_GET_WAY_INFO_KEY, PayGatewayEnum.WECHAT);

            String apiKey = payGatewayConfig.getApiKey();
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayResultResponse.class);
            Class<?>[] classes = new Class[] { WxPayResultResponse.class};
            xStream.allowTypes(classes);
            WxPayResultResponse wxPayResultResponse =
                    (WxPayResultResponse) xStream.fromXML(tradePayOnlineCallBackRequest.getWxPayCallBackResultStr());
            log.info("-------------微信支付回调,wxPayResultResponse：{}------------", wxPayResultResponse);
            //判断当前回调是否是合并支付
            businessId = wxPayResultResponse.getOut_trade_no();
            boolean isMergePay = isMergePayOrder(businessId);
            // 是否是授信还款
            boolean isCreditRepay = isCreditRepayFlag(businessId);
            String lockName;
            //非组合支付，则查出该单笔订单。
            if (isMergePay) {
                lockName = businessId;
            } else if (isCreditRepay) {
                lockName = RedisPayUtil.getCallbackLockName(businessId);
            } else {
                Trade trade = new Trade();
                if (isTailPayOrder(businessId)) {
                    trade = tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
                } else {
                    trade = tradeService.detail(businessId);
                }
                // 锁资源：无论是否组合支付，都锁父单号，确保串行回调
                lockName = trade.getParentId();
            }
            //redis锁，防止同一订单重复回调
            RLock rLock = redissonClient.getFairLock(lockName);
            //执行回调
            try {
                rLock.lock();
                //支付回调事件成功
                if (wxPayResultResponse.getReturn_code().equals(WXPayConstants.SUCCESS) &&
                        wxPayResultResponse.getResult_code().equals(WXPayConstants.SUCCESS)) {
                    log.info("微信支付异步通知回调状态---成功");
                    //微信回调参数数据map
                    Map<String, String> params =
                            WXPayUtil.xmlToMap(tradePayOnlineCallBackRequest.getWxPayCallBackResultXmlStr());
                    String trade_type = wxPayResultResponse.getTrade_type();
                    //app支付回调对应的api key为开放平台对应的api key
                    if (trade_type.equals("APP")) {
                        apiKey = payGatewayConfig.getOpenPlatformApiKey();
                    }
                    //微信签名校验
                    if (WXPayUtil.isSignatureValid(params, apiKey)) {
                        //签名正确，进行逻辑处理--对订单支付单以及操作信息进行处理并添加交易数据
                        List<Trade> trades = new ArrayList<>();
                        //查询交易记录

                        PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(businessId);
                        PayCallBackResult payCallBackResult =
                                payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessId(businessId).build()).get(0);
                        if (isMergePay) {
                            /*
                             * 合并支付
                             * 查询订单是否已支付或过期作废
                             */
                            trades = tradeService.detailsByParentId(businessId);
                            //订单合并支付场景状态采样
                            boolean paid =
                                    trades.stream().anyMatch(i -> i.getTradeState().getPayState() == PayState.PAID);
                            boolean cancel =
                                    trades.stream().anyMatch(i -> i.getTradeState().getFlowState() == FlowState.VOID);
                            if (cancel || (paid && !payTradeRecord.getTradeNo().equals(wxPayResultResponse.getTransaction_id()))) {
                                //同一批订单重复支付或过期作废，直接退款
                                wxRefundHandle(wxPayResultResponse, businessId, -1L);
                            } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                                wxPayCallbackHandle(payGatewayConfig, wxPayResultResponse, businessId, trades, true, false, null);
                            }
                        } else if (isCreditRepay) {
                            creditWxPayCallbackHandle(payGatewayConfig, wxPayResultResponse, -1L);
                        } else {
                            //单笔支付
                            Trade trade = new Trade();
                            if (isTailPayOrder(businessId)) {
                                trade =
                                        tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
                            } else {
                                trade = tradeService.detail(businessId);
                            }
                            if (trade.getTradeState().getFlowState() == FlowState.VOID || (trade.getTradeState()
                                    .getPayState() == PayState.PAID
                                    && !payTradeRecord.getTradeNo().equals(wxPayResultResponse.getTransaction_id()))) {
                                //同一批订单重复支付或过期作废，直接退款
                                wxRefundHandle(wxPayResultResponse, businessId, -1L);
                            } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                                trades.add(trade);
                                wxPayCallbackHandle(payGatewayConfig, wxPayResultResponse, businessId, trades, false, false, null);
                            }
                        }
                        //支付回调处理成功
                        payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.SUCCESS);
                    } else {
                        log.info("微信支付异步回调验证签名结果[失败].");
                        //支付处理结果回写回执支付结果表
                        payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.FAILED);
                    }
                } else {
                    log.info("微信支付异步通知回调状态---失败");
                    //支付处理结果回写回执支付结果表
                    payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.FAILED);
                }
                log.info("微信支付异步通知回调end---------");
            } catch (Exception e) {
                log.error(e.getMessage());
                //支付处理结果回写回执支付结果表
                payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.FAILED);
            } finally {
                //解锁
                rLock.unlock();
            }
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(businessId)) {
                payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.FAILED);
            }
            log.error(ex.getMessage());
        }
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 支付回调处理，将原有逻辑迁移到order处理
     * @Date 14:56 2020/7/2
     * @Param [tradePayOnlineCallBackRequest]
     **/
    @Transactional
    @GlobalTransactional
    public void aliPayOnlineCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        log.info("===============支付宝回调开始==============");

        PayGatewayConfigResponse payGatewayConfigResponse = getGatewayConfigByGateway(ALIPAY_GET_WAY_INFO_KEY, PayGatewayEnum.ALIPAY);

        //支付宝公钥
        String aliPayPublicKey = payGatewayConfigResponse.getPublicKey();
        boolean signVerified = false;
        Map<String, String> params =
                JSONObject.parseObject(tradePayOnlineCallBackRequest.getAliPayCallBackResultStr(), Map.class);
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "UTF-8", "RSA2"); //调用SDK验证签名
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名校验异常：", e);
        }
        //商户订单号
        String out_trade_no = params.get("out_trade_no");
        if (signVerified) {
            try {
                //支付宝交易号
                String trade_no = params.get("trade_no");
                //交易状态
                String trade_status = params.get("trade_status");
                //订单金额
                String total_amount = params.get("total_amount");
                //支付终端类型
                String type = params.get("passback_params");

                // 是否是合并支付
                boolean isMergePay = isMergePayOrder(out_trade_no);
                // 是否是授信还款
                boolean isCreditRepay = isCreditRepayFlag(out_trade_no);
                log.info("-------------支付回调,单号：{}，流水：{}，交易状态：{}，金额：{}，是否合并支付：{}，是否授信还款：{}------------",
                        out_trade_no, trade_no, trade_status, total_amount, isMergePay, isCreditRepay);
                String lockName;
                //非组合支付，则查出该单笔订单。
                //非组合支付，则查出该单笔订单。
                if (isMergePay) {
                    lockName = out_trade_no;
                } else if (isCreditRepay) {
                    lockName = RedisPayUtil.getCallbackLockName(out_trade_no);
                } else {
                    Trade trade = new Trade();
                    if (isTailPayOrder(out_trade_no)) {
                        trade =
                                tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(out_trade_no).build()).get(0);
                    } else {
                        trade = tradeService.detail(out_trade_no);
                    }
                    // 锁资源：无论是否组合支付，都锁父单号，确保串行回调
                    lockName = trade.getParentId();
                }
                Operator operator =
                        Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.ALIPAY.name())
                                .account(PayGatewayEnum.ALIPAY.name()).platform(Platform.THIRD).build();
                //redis锁，防止同一订单重复回调
                RLock rLock = redissonClient.getFairLock(lockName);
                //执行
                try {
                    rLock.lock();
                    List<Trade> trades = new ArrayList<>();
                    //查询交易记录
                    PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(out_trade_no);
                    PayCallBackResult payCallBackResult =
                            payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessId(out_trade_no).build()).get(0);
                    if (isMergePay) {
                        /*
                         * 合并支付
                         * 查询订单是否已支付或过期作废
                         */
                        trades = tradeService.detailsByParentId(out_trade_no);
                        //订单合并支付场景状态采样
                        boolean paid =
                                trades.stream().anyMatch(i -> i.getTradeState().getPayState() == PayState.PAID);
                        boolean cancel =
                                trades.stream().anyMatch(i -> i.getTradeState().getFlowState() == FlowState.VOID);
                        //订单的支付渠道。17、18、19是我们自己对接的支付宝渠道， 表：pay_channel_item
                        if (cancel || (paid && payTradeRecord.getChannelItemId() != 17L && payTradeRecord.getChannelItemId()
                                != 18L && payTradeRecord.getChannelItemId() != 19L)) {
                            //重复支付，直接退款
                            alipayRefundHandle(out_trade_no, total_amount);
                        } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                            alipayCallbackHandle(out_trade_no, trade_no, trade_status, total_amount, type,
                                    operator, trades, true, payTradeRecord, false, null);
                        }
                    } else if (isCreditRepay) {
                        creditAlipayCallbackHandle(out_trade_no, trade_no, trade_status, total_amount, type,
                                operator, payTradeRecord, -1L);
                    } else {
                        //单笔支付
                        //单笔支付
                        Trade trade = new Trade();
                        if (isTailPayOrder(out_trade_no)) {
                            trade =
                                    tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(out_trade_no).build()).get(0);
                        } else {
                            trade = tradeService.detail(out_trade_no);
                        }
                        if (trade.getTradeState().getFlowState() == FlowState.VOID || (trade.getTradeState()
                                .getPayState() == PayState.PAID && payTradeRecord.getChannelItemId() != 17L && payTradeRecord.getChannelItemId()
                                != 18L && payTradeRecord.getChannelItemId() != 19L)) {
                            //同一批订单重复支付或过期作废，直接退款
                            alipayRefundHandle(out_trade_no, total_amount);
                        } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                            trades.add(trade);
                            alipayCallbackHandle(out_trade_no, trade_no, trade_status, total_amount, type,
                                    operator, trades, false, payTradeRecord, false, null);
                        }
                    }
                    payCallBackResultService.updateStatus(out_trade_no, PayCallBackResultStatus.SUCCESS);
                } finally {
                    //解锁
                    rLock.unlock();
                }
            } catch (Exception e) {
                log.error("支付宝回调异常：", e);
                payCallBackResultService.updateStatus(out_trade_no, PayCallBackResultStatus.FAILED);
            }
        }
    }

    /**
     * 支付宝退款处理
     *
     * @param out_trade_no
     * @param total_amount
     */
    private void alipayRefundHandle(String out_trade_no, String total_amount) {
        AliPayRefundRequest aliPayRefundRequest = AliPayRefundRequest.builder().businessId(out_trade_no)
                .amount(new BigDecimal(total_amount)).description("重复支付退款").build();
        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setAliPayRefundRequest(aliPayRefundRequest);
        payRefundBaseRequest.setPayType(PayType.ALIPAY);
        //调用退款接口。直接退款。不走退款流程，没有交易对账，只记了操作日志
        payProvider.payRefund(payRefundBaseRequest);
        log.info("支付宝重复支付、超时订单退款,单号：{}", out_trade_no);
    }

    private void alipayCallbackHandle(String out_trade_no, String trade_no, String trade_status, String total_amount,
                                      String type, Operator operator, List<Trade> trades, boolean isMergePay,
                                      PayTradeRecord payTradeRecord, boolean isCreditRepay, CustomerCreditRepayVO customerCreditRepay) {

        Boolean correctAmount;
        if (isCreditRepay) {
            correctAmount = customerCreditRepay.getRepayAmount().compareTo(new BigDecimal(total_amount)) == 0;
        } else {
            correctAmount = payTradeRecord.getApplyPrice().compareTo(new BigDecimal(total_amount)) == 0;
        }
        if (correctAmount
                && trade_status.equals("TRADE_SUCCESS")) {
            //异步回调添加交易数据
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            //流水号
            payTradeRecordRequest.setTradeNo(trade_no);
            //商品订单号
            payTradeRecordRequest.setBusinessId(out_trade_no);
            payTradeRecordRequest.setResult_code("SUCCESS");
            payTradeRecordRequest.setPracticalPrice(new BigDecimal(total_amount));
            payTradeRecordRequest.setChannelItemId(Long.valueOf(type));
            //添加交易数据（与微信共用）
            if (payTradeRecord == null) {
                payTradeRecord = new PayTradeRecord();
                payTradeRecord.setId(GeneratorUtils.generatePT());
            }
            if (payTradeRecordRequest.getChannelItemId() != null) {
                //更新支付记录支付项字段
                payTradeRecord.setChannelItemId(payTradeRecordRequest.getChannelItemId());
            }
            payTradeRecordService.addPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);
            if (isCreditRepay) {
                customerCreditRepay.setRepayType(CreditRepayTypeEnum.ALIPAY);
                creditRepayCallbackOnline(out_trade_no, trades, operator, customerCreditRepay);
            } else {
                payCallbackOnline(trades, operator, isMergePay);
            }
            log.info("支付回调成功,单号：{}", out_trade_no);
        }
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 微信支付退款处理
     * @Date 15:29 2020/7/2
     * @Param [wxPayResultResponse, businessId, storeId]
     **/
    private void wxRefundHandle(WxPayResultResponse wxPayResultResponse, String businessId, Long storeId) {
        WxPayRefundRequest refundInfoRequest = new WxPayRefundRequest();

        refundInfoRequest.setStoreId(storeId);
        refundInfoRequest.setOut_refund_no(businessId);
        refundInfoRequest.setOut_trade_no(businessId);
        refundInfoRequest.setTotal_fee(wxPayResultResponse.getTotal_fee());
        refundInfoRequest.setRefund_fee(wxPayResultResponse.getTotal_fee());
        String tradeType = wxPayResultResponse.getTrade_type();
        if (!tradeType.equals("APP")) {
            tradeType = "PC/H5/JSAPI";
        }
        refundInfoRequest.setPay_type(tradeType);
        //重复支付进行退款处理标志
        refundInfoRequest.setRefund_type("REPEATPAY");

        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setWxPayRefundRequest(refundInfoRequest);
        payRefundBaseRequest.setPayType(PayType.WXPAY);
        payProvider.payRefund(payRefundBaseRequest);
    }

    private void wxPayCallbackHandle(PayGatewayConfigResponse payGatewayConfig, WxPayResultResponse wxPayResultResponse,
                                     String businessId, List<Trade> trades, boolean isMergePay, boolean isCreditRepay, CustomerCreditRepayVO customerCreditRepay) {
        //异步回调添加交易数据
        PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
        //微信支付订单号--及流水号
        payTradeRecordRequest.setTradeNo(wxPayResultResponse.getTransaction_id());
        //商户订单号或父单号
        payTradeRecordRequest.setBusinessId(businessId);
        payTradeRecordRequest.setResult_code(wxPayResultResponse.getResult_code());
        payTradeRecordRequest.setPracticalPrice(new BigDecimal(wxPayResultResponse.getTotal_fee()).
                divide(new BigDecimal(100)).setScale(2, RoundingMode.DOWN));
        ChannelItemByGatewayRequest channelItemByGatewayRequest = new ChannelItemByGatewayRequest();
        channelItemByGatewayRequest.setGatewayName(payGatewayConfig.getPayGateway().getName());
//        PayChannelItemListResponse payChannelItemListResponse =
//                paySettingQueryProvider.listChannelItemByGatewayName(channelItemByGatewayRequest).getContext();
        //支付回调优化--查询走缓存--TODO
        String payChannelItemGatewayNameKey = "PAY_CHANNEL_ITEM_GATEWAY_NAME_KEY:" + payGatewayConfig.getPayGateway().getName();
        PayChannelItemListResponse payChannelItemListResponse = redisService.getObj(payChannelItemGatewayNameKey, PayChannelItemListResponse.class);
        if (Objects.isNull(payChannelItemListResponse)) {
            payChannelItemListResponse =
                    paySettingQueryProvider.listChannelItemByGatewayName(channelItemByGatewayRequest).getContext();
            redisService.setObj(payChannelItemGatewayNameKey, payChannelItemListResponse, 6000);
        }
        List<PayChannelItemVO> payChannelItemVOList =
                payChannelItemListResponse.getPayChannelItemVOList();
        String tradeType = wxPayResultResponse.getTrade_type();
        ChannelItemSaveRequest channelItemSaveRequest = new ChannelItemSaveRequest();
        String code = "wx_qr_code";
        if (tradeType.equals("APP")) {
            code = "wx_app";
        } else if (tradeType.equals("JSAPI")) {
            code = "js_api";
        } else if (tradeType.equals("MWEB")) {
            code = "wx_mweb";
        }
        channelItemSaveRequest.setCode(code);
        payChannelItemVOList.forEach(payChannelItemVO -> {
            if (channelItemSaveRequest.getCode().equals(payChannelItemVO.getCode())) {
                //更新支付项
                payTradeRecordRequest.setChannelItemId(payChannelItemVO.getId());
            }
        });
        PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(payTradeRecordRequest.getBusinessId());
        //微信支付异步回调添加交易数据
        if (payTradeRecord == null) {
            payTradeRecord = new PayTradeRecord();
            payTradeRecord.setId(GeneratorUtils.generatePT());
        }
        if (payTradeRecordRequest.getChannelItemId() != null) {
            //更新支付记录支付项字段
            payTradeRecord.setChannelItemId(payTradeRecordRequest.getChannelItemId());
        }
        payTradeRecordService.addPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);
        //订单 支付单 操作信息
        Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.WECHAT.name())
                .account(PayGatewayEnum.WECHAT.name()).platform(Platform.THIRD).build();
        if (isCreditRepay) {
            customerCreditRepay.setRepayType(CreditRepayTypeEnum.WECHAT);
            creditRepayCallbackOnline(businessId, trades, operator, customerCreditRepay);
        } else {
            payCallbackOnline(trades, operator, isMergePay);
        }
    }

    /**
     * @return boolean
     * @Author lvzhenwei
     * @Description 判断是否为主订单
     * @Date 15:36 2020/7/2
     * @Param [businessId]
     **/
    private boolean isMergePayOrder(String businessId) {
        return businessId.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID) || businessId.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID);
    }


    /**
     * 是否是尾款订单号
     *
     * @param businessId
     * @return
     */
    private boolean isTailPayOrder(String businessId) {
        return businessId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID);
    }

    /**
     * 判断是否为授信还款支付
     *
     * @param businessId
     * @return
     */
    public Boolean isCreditRepayFlag(String businessId) {
        return businessId.startsWith(GeneratorService._PREFIX_CREDIT_REPAY_ID);
    }

    /**
     * 线上订单支付回调
     * 订单 支付单 操作信息
     *
     * @return 操作结果
     */
    private void payCallbackOnline(List<Trade> trades, Operator operator, boolean isMergePay) {
        List<PayCallBackOnlineBatch> payCallBackOnlineBatchList = trades.stream().map(trade -> {
            //每笔订单做是否合并支付标识
            trade.getPayInfo().setMergePay(isMergePay);
            tradeService.updateTrade(trade);
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && trade.getBookingType() == BookingType.EARNEST_MONEY &&
                    StringUtils.isNotEmpty(trade.getTailOrderNo()) && StringUtils.isNotEmpty(trade.getTailPayOrderId())) {
                //支付单信息
                PayOrder payOrder = tradeService.findPayOrder(trade.getTailPayOrderId());
                PayCallBackOnlineBatch backOnlineBatch = new PayCallBackOnlineBatch();
                backOnlineBatch.setTrade(trade);
                backOnlineBatch.setPayOrderOld(payOrder);
                return backOnlineBatch;
            } else {
                //支付单信息
                PayOrder payOrder = tradeService.findPayOrder(trade.getPayOrderId());
                PayCallBackOnlineBatch backOnlineBatch = new PayCallBackOnlineBatch();
                backOnlineBatch.setTrade(trade);
                backOnlineBatch.setPayOrderOld(payOrder);
                return backOnlineBatch;
            }
        }).collect(Collectors.toList());
        tradeService.payCallBackOnlineBatch(payCallBackOnlineBatchList, operator);
    }

    /**
     * 获取支付方式配置信息
     *
     * @param payGetWayInfoKey
     * @param payGatewayEnum
     * @return
     */
    private PayGatewayConfigResponse getGatewayConfigByGateway(String payGetWayInfoKey, PayGatewayEnum payGatewayEnum) {
        PayGatewayConfigResponse payGatewayConfig = redisService.getObj(payGetWayInfoKey, PayGatewayConfigResponse.class);
        if (Objects.isNull(payGatewayConfig)) {
            payGatewayConfig = paySettingQueryProvider.getGatewayConfigByGateway(new GatewayConfigByGatewayRequest(payGatewayEnum, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
            redisService.setObj(payGetWayInfoKey, payGatewayConfig, 6000);
        }
        return payGatewayConfig;
    }


    /**
     * 授信还款支付成功-更新相关数据
     *
     * @param businessId
     * @param trades
     * @param operator
     */
    private void creditRepayCallbackOnline(String businessId, List<Trade> trades, Operator operator, CustomerCreditRepayVO customerCreditRepay) {
        //更新还款记录
        customerCreditRepayProvider.modifyByPaySuccess(CustomerCreditRepayModifyRequest.builder()
                .repayOrderCode(businessId)
                .repayType(customerCreditRepay.getRepayType())
                .updatePerson(operator.getAdminId())
                .repayTime(LocalDateTime.now())
                .build());

        //更新订单中还款状态
        trades.forEach(item -> {
            tradeService.updateCreditHasRepaid(item.getId(), Boolean.TRUE);
        });
    }


    /**
     * 授信还款-处理微信回调
     */
    private void creditWxPayCallbackHandle(PayGatewayConfigResponse payGatewayConfig, WxPayResultResponse wxPayResultResponse, Long storeId) {
        String businessId = wxPayResultResponse.getOut_trade_no();
        CustomerCreditRepayAndOrdersByRepayCodeResponse creditRepayAndOrders =
                customerCreditRepayQueryProvider.getCreditRepayAndOrdersByRepayCode(CustomerCreditRepayByRepayCodeRequest.builder()
                        .repayCode(businessId)
                        .build()).getContext();
        List<Trade> tradeVOList = tradeService.queryAll(TradeQueryRequest.builder()
                .ids(creditRepayAndOrders.getCreditOrderVOList().stream()
                        .map(CustomerCreditOrderVO::getOrderId)
                        .distinct()
                        .toArray(String[]::new))
                .build());

        tradeVOList.forEach(this::fillTradeBookingTimeOut);
        wrapperCreditTrade(tradeVOList);
        //订单合并支付场景状态采样
        boolean paid =
                tradeVOList.stream().anyMatch(i -> i.getCreditPayInfo().getHasRepaid());
        boolean cancel =
                CreditRepayStatus.VOID.equals(creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        boolean returning = tradeVOList.stream().anyMatch(Trade::getReturningFlag);
        boolean correctAmount =
                tradeVOList.stream().map(Trade::getCanRepayPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(new BigDecimal(wxPayResultResponse.getTotal_fee()).divide(new BigDecimal(100)).
                        setScale(2, RoundingMode.DOWN)) == 0;
        if (cancel || paid || !correctAmount || returning) {
            //直接退款
            wxRefundHandle(wxPayResultResponse, businessId, storeId);
        } else {
            creditRepayAndOrders.getCustomerCreditRepayVO().setRepayType(CreditRepayTypeEnum.WECHAT);
            wxPayCallbackHandle(payGatewayConfig, wxPayResultResponse, businessId, tradeVOList, true, true, creditRepayAndOrders.getCustomerCreditRepayVO());
        }
    }


    /**
     * 授信退款-处理支付宝回调
     */
    private void creditAlipayCallbackHandle(String out_trade_no, String trade_no, String trade_status, String total_amount,
                                            String type, Operator operator, PayTradeRecord payTradeRecord, Long storeId) {
        CustomerCreditRepayAndOrdersByRepayCodeResponse creditRepayAndOrders =
                customerCreditRepayQueryProvider.getCreditRepayAndOrdersByRepayCode(CustomerCreditRepayByRepayCodeRequest.builder()
                        .repayCode(out_trade_no)
                        .build()).getContext();
        List<Trade> tradeVOList = tradeService.queryAll(TradeQueryRequest.builder()
                .ids(creditRepayAndOrders.getCreditOrderVOList().stream()
                        .map(CustomerCreditOrderVO::getOrderId)
                        .distinct()
                        .toArray(String[]::new))
                .build());

        tradeVOList.forEach(this::fillTradeBookingTimeOut);
        wrapperCreditTrade(tradeVOList);
        //订单合并支付场景状态采样
        boolean paid =
                tradeVOList.stream().anyMatch(i -> i.getCreditPayInfo().getHasRepaid())
                        && payTradeRecord.getChannelItemId() != 17L
                        && payTradeRecord.getChannelItemId() != 18L
                        && payTradeRecord.getChannelItemId() != 19L;
        boolean cancel =
                CreditRepayStatus.VOID.equals(creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        boolean correctAmount =
                tradeVOList.stream().map(Trade::getCanRepayPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(new BigDecimal(total_amount)) == 0;
        boolean returning = tradeVOList.stream().filter(tradeVO -> tradeVO.getReturningFlag()).findFirst().isPresent();
        if (cancel || paid || !correctAmount || returning) {
            //重复支付，直接退款
            alipayRefundHandle(out_trade_no, total_amount);
        } else {
            creditRepayAndOrders.getCustomerCreditRepayVO().setRepayType(CreditRepayTypeEnum.ALIPAY);
            alipayCallbackHandle(out_trade_no, trade_no, trade_status, total_amount, type,
                    operator, tradeVOList, false, payTradeRecord, true, creditRepayAndOrders.getCustomerCreditRepayVO());
        }
    }


    /**
     * 未完全支付的定金预售订单状态填充为已作废状态
     * <p>
     * （主要订单真实作废比较延迟，计时过后仍然处于待支付尾款情况，前端由订单状态判断来控制支付尾款按钮的展示）
     *
     * @param detail 订单
     */
    private void fillTradeBookingTimeOut(Trade detail) {
        //未完全支付的定金预售订单
        if (Boolean.TRUE.equals(detail.getIsBookingSaleGoods())
                && BookingType.EARNEST_MONEY == detail.getBookingType()
                && Objects.nonNull(detail.getTradeState())
                && (PayState.PAID != detail.getTradeState().getPayState())) {
            //尾款时间 < 今天
            if (Objects.nonNull(detail.getTradeState().getTailEndTime())
                    && detail.getTradeState().getTailEndTime().isBefore(LocalDateTime.now())) {
                //作废
                detail.getTradeState().setFlowState(FlowState.VOID);
            }
        }
    }

    /**
     * 封装还款订单-可还款金额
     *
     * @param tradeVOS
     * @return
     */
    public void wrapperCreditTrade(List<Trade> tradeVOS) {
        if (CollectionUtils.isNotEmpty(tradeVOS)) {
            List<String> orderIds = tradeVOS.stream().map(Trade::getId).collect(Collectors.toList());
            List<ReturnOrder> returnOrderList = returnOrderService.findByCondition(ReturnQueryRequest.builder()
                    .tids(orderIds)
                    .build());
            tradeVOS.forEach(tradeVO -> {
                // 默认都不是退货退款中的订单
                tradeVO.setReturningFlag(Boolean.FALSE);
                // 授信信息
                CreditPayInfo creditPayInfoVO = tradeVO.getCreditPayInfo();
                // 判断是否为空
                if (Objects.nonNull(creditPayInfoVO.getCreditPayState())) {
                    // 可还款金额
                    switch (creditPayInfoVO.getCreditPayState()) {
                        case PAID:
                            tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getTotalPrice());
                            break;
                        case DEPOSIT:
                            tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getEarnestPrice());
                            break;
                        case BALANCE:
                            tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getTailPrice());
                            break;
                        case ALL:
                            tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getTotalPrice());
                            break;
                        default:
                            break;
                    }
                } else {
                    tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getTotalPrice());
                }
            });

            // 处理退货退款中的订单
            if (CollectionUtils.isNotEmpty(returnOrderList)) {
                Map<String, List<ReturnOrder>> map =
                        returnOrderList.stream().collect(Collectors.groupingBy(ReturnOrder::getTid));
                tradeVOS.stream().map(tradeVO -> {
                    List<ReturnOrder> returnOrderVOList = map.get(tradeVO.getId());
                    if (CollectionUtils.isNotEmpty(returnOrderVOList)) {

                        // 查询是否存在已完成的退单
                        Optional<ReturnOrder> returnComplete = returnOrderVOList.stream().filter(item ->
                                item.getReturnFlowState() == ReturnFlowState.COMPLETED
                        ).findFirst();
                        // 如果订单状态为已完成 如果存在符合条件的退单
                        if (tradeVO.getTradeState().getFlowState() == FlowState.COMPLETED || tradeVO.getTradeState().getFlowState() == FlowState.VOID) {
                            // 订单已完成 且 退单已完成
                            if (returnComplete.isPresent()) {
                                // 退单总金额
                                BigDecimal returnPrice = returnOrderVOList.stream().filter(returnOrderVO -> returnOrderVO.getReturnFlowState() == ReturnFlowState.COMPLETED)
                                        .map(returnOrder -> returnOrder.getReturnPrice().getActualReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                // 订单总金额
                                BigDecimal orderPrice = tradeVO.getTradePrice().getTotalPrice();
                                // 金额差异：订单金额-退单金额
                                BigDecimal canRepayPrice = orderPrice.subtract(returnPrice).setScale(2, RoundingMode.DOWN);
                                // 可还款金额
                                tradeVO.setCanRepayPrice(canRepayPrice);
                            }
                        }

                        // 查询是否存在正在进行中的退单(不是作废,不是拒绝退款,不是已结束)
                        Optional<ReturnOrder> returningOrder = returnOrderVOList.stream().filter(item ->
                                item.getReturnFlowState() != ReturnFlowState.VOID
                                        && item.getReturnFlowState() != ReturnFlowState.REJECT_REFUND
                                        && item.getReturnFlowState() != ReturnFlowState.COMPLETED
                                        // 不是退款失败,不是拒绝收货
                                        && item.getReturnFlowState() != ReturnFlowState.REFUND_FAILED
                                        && item.getReturnFlowState() != ReturnFlowState.REJECT_RECEIVE
                        ).findFirst();
                        if (returningOrder.isPresent()) {
                            tradeVO.setReturningFlag(Boolean.TRUE);
                        }
                    }

                    return tradeVO;
                }).collect(Collectors.toList());
            }
        }
    }
    /**
     * @return void
     * @Author zhangyong
     * @Description 银联支付回调处理
     * @Date 14:56 2021/3/17
     * @Param [tradePayOnlineCallBackRequest]
     **/
    @Transactional
    @GlobalTransactional
    public void unionPayOnlineCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        String businessId = "";
        try {
            //获取银联支付网关信息
            PayGatewayConfigResponse payGatewayConfig = getGatewayConfigByGateway(UNION_PAY_GET_WAY_INFO_KEY,
                    PayGatewayEnum.UNIONPAY);

            String apiKey = payGatewayConfig.getApiKey();
            //获取数据库 或者 回调值
            String unionPayCallBackResultStr = tradePayOnlineCallBackRequest.getUnionPayCallBackResultStr();
            //字符串转成map
            Map<String, String> respParam =
                    JSONObject.parseObject(unionPayCallBackResultStr, Map.class);
            log.info("-------------银联支付回调,unionPayCallBackResultStr：{}------------", unionPayCallBackResultStr);
            //判断当前回调是否是合并支付
            businessId = respParam.get("orderId");
            boolean isMergePay = isMergePayOrder(businessId);
            log.info("-------------银联支付回调,单号：{}，流水：{}，交易状态：{}，金额：{}，是否合并支付：{}------------", businessId, respParam.get(
                    "merId"), respParam.get("respCode"), isMergePay, isMergePay);
            String lockName;
            //非组合支付，则查出该单笔订单。
            if (!isMergePay) {
                Trade trade = new Trade();
                if (isTailPayOrder(businessId)) {
                    trade = tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
                } else {
                    trade = tradeService.detail(businessId);
                }
                // 锁资源：无论是否组合支付，都锁父单号，确保串行回调
                lockName = trade.getParentId();
            } else {
                lockName = businessId;
            }
            //日志记录
            Operator operator =
                    Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.UNIONPAY.name())
                            .account(PayGatewayEnum.UNIONPAY.name()).platform(Platform.THIRD).build();
            //redis锁，防止同一订单重复回调
            RLock rLock = redissonClient.getFairLock(lockName);
            rLock.lock();
            //执行回调
            try {
                List<Trade> trades = new ArrayList<>();
                String respCode = respParam.get("respCode");
                log.info("-------------银联支付回调,respParam：{}------------", JSONObject.toJSONString(respParam));
                if ("00".equals(respCode)) {
//                        //1/判断respCode=00 后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
//                        UnionPayRequest unionPayRequest = new UnionPayRequest();
//                        unionPayRequest.setApiKey(respParam.get("merId"));
//                        unionPayRequest.setBusinessId(respParam.get("orderId"));
//                        unionPayRequest.setTxnTime(respParam.get("txnTime"));
//                        Map<String, String> resultMap =
//                                unionCloudPayProvider.getUnionCloudPayResult(unionPayRequest).getContext();
//                        if (resultMap != null && "00".equals(resultMap.get("respCode"))) {
                    //查询交易记录
                    PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(businessId);
                    //查询支付回调记录
                    PayCallBackResult payCallBackResult =
                            payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessId(businessId).build()).get(0);
                    if (isMergePay) {
                        /*
                         * 合并支付
                         * 查询订单是否已支付或过期作废
                         */
                        trades = tradeService.detailsByParentId(businessId);
                        //订单合并支付场景状态采样
                        boolean paid =
                                trades.stream().anyMatch(i -> i.getTradeState().getPayState() == PayState.PAID);
                        boolean cancel =
                                trades.stream().anyMatch(i -> i.getTradeState().getFlowState() == FlowState.VOID);
                        //或者已支付 不是银联通道24 25 渠道
                        if (cancel || (paid && payTradeRecord.getChannelItemId() != 24L && payTradeRecord.getChannelItemId()
                                != 18L && payTradeRecord.getChannelItemId() != 25L)) {
                            //同一批订单重复支付或过期作废，直接退款
                            unionPayRefundHandle(businessId);
                        } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                            unionPayCallbackHandle(operator, respParam, trades,
                                    true);
                        }
                    } else {
                        //单笔支付
                        Trade trade = new Trade();
                        if (isTailPayOrder(businessId)) {
                            trade =
                                    tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
                        } else {
                            trade = tradeService.detail(businessId);
                        }
                        if (trade.getTradeState().getFlowState() == FlowState.VOID || (trade.getTradeState()
                                .getPayState() == PayState.PAID
                                && payTradeRecord.getChannelItemId() != 24L && payTradeRecord.getChannelItemId()
                                != 18L && payTradeRecord.getChannelItemId() != 25L)) {
                            //同一批订单重复支付或过期作废，直接退款
                            unionPayRefundHandle(businessId);
                        } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                            trades.add(trade);
                            unionPayCallbackHandle(operator, respParam, trades,
                                    false);
                        }
                    }
                }
//                    }
                //回调表更新成功 失败 会触发定时任务扫描
                payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.SUCCESS);
                log.info("银联支付异步通知回调end---------");
            } catch (Exception e) {
                log.error(e.getMessage());
                //支付处理结果回写回执支付结果表
                payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.FAILED);
            } finally {
                //解锁
                rLock.unlock();
            }
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(businessId)) {
                payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.FAILED);
            }
            log.error(ex.getMessage());
        }
    }


    private void unionPayRefundHandle(String orderId) {
        UnionCloudPayRefundRequest unionPayRequest = new UnionCloudPayRefundRequest();
        unionPayRequest.setBusinessId(orderId);
        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setUnionCloudPayRefundRequest(unionPayRequest);
        payRefundBaseRequest.setPayType(PayType.UNIONCLONDPAY);
        payProvider.payRefund(payRefundBaseRequest);
        log.info("银联重复支付、超时订单退款,单号：{}", orderId);
    }


    /***
     * 银联支付回调操作逻辑
     * @date 13:48 2021/3/18
     * @author zhangyong
     * @param operator
     * @param resultMap
     * @param trades
     * @param isMergePay
     * @return
     */
    private void unionPayCallbackHandle(Operator operator, Map<String, String> resultMap, List<Trade> trades,
                                        boolean isMergePay) {
        //添加交易数据
        unionB2BProvider.unionCallBack(resultMap);
        payCallbackOnline(trades, operator, isMergePay);
    }

}
