package com.wanmi.sbc.order.trade.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.follow.request.TradeSettingRequest;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.orderperformance.service.OrderPerformanceService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.setting.api.provider.AuditProvider;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigsModifyRequest;
import com.wanmi.sbc.setting.api.response.OrderAutoReceiveConfigGetResponse;
import com.wanmi.sbc.setting.bean.dto.TradeConfigDTO;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 订单设置服务
 */
@Service
@Slf4j
public class TradeSettingService {
    @Autowired
    private AuditProvider auditProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private OrderProducerService orderProducerService;

    @Autowired
    private OrderPerformanceService orderPerformanceService;


    /**
     * 查询订单设置配置
     *
     * @return List<Config>
     */
    public List<ConfigVO> queryTradeConfigs() {
        return auditQueryProvider.listTradeConfig().getContext().getConfigVOList();
    }

    /**
     * 修改订单配置
     *
     * @param tradeSettingRequests tradeSettingRequests
     */
    @Transactional
    public void updateTradeConfigs(List<TradeSettingRequest> tradeSettingRequests) {
        if (CollectionUtils.isEmpty(tradeSettingRequests)) {
            log.warn("tradeSettingRequests params is empty, can't modify");
            return;
        }

        List<TradeConfigDTO> tradeConfigDTOS = KsBeanUtil.convertList(tradeSettingRequests, TradeConfigDTO.class);

        TradeConfigsModifyRequest request = new TradeConfigsModifyRequest();
        request.setTradeConfigDTOList(tradeConfigDTOS);

        auditProvider.modifyTradeConfigs(request);
    }

