package com.wanmi.sbc.order.paycallback.wechat;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.customer.bean.vo.VideoUserVO;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultQueryRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
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
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import com.wanmi.sbc.order.paytimeseries.service.PayTimeSeriesService;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.service.RefundOrderService;
import com.wanmi.sbc.order.refundcallbackresult.service.RefundCallBackResultService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.order.util.GeneratorUtils;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformOrderProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformOrderRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformQueryOrderResponse;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformPromotionInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @description     视频号支付回调业务
 * @author  wur
 * @date: 2022/4/21 16:48
 **/
@Service("wxChannelsPayAndRefundCallBackService")
@Slf4j
public class WxChannelsPayAndRefundCallBackService implements PayAndRefundCallBackBaseService {

    @Autowired
    private PayCallBackUtil payCallBackUtil;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private PayCallBackResultService payCallBackResultService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private RefundCallBackResultService refundCallBackResultService;

    @Autowired
    private PayTradeRecordService payTradeRecordService;

    @Autowired
    private PayTimeSeriesService payTimeSeriesService;
    /**
    *
     * @description
     * @author  wur
     * @date: 2022/4/21 16:48
     * @param tradePayOnlineCallBackRequest
     * @return
     **/
    @Override
    public BaseResponse payCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        String outTradeNo = "";
        try {
            log.info("-------------微信视频号支付回调,wxChannelsPayCallBackBody：{}------------", tradePayOnlineCallBackRequest.getWxChannelsPayCallBackBody());
            JSONObject callBackBode = JSONObject.parseObject(tradePayOnlineCallBackRequest.getWxChannelsPayCallBackBody());

            //判断当前回调是否是合并支付
            JSONObject orderInfo = callBackBode.getJSONObject("order_info");
            outTradeNo = orderInfo.getString("out_order_id");
            String transaction_id = orderInfo.getString("transaction_id");
            PayTimeSeries payTimeSeries = payTimeSeriesService.getOne(outTradeNo);
            if(payTimeSeries ==null){
                log.error("查询不到支付流水记录，流水号为：{}",outTradeNo);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            String businessId = payTimeSeries.getBusinessId();
            /// 锁资源：无论是否组合支付，都锁父单号，确保串行回调
            Trade trade = tradeService.detail(businessId);
            String lockName = trade.getParentId();
            //redis锁，防止同一订单重复回调
            RLock rLock = redissonClient.getFairLock(lockName);
            rLock.lock();
            //执行回调
            try {

                PayCallBackResult payCallBackResult =
                        payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessId(outTradeNo).build()).get(0);
                //单笔支付
                if (trade.getTradeState().getFlowState() == FlowState.VOID || trade.getTradeState().getPayState() == PayState.PAID ) {
                    //同一批订单重复支付或过期作废
                    log.error("微信视频号支付回调 - 订单重复支付或过期作废,wxPayResultResponse:{}.businessId:{}",payCallBackResult, businessId);

                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                    wxPayCallbackHandle(businessId, transaction_id, trade);
                }
                payCallBackResultService.updateStatus(outTradeNo, PayCallBackResultStatus.SUCCESS);
                log.info("微信视频号支付回调end---------");
            } catch (Exception e) {
                log.error(e.getMessage());
                //支付处理结果回写回执支付结果表
                payCallBackResultService.updateStatus(outTradeNo, PayCallBackResultStatus.FAILED);
            } finally {
                //解锁
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

    @Autowired private SellPlatformOrderProvider sellPlatformOrderProvider;

    /**
     * @description    支付成功处理
     * @author  wur
     * @date: 2022/4/21 19:13
     * @param businessId    订单Id
     * @param payOrderId    支付单Id
     * @param trade        订单信息
     * @return
     **/
    private void wxPayCallbackHandle(String businessId, String payOrderId, Trade trade) {
        //异步回调添加交易数据
        PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(businessId);
        //微信支付异步回调添加交易数据
        if (payTradeRecord == null) {
            payTradeRecord = new PayTradeRecord();
            payTradeRecord.setId(GeneratorUtils.generatePT());
        }
        PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
        //微信支付订单号--及流水号
        payTradeRecordRequest.setTradeNo(payOrderId);
        //商户订单号或父单号
        payTradeRecordRequest.setBusinessId(businessId);
        payTradeRecordRequest.setResult_code("SUCCESS");
        payTradeRecordService.addPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);
        //查询订单  封装推广员信息
        trade = querySellOrder(trade);
        //订单 支付单 操作信息
        Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.WECHAT.name())
                .account(PayGatewayEnum.WECHAT.name()).platform(Platform.THIRD).build();
        payCallBackUtil.payCallbackOnline(Arrays.asList(trade), operator, Boolean.FALSE);
    }

