package com.wanmi.sbc.job.jdvop.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.message.api.provider.vopmessage.VopLogProvider;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import com.wanmi.sbc.message.bean.enums.VopLogType;
import com.wanmi.sbc.order.api.constant.RefundReasonConstants;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.thirdplatformreturn.ThirdPlatformReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderAutoReturnByProviderTradeIdRequest;
import com.wanmi.sbc.order.api.request.thirdplatformreturn.ThirdPlatformReturnOrderAutoApplySubRequest;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeByThirdPlatformOrderIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeCancelRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.ThirdPlatformTradeVO;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hanwei
 * @className JdVopCancelOrderHandler
 * @description 处理取消订单消息
 * @date 2021/6/2 10:52
 **/
@Slf4j
@Component
public class JdVopCancelOrderMessageHandler implements JdVopMessageHandler {

    @Autowired
    private ThirdPlatformTradeQueryProvider thirdPlatformTradeQueryProvider;

    @Autowired
    private ThirdPlatformReturnOrderProvider thirdPlatformReturnOrderProvider;

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private VopLogProvider vopLogProvider;

    /**
     * 是否记录vop日志，true是记录, false是关闭
     * @return
     */
    @Value("${vopLogFlag}")
    private boolean VOP_LOG_FLAG = true;

    /**
     * 要处理的京东VOP消息类型
     * 1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更
     *
     * @return
     */
    @Override
    public Integer getVopMessageType() {
        return 10;
    }

    /**
     * @param messageList
     * @return java.util.List<java.lang.String>
     * @description 消息处理
     * @author hanwei
     * @date 2021/6/2 10:26
     **/
    @Override
    public List<String> handleMessage(List<VopMessageResponse> messageList) {
        List<String> successList = new ArrayList<>();
        messageList.forEach(message -> {
            try {
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Ten).content("订单取消-VOP响应信息-".concat(JSON.toJSONString(message))).build());
                }
                JSONObject jsonObject = JSON.parseObject(message.getResult());
                String jdOrderId = jsonObject.getString("orderId");
                //买家支付后才下京东订单，暂不处理取消订单消息
                ThirdPlatformTradeVO thirdPlatformTradeVO =
                        thirdPlatformTradeQueryProvider.queryByThirdPlatformOrderId(
                                ThirdPlatformTradeByThirdPlatformOrderIdRequest.builder().thirdPlatformOrderId(jdOrderId)
                                        .thirdPlatformType(ThirdPlatformType.VOP).build()).getContext().getThirdPlatformTradeVO();
                if (Objects.nonNull(thirdPlatformTradeVO)) {
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Ten).majorId(jdOrderId).content("订单取消-".concat(JSON.toJSONString(thirdPlatformTradeVO))).build());
                    }
                    log.error("京东订单被取消，京东订单ID：{}，第三方订单信息：{}", jdOrderId, JSON.toJSONString(thirdPlatformTradeVO));
                    if (!FlowState.VOID.equals(thirdPlatformTradeVO.getTradeState().getFlowState())) {
                        if (PayState.NOT_PAID.equals(thirdPlatformTradeVO.getTradeState().getPayState())) {
                            // 执行取消订单
                            tradeProvider.cancel(TradeCancelRequest.builder().tid(thirdPlatformTradeVO.getTradeId())
                                    .operator(Operator.builder().platform(Platform.PLATFORM).userId(thirdPlatformTradeVO.getBuyer().getId()).build())
                                    .build());
                            successList.add(message.getId());
                        } else if(PayState.PAID.equals(thirdPlatformTradeVO.getTradeState().getPayState())) {
                            //自动退款
                            ReturnOrderAutoReturnByProviderTradeIdRequest returnRequest = new ReturnOrderAutoReturnByProviderTradeIdRequest();
                            returnRequest.setProviderOrderId(thirdPlatformTradeVO.getParentId());
                            returnRequest.setDescription(RefundReasonConstants.Q_ORDER_SERVICE_THIRD_AUTO_MIDWAY_RETURN);
                            returnOrderProvider.autoReturnByProviderOrderId(returnRequest);
                            successList.add(message.getId());
                        }
                        // 未作废&已支付 记录日志？
                    } else {
//                        successList.add(message.getId());
                    }
                } else {
                    log.info("尝试根据子订单自动退款");
                    ThirdPlatformReturnOrderAutoApplySubRequest subRequest = ThirdPlatformReturnOrderAutoApplySubRequest.builder()
                            .thirdPlatformSubTradeId(jdOrderId)
                            .description(RefundReasonConstants.Q_ORDER_SERVICE_THIRD_AUTO_MIDWAY_RETURN).build();
                    thirdPlatformReturnOrderProvider.autoApplyBySub(subRequest);

                    log.error("订单不存在，第三方订单id：{}", jdOrderId);
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Ten).majorId(jdOrderId).content("订单取消-订单不存在").build());
                    }
                    XxlJobHelper.log("订单不存在，第三方订单id：{}", jdOrderId);
                }
            } catch (Exception e) {
                if (e instanceof SbcRuntimeException) {
                    if(OrderErrorCodeEnum.K050080.getCode().equals(((SbcRuntimeException) e).getErrorCode())) {
                        successList.add(message.getId());
                    }
                }
                log.error("处理京东推送信息[10:订单取消]出现异常，消息内容为：{}", JSON.toJSONString(message), e);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Ten).content("订单取消-异常".concat(JSON.toJSONString(message))).build());
                }
                XxlJobHelper.log("处理京东推送信息[10:订单取消]出现异常，消息内容为：{}", JSON.toJSONString(message));
                XxlJobHelper.log(e);
            }
        });
        return successList;
    }
}