    /**
     * 订单代发货自动收货
     */
//    @GlobalTransactional
//    @Transactional
    public void orderAutoReceive() {
        //查询符合订单
        //批量扭转状态
//        Config config = configRepository.findByConfigTypeAndDelFlag(ConfigType.ORDER_SETTING_AUTO_RECEIVE.toString(), DeleteFlag.NO);
        OrderAutoReceiveConfigGetResponse config =auditQueryProvider.getOrderAutoReceiveConfig().getContext();

        val pageSize = 1000;
        try {
            Integer day = Integer.valueOf(JSON.parseObject(config.getContext()).get("day").toString());
            LocalDateTime endDate = LocalDateTime.now().minusDays(day);

            long total = tradeService.countTradeByDate(endDate, FlowState.DELIVERED, false);

            log.info("自动确认收货分页订单数:{} " , total);
            int pageNum = 0;
            boolean loopFlag = true;
            //超过1000条批量处理
            while(loopFlag && total > 0){
                List<Trade> tradeList = tradeService.queryTradeByDate(endDate, FlowState.DELIVERED, pageNum, pageSize, false);
                if(tradeList != null && !tradeList.isEmpty()){
                    tradeList.forEach(trade -> tradeService.confirmReceive(trade.getId(), Operator.builder().platform(Platform.PLATFORM)
                            .name("system").account("system").platform(Platform.PLATFORM).build()));
                    if(tradeList.size() == pageSize){
                        pageNum++;
                        continue;
                    }
                }
                loopFlag = false;
            }

            log.info("自动确认收货成功");


        } catch (Exception ex) {
            log.error("orderAutoReceive schedule error", ex);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050039);
        }
    }

    /**
     * 自动确认收货前24小时
     */
    public void orderAutoReceiveSendMiniProgramMsg() {
        //查询符合订单
        int pageSize = 1000;
        try {
            OrderAutoReceiveConfigGetResponse config =auditQueryProvider.getOrderAutoReceiveConfig().getContext();

            Integer day = Integer.valueOf(JSON.parseObject(config.getContext()).get("day").toString());
            LocalDateTime endDate = LocalDateTime.now().minusDays(day);

            long total = tradeService.countTradeByDate(endDate, FlowState.DELIVERED, true);

            log.info("自动确认收货前24小时分页订单数:{} " , total);
            int pageNum = 0;
            boolean loopFlag = true;
            //超过1000条批量处理
            while(loopFlag && total > 0){
                List<Trade> tradeList = tradeService.queryTradeByDate(endDate, FlowState.DELIVERED, pageNum, pageSize, true);
                if(tradeList != null && !tradeList.isEmpty()){
                    tradeList.forEach(trade -> {
                        // 将消息制为已发送
                        Trade newTrade = tradeService.detail(trade.getId());
                        newTrade.setOrderAutoReceiveSendMiniProgramMsgFlag(Boolean.TRUE);
                        tradeService.updateTrade(newTrade);
                        // 自动确认收货前24小时，发送订阅消息
                        try {
                            // 订单编号
                            String tradeId = trade.getId();
                            //发货时间
                            String deliverTime = DateUtil.format(trade.getTradeState().getDeliverTime(), DateUtil.FMT_TIME_1);
                            // 自动确认收货时间
                            String autoConfirmTime = DateUtil.format(trade.getTradeState().getDeliverTime().plusDays(day), DateUtil.FMT_TIME_1);
                            orderProducerService.sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest.builder()
                                    .triggerNodeId(TriggerNodeType.AUTO_CONFIRM_RECEIPT).tradeId(tradeId)
                                    .deliverTime(deliverTime).autoConfirmTime(autoConfirmTime).customerId(trade.getBuyer().getId()).build());
                        }catch (Exception e){
                            log.error("自动确认收货前24小时，订阅消息发送失败，e={}",  e.getMessage());
                        }
                    });
                    if(tradeList.size() == pageSize){
                        pageNum++;
                        continue;
                    }
                }
                loopFlag = false;
            }

            log.info("自动确认收货前24小时");

        } catch (Exception ex) {
            log.error("orderAutoReceiveSendMiniProgramMsg schedule error", ex);
        }
    }

    /**
     * 退单自动审核
     */
    public void returnOrderAutoAudit(Integer day) {
        //查询符合订单
        //批量扭转状态
        val pageSize = 1000;
        try {
            LocalDateTime endDate = LocalDateTime.now().minusDays(day);
            int total = returnOrderService.countReturnOrderByEndDate(endDate, ReturnFlowState.INIT);
            log.info("退单自动审核分页订单数: {}" , total);
            //超过1000条批量处理
            if (total > pageSize) {
                int pageNum = calPage(total, pageSize);
                for (int i = 0; i < pageNum; i++) {
                    returnOrderService.queryReturnOrderByEndDate(endDate, i * pageSize, i + pageSize + pageSize
                            , ReturnFlowState.INIT).stream()
                            .filter(returnOrder -> Objects.isNull(returnOrder.getThirdPlatformType()))
                            .forEach(returnOrder ->{
                                try{
                                    processReturnAutoAction(ReturnFlowState.INIT, returnOrder);
                                } catch (SbcRuntimeException brt){
                                    log.error("rid:{}异常：" ,  returnOrder.getTid() , brt);
                                }
                            } );
                }
            } else {
                List<ReturnOrder> returnOrders = returnOrderService.queryReturnOrderByEndDate(endDate, 0, total, ReturnFlowState.INIT);
                returnOrders.stream()
                        .filter(returnOrder -> Objects.isNull(returnOrder.getThirdPlatformType()))
                        .forEach(returnOrder -> {
                    log.info("执行的退单号:{} " , returnOrder.getId());
                    try{
                        processReturnAutoAction(ReturnFlowState.INIT, returnOrder);
                    } catch (SbcRuntimeException brt){
                        log.error("rid：{}异常：" ,  returnOrder.getTid() , brt);
                    }
                });
            }
            log.info("退单自动审核成功");
        } catch (Exception ex) {
            log.error("returnOrderAutoAudit schedule error", ex);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050008);
        }
    }


    /**
     * 退单自动确认收货 由于es索引的问题，用mongodb 分页查询，考虑把订单，退单从es中移除
     * @param day day
     */
    public void returnOrderAutoReceive(Integer day) {
        val pageSize = 1000;
        LocalDateTime endDate = LocalDateTime.now().minusDays(day);
        int total = returnOrderService.countReturnOrderByEndDate(endDate, ReturnFlowState.DELIVERED);
        log.info("退单去人收货分页订单数:{} " , total);
        //超过1000条批量处理
        if (total > pageSize) {
            int pageNum = calPage(total, pageSize);
            for (int i = 0; i < pageNum; i++) {
                returnOrderService.queryReturnOrderByEndDate(endDate, i * pageSize, i * pageSize + pageSize, ReturnFlowState.DELIVERED)
                        .forEach(returnOrder -> {
                            log.info("执行的退单号:{} " , returnOrder.getId());
                            try{
                                processReturnAutoAction(ReturnFlowState.DELIVERED, returnOrder);
                            } catch (SbcRuntimeException brt){
                                log.error("rid:{}异常： " ,  returnOrder.getTid() , brt);
                            }
                        });
            }
        } else {
            List<ReturnOrder> returnOrders = returnOrderService.queryReturnOrderByEndDate(endDate, 0, total, ReturnFlowState.DELIVERED);

            returnOrders.forEach(returnOrder
                    -> {
                log.info("执行的退单号: {}" , returnOrder.getId());
                try{
                    processReturnAutoAction(ReturnFlowState.DELIVERED, returnOrder);
                } catch (SbcRuntimeException brt){
                    log.error("rid :{}异常：" , returnOrder.getTid() , brt);
                }
            });
        }

        log.info("退单收货审核成功");
    }


    /**
     * 退单自动处理任务
     *
     * @param returnFlowState returnFlowState
     * @param returnOrder     returnOrder
     */
    private void processReturnAutoAction(ReturnFlowState returnFlowState, ReturnOrder returnOrder) {
        if (ReturnFlowState.DELIVERED == returnFlowState) {
            returnOrderService.receive(returnOrder.getId(),
                    Operator.builder().name("system").account("system").platform(Platform.PLATFORM).build());
        } else if (ReturnFlowState.INIT == returnFlowState) {
            returnOrderService.audit(returnOrder.getId(),
                    Operator.builder().name("system").account("system").platform(Platform.PLATFORM).build(), null);
        }
    }

    /**
     * 计算页码
     *
     * @param count
     * @param size
     * @return
     */
    private int calPage(int count, int size) {
        int page = count / size;
        if (count % size == 0) {
            return page;
        } else {
            return page + 1;
        }
    }


    /**
     * 订单业绩自动结账
     */
    public void orderPerformanceAutoSettle() {

        //查询符合订单
        //批量扭转状态

        val pageSize = 1000;
        try {


            long total = tradeService.countCompletedTradeByDate();

            log.info("订单业绩自动结账分页订单数:{} " , total);
            int pageNum = 0;
            boolean loopFlag = true;
            //超过1000条批量处理
            while(loopFlag && total > 0){
                List<Trade> tradeList = tradeService.queryCompletedTradeByDate(pageNum, pageSize);
                if(tradeList != null && !tradeList.isEmpty()){
                    tradeList.forEach(trade -> orderPerformanceService.autoSettle(trade.getId(), Operator.builder().platform(Platform.PLATFORM)
                            .name("system").account("system").platform(Platform.PLATFORM).build()));

                    if(tradeList.size() == pageSize){
                        pageNum++;
                        continue;
                    }
                }
                loopFlag = false;
            }

            log.info("订单业绩自动结账成功");


        } catch (Exception ex) {
            log.error("orderPerformanceAutoSettle schedule error", ex);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050039);
        }

    }
}
