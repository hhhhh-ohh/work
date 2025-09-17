package com.wanmi.sbc.order.paycallback.wechat;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayByRepayCodeRequest;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayAndOrdersByRepayCodeResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisPayUtil;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemSaveRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayRefundRequest;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemListResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayResultResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultQueryRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.trade.CreditCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.RefundChannel;
import com.wanmi.sbc.order.paycallback.PayAndRefundCallBackBaseService;
import com.wanmi.sbc.order.paycallback.PayCallBackUtil;
import com.wanmi.sbc.order.paycallbackresult.model.root.PayCallBackResult;
import com.wanmi.sbc.order.paycallbackresult.service.PayCallBackResultService;
import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import com.wanmi.sbc.order.payingmemberpayrecord.service.PayingMemberPayRecordService;
import com.wanmi.sbc.order.payingmemberrecordtemp.model.root.PayingMemberRecordTemp;
import com.wanmi.sbc.order.payingmemberrecordtemp.service.PayingMemberRecordTempService;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import com.wanmi.sbc.order.paytimeseries.service.PayTimeSeriesService;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.service.RefundOrderService;
import com.wanmi.sbc.order.refundcallbackresult.service.RefundCallBackResultService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.request.ReturnQueryRequest;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.service.WxPayUploadShippingInfoService;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("wxPayV3AndRefundCallBackService")
@Slf4j
public class WxPayV3AndRefundCallBackService implements PayAndRefundCallBackBaseService {

    private static final String WECHAT_PAY_GET_WAY_INFO_KEY = "WECHAT_PAY_GETWAY_INFO_KEY:";

    private static final String AMOUNT = "amount";

    @Autowired private RedisUtil redisService;

    @Autowired private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired private PayCallBackUtil payCallBackUtil;

    @Autowired private TradeService tradeService;

    @Autowired private RedissonClient redissonClient;

    @Autowired private PayCallBackResultService payCallBackResultService;

    @Autowired private PayProvider payProvider;

    @Autowired private ReturnOrderService returnOrderService;

    @Autowired private RefundOrderService refundOrderService;

    @Autowired private RefundCallBackResultService refundCallBackResultService;

    @Autowired private CreditRepayQueryProvider customerCreditRepayQueryProvider;

    @Autowired private PayTradeRecordService payTradeRecordService;

    @Autowired private PayingMemberPayRecordService payingMemberPayRecordService;

    @Autowired private PayingMemberRecordTempService payingMemberRecordTempService;

    @Autowired private PayTimeSeriesService payTimeSeriesService;

    @Autowired private WxPayUploadShippingInfoService wxPayUploadShippingInfoService;

