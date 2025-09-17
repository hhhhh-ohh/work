package com.wanmi.sbc.order.provider.impl.thirdplatformreturn;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelRefundProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundQueryStatusRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundQueryStatusResponse;
import com.wanmi.sbc.order.api.provider.thirdplatformreturn.ThirdPlatformReturnOrderProvider;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderApplyRequest;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderSyncRequest;
import com.wanmi.sbc.order.api.request.thirdplatformreturn.ThirdPlatformReturnOrderAutoApplySubRequest;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.request.ReturnQueryRequest;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.returnorder.service.ThirdPlatformReturnOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>linkedMall退单服务接口</p>
 */
@Slf4j
@RestController
public class ThirdPlatformReturnOrderController implements ThirdPlatformReturnOrderProvider {

    @Autowired
    private ThirdPlatformReturnOrderService thirdPlatformReturnOrderService;

    @Autowired
    private ChannelRefundProvider channelRefundProvider;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Override
    public BaseResponse apply(@RequestBody @Valid ThirdPlatformReturnOrderApplyRequest request){
        thirdPlatformReturnOrderService.apply(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse syncStatus(ThirdPlatformReturnOrderSyncRequest syncRequest){
        Operator system = Operator.builder().name("system").account("system").platform(Platform.PLATFORM).build();

        //处理待审核
        ReturnQueryRequest request = new ReturnQueryRequest();
        request.setThirdPlatFormApplyFlag(Boolean.TRUE);
        request.setThirdPlatformType(syncRequest.getThirdPlatformType());
        request.setReturnFlowState(ReturnFlowState.INIT);
        request.setPageNum(0);
        request.setPageSize(20000);//一批处理20000条
        request.putSort("createTime", SortType.ASC.toValue());
        List<ReturnOrder> returnOrderList = returnOrderService.findByPage(request);
        if(CollectionUtils.isNotEmpty(returnOrderList)) {
            ChannelRefundQueryStatusRequest detailRequest = new ChannelRefundQueryStatusRequest();
            detailRequest.setThirdPlatformType(syncRequest.getThirdPlatformType());
            for (ReturnOrder returnOrder : returnOrderList) {
                if (CollectionUtils.isEmpty(returnOrder.getReturnItems())
                        || StringUtils.isBlank(returnOrder.getReturnItems().get(0).getThirdPlatformSubOrderId())) {
                    continue;
                }
                detailRequest.setBizUid(returnOrder.getId());
                detailRequest.setSubChannelOrderId(returnOrder.getReturnItems().get(0).getThirdPlatformSubOrderId());
                try {
                    ChannelRefundQueryStatusResponse detail =
                            channelRefundProvider.queryRefundStatus(detailRequest).getContext();
                    if (detail == null) {
                        log.error("退单不存在, rid={}, subLmOrderId={}", returnOrder.getId(), detailRequest.getSubChannelOrderId());
                        continue;
                    }
                    //由定时任务执行，考虑淘宝那边状态较快的情况下
                    //2: "卖家已经同意退款，等待买家退货"
                    //3: "买家已经退货，等待卖家确认收货"
                    //5: "退款成功"
                    if(Integer.valueOf(2).equals(detail.getDisputeStatus())
                        ||Integer.valueOf(3).equals(detail.getDisputeStatus())
                            || Integer.valueOf(5).equals(detail.getDisputeStatus())){
                        //审核通过
                        returnOrderService.audit(returnOrder.getId(), system, null);
                    }else if(Integer.valueOf(4).equals(detail.getDisputeStatus()) //4: "退款关闭"
                            ||Integer.valueOf(6).equals(detail.getDisputeStatus())) { //6: "卖家拒绝退款"
                        //驳回
                        String message = detail.getSellerRefuseReason();
                        if(StringUtils.isBlank(message)){
                            message = detail.getSellerRefuseAgreementMessage();
                        }
                        returnOrderService.cancel(returnOrder.getId(), system, message);
                    }
                } catch (Exception e) {
                    log.error("退单审核同步异常, rid=" + returnOrder.getId(), e);
                }
            }
        }

        //处理待商家收货
        request.setReturnFlowState(ReturnFlowState.DELIVERED);
        List<ReturnOrder> returnOrders = returnOrderService.findByPage(request);
        if(CollectionUtils.isNotEmpty(returnOrders)) {
            ChannelRefundQueryStatusRequest detailRequest = new ChannelRefundQueryStatusRequest();
            for (ReturnOrder returnOrder : returnOrders) {
                if (CollectionUtils.isEmpty(returnOrder.getReturnItems())
                        || StringUtils.isBlank(returnOrder.getReturnItems().get(0).getThirdPlatformSubOrderId())) {
                    continue;
                }
                detailRequest.setBizUid(returnOrder.getId());
                detailRequest.setSubChannelOrderId(returnOrder.getReturnItems().get(0).getThirdPlatformSubOrderId());
                try {
                    ChannelRefundQueryStatusResponse detail =
                            channelRefundProvider.queryRefundStatus(detailRequest).getContext();
                    if (detail == null) {
                        log.error("退单不存在, rid={}, subLmOrderId={}", returnOrder.getId(), detailRequest.getSubChannelOrderId());
                        continue;
                    }
                    //由定时任务执行，考虑淘宝那边状态较快的情况下
                    //5: "退款成功"
                    //11: "退款结束"
                    if (Integer.valueOf(5).equals(detail.getDisputeStatus())
                            || Integer.valueOf(11).equals(detail.getDisputeStatus())) {
                        returnOrderService.receive(returnOrder.getId(), system);
                    } else if(Integer.valueOf(4).equals(detail.getDisputeStatus())){ //4: "退款关闭"
                        String message = detail.getSellerRefuseReason();
                        if(StringUtils.isBlank(message)){
                            message = detail.getSellerRefuseAgreementMessage();
                        }
                        returnOrderService.rejectReceive(returnOrder.getId(), message, system);
                    }
                } catch (Exception e) {
                    log.error("退单确认收货同步异常, rid=" + returnOrder.getId(), e);
                }
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse autoApplyBySub(@RequestBody @Valid ThirdPlatformReturnOrderAutoApplySubRequest request) {
        thirdPlatformReturnOrderService.autoApplyBySubOrder(request);
        return BaseResponse.SUCCESSFUL();
    }
}