    private Trade querySellOrder(Trade trade) {
        try {
            BaseResponse<SellPlatformQueryOrderResponse> orderResponse = sellPlatformOrderProvider.queryOrder(
                    SellPlatformOrderRequest.builder().orderId(trade.getId()).sellOrderId(trade.getSellPlatformOrderId()).thirdOpenId(trade.getBuyer().getThirdLoginOpenId()).build());
            if (!orderResponse.isSuccess()) {
                return trade;
            }
            SellPlatformQueryOrderResponse queryOrderResponse = orderResponse.getContext();
            if ( Objects.nonNull(queryOrderResponse.getOrder().getOrder_detail().getPromotion_info())) {
                SellPlatformPromotionInfoVO promotion_info = queryOrderResponse.getOrder().getOrder_detail().getPromotion_info();
                VideoUserVO videoUserVO = new VideoUserVO();
                videoUserVO.setVideoName(promotion_info.getFinder_nickname());
                videoUserVO.setVideoAccount(promotion_info.getPromoter_id());
                trade.setVideoUser(videoUserVO);
            }
        } catch (Exception e) {
            log.error("微信视频号订单查询 - 异常, {}", trade.getId());
        }
        return trade;
    }

    @Override
    public BaseResponse refundCallBack(TradeRefundOnlineCallBackRequest request) {
        String businessId = "";
        String resultStr = request.getWechatVideoRefundCallBackResultStr();
        try {

            log.info("-------------微信视频号退款回调,wxChannelsPayCallBackBody：{}------------", resultStr);
            JSONObject callBackBode = JSONObject.parseObject(resultStr);
            businessId = callBackBode.getString("out_aftersale_id");
            String aftersaleId = callBackBode.getString("aftersale_id");
            //查询退单信息
            ReturnOrder returnOrder = returnOrderService.findById(businessId);
            if (Objects.isNull(returnOrder)) {
                log.info("-------------微信视频号退款回调,：退单{} 不存在------------", businessId);
                refundCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.SUCCESS);
                return BaseResponse.SUCCESSFUL();
            }
            //  退款状态
            PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(businessId);

            RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(businessId);
            if (Objects.isNull(refundOrder)) {
                log.info("-------------微信视频号退款回调,：退款单{} 不存在------------", businessId);
                refundCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.SUCCESS);
                return BaseResponse.SUCCESSFUL();
            }
            if (businessId.startsWith(Constants.STR_RT)) {
                refundOrder.setRefundChannel(RefundChannel.TAIL);
            }
            //微信支付异步回调添加交易数据
            //异步回调添加交易数据
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            //微信支付订单号--及流水号
            payTradeRecordRequest.setTradeNo("R"+aftersaleId);
            //商户订单号--业务id(商品退单号)
            payTradeRecordRequest.setBusinessId("R"+businessId);
            payTradeRecordRequest.setApplyPrice(refundOrder.getReturnPrice());
            payTradeRecordRequest.setPracticalPrice(refundOrder.getReturnPrice());
            payTradeRecordRequest.setResult_code("SUCCESS");
            if (payTradeRecord == null) {
                payTradeRecord = new PayTradeRecord();
                payTradeRecord.setId(GeneratorUtils.generatePT());
                payTradeRecord.setApplyPrice(refundOrder.getReturnPrice());
                payTradeRecord.setChannelItemId(Constants.NUM_30L);
                payTradeRecord.setCreateTime(LocalDateTime.now());
            }
            payTradeRecordService.addPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);
            Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name("WECHATVIDEO")
                    .platform(Platform.PLATFORM).build();
            returnOrderService.refundOnline(returnOrder, refundOrder, operator);
            refundCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.SUCCESS);
        } catch (Exception e) {
            log.error("refund:微信视频号退款回调异常：", e);
            if (StringUtils.isNotBlank(businessId)) {
                refundCallBackResultService.updateStatus(businessId, PayCallBackResultStatus.FAILED);
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

}