    /**
     * @return void @Author lvzhenwei @Description 支付回调处理，将原有逻辑迁移到order处理 @Date 14:56
     *     2020/7/2 @Param [tradePayOnlineCallBackRequest]
     */
    @Override
    public BaseResponse payCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        String callBackStr = tradePayOnlineCallBackRequest.getWxPayV3CallBackBody();
        log.info("-------------微信-V3支付回调,wxPayResultResponse：{}------------", callBackStr);
        JSONObject callBackJson = JSON.parseObject(callBackStr);
        String outTradeNo = callBackJson.getString("out_trade_no");
        try {
            WxPayResultResponse wxPayResultResponse1 = new WxPayResultResponse();
            wxPayResultResponse1.setMch_id(callBackJson.getString("mchid"));
            wxPayResultResponse1.setOut_trade_no(callBackJson.getString("out_trade_no"));
            wxPayResultResponse1.setTransaction_id(callBackJson.getString("transaction_id"));
            wxPayResultResponse1.setTrade_type(callBackJson.getString("trade_type"));
            wxPayResultResponse1.setResult_code(callBackJson.getString("trade_state")); // 订单状态
            wxPayResultResponse1.setTime_end(
                    this.getTime(
                            callBackJson.getString("success_time"))); //  2018-06-08T10:34:56+08:00
            // 封装用户信息
            if (callBackJson.containsKey("payer")) {
                JSONObject payerJson = callBackJson.getJSONObject("payer");
                wxPayResultResponse1.setOpenid(payerJson.getString("openid"));
            }
            // 封装支付金额信息
            if (callBackJson.containsKey(AMOUNT)) {
                JSONObject amountJson = callBackJson.getJSONObject(AMOUNT);
                wxPayResultResponse1.setTotal_fee(amountJson.getString("total"));
                wxPayResultResponse1.setFee_type(amountJson.getString("currency"));
                wxPayResultResponse1.setCash_fee(amountJson.getString("payer_total"));
                wxPayResultResponse1.setCash_fee_type(amountJson.getString("payer_currency"));
            }

            // 判断当前回调是否是合并支付
            // 如果是付费会员 回调
            if (outTradeNo.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
                RLock rLock = redissonClient.getFairLock(outTradeNo);
                try {
                    rLock.lock();
                    if (WXPayConstants.SUCCESS.equals(callBackJson.getString("trade_state"))) {
                        log.info("微信支付异步通知回调状态---成功");

                        String trade_type = wxPayResultResponse1.getTrade_type();
                        PayGatewayConfigResponse payGatewayConfig =
                                payCallBackUtil.getGatewayConfigByGateway(
                                        WECHAT_PAY_GET_WAY_INFO_KEY, PayGatewayEnum.WECHAT);
                        PayingMemberPayRecord payingMemberPayRecord =
                                payingMemberPayRecordService.findByBusinessId(outTradeNo);
                        PayCallBackResult payCallBackResult =
                                payCallBackResultService
                                        .list(
                                                PayCallBackResultQueryRequest.builder()
                                                        .businessId(outTradeNo)
                                                        .build())
                                        .get(0);
                        PayingMemberRecordTemp payingMemberRecordTemp =
                                payingMemberRecordTempService.getOne(outTradeNo);
                        Integer payState = payingMemberRecordTemp.getPayState();
                        if (NumberUtils.INTEGER_ONE.equals(payState)) {
                            log.error(
                                    "订单重复支付或过期作废,不直接退款payCallBackResult:{}.businessId:{}",
                                    payCallBackResult,
                                    outTradeNo);
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        } else {
                            // 添加交易数据
                            // 异步回调添加交易数据
                            PayTradeRecordRequest payTradeRecordRequest =
                                    new PayTradeRecordRequest();
                            // 微信支付订单号--及流水号
                            payTradeRecordRequest.setTradeNo(
                                    wxPayResultResponse1.getTransaction_id());
                            // 商户订单号或父单号
                            payTradeRecordRequest.setBusinessId(outTradeNo);
                            payTradeRecordRequest.setResult_code(
                                    wxPayResultResponse1.getResult_code());
                            payTradeRecordRequest.setPracticalPrice(
                                    new BigDecimal(wxPayResultResponse1.getTotal_fee())
                                            .divide(new BigDecimal(100))
                                            .setScale(2, RoundingMode.DOWN));
                            ChannelItemByGatewayRequest channelItemByGatewayRequest =
                                    new ChannelItemByGatewayRequest();
                            channelItemByGatewayRequest.setGatewayName(
                                    payGatewayConfig.getPayGateway().getName());

                            // 支付回调优化--查询走缓存--TODO
                            String payChannelItemGatewayNameKey =
                                    "PAY_CHANNEL_ITEM_GATEWAY_NAME_KEY:"
                                            + payGatewayConfig.getPayGateway().getName();
                            PayChannelItemListResponse payChannelItemListResponse =
                                    redisService.getObj(
                                            payChannelItemGatewayNameKey,
                                            PayChannelItemListResponse.class);
                            if (Objects.isNull(payChannelItemListResponse)) {
                                payChannelItemListResponse =
                                        paySettingQueryProvider
                                                .listChannelItemByGatewayName(
                                                        channelItemByGatewayRequest)
                                                .getContext();
                                redisService.setObj(
                                        payChannelItemGatewayNameKey,
                                        payChannelItemListResponse,
                                        6000);
                            }
                            List<PayChannelItemVO> payChannelItemVOList =
                                    payChannelItemListResponse.getPayChannelItemVOList();
                            String tradeType = wxPayResultResponse1.getTrade_type();
                            ChannelItemSaveRequest channelItemSaveRequest =
                                    new ChannelItemSaveRequest();
                            String code = "wx_qr_code";
                            if (tradeType.equals("APP")) {
                                code = "wx_app";
                            } else if (tradeType.equals("JSAPI")) {
                                code = "js_api";
                            } else if (tradeType.equals("MWEB")) {
                                code = "wx_mweb";
                            }
                            channelItemSaveRequest.setCode(code);
                            payChannelItemVOList.forEach(
                                    payChannelItemVO -> {
                                        if (channelItemSaveRequest
                                                .getCode()
                                                .equals(payChannelItemVO.getCode())) {
                                            // 更新支付项
                                            payTradeRecordRequest.setChannelItemId(
                                                    payChannelItemVO.getId());
                                            payingMemberPayRecord.setChannelItemId(
                                                    payChannelItemVO.getId().intValue());
                                        }
                                    });
                            payTradeRecordService.addPayRecordForCallBack(
                                    payTradeRecordRequest, payingMemberPayRecord);
                        }
                    }
                    payCallBackResultService.updateStatus(
                            outTradeNo, PayCallBackResultStatus.SUCCESS);
                    payingMemberRecordTempService.addForCallBack(outTradeNo);
                } catch (Exception e) {
                    log.error("微信支付回调：", e);
                    payCallBackResultService.updateStatus(
                            outTradeNo, PayCallBackResultStatus.FAILED);
                } finally {
                    rLock.unlock();
                }
                //调用微信小程序支付发货信息录入接口
                // 改成订单发货时调用
                if(wxPayResultResponse1.getTrade_type().equals("JSAPI")){
                  try {
                        wxPayUploadShippingInfoService.wxPayUploadShippingInfo(null, wxPayResultResponse1, outTradeNo, false, false);
                      } catch (Exception e) {
                        log.error("付费会员微信支付回调：调用微信小程序支付发货信息录入接口异常", e);
                  }
                }
                return BaseResponse.SUCCESSFUL();
            }
            Trade trade = new Trade();
            PayTimeSeries payTimeSeries = payTimeSeriesService.getOne(outTradeNo);
            if(payTimeSeries ==null){
                log.error("查询不到支付流水记录，流水号为：{}",outTradeNo);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            String businessId = payTimeSeries.getBusinessId();
            boolean isMergePay = payCallBackUtil.isMergePayOrder(businessId);
            // 是否是授信还款
            boolean isCreditRepay = payCallBackUtil.isCreditRepayFlag(businessId);
            String lockName;
            // 非组合支付，则查出该单笔订单。
            if (isMergePay) {
                lockName = businessId;
                //查询订单信息
                List<Trade> tradeList = tradeService.detailsByParentId(businessId);
                if(!tradeList.isEmpty()){
                    trade = tradeList.get(0);
                }
            } else if (isCreditRepay) {
                lockName = RedisPayUtil.getCallbackLockName(businessId);
            } else {
                if (payCallBackUtil.isTailPayOrder(businessId)) {
                    trade =
                            tradeService
                                    .queryAll(
                                            TradeQueryRequest.builder()
                                                    .tailOrderNo(businessId)
                                                    .build())
                                    .get(0);
                } else {
                    trade = tradeService.detail(businessId);
                }
                // 锁资源：无论是否组合支付，都锁父单号，确保串行回调
                lockName = trade.getParentId();
            }
            // redis锁，防止同一订单重复回调
            RLock rLock = redissonClient.getFairLock(lockName);
            rLock.lock();
            // 执行回调
            try {
                payCallBackEvent(wxPayResultResponse1, businessId, isMergePay, isCreditRepay,payTimeSeries);
                // 改成订单发货时调用
                if(wxPayResultResponse1.getTrade_type().equals("JSAPI")) {
                    //是否发送小程序发货
                    boolean uploadFlag = false;
                    //虚拟订单 or 卡券订单
                    if (trade.getOrderTag() != null &&
                            (Boolean.TRUE.equals(trade.getOrderTag().getVirtualFlag()) || Boolean.TRUE.equals(trade.getOrderTag().getElectronicCouponFlag()))) {
                        uploadFlag = true;
                    }
                    //自提订单
                    if (Boolean.TRUE.equals(trade.getPickupFlag())) {
                        uploadFlag = true;
                    }
                    //同城配送
                    if (trade.getPayWay() != null && DeliverWay.SAME_CITY.equals(trade.getDeliverWay())) {
                        uploadFlag = true;
                    }
                    //授信还款
                    if (isCreditRepay) {
                        uploadFlag = true;
                    }
                    if (uploadFlag) {
                        wxPayUploadShippingInfoService.wxPayUploadShippingInfo(trade, wxPayResultResponse1, businessId, isMergePay, isCreditRepay);
                    }
                }
                log.info("微信支付异步通知回调end---------");
            } catch (Exception e) {
                log.error(e.getMessage());
                // 支付处理结果回写回执支付结果表
                payCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.FAILED);
            } finally {
                // 解锁
                rLock.unlock();
            }
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(outTradeNo)) {
                payCallBackResultService.updateStatus(outTradeNo, PayCallBackResultStatus.FAILED);
            }
            log.error(ex.getMessage());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 微信支付回调处理解决事务竞争问题
     *
     * @param wxPayResultResponse1
     * @param businessId
     * @param isMergePay
     * @param isCreditRepay
     * @throws Exception
     */
    public void payCallBackEvent(
            WxPayResultResponse wxPayResultResponse1,
            String businessId,
            boolean isMergePay,
            boolean isCreditRepay,
            PayTimeSeries payTimeSeries) {
        PayGatewayConfigResponse payGatewayConfig =
                payCallBackUtil.getGatewayConfigByGateway(
                        WECHAT_PAY_GET_WAY_INFO_KEY, PayGatewayEnum.WECHAT);
        // 支付回调事件成功
        if (WXPayConstants.SUCCESS.equals(wxPayResultResponse1.getResult_code())) {
            log.info("微信支付异步通知回调状态---成功");
            /// 签名正确，进行逻辑处理--对订单支付单以及操作信息进行处理并添加交易数据
            List<Trade> trades = new ArrayList<>();
            // 查询交易记录
            PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(businessId);
            List<PayCallBackResult> payCallBackResultList =
                    payCallBackResultService.list(
                            PayCallBackResultQueryRequest.builder().businessId(payTimeSeries.getPayNo()).build());
            PayCallBackResult payCallBackResult = new PayCallBackResult();
            if (payCallBackResultList.size() > 0) {
                payCallBackResult = payCallBackResultList.get(0);
            }
            if (isMergePay) {
                /*
                 * 合并支付
                 * 查询订单是否已支付或过期作废
                 */
                trades = tradeService.detailsByParentId(businessId);
                // 订单合并支付场景状态采样
                boolean paid =
                        trades.stream()
                                .anyMatch(i -> i.getTradeState().getPayState() == PayState.PAID);
                boolean cancel =
                        trades.stream()
                                .anyMatch(i -> i.getTradeState().getFlowState() == FlowState.VOID);
                if (cancel
                        || (paid
                                && !payTradeRecord
                                        .getTradeNo()
                                        .equals(wxPayResultResponse1.getTransaction_id()))) {
                    // 同一批订单重复支付或过期作废，直接退款
                    log.error(
                            "订单重复支付或过期作废,不直接退款wxPayResultResponse:{}.businessId:{}",
                            wxPayResultResponse1,
                            businessId);
                    this.modifyPayTimeSeries(wxPayResultResponse1,payTimeSeries);
                   // throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                } else if (payCallBackResultList.size() == 0
                        || (payCallBackResultList.size() > 0
                                && payCallBackResult.getResultStatus()
                                        != PayCallBackResultStatus.SUCCESS)) {
                    wxPayCallbackHandle(
                            payGatewayConfig,
                            wxPayResultResponse1,
                            businessId,
                            trades,
                            true,
                            false,
                            payTimeSeries);
                }
            } else if (isCreditRepay) {
                creditWxPayCallbackHandle(payGatewayConfig, wxPayResultResponse1, -1L,payTimeSeries);
            } else {
                // 单笔支付
                Trade trade;
                if (payCallBackUtil.isTailPayOrder(businessId)) {
                    trade =
                            tradeService
                                    .queryAll(
                                            TradeQueryRequest.builder()
                                                    .tailOrderNo(businessId)
                                                    .build())
                                    .get(0);
                } else {
                    trade = tradeService.detail(businessId);
                }
                if (trade.getTradeState().getFlowState() == FlowState.VOID
                        || (trade.getTradeState().getPayState() == PayState.PAID
                                && !payTradeRecord
                                        .getTradeNo()
                                        .equals(wxPayResultResponse1.getTransaction_id()))) {
                    // 同一批订单重复支付或过期作废，直接退款
                    log.error(
                            "订单重复支付或过期作废,不直接退款wxPayResultResponse:{}.businessId:{},storeId:{}",
                            wxPayResultResponse1,
                            businessId);
                    this.modifyPayTimeSeries(wxPayResultResponse1,payTimeSeries);
                   // throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                } else if (payCallBackResultList.size() == 0
                        || (payCallBackResultList.size() > 0
                                && payCallBackResult.getResultStatus()
                                        != PayCallBackResultStatus.SUCCESS)) {
                    trades.add(trade);
                    wxPayCallbackHandle(
                            payGatewayConfig,
                            wxPayResultResponse1,
                            businessId,
                            trades,
                            false,
                            false,
                            payTimeSeries);
                }
            }
            // 支付回调处理成功
            payCallBackResultService.updateStatus(payTimeSeries.getPayNo(), PayCallBackResultStatus.SUCCESS);
        } else {
            log.info("微信支付异步通知回调状态---失败");
            // 支付处理结果回写回执支付结果表
            payCallBackResultService.updateStatus(payTimeSeries.getPayNo(), PayCallBackResultStatus.FAILED);
        }
    }

