package com.wanmi.sbc.pay;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.order.api.provider.paycallbackresult.PayCallBackResultProvider;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultModifyResultStatusRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.response.paycallbackresult.PayCallBackResultPageResponse;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;
import com.wanmi.sbc.order.bean.vo.PayCallBackResultVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 10:44 2021/3/9
 * @Description: 支付回调service
 */
@Slf4j
@Service
public class PayCallBackService {

    @Autowired
    private PayCallBackResultProvider payCallBackResultProvider;
    @Autowired
    private PayProvider payProvider;
    @Autowired
    private PayAndRefundCallBackTaskService payCallBackTaskService;



    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void payCallBackHandle(PayCallBackResultPageResponse payCallBackResultPageResponse){
        List<PayCallBackResultVO> payCallBackResultVOList = payCallBackResultPageResponse.getPayCallBackResultVOPage().getContent();
        if(CollectionUtils.isNotEmpty(payCallBackResultVOList)){
            payCallBackResultVOList.forEach(payCallBackResultVO -> {
                if(payCallBackResultVO.getResultStatus() != PayCallBackResultStatus.SUCCESS){
                    payCallBackResultProvider.modifyResultStatusByBusinessId(PayCallBackResultModifyResultStatusRequest.builder()
                            .businessId(payCallBackResultVO.getBusinessId())
                            .resultStatus(PayCallBackResultStatus.HANDLING)
                            .build());
                    try{
                        if(payCallBackResultVO.getPayType()== PayCallBackType.WECAHT){
                            //查询微信支付单报文，根据支付单支付的状态判断判断是否是已支付完成
                            LinkedHashMap wxPayOrderDetailReponse = (LinkedHashMap) payProvider.getPayOrderDetail(PayOrderDetailRequest.builder()
                                    .businessId(payCallBackResultVO.getBusinessId())
                                    .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                                    .payType(PayType.WXPAY)
                                    .build()).getContext();
                            if(Constants.SUCCESS.equals(String.valueOf(wxPayOrderDetailReponse.get("return_code")))&&
                                    Constants.SUCCESS.equals(wxPayOrderDetailReponse.get("result_code"))
                                    &&Constants.SUCCESS.equals(wxPayOrderDetailReponse.get("trade_state"))){
                                payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(payCallBackResultVO.getPayType())
                                        .wxPayCallBackResultStr(payCallBackResultVO.getResultContext())
                                        .wxPayCallBackResultXmlStr(payCallBackResultVO.getResultXml())
                                        .build());
                            }
                        } else if (payCallBackResultVO.getPayType()== PayCallBackType.WECAHT_V3){
                            //查询微信支付单报文，根据支付单支付的状态判断判断是否是已支付完成
                            payCallBackTaskService.payCallBack(
                                    TradePayOnlineCallBackRequest.builder()
                                            .payCallBackType(PayCallBackType.WECAHT_V3)
                                            .wxPayV3CallBackBody(payCallBackResultVO.getResultXml())
                                            .build());
                        } else if(payCallBackResultVO.getPayType()== PayCallBackType.ALI) {
                            PayOrderDetailRequest orderDetailRequest = PayOrderDetailRequest.builder()
                                    .businessId(payCallBackResultVO.getBusinessId())
                                    .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                                    .payType(PayType.ALIPAY).build();
                            orderDetailRequest.setOut_trade_no(payCallBackResultVO.getBusinessId());
                            LinkedHashMap tradeQueryResponse =
                                    (LinkedHashMap) payProvider.getPayOrderDetail(orderDetailRequest).getContext();
                            if(Constants.STR_10000.equals(tradeQueryResponse.get("code")) && ("TRADE_SUCCESS".equals(tradeQueryResponse.get("tradeStatus"))
                                    || "TRADE_FINISHED".equals(tradeQueryResponse.get("tradeStatus")))  ) {
                                payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(payCallBackResultVO.getPayType())
                                        .aliPayCallBackResultStr(payCallBackResultVO.getResultContext())
                                        .build());
                            }
                        } //银联云闪付定时支付补偿
                        else if(payCallBackResultVO.getPayType()== PayCallBackType.UNIONPAY) {
                            payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(payCallBackResultVO.getPayType())
                                    .unionPayCallBackResultStr(payCallBackResultVO.getResultContext())
                                    .build());
                        } else if (payCallBackResultVO.getPayType() == PayCallBackType.LAKALA){
                            payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(payCallBackResultVO.getPayType())
                                    .lakalaPayCallBack(payCallBackResultVO.getResultContext())
                                    .build());
                        } else if (payCallBackResultVO.getPayType() == PayCallBackType.LAKALA_CASHER){
                            payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(payCallBackResultVO.getPayType())
                                    .lakalaPayCallBack(payCallBackResultVO.getResultContext())
                                    .build());
                        }
                    } catch (Exception e) {
                        log.error("定时任务补偿回调时报，businessId={}", payCallBackResultVO.getBusinessId(), e);
                    }
                }
            });
        }
    }
}
