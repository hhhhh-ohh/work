package com.wanmi.sbc.order.mqconsumer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.NodeType;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.enums.node.OrderProcessType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.employee.EmployeeHandoverRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.restrictedrecord.RestrictedRecordSaveProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.restrictedrecord.RestrictedRecordBatchAddRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.RestrictedRecordSimpVO;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;
import com.wanmi.sbc.order.api.request.areas.OrderListAddRequest;
import com.wanmi.sbc.order.api.request.mqconsumer.OrderMqConsumerRequest;
import com.wanmi.sbc.order.api.request.trade.TradeBackRestrictedRequest;
import com.wanmi.sbc.order.bean.dto.TradeDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.groupon.service.GrouponOrderService;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.request.ReturnQueryRequest;
import com.wanmi.sbc.order.returnorder.service.GrouponReturnOrderService;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.root.GrouponInstance;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.service.TradeService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lvzhenwei
 * @className OrderMqConsumerService
 * @description mq消费方法处理
 * @date 2021/8/13 5:22 下午
 **/
@Slf4j
@Service
public class OrderMqConsumerService {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ReturnOrderService returnOrderService;
    
    @Autowired
    private GrouponOrderService grouponOrderService;

    @Autowired
    private GrouponReturnOrderService grouponReturnOrderService;

    @Autowired
    private RestrictedRecordSaveProvider restrictedRecordSaveProvider;

    @Autowired private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired private MongoTemplate mongoTemplate;

    @Autowired
    private OrderProducerService producerService;

    @Autowired
    protected RedisUtil redisService;


    /**
     * @description 业务员交接
     * @author  lvzhenwei
     * @date 2021/8/17 9:44 上午
     * @param json
     * @return void
     **/
    public void modifyEmployeeData(String json){
        EmployeeHandoverRequest request = JSONObject.parseObject(json, EmployeeHandoverRequest.class);
        Integer pageNum = 0;
        Integer pageSize = 1000;
        while(true){
            TradeQueryRequest tradeQueryRequest = TradeQueryRequest.builder().employeeIds(request.getEmployeeIds()).build();
            tradeQueryRequest.setPageNum(pageNum);
            tradeQueryRequest.setPageSize(pageSize);
            Page<Trade> page = tradeService.page(tradeQueryRequest.getWhereCriteria(), tradeQueryRequest);
            if(page.getTotalElements() == 0){
                break;
            }
            List<Trade> tradeList = page.getContent();
            tradeList.stream().forEach(trade -> {
                tradeService.updateEmployeeId(request.getNewEmployeeId(), trade.getBuyer().getId());
            });
            log.info("业务员交接订单数量：{}" , tradeList.size());
            pageNum ++;
        }

        while(true){
            ReturnQueryRequest returnQueryRequest = new ReturnQueryRequest();
            returnQueryRequest.setEmployeeIds(request.getEmployeeIds());
            Page<ReturnOrder> page = returnOrderService.page(returnQueryRequest);
            if(page.getTotalElements() == 0){
                break;
            }
            List<ReturnOrder> returnOrders = page.getContent();
            returnOrders.stream().forEach(returnOrder -> {
                returnOrderService.updateEmployeeId(request.getNewEmployeeId(), returnOrder.getBuyer().getId());
            });
            log.info("业务员交接订单数量：{}" , returnOrders.size());
            pageNum ++;
        }
    }

    /**
     * @description 订单取消
     * @author  lvzhenwei
     * @date 2021/8/17 9:42 上午
     * @param orderId
     * @return void
     **/
    public void cancelOrder(String orderId) {
        log.info("订单号：{},取消订单MQ消息，开始运行处理",orderId);
        Operator operator = Operator.builder().adminId("1").name("system").account("system").ip("127.0.0.1").platform(Platform
                .PLATFORM).build();
        tradeService.autoCancelOrder(orderId,operator);
    }