    @Override
    public BaseResponse refundCallBack(TradeRefundOnlineCallBackRequest request) {
        String resultStr = request.getWxV3RefundCallBackResultStr();
        JSONObject resultJson = JSON.parseObject(resultStr);
        String out_refund_no = resultJson.getString("out_refund_no");
        try {
            log.info("refund:解密后的字符串:{}", resultStr);
            //  退款状态   SUCCESS SUCCESS-退款成功、CHANGE-退款异常、REFUNDCLOSE—退款关闭
            PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(out_refund_no);

            ReturnOrder returnOrder;
            if (out_refund_no.startsWith(Constants.STR_RT)) {
                returnOrder =
                        returnOrderService
                                .findByCondition(
                                        ReturnQueryRequest.builder()
                                                .businessTailId(payTradeRecord.getBusinessId())
                                                .build())
                                .get(0);
            } else {
                returnOrder = returnOrderService.findById(payTradeRecord.getBusinessId());
            }

            RefundOrder refundOrder =
                    refundOrderService.findRefundOrderByReturnOrderNo(out_refund_no);

            if (out_refund_no.startsWith(Constants.STR_RT)) {
                refundOrder.setRefundChannel(RefundChannel.TAIL);
            }
            Operator operator =
                    Operator.builder()
                            .ip(HttpUtil.getIpAddr())
                            .adminId("-1")
                            .name("WECHAT")
                            .platform(Platform.PLATFORM)
                            .build();
            // 微信支付异步回调添加交易数据
            // 异步回调添加交易数据
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            // 微信支付订单号--及流水号
            payTradeRecordRequest.setTradeNo(resultJson.getString("refund_id"));
            // 商户订单号--业务id(商品退单号)
            payTradeRecordRequest.setBusinessId(out_refund_no);
            payTradeRecordRequest.setResult_code(resultJson.getString("refund_status"));
            if (resultJson.containsKey(AMOUNT)) {
                JSONObject amountJson = resultJson.getJSONObject(AMOUNT);
                payTradeRecordRequest.setApplyPrice(
                        new BigDecimal(amountJson.getString("refund"))
                                .divide(new BigDecimal(100))
                                .setScale(2, RoundingMode.DOWN));
                payTradeRecordRequest.setPracticalPrice(
                        new BigDecimal(amountJson.getString("total"))
                                .divide(new BigDecimal(100))
                                .setScale(2, RoundingMode.DOWN));
            }
            payTradeRecordService.addPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);
            returnOrderService.refundOnline(returnOrder, refundOrder, operator);
            refundCallBackResultService.updateStatus(
                    out_refund_no, PayCallBackResultStatus.SUCCESS);
        } catch (Exception e) {
            log.error("refund:微信退款回调发布异常：", e);
            if (StringUtils.isNotBlank(out_refund_no)) {
                refundCallBackResultService.updateStatus(
                        out_refund_no, PayCallBackResultStatus.FAILED);
            }
        }
        return null;
    }

    /**
     * @return void @Author lvzhenwei @Description 微信支付退款处理 @Date 15:29 2020/7/2 @Param
     *     [wxPayResultResponse, businessId, storeId]
     */
    private void wxRefundHandle(
            WxPayResultResponse wxPayResultResponse1, String businessId, Long storeId) {
        WxPayRefundRequest refundInfoRequest = new WxPayRefundRequest();

        refundInfoRequest.setStoreId(storeId);
        refundInfoRequest.setOut_refund_no(businessId);
        refundInfoRequest.setOut_trade_no(businessId);
        refundInfoRequest.setTotal_fee(wxPayResultResponse1.getTotal_fee());
        refundInfoRequest.setRefund_fee(wxPayResultResponse1.getTotal_fee());
        String tradeType = wxPayResultResponse1.getTrade_type();
        if (!tradeType.equals("APP")) {
            tradeType = "PC/H5/JSAPI";
        }
        refundInfoRequest.setPay_type(tradeType);
        // 重复支付进行退款处理标志
        refundInfoRequest.setRefund_type("REPEATPAY");
        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setWxPayRefundRequest(refundInfoRequest);
        payRefundBaseRequest.setPayType(PayType.WXPAY);
        payProvider.payRefund(payRefundBaseRequest);
    }

    private void wxPayCallbackHandle(
            PayGatewayConfigResponse payGatewayConfig,
            WxPayResultResponse wxPayResultResponse1,
            String businessId,
            List<Trade> trades,
            boolean isMergePay,
            boolean isCreditRepay,
            PayTimeSeries payTimeSeries) {
        // 异步回调添加交易数据
//        PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
//        // 微信支付订单号--及流水号
//        payTradeRecordRequest.setTradeNo(wxPayResultResponse1.getTransaction_id());
//        // 商户订单号或父单号
//        payTradeRecordRequest.setBusinessId(businessId);
//        payTradeRecordRequest.setResult_code(wxPayResultResponse1.getResult_code());
//        payTradeRecordRequest.setPracticalPrice(
//                new BigDecimal(wxPayResultResponse1.getTotal_fee())
//                        .divide(new BigDecimal(100))
//                        .setScale(2, BigDecimal.ROUND_DOWN));
//        ChannelItemByGatewayRequest channelItemByGatewayRequest = new ChannelItemByGatewayRequest();
//        channelItemByGatewayRequest.setGatewayName(payGatewayConfig.getPayGateway().getName());
//        // 支付回调优化--查询走缓存--
//        String payChannelItemGatewayNameKey =
//                "PAY_CHANNEL_ITEM_GATEWAY_NAME_KEY:" + payGatewayConfig.getPayGateway().getName();
//        PayChannelItemListResponse payChannelItemListResponse =
//                redisService.getObj(payChannelItemGatewayNameKey, PayChannelItemListResponse.class);
//        if (Objects.isNull(payChannelItemListResponse)) {
//            payChannelItemListResponse =
//                    paySettingQueryProvider
//                            .listChannelItemByGatewayName(channelItemByGatewayRequest)
//                            .getContext();
//            redisService.setObj(payChannelItemGatewayNameKey, payChannelItemListResponse, 6000);
//        }
//        List<PayChannelItemVO> payChannelItemVOList =
//                payChannelItemListResponse.getPayChannelItemVOList();
//        String tradeType = wxPayResultResponse1.getTrade_type();
//        ChannelItemSaveRequest channelItemSaveRequest = new ChannelItemSaveRequest();
//        String code = "wx_qr_code";
//        if (tradeType.equals("APP")) {
//            code = "wx_app";
//        } else if (tradeType.equals("JSAPI")) {
//            code = "js_api";
//        } else if (tradeType.equals("MWEB")) {
//            code = "wx_mweb";
//        }
//        channelItemSaveRequest.setCode(code);
//        payChannelItemVOList.forEach(
//                payChannelItemVO -> {
//                    if (channelItemSaveRequest.getCode().equals(payChannelItemVO.getCode())) {
//                        // 更新支付项
//                        payTradeRecordRequest.setChannelItemId(payChannelItemVO.getId());
//                    }
//                });
//        PayTradeRecord payTradeRecord =
//                payTradeRecordService.queryByBusinessId(payTradeRecordRequest.getBusinessId());
//        // 微信支付异步回调添加交易数据
//        if (payTradeRecord == null) {
//            payTradeRecord = new PayTradeRecord();
//            payTradeRecord.setId(GeneratorUtils.generatePT());
//        }
//        if (payTradeRecordRequest.getChannelItemId() != null) {
//            // 更新支付记录支付项字段
//            payTradeRecord.setChannelItemId(payTradeRecordRequest.getChannelItemId());
//        }

//        payTradeRecordService.addPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);
        this.buildPayTimeSeries(wxPayResultResponse1,payTimeSeries);
        payTradeRecordService.addPayRecordForCallBackByTimeSeries(payTimeSeries);
        // 订单 支付单 操作信息
        Operator operator =
                Operator.builder()
                        .ip(HttpUtil.getIpAddr())
                        .adminId("-1")
                        .name(PayGatewayEnum.WECHAT.name())
                        .account(PayGatewayEnum.WECHAT.name())
                        .platform(Platform.THIRD)
                        .build();
        if (isCreditRepay) {
            List<String> ids = trades.stream().map(Trade::getId).collect(Collectors.toList());
            payCallBackUtil.creditRepayCallbackOnline(
                    CreditCallBackRequest.builder()
                            .repayOrderCode(businessId)
                            .ids(ids)
                            .userId(operator.getUserId())
                            .repayType(CreditRepayTypeEnum.WECHAT)
                            .build());
        } else {
            payCallBackUtil.updateTradeSellInfo(trades);
            payCallBackUtil.payCallbackOnline(trades, operator, isMergePay);
        }
    }

    /** 授信还款-处理微信回调 */
    private void creditWxPayCallbackHandle(
            PayGatewayConfigResponse payGatewayConfig,
            WxPayResultResponse wxPayResultResponse1,
            Long storeId,
            PayTimeSeries payTimeSeries) {
        String businessId = payTimeSeries.getBusinessId();
        CustomerCreditRepayAndOrdersByRepayCodeResponse creditRepayAndOrders =
                customerCreditRepayQueryProvider
                        .getCreditRepayAndOrdersByRepayCode(
                                CustomerCreditRepayByRepayCodeRequest.builder()
                                        .repayCode(businessId)
                                        .build())
                        .getContext();
        List<Trade> tradeVOList =
                tradeService.queryAll(
                        TradeQueryRequest.builder()
                                .ids(
                                        creditRepayAndOrders.getCreditOrderVOList().stream()
                                                .map(CustomerCreditOrderVO::getOrderId)
                                                .distinct()
                                                .toArray(String[]::new))
                                .build());

        tradeVOList.forEach((trade -> payCallBackUtil.fillTradeBookingTimeOut(trade)));
        payCallBackUtil.wrapperCreditTrade(tradeVOList);
        // 订单合并支付场景状态采样
        boolean paid = tradeVOList.stream().anyMatch(i -> i.getCreditPayInfo().getHasRepaid());
        boolean cancel =
                CreditRepayStatus.VOID.equals(
                        creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        //添加已经支付判断
        boolean finish = CreditRepayStatus.FINISH.equals(
                creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        boolean returning = tradeVOList.stream().anyMatch(Trade::getReturningFlag);
        boolean correctAmount =
                tradeVOList.stream()
                                .map(Trade::getCanRepayPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .compareTo(
                                        new BigDecimal(wxPayResultResponse1.getTotal_fee())
                                                .divide(new BigDecimal(100))
                                                .setScale(2, RoundingMode.DOWN))
                        == 0;
        if (cancel || paid || !correctAmount || returning||finish) {
            // 直接退款
            wxRefundHandle(wxPayResultResponse1, payTimeSeries.getPayNo(), storeId);
        } else {
            creditRepayAndOrders
                    .getCustomerCreditRepayVO()
                    .setRepayType(CreditRepayTypeEnum.WECHAT);
            wxPayCallbackHandle(
                    payGatewayConfig,
                    wxPayResultResponse1,
                    businessId,
                    tradeVOList,
                    true,
                    true,
                    payTimeSeries);
        }
    }

    /**
     * @description 时间格式转换和V2 保持一致
     * @author wur
     * @date: 2022/11/30 14:50
     * @param time
     * @return
     */
    private String getTime(String time) {
        StringBuilder timeBuf = new StringBuilder("");
        timeBuf.append(time.substring(0, 10).replace("-", ""));
        timeBuf.append(time.substring(11, 19).replace(":", ""));
        return timeBuf.toString();
    }

    private PayTimeSeries buildPayTimeSeries(WxPayResultResponse wxPayResultResponse, PayTimeSeries payTimeSeries){
        payTimeSeries.setStatus(WXPayConstants.SUCCESS.equals(wxPayResultResponse.getResult_code())? TradeStatus.SUCCEED:TradeStatus.FAILURE);
        payTimeSeries.setTradeNo(wxPayResultResponse.getTransaction_id());
        payTimeSeries.setCallbackTime(LocalDateTime.now());
        payTimeSeries.setPracticalPrice(new BigDecimal(wxPayResultResponse.getTotal_fee())
                .divide(new BigDecimal(100))
                .setScale(2, RoundingMode.DOWN));
        return payTimeSeries;
    }

    private void modifyPayTimeSeries(WxPayResultResponse wxPayResultResponse,PayTimeSeries payTimeSeries){
        payTimeSeries = this.buildPayTimeSeries(wxPayResultResponse,payTimeSeries);
        payTimeSeriesService.modify(payTimeSeries);
    }
}
