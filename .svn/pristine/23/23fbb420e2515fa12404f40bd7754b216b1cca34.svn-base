package com.wanmi.sbc.pay;

import com.wanmi.sbc.order.api.provider.refundcallbackresult.RefundCallBackResultProvider;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultModifyResultStatusRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import com.wanmi.sbc.order.api.response.refundcallbackresult.RefundCallBackResultPageResponse;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;
import com.wanmi.sbc.order.bean.vo.RefundCallBackResultVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: xufeng
 * @Date: Created In 10:44 2021/3/18
 * @Description: 支付回调service
 */
@Slf4j
@Service
public class RefundCallBackService {

    @Autowired
    private RefundCallBackResultProvider refundCallBackResultProvider;

    @Autowired
    private PayAndRefundCallBackTaskService payCallBackTaskService;

    @Transactional
    @GlobalTransactional
    public void refundCallBackHandle(RefundCallBackResultPageResponse response){
        List<RefundCallBackResultVO> payCallBackResultVOList = response.getPage().getContent();
        if(CollectionUtils.isNotEmpty(payCallBackResultVOList)){
            payCallBackResultVOList.forEach(resultVO -> {
                if(resultVO.getResultStatus() != PayCallBackResultStatus.SUCCESS){
                    refundCallBackResultProvider.modifyResultStatusByBusinessId(RefundCallBackResultModifyResultStatusRequest.builder()
                            .businessId(resultVO.getBusinessId())
                            .resultStatus(PayCallBackResultStatus.HANDLING)
                            .build());
                    try{
                        if(resultVO.getPayType() == PayCallBackType.WECAHT){
                            //查询微信退款单报文，根据退款单退单的状态判断判断是否是已退款完成
//                            WxRefundOrderDetailReponse wxPayOrderDetailResponse = wxPayProvider.getWxRefundOrderDetail(WxRefundOrderDetailRequest.builder()
//                                    .businessId(resultVO.getBusinessId())
//                                    .storeId(Constants.BOSS_DEFAULT_STORE_ID)
//                                    .build()).getContext();
//                            if(SUCCESS_STR.equals(wxPayOrderDetailResponse.getReturn_code()) && SUCCESS_STR.equals(wxPayOrderDetailResponse.getResult_code())
//                                    && SUCCESS_STR.equals(wxPayOrderDetailResponse.getRefund_status_0())){
                            payCallBackTaskService.refundCallBack(TradeRefundOnlineCallBackRequest.builder().payCallBackType(resultVO.getPayType())
                                    .wxRefundCallBackResultXmlStr(resultVO.getResultXml())
                                    .wxRefundCallBackResultStr(resultVO.getResultContext())
                                    .out_refund_no(resultVO.getBusinessId())
                                    .build());
//                            }
                        } else if(resultVO.getPayType() == PayCallBackType.UNIONPAY){
                            payCallBackTaskService.refundCallBack(TradeRefundOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.UNIONPAY)
                                    .unionRefundCallBackResultStr(resultVO.getResultContext()).out_refund_no(resultVO.getBusinessId())
                                    .build());
                        } else if (resultVO.getPayType() == PayCallBackType.LAKALA){
                            payCallBackTaskService.refundCallBack(TradeRefundOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.LAKALA)
                                    .lakalaIdRefundRequest(resultVO.getResultContext())
                                    .out_refund_no(resultVO.getBusinessId())
                                    .build());
                        } else if (resultVO.getPayType() == PayCallBackType.WECAHT_V3){
                            payCallBackTaskService.refundCallBack(TradeRefundOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.WECAHT_V3)
                                    .wxV3RefundCallBackResultStr(resultVO.getResultXml()).out_refund_no(resultVO.getBusinessId())
                                    .build());
                        } else if (resultVO.getPayType() == PayCallBackType.LAKALA_CASHER){
                            payCallBackTaskService.refundCallBack(TradeRefundOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.LAKALA_CASHER)
                                    .lakalaIdRefundRequest(resultVO.getResultContext())
                                    .out_refund_no(resultVO.getBusinessId())
                                    .build());
                        }
                    } catch (Exception e) {
                        log.error("定时任务补偿回调时报，businessId:{},异常信息：{}",resultVO.getBusinessId(),e.getMessage());
                    }
                }
            });
        }
    }
}