    /**
     * @description 拼团提醒
     * @author  lvzhenwei
     * @date 2021/8/17 11:23 上午
     * @param grouponNo 
     * @return void
     **/
    public void openGroupon(String grouponNo) {
        log.info("团编号：{},延迟消息开始运行处理",grouponNo);
        //根据团编号查询团长开团信息
        GrouponInstance grouponInstance = grouponOrderService.getGrouponInstanceByActivityIdAndGroupon(grouponNo);
        if (null == grouponInstance || StringUtils.isBlank(grouponInstance.getGrouponActivityId())){
            log.info("团编号:{},未查询到拼团订单数据，请检查团编号是否正确！",grouponNo);
            return;
        }
        GrouponActivityVO grouponActivityVO = grouponOrderService.getGrouponActivityById(grouponInstance.getGrouponActivityId());
        if (Objects.isNull(grouponActivityVO)){
            log.info("团编号:{},活动ID:{},未查询到拼团活动信息，请检查团编号是否正确！",grouponNo,grouponInstance.getGrouponActivityId());
            return;
        }
        log.info("团编号：{},具体的团实例信息如下:{}",grouponNo,grouponInstance);
        //已成团/团已作废不作处理
        if (grouponInstance.getGrouponStatus() == GrouponOrderStatus.COMPLETE || grouponInstance.getGrouponStatus() == GrouponOrderStatus.FAIL ){
            log.info("团编号：{},已成团或者已作废,不作任何处理！",grouponNo);
            return;
        }else{
            //团截止时间
            LocalDateTime endTime =  grouponInstance.getEndTime();
            log.info("团编号:{}，截止时间:{},当前系统时间:{}", grouponNo, endTime, LocalDateTime.now());
            //倒计时结束（已超团结束时长24小时) && 团状态未成团
            if (grouponInstance.getGrouponStatus() == GrouponOrderStatus.WAIT){
                boolean autoGroupon = grouponActivityVO.isAutoGroupon();
                final LocalDateTime currentTime = LocalDateTime.now();
                //自动成团
                if (autoGroupon){
                    //1、更新订单信息-已成团、待发货
                    grouponOrderService.autoGrouponSuccess(grouponNo,Operator.builder().adminId("1").name("system").account("system").ip("127.0.0.1").platform(Platform
                            .PLATFORM).build());
                    //3、修改团实例信息
                    grouponInstance.setCompleteTime(currentTime);
                    grouponInstance.setGrouponStatus(GrouponOrderStatus.COMPLETE);
                    grouponOrderService.updateGrouponInstance(grouponInstance);
                    grouponInstance.setGrouponNum(grouponInstance.getJoinNum());
                    //更新拼团活动-已成团、待成团、团失败人数；拼团活动商品-已成团人数
                    grouponOrderService.updateStatisticsNum(grouponInstance);
                    //同步开团成功，渠道订单
                    grouponReturnOrderService.handleGrouponOrderSyncChannel(grouponNo);
                }else{
                    //自动退款
                    grouponReturnOrderService.handleGrouponOrderRefund(grouponNo);
                }
            }
        }
    }

    /**
     * @description 拼团订单-支付成功，订单异常，自动退款
     * @author  lvzhenwei
     * @date 2021/8/17 2:19 下午
     * @param json
     * @return void
     **/
    @GlobalTransactional
    public void grouponOrderPaySuccessAutoRefund(String json) {
        try {
            log.info("订单信息：{}，拼团订单-支付成功，订单异常，自动退款,开始运行处理!",json);
            Trade trade = JSONObject.parseObject(json,Trade.class);
            grouponReturnOrderService.handleGrouponOrderRefund(trade);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("订单信息：{}，拼团订单-支付成功，订单异常，自动退款! param={}", json, e);
        }
    }

