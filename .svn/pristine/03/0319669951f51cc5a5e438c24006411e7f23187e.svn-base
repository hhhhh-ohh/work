package com.wanmi.sbc.job.jdvop.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.channel.vop.order.VopOrderProvider;
import com.wanmi.sbc.empower.api.request.channel.vop.VopOrderRePayRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.order.VopQueryOrderDetailRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.order.VopQueryOrderDetailResponse;
import com.wanmi.sbc.message.api.provider.vopmessage.VopLogProvider;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import com.wanmi.sbc.message.bean.enums.VopLogType;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeByThirdPlatformOrderIdRequest;
import com.wanmi.sbc.order.bean.vo.ThirdPlatformTradeVO;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanwei
 * @className JdVopPayFailMessageHandler
 * @description 处理京东VOP订单支付失败的消息
 * @date 2021/6/2 11:04
 **/
@Slf4j
@Component
public class JdVopPayFailMessageHandler implements JdVopMessageHandler {

    private static final String ORDER_DETAIL_QUERY_EXTS = "jdOrderState";

    private static final Integer ORDER_STATE_EFFECTIVE = 1;

    private static final Integer SUBMIT_STATE_CONFIRMED = 1;

    @Autowired
    private VopOrderProvider vopOrderProvider;
    @Autowired
    private ThirdPlatformTradeQueryProvider thirdPlatformTradeQueryProvider;
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
        return 14;
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
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Fourteen).content(("支付失败消息-VOP" +
                            "响应信息-").concat(JSON.toJSONString(message))).build());
                }
                JSONObject jsonObject = JSON.parseObject(message.getResult());
                String jdOrderId = jsonObject.getString("orderId");

                // 获取第三方订单信息
                ThirdPlatformTradeVO thirdPlatformTradeVO =
                        thirdPlatformTradeQueryProvider.queryByThirdPlatformOrderId(
                                ThirdPlatformTradeByThirdPlatformOrderIdRequest.builder().thirdPlatformOrderId(jdOrderId)
                                        .thirdPlatformType(ThirdPlatformType.VOP).build()).getContext().getThirdPlatformTradeVO();
                if(thirdPlatformTradeVO == null){
                    log.error("订单不存在，第三方订单id：{}", jdOrderId);
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Fourteen).majorId(jdOrderId).content("支付失败消息-订单不存在").build());
                    }
                    XxlJobHelper.log("订单不存在，第三方订单id：{}", jdOrderId);
                    return;
                }else{
                    log.info("订单存在，订单id：{}，第三方订单id：{}", thirdPlatformTradeVO.getParentId(), jdOrderId);
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Fourteen).majorId(jdOrderId).content("支付失败消息-订单存在".concat(JSON.toJSONString(thirdPlatformTradeVO))).build());
                    }
                    XxlJobHelper.log("订单存在，订单id：{}，第三方订单id：{}", thirdPlatformTradeVO.getParentId(), jdOrderId);
                }

                // 获取订单详情
                VopQueryOrderDetailResponse vopQueryOrderDetailResponse = vopOrderProvider.queryOrderDetail(
                                VopQueryOrderDetailRequest.builder()
                                        .jdOrderId(Long.valueOf(jdOrderId))
                                        .queryExts(ORDER_DETAIL_QUERY_EXTS)
                                        .build())
                        .getContext();
                if (ORDER_STATE_EFFECTIVE.equals(vopQueryOrderDetailResponse.getOrderState())
                        && SUBMIT_STATE_CONFIRMED.equals(vopQueryOrderDetailResponse.getSubmitState())) {
                    if((vopQueryOrderDetailResponse.getJdOrderState() >= Constants.FIVE)){
                        successList.add(message.getId());
                    }else {
                        // 调用重新支付接口
                        BaseResponse rePayResponse = vopOrderProvider.rePay(VopOrderRePayRequest.builder().jdOrderId(jdOrderId).build());
                        log.info("调用重新支付接口日志：第三方订单id:{},响应结果：{}", jdOrderId, JSON.toJSONString(rePayResponse));
                        if (VOP_LOG_FLAG){
                            vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Fourteen).majorId(jdOrderId).content("支付失败消息-调用重新支付接口日志".concat(JSON.toJSONString(rePayResponse))).build());
                        }
                    }
                }else{
                    log.warn("处理京东推送消息[14:支付失败]，对应的订单状态异常：{}", JSON.toJSONString(vopQueryOrderDetailResponse));
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Fourteen).majorId(jdOrderId).content("支付失败消息-支付失败".concat(JSON.toJSONString(vopQueryOrderDetailResponse))).build());
                    }
                    XxlJobHelper.log("处理京东推送消息[14:支付失败]，对应的订单状态异常：{}", JSON.toJSONString(vopQueryOrderDetailResponse));
                }
            } catch (Exception e) {
                log.error("处理京东推送消息[14:支付失败]出现异常，消息内容为：{}", JSON.toJSONString(message), e);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Fourteen).content("支付失败消息-支付失败".concat(JSON.toJSONString(message))).build());
                }
                XxlJobHelper.log("处理京东推送消息[14:支付失败]出现异常，消息内容为：{}", JSON.toJSONString(message));
                XxlJobHelper.log(e);
            }
        });
        return successList;
    }
}