    /**
     * @description 参团人数延迟消息处理
     * @author  lvzhenwei
     * @date 2021/8/17 2:40 下午
     * @param grouponNo
     * @return void
     **/
    public void grouponNumLimit(String grouponNo){
        log.info("团编号：{},拼团人员不足开始运行处理",grouponNo);
        //根据团编号查询团长开团信息
        GrouponInstance grouponInstance = grouponOrderService.getGrouponInstanceByActivityIdAndGroupon(grouponNo);
        if (null == grouponInstance || StringUtils.isBlank(grouponInstance.getGrouponActivityId())){
            log.info("团编号:{},未查询到拼团订单数据，请检查团编号是否正确！",grouponNo);
            return;
        }
        GrouponActivityVO grouponActivityVO = grouponOrderService.getGrouponActivityById(grouponInstance.getGrouponActivityId());
        if (Objects.isNull(grouponActivityVO)){
            log.info("团编号:{},活动ID:{},未查询到拼团活动信息，请检查团编号是否正确！",grouponNo,grouponInstance.getGrouponActivityId());
            return;
        }
        log.info("团编号：{},具体的团实例信息如下:{}",grouponNo,grouponInstance);
        //已成团/团已作废不作处理
        if (grouponInstance.getGrouponStatus() == GrouponOrderStatus.COMPLETE || grouponInstance.getGrouponStatus() == GrouponOrderStatus.FAIL ){
            log.info("团编号：{},已成团或者已作废,不作任何处理！",grouponNo);
            return;
        }else{
            //团截止时间
            LocalDateTime endTime =  grouponInstance.getEndTime();
            log.info("团编号:{}，截止时间:{},当前系统时间:{}", grouponNo, endTime, LocalDateTime.now());
            //团状态未成团
            if (grouponInstance.getGrouponStatus() == GrouponOrderStatus.WAIT){
                List<Trade> tradeList = grouponOrderService.getTradeByGrouponNo(grouponNo);
                for (Trade trade : tradeList) {
                    grouponOrderService.sendNoticeMessage(NodeType.ORDER_PROGRESS_RATE, OrderProcessType.GROUP_NUM_LIMIT, trade, grouponNo, grouponActivityVO.getWaitGrouponNum());
                }
            }
        }
    }

    /**
     * @description 异常订单发送限售信息 ——（取消订单，超时未支付，退货，退款，审批未通过）
     * @author  lvzhenwei
     * @date 2021/8/17 2:48 下午
     * @param restrictedRequest
     * @return void
     **/
    public void reduceRestrictedPurchaseNum(TradeBackRestrictedRequest restrictedRequest){
        log.info("======================= 1. 退还限售库存开始 ==================");
        //直接退款和审批驳回的的流程
        if(StringUtils.isNotBlank(restrictedRequest.getTradeId())){
            Trade trade = tradeService.detail(restrictedRequest.getTradeId());
            //订单为拼团，秒杀类不做处理
            if(this.checkBackRestrictedNum(trade)){
                log.info("======================= 1.1. 秒杀和拼团的订单不做处理 ==================");
                return ;
            }
            List<RestrictedRecordSimpVO> simpVOS = KsBeanUtil.convert(trade.getTradeItems(),RestrictedRecordSimpVO.class);
            log.info("======================= 2. 退还会员：{} ///// 限售的数据 ：{} ==================", trade.getBuyer().getId(), simpVOS);
            StoreType storeType = trade.getSupplier().getStoreType();
            Long storeId = null;
            if(StoreType.O2O == storeType) {
                storeId = trade.getSupplier().getStoreId();
            }
            //获取订单的Item信息，会员的信息
            restrictedRecordSaveProvider.reduceRestrictedRecord(RestrictedRecordBatchAddRequest.builder()
                    .restrictedRecordSimpVOS(simpVOS)
                    .customerId(trade.getBuyer().getId())
                    .storeId(storeId)
                    .orderTime(trade.getTradeState().getCreateTime())
                    .build());
        }
        //退单流程
        if(StringUtils.isNotBlank(restrictedRequest.getBackOrderId())){
            ReturnOrder returnOrder = returnOrderService.findById(restrictedRequest.getBackOrderId());
            if(returnOrder == null) {return;}
            Trade trade = tradeService.detail(returnOrder.getTid());
            if(this.checkBackRestrictedNum(trade)){
                log.info("======================= 1.1. 秒杀和拼团的订单不做处理 ==================");
                return ;
            }
            List<RestrictedRecordSimpVO> restrictedRecordSimpVOS = KsBeanUtil.convert(returnOrder.getReturnItems(),RestrictedRecordSimpVO.class);
            log.info("======================= 2. 退还会员：{} ///// 限售的数据 ：{} ==================", trade.getBuyer().getId(), restrictedRecordSimpVOS);
            //获取退单的Item信息，会员的信息
            restrictedRecordSaveProvider.reduceRestrictedRecord(RestrictedRecordBatchAddRequest.builder()
                    .restrictedRecordSimpVOS(restrictedRecordSimpVOS)
                    .customerId(trade.getBuyer().getId())
                    .orderTime(trade.getTradeState().getCreateTime())
                    .build());
        }
    }

    public void dealOrderPointsIncrease(OrderMqConsumerRequest request){
        try {
            log.info("下单异步更新积分--异步开始");
            OrderListAddRequest orderListAddRequest = JSONObject.parseObject(request.getMqContentJson(), OrderListAddRequest.class);
            List<TradeDTO> tradeDTOS = orderListAddRequest.getTradeDTOS();
            for (TradeDTO trade:tradeDTOS) {
                // 遍历订单商品，获取商品类目下积分增长比例，统计积分
                int i = 0;
                List<Long> cateIds =
                        trade.getTradeItems().stream().map(TradeItemDTO::getCateId).collect(Collectors.toList());

                List<GoodsCateVO> goodsCateVOList =
                        goodsCateQueryProvider.getByIds(new GoodsCateByIdsRequest(cateIds)).getContext().getGoodsCateVOList();

                Map<Long, BigDecimal> map = Maps.newHashMap();
                goodsCateVOList.forEach(goodsCateVO -> {
                    map.put(goodsCateVO.getCateId(), goodsCateVO.getPointsRate());
                });

                Update update = new Update();
                for (TradeItemDTO item:trade.getTradeItems()){
                    // 获取商品类目下成长值增长比例
                    BigDecimal pointsRate = map.get(item.getCateId());
                    update.set("tradeItems."+(i++)+".pointsRate", pointsRate);
                }

                if(CollectionUtils.isNotEmpty(trade.getTradeItems())){
                    mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(trade.getId())),
                            update, "trade");
                    log.info("下单异步更新积分--异步成功");
                }else{
                    log.error("下单异步更新积分--异步失败，item为空");
                }

            }
        } catch (Exception e) {
            log.error("下单异步更新积分--异步失败consumer, param={},e={}", request.getMqContentJson(), e);
        }
    }

    /**
     * 拼团和抢购不需要校验
     * @param trade
     * @return
     */
    private boolean checkBackRestrictedNum(Trade trade){
        if(Objects.isNull(trade)){
            return true;
        }
        if(!Objects.isNull(trade.getGrouponFlag()) && trade.getGrouponFlag()){
            return true;
        }
        if(!Objects.isNull(trade.getIsFlashSaleGoods()) && trade.getIsFlashSaleGoods()){
            return true;
        }
        if(Objects.nonNull(trade.getIsFlashPromotionGoods()) && trade.getIsFlashPromotionGoods()){
            return true;
        }
        return false;
    }

    @Async
    public void sendReturnMiniProgramMsg(ReturnOrder returnOrder) {
        ReturnOrder returnOrderForMsg = null;
        try{
            String rid = returnOrder.getId();
            String refundSuccessKey = RedisKeyConstant.REFUND_SUCCESS.concat(rid);
            // 消息重复发送处理
            if (redisService.hasKey(refundSuccessKey)) {
                return;
            }
            redisService.setNx(refundSuccessKey, Constants.STR_1, Constants.NUM_300L);
            // 这边30秒之后再发消息，等回调结束
            Thread.sleep(Constants.NUM_30000);
            // 判断是否为退单尾款单号
            if (rid.startsWith(GeneratorService._PREFIX_RETURN_TRADE_TAIL_ID)) {
                returnOrderForMsg = returnOrderService.findByBusinessTailId(rid);
            } else {
                returnOrderForMsg = returnOrderService.findById(rid);
            }

            producerService.sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest.builder()
                    .triggerNodeId(TriggerNodeType.REFUND_SUCCESS).rid(returnOrder.getId())
                    .finishTime(DateUtil.format(returnOrderForMsg.getFinishTime(), DateUtil.FMT_TIME_1))
                    .actualReturnPrice(String.valueOf(returnOrderForMsg.getReturnPrice().getActualReturnPrice()))
                    .actualPoints(String.valueOf(returnOrderForMsg.getReturnPoints().getActualPoints()))
                    .customerId(returnOrder.getBuyer().getId()).build());
        }catch (Exception e){
            log.error("退款成功通知失败，returnOrderForMsg={}", JSON.toJSONString(returnOrderForMsg));
        }
        try{
            if(returnOrderForMsg != null) {
                producerService.sendWxPayShippingMessage(returnOrderForMsg.getTid());
            }
        }catch (Exception e){
            log.error("小程序物流发货消息失败，returnOrderForMsg={}", JSON.toJSONString(returnOrderForMsg));
        }
    }
}
