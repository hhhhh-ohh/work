package com.wanmi.sbc.order.thirdplatformtrade.service;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelOrderProvider;
import com.wanmi.sbc.empower.api.provider.channel.linkedmall.order.LinkedMallOrderProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderConfirmReceivedRequest;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallLogisticsQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallOrderListQueryRequest;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallLogisticsQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallOrderListQueryResponse;
import com.wanmi.sbc.order.bean.dto.ThirdPlatformTradeUpdateStateDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.ShipperType;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.orderinvoice.service.OrderInvoiceService;
import com.wanmi.sbc.order.thirdplatformtrade.model.entity.LinkedMallGoods;
import com.wanmi.sbc.order.thirdplatformtrade.model.entity.LinkedMallLogisticsDetail;
import com.wanmi.sbc.order.thirdplatformtrade.model.root.LinkedMallTradeLogistics;
import com.wanmi.sbc.order.thirdplatformtrade.model.root.ThirdPlatformTrade;
import com.wanmi.sbc.order.thirdplatformtrade.repository.ThirdPlatformTradeRepository;
import com.wanmi.sbc.order.trade.model.entity.value.TradeEventLog;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.TradeService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 第三方渠道-linkedMall订单处理服务层
 * @Description: 第三方渠道-linkedMall订单处理服务层
 * @Autho qiaokang
 * @Date：2020-02-11 22:56
 */
@Service
@Slf4j
public class LinkedMallTradeService {

    @Autowired
    private ThirdPlatformTradeRepository thirdPlatformTradeRepository;

    @Autowired
    private LinkedMallOrderProvider linkedMallOrderProvider;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private ProviderTradeService providerTradeService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private LinkedMallTradeLogisticsService linkedMallTradeLogisticsService;

    @Autowired private ChannelOrderProvider channelOrderProvider;

    @Autowired private ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired private OrderInvoiceService orderInvoiceService;

    /**
     * 确认收货
     * @param tradeId
     * @param userId
     */
    @GlobalTransactional
    @Transactional
    public void confirmDisburse(String tradeId, String userId) {
        List<ThirdPlatformTrade> tradeList = thirdPlatformTradeRepository.findListByTradeId(tradeId);
        tradeList.stream()
                .filter(t -> CollectionUtils.isNotEmpty(t.getThirdPlatformOrderIds())
                        && ThirdPlatformType.LINKED_MALL.equals(t.getThirdPlatformType()))
                .forEach(t -> {
                    t.getThirdPlatformOrderIds().forEach(id -> {
                        ChannelOrderConfirmReceivedRequest request = new ChannelOrderConfirmReceivedRequest();
                        request.setChannelOrderId(id);
                        request.setUserId(userId);
                        request.setThirdPlatformType(ThirdPlatformType.LINKED_MALL);
                        channelOrderProvider.confirmReceived(request);
                    });
                    t.getTradeState().setFlowState(FlowState.COMPLETED);
                    thirdPlatformTradeRepository.save(t);
                });
    }


    // 填充linkedmall订单中商品对应的子单号
    @Transactional
    public void fillSubLmOrderId(List<TradeVO> tradeVOS, List<LinkedMallOrderListQueryResponse.LmOrderListItem> lmOrderListItems) {
        if(CollectionUtils.isEmpty(tradeVOS) || CollectionUtils.isEmpty(lmOrderListItems)) {
            return;
        }

        // 查询linkedmall订单详情
        Map<Long, LinkedMallOrderListQueryResponse.LmOrderListItem> lmOrderMap = lmOrderListItems.stream()
                .collect(Collectors.toMap(LinkedMallOrderListQueryResponse.LmOrderListItem::getLmOrderId, Function.identity()));

        if(MapUtils.isEmpty(lmOrderMap)) {
            return;
        }

        List<ThirdPlatformTrade> thirdPlatformTrades = KsBeanUtil.convert(tradeVOS,ThirdPlatformTrade.class);

        // 遍历linkedmall订单，填充linkedmall商品对应子单号
        thirdPlatformTrades.stream()
                .filter(t ->
                        ThirdPlatformType.LINKED_MALL.equals(t.getThirdPlatformType())
                                && CollectionUtils.isNotEmpty(t.getThirdPlatformOrderIds()))
                .forEach(trade -> {
                    AtomicBoolean flag = new AtomicBoolean(false);
                    String lmOrderId = trade.getThirdPlatformOrderIds().get(0);
                    if(CollectionUtils.isNotEmpty(trade.getTradeItems())) {
                        trade.getTradeItems().stream().filter(t -> Objects.isNull(t.getThirdPlatformSubOrderId())).forEach(t -> {
                            lmOrderMap.get(NumberUtils.toLong(lmOrderId)).getSubOrderList().stream().filter(Objects::nonNull)
                                    .filter(s -> t.getThirdPlatformSkuId().equals(String.valueOf(s.getSkuId()))
                                            && t.getThirdPlatformSpuId().equals(String.valueOf(s.getItemId())))
                                    .forEach(s -> {
                                        t.setThirdPlatformSubOrderId(String.valueOf(s.getLmOrderId()));
                                        flag.set(true);
                                    });
                        });
                    }
                    if(CollectionUtils.isNotEmpty(trade.getGifts())) {
                        trade.getGifts().stream().filter(t -> Objects.isNull(t.getThirdPlatformSubOrderId())).forEach(t -> {
                            lmOrderMap.get(NumberUtils.toLong(lmOrderId)).getSubOrderList().stream().filter(Objects::nonNull)
                                    .filter(s -> t.getThirdPlatformSkuId().equals(String.valueOf(s.getSkuId()))
                                            && t.getThirdPlatformSpuId().equals(String.valueOf(s.getItemId())))
                                    .forEach(s -> {
                                        t.setThirdPlatformSubOrderId(String.valueOf(s.getLmOrderId()));
                                        flag.set(true);
                                    });
                        });
                    }
                    if(CollectionUtils.isNotEmpty(trade.getPreferential())) {
                        trade.getPreferential().stream().filter(t -> Objects.isNull(t.getThirdPlatformSubOrderId())).forEach(t -> {
                            lmOrderMap.get(NumberUtils.toLong(lmOrderId)).getSubOrderList().stream().filter(Objects::nonNull)
                                    .filter(s -> t.getThirdPlatformSkuId().equals(String.valueOf(s.getSkuId()))
                                            && t.getThirdPlatformSpuId().equals(String.valueOf(s.getItemId())))
                                    .forEach(s -> {
                                        t.setThirdPlatformSubOrderId(String.valueOf(s.getLmOrderId()));
                                        flag.set(true);
                                    });
                        });
                    }
                    if(flag.get()) {
                        thirdPlatformTradeRepository.save(trade);
                    }
        });
    }

    /**
     * 更新订单状态,同时更新父订单、主订单状态
     *
     * @param tradeUpdateStateDTO
     */
    @Transactional
    public void updateThirdPlatformTradeState(ThirdPlatformTradeUpdateStateDTO tradeUpdateStateDTO) {
        // 更新后的状态
        FlowState newFlowState = tradeUpdateStateDTO.getFlowState();
        DeliverStatus newDeliverStatus = tradeUpdateStateDTO.getDeliverStatus();
        PayState newPayState = tradeUpdateStateDTO.getPayState();
        // 获取数据库订单详情
        ThirdPlatformTrade thirdPlatformTrade = thirdPlatformTradeRepository.findFirstById(tradeUpdateStateDTO.getId());
        // 当前数据库中订单状态
        FlowState oldFlowState= thirdPlatformTrade.getTradeState().getFlowState();
        DeliverStatus oldDeliverStatus = thirdPlatformTrade.getTradeState().getDeliverStatus();
        PayState oldPatState = thirdPlatformTrade.getTradeState().getPayState();
        if(FlowState.VOID == oldFlowState || FlowState.COMPLETED == oldFlowState) {
            return;
        }
        // 拼装日志详情,更新状态
        StringBuilder eventDetail = new StringBuilder("同步linkedmall订单").append(tradeUpdateStateDTO.getId()).append("状态");
        if(Objects.nonNull(newFlowState) && !(newFlowState == oldFlowState)) {
            eventDetail.append(",订单状态从【").append(oldFlowState.getDescription())
                    .append("】扭转为【").append(newFlowState.getDescription()).append('】');
            thirdPlatformTrade.getTradeState().setFlowState(newFlowState);
        }
        if(Objects.nonNull(newDeliverStatus) && !(newDeliverStatus == oldDeliverStatus)) {
            eventDetail.append(",发货状态从【").append(oldDeliverStatus.getDescription())
                    .append("】扭转为【").append(newDeliverStatus.getDescription()).append('】');
            thirdPlatformTrade.getTradeState().setDeliverStatus(newDeliverStatus);
        }
        if(PayState.UNCONFIRMED == oldPatState && Objects.nonNull(newPayState) && !(newPayState == oldPatState)) {
            eventDetail.append(",支付状态从【").append(oldPatState.getDescription())
                    .append("】扭转为【").append(newPayState.getDescription()).append('】');
            thirdPlatformTrade.getTradeState().setPayState(newPayState);
        }

        // 1、更新三级订单状态
        Operator system = Operator.builder().name("system").account("system").platform(Platform.PLATFORM).build();
        TradeEventLog tradeEventLog = TradeEventLog
                .builder()
                .operator(system)
                .eventType("同步linkedmall订单状态")
                .eventDetail(eventDetail.toString())
                .eventTime(LocalDateTime.now())
                .build();
        thirdPlatformTrade.appendTradeEventLog(tradeEventLog);
        thirdPlatformTradeRepository.save(thirdPlatformTrade);

        String parentId = tradeUpdateStateDTO.getParentId();
        String tradeId = tradeUpdateStateDTO.getTradeId();

        // 2、获取父订单所有子订单
        ProviderTrade providerTrade = providerTradeService.providerDetail(parentId);
        if(FlowState.VOID == providerTrade.getTradeState().getFlowState() ||
                FlowState.COMPLETED == providerTrade.getTradeState().getFlowState()) {
            return;
        }
        List<ThirdPlatformTrade> thirdPlatformTrades = thirdPlatformTradeRepository.findListByParentId(parentId);
        // 防止事务未提交，查出来的数据未更新
        thirdPlatformTrades.forEach(trade -> {
            if (trade.getId().equals(tradeUpdateStateDTO.getId())) {
                if(Objects.nonNull(newFlowState)) {
                    trade.getTradeState().setFlowState(newFlowState);
                }
                if(Objects.nonNull(newDeliverStatus)) {
                    trade.getTradeState().setDeliverStatus(newDeliverStatus);
                }
            }
        });
        TradeVO tradeVO = KsBeanUtil.convert(providerTrade, TradeVO.class);
        boolean updateProviderFlag = changeParentTradeState(tradeVO, KsBeanUtil.convert(thirdPlatformTrades,
                TradeVO.class),tradeEventLog);
        //3、更新二级父订单状态
        if(updateProviderFlag) {
            providerTrade = KsBeanUtil.convert(tradeVO, ProviderTrade.class);
            providerTrade.appendTradeEventLog(tradeEventLog);
            providerTradeService.updateProviderTrade(providerTrade);

            //4、获取一级主订单所有子订单
            Trade trade = tradeService.detail(tradeId);
            if(FlowState.VOID == trade.getTradeState().getFlowState() ||
                    FlowState.COMPLETED == trade.getTradeState().getFlowState()) {
                return;
            }
            List<ProviderTrade> providerTrades = providerTradeService.findListByParentId(tradeId);
            // 防止事务未提交，查出来的数据未更新
            providerTrades.forEach(pTrade -> {
                if (pTrade.getId().equals(parentId)) {
                    if(Objects.nonNull(newFlowState)) {
                        pTrade.getTradeState().setFlowState(newFlowState);
                    }
                    if(Objects.nonNull(newDeliverStatus)) {
                        pTrade.getTradeState().setDeliverStatus(newDeliverStatus);
                    }
                }
            });
            TradeVO convert = KsBeanUtil.convert(trade, TradeVO.class);
            boolean updateTradeFlag = changeParentTradeState(convert, KsBeanUtil.convert(providerTrades,
                    TradeVO.class),tradeEventLog);
            // 5、更新一级主订单状态
            if(updateTradeFlag) {
                trade = KsBeanUtil.convert(convert, Trade.class);
                trade.appendTradeEventLog(tradeEventLog);
                tradeService.updateTrade(trade);
                // 同步订单状态至ES的订单开票索引
                orderInvoiceService.syncStateToInvoice(trade);
            }
        }
    }

    // 根据子订单状态判断是否更新父订单状态,子订单状态一致时更改父订单状态
    private boolean changeParentTradeState(TradeVO parentTrade, List<TradeVO> sonTrades,TradeEventLog tradeEventLog) {
        boolean updateFlag = false;
        // 1、获取父订单状态
        FlowState pFlowState = parentTrade.getTradeState().getFlowState();
        DeliverStatus pDeliverStatus = parentTrade.getTradeState().getDeliverStatus();
        PayState pPayState = parentTrade.getTradeState().getPayState();
        if(CollectionUtils.isNotEmpty(sonTrades)) {
            // 获取所有子订单去重后的 订单状态集合
            List<FlowState> flowStateList =
                    sonTrades.stream().map(v -> v.getTradeState().getFlowState()).distinct().collect(Collectors.toList());
            // 获取所有子订单去重后的 配送状态集合
            List<DeliverStatus> deliverStatusList =
                    sonTrades.stream().map(v -> v.getTradeState().getDeliverStatus()).distinct().collect(Collectors.toList());
            // 拼装日志详情,更新状态
            StringBuilder eventDetail = new StringBuilder("同步linkedmall订单").append(parentTrade.getId()).append("状态");
            // 所有子订单状态一致且与父订单状态不一致时，更改父订单状态
            if(CollectionUtils.isNotEmpty(flowStateList) && flowStateList.size() == 1 && !(flowStateList.get(0) == pFlowState)) {
                eventDetail.append(",订单状态从【").append(pFlowState.getDescription())
                        .append("】扭转为【").append(flowStateList.get(0).getDescription()).append('】');
                parentTrade.getTradeState().setFlowState(flowStateList.get(0));
                updateFlag = true;
            }
            if(CollectionUtils.isNotEmpty(deliverStatusList) && deliverStatusList.size() == 1 && !(deliverStatusList.get(0) == pDeliverStatus)) {
                eventDetail.append(",发货状态从【").append(pDeliverStatus.getDescription())
                        .append("】扭转为【").append(deliverStatusList.get(0).getDescription()).append('】');
                parentTrade.getTradeState().setDeliverStatus(deliverStatusList.get(0));
                updateFlag = true;
            }
            // 如果支付状态为未确认，
            if(PayState.UNCONFIRMED == pPayState && ThirdPlatformType.LINKED_MALL == parentTrade.getThirdPlatformType()) {
                // 获取所有子订单去重后的 支付状态集合
                List<PayState> payStateList =
                        sonTrades.stream().map(v -> v.getTradeState().getPayState()).distinct().collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(payStateList) && payStateList.size() == 1 && !(payStateList.get(0) == pPayState)) {
                    eventDetail.append(",支付状态从【").append(pPayState.getDescription())
                            .append("】扭转为【").append(payStateList.get(0).getDescription()).append('】');
                    parentTrade.getTradeState().setPayState(payStateList.get(0));
                    updateFlag = true;
                }
            }
            tradeEventLog.setEventDetail(eventDetail.toString());
        }
        return updateFlag;
    }

    /**
     * 填充并保存linkedmall订单配送信息
     * @param tradeVO 订单详细信息
     * @return
     */
    @Transactional
    public TradeVO fillLinkedMallTradeDelivers(TradeVO tradeVO) {
        if(Objects.isNull(tradeVO)) {
            return null;
        }
        // 是否积分订单
        boolean isPointsTrade = OrderType.POINTS_ORDER.equals(tradeVO.getOrderType());
        if((!isPointsTrade && CollectionUtils.isEmpty(tradeVO.getTradeVOList())) ||
                (isPointsTrade && CollectionUtils.isEmpty(tradeVO.getPointsTradeVOList()))) {
            return tradeVO;
        }

        List<TradeVO> tradeVOList = new ArrayList<>();
        if(!isPointsTrade && CollectionUtils.isNotEmpty(tradeVO.getTradeVOList())) {
            tradeVOList = tradeVO.getTradeVOList();
        }

        if(isPointsTrade && CollectionUtils.isNotEmpty(tradeVO.getPointsTradeVOList())) {
            tradeVOList = KsBeanUtil.convert(tradeVO.getPointsTradeVOList(), TradeVO.class);
        }

        if(CollectionUtils.isNotEmpty(tradeVOList)) {
            tradeVOList = tradeVOList.stream().map(providerTrade -> {
                if(Objects.isNull(providerTrade.getThirdPlatformType())
                        || !(ThirdPlatformType.LINKED_MALL.equals(providerTrade.getThirdPlatformType())
                        || ThirdPlatformType.VOP.equals(providerTrade.getThirdPlatformType()))){
                    return providerTrade;
                }
                List<ThirdPlatformTrade> thirdPlatformTrades = thirdPlatformTradeRepository.findListByParentId(providerTrade.getId());
                List<TradeVO> tradeVOS = KsBeanUtil.convert(thirdPlatformTrades, TradeVO.class);
                // 是linkedmall订单
                if(ThirdPlatformType.LINKED_MALL.equals(providerTrade.getThirdPlatformType())) {
                    try {
                        // linkedmall订单状态同步
                        this.thirdPlatformTradeStateSync(tradeVOS,providerTrade);
                        TradeVO trade = KsBeanUtil.convert(tradeService.detail(providerTrade.getParentId()), TradeVO.class);
                        // 填充linkedmall订单物流信息
                        this.fillLinkedMallTradeDelivers(tradeVOS,providerTrade,trade);
                    } catch (Exception e) {
                        log.error("查询订单详情接口，更新linkedmall订单:{},订单状态及发货清单出现异常",providerTrade.getId(),e);
                    }
                }
                // 是linkedmall、京东订单,填充linkedmall、京东子单信息
                //tradeVOS = KsBeanUtil.convert(thirdPlatformTradeRepository.findListByParentId(providerTrade.getId()), TradeVO.class);
                //providerTrade = KsBeanUtil.convert(providerTradeService.providerDetail(providerTrade.getId()), TradeVO.class);
                if(ThirdPlatformType.VOP.equals(providerTrade.getThirdPlatformType())){
                    tradeVOS = KsBeanUtil.convert(thirdPlatformTradeService.detailSplit(thirdPlatformTrades), TradeVO.class);
                }
                if(isPointsTrade) {
                    providerTrade.setPointsTradeVOList(KsBeanUtil.convert(tradeVOS,PointsTradeVO.class));
                } else {
                    providerTrade.setTradeVOList(tradeVOS);
                }
                return providerTrade;
            }).collect(Collectors.toList());
        }

        TradeVO trade = KsBeanUtil.convert(tradeService.detail(tradeVO.getId()), TradeVO.class);

        if(isPointsTrade) {
            trade.setPointsTradeVOList(KsBeanUtil.convert(tradeVOList, PointsTradeVO.class));
        } else {
            trade.setTradeVOList(tradeVOList);
        }

        return trade;
    }

    // linkedmall订单状态同步
    @Transactional
    public void thirdPlatformTradeStateSync(List<TradeVO> tradeVOList, TradeVO providerTrade) {
        if(CollectionUtils.isEmpty(tradeVOList)) {
            return;
        }

        // 过滤需要查询linkedmall详情的订单
        List<TradeVO> tradeVOS = tradeVOList.stream()
                .filter(t -> ThirdPlatformType.LINKED_MALL.equals(t.getThirdPlatformType()) &&
                        CollectionUtils.isNotEmpty(t.getThirdPlatformOrderIds()) &&
                        !(FlowState.VOID == t.getTradeState().getFlowState()) &&
                        !(FlowState.COMPLETED ==t.getTradeState().getFlowState()) &&
                        !(PayState.NOT_PAID == t.getTradeState().getPayState()))
                .collect(Collectors.toList());

        List<String> lmOrderIds =
                tradeVOS.stream().map(t -> t.getThirdPlatformOrderIds().get(0)).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(tradeVOS) || CollectionUtils.isEmpty(lmOrderIds)) {
            return;
        }

        Map<String, TradeVO> map =
                tradeVOS.stream().collect(Collectors.toMap(t -> t.getThirdPlatformOrderIds().get(0), Function.identity()));

        List<ThirdPlatformTradeUpdateStateDTO> tradeUpdateStates = new ArrayList<>();
        // 查询linkedmall订单详情
        LinkedMallOrderListQueryResponse response =
                linkedMallOrderProvider.queryOrderDetail(LinkedMallOrderListQueryRequest.builder()
                        .lmOrderList(lmOrderIds).bizUid(providerTrade.getBuyer().getId()).allFlag(Boolean.TRUE).build()).getContext();
        if (Objects.nonNull(response) && CollectionUtils.isNotEmpty(response.getLmOrderList())) {
            // 填充linkedmall订单商品对应子单号
            this.fillSubLmOrderId(tradeVOList,response.getLmOrderList());
            response.getLmOrderList().forEach(lmOrderListItem -> {
                // linkedMall 订单状态
                Integer orderStatus = lmOrderListItem.getOrderStatus();
                // linkedMall 物流状态
                Integer logisticsStatus = lmOrderListItem.getLogisticsStatus();
                Long lmOrderId = lmOrderListItem.getLmOrderId();
                if(map.containsKey(Objects.toString(lmOrderId))) {
                    this.fillTradeStatusByLinkedMallOrderStatus(tradeUpdateStates, map.get(Objects.toString(lmOrderId)), orderStatus, logisticsStatus);
                }
            });
        }

        // 更新linkedmall订单状态
        tradeUpdateStates.forEach(this::updateThirdPlatformTradeState);

    }

    // 根据linkedmall订单状态 填充 商城订单状态
    private void fillTradeStatusByLinkedMallOrderStatus(List<ThirdPlatformTradeUpdateStateDTO> tradeUpdateStates, TradeVO tradeVO, Integer orderStatus,
                                                        Integer logisticsStatus) {
        /*
        "orderStatus":"12=待支付，2=已支付，4=已退款关闭，6=交易成功，8=被淘宝关闭 ",
        "logisticsStatus":" 1=未发货 -> 等待卖家发货 2=已发货 -> 等待买家确认收货 3=已收货 -> 交易成功 4=已经退货 -> 交易失败 5=部分收货 -> 交易成功 6=部分发货中
        8=还未创建物流订单",
         */
        FlowState flowState = null;
        DeliverStatus deliverStatus = null;

        if (Integer.valueOf(6).equals(orderStatus) || Integer.valueOf(3).equals(logisticsStatus)) {
            flowState = FlowState.COMPLETED;
        } else if (Integer.valueOf(4).equals(orderStatus)) {
            flowState = FlowState.VOID;
        } else if (Integer.valueOf(8).equals(orderStatus)) {
            flowState = FlowState.VOID;
        } else if (Integer.valueOf(12).equals(orderStatus)) {
            flowState = FlowState.AUDIT;
        } else if (Integer.valueOf(2).equals(orderStatus) && (Integer.valueOf(2).equals(logisticsStatus) ||
                Integer.valueOf(5).equals(logisticsStatus) || Integer.valueOf(6).equals(logisticsStatus))) {
            flowState = FlowState.DELIVERED;
        } else if (Integer.valueOf(2).equals(orderStatus) &&
                (Integer.valueOf(1).equals(logisticsStatus) || Integer.valueOf(8).equals(logisticsStatus))) {
            flowState = FlowState.AUDIT;
        } else if (Integer.valueOf(4).equals(logisticsStatus)) {
            flowState = FlowState.VOID;
        }

        if (Integer.valueOf(1).equals(logisticsStatus) ||
                (Integer.valueOf(8).equals(logisticsStatus) && Integer.valueOf(12).equals(orderStatus))) {
            deliverStatus = DeliverStatus.NOT_YET_SHIPPED;
        } else if (Integer.valueOf(2).equals(logisticsStatus) || Integer.valueOf(3).equals(logisticsStatus)) {
            deliverStatus = DeliverStatus.SHIPPED;
        } else if (Integer.valueOf(5).equals(logisticsStatus) || Integer.valueOf(6).equals(logisticsStatus)) {
            deliverStatus = DeliverStatus.PART_SHIPPED;
        } else if (Integer.valueOf(4).equals(logisticsStatus) || Integer.valueOf(4).equals(orderStatus) ||
                (Integer.valueOf(8).equals(logisticsStatus) && Integer.valueOf(8).equals(orderStatus))) {
            deliverStatus = DeliverStatus.VOID;
        }

        boolean updateFlag = false;
        if (Objects.nonNull(flowState) && !(flowState == tradeVO.getTradeState().getFlowState())) {
            tradeVO.getTradeState().setFlowState(flowState);
            updateFlag = true;
        }
        if (Objects.nonNull(deliverStatus) && !(deliverStatus == tradeVO.getTradeState().getDeliverStatus())) {
            tradeVO.getTradeState().setDeliverStatus(deliverStatus);
            updateFlag = true;
        }
        if(PayState.UNCONFIRMED == tradeVO.getTradeState().getPayState() && Integer.valueOf(2).equals(orderStatus)) {
            tradeVO.getTradeState().setPayState(PayState.PAID);
            updateFlag = true;
        }
        if(PayState.UNCONFIRMED == tradeVO.getTradeState().getPayState() && Integer.valueOf(12).equals(orderStatus)) {
            tradeVO.getTradeState().setPayState(PayState.NOT_PAID);
            updateFlag = true;
        }
        if (updateFlag) {
            ThirdPlatformTradeUpdateStateDTO tradeUpdateStateDTO = new ThirdPlatformTradeUpdateStateDTO();
            tradeUpdateStateDTO.setId(tradeVO.getId());
            tradeUpdateStateDTO.setParentId(tradeVO.getParentId());
            tradeUpdateStateDTO.setTradeId(tradeVO.getTradeId());
            tradeUpdateStateDTO.setFlowState(tradeVO.getTradeState().getFlowState());
            tradeUpdateStateDTO.setDeliverStatus(tradeVO.getTradeState().getDeliverStatus());
            tradeUpdateStateDTO.setPayState(tradeVO.getTradeState().getPayState());
            tradeUpdateStates.add(tradeUpdateStateDTO);
        }
    }

    // 填充并保存linkedmall订单配送信息
    @Transactional
    public void fillLinkedMallTradeDelivers(List<TradeVO> tradeVOS, TradeVO providerTrade, TradeVO trade) {
        if(CollectionUtils.isEmpty(tradeVOS)) {
            return;
        }
        // providerTrade下所有linkedmall二次拆单的订单 对应的 发货清单集合
        List<TradeDeliverVO> tradeDeliverVOs = new ArrayList<>();
        Map<String,TradeItemVO> tradeItemList = new HashMap<>();
        Map<String,TradeItemVO> giftList = new HashMap<>();
        Map<Long,Map<String,TradeItemVO>> preferentialMap = new HashMap<>();

        Operator system = Operator.builder().name("system").account("system").platform(Platform.PLATFORM).build();
        TradeEventLog tradeEventLog = TradeEventLog
                .builder()
                .operator(system)
                .eventType("同步linkedmall发货清单")
                .eventTime(LocalDateTime.now())
                .build();

        tradeVOS.forEach(tradeVO -> {
            if(Objects.nonNull(tradeVO.getThirdPlatformType()) && ThirdPlatformType.LINKED_MALL.equals(tradeVO.getThirdPlatformType())
                    && Objects.nonNull(tradeVO.getBuyer()) && StringUtils.isNotBlank(tradeVO.getBuyer().getId())
                    && CollectionUtils.isNotEmpty(tradeVO.getThirdPlatformOrderIds())
                    && PayState.PAID == tradeVO.getTradeState().getPayState() &&
                    (DeliverStatus.SHIPPED == tradeVO.getTradeState().getDeliverStatus() ||
                            DeliverStatus.PART_SHIPPED == tradeVO.getTradeState().getDeliverStatus())) {

                // 当前第三方订单 商品、赠品全部发货完毕，不再同步linkedmall发货信息
                long count = Stream.concat(Stream.concat(tradeVO.getTradeItems().stream(),
                                tradeVO.getGifts().stream()), tradeVO.getPreferential().stream())
                        .filter(tradeItemVO -> tradeItemVO.getDeliveredNum() < tradeItemVO.getNum()).count();
                if(count == 0) {
                    return;
                }

                String customerId = tradeVO.getBuyer().getId();
                List<String> lmOrderIds = tradeVO.getThirdPlatformOrderIds();

                LinkedMallLogisticsQueryResponse response = linkedMallOrderProvider.getOrderLogistics(LinkedMallLogisticsQueryRequest.builder()
                        .lmOrderId(NumberUtils.toLong(lmOrderIds.get(0)))
                        .bizUid(customerId)
                        .build()).getContext();

                if(Objects.isNull(response) || CollectionUtils.isEmpty(response.getDataItems())) {
                    return;
                }

                // 获取当前订单所有物流单号集合
                List<String> logisticNos = new ArrayList<>();
                if(CollectionUtils.isNotEmpty(tradeVO.getTradeDelivers())) {
                    tradeVO.getTradeDelivers().stream().filter(Objects::nonNull).forEach(v -> {
                        if(Objects.nonNull(v.getLogistics()) && StringUtils.isNotBlank(v.getLogistics().getLogisticNo())) {
                            logisticNos.add(v.getLogistics().getLogisticNo());
                        }
                    });
                }

                // 该笔linkedmall二次拆单的订单 对应的 发货清单集合
                List<TradeDeliverVO> tradeDeliverVOList = new ArrayList<>();

                response.getDataItems().stream()
                        .filter(dataItem -> Objects.nonNull(dataItem) && StringUtils.isNotBlank(dataItem.getMailNo()))
                        .forEach(dataItem -> {

                            // 物流信息存档入库
                            this.saveLinkedMallLogictics(dataItem, tradeVO.getId(), lmOrderIds.get(0), customerId);

                            // 商城订单物流信息中 没有 linkedmall 物流单号 则新增到该订单中
                            if (CollectionUtils.isEmpty(logisticNos) || !logisticNos.contains(dataItem.getMailNo())) {

                                // 同步配送商品信息
                                List<ShippingItemVO> shippingItems = new ArrayList<>();
                                tradeVO.getTradeItems().stream()
                                        .filter(tradeItemVO -> ThirdPlatformType.LINKED_MALL.equals(tradeItemVO.getThirdPlatformType()))
                                        .forEach(tradeItemVO -> {
                                            ShippingItemVO shippingItemVO = KsBeanUtil.convert(tradeItemVO, ShippingItemVO.class);
                                            shippingItemVO.setItemName(tradeItemVO.getSkuName());
                                            shippingItemVO.setItemNum(tradeItemVO.getNum());
                                            shippingItems.add(shippingItemVO);
                                            // 同步已发货数量、发货状态
                                            tradeItemVO.setDeliveredNum(tradeItemVO.getNum());
                                            tradeItemVO.setDeliverStatus(DeliverStatus.SHIPPED);
                                            tradeItemList.put(tradeItemVO.getSkuId(),tradeItemVO);
                                        });

                                // 同步配送赠品信息
                                List<ShippingItemVO> giftItems = new ArrayList<>();
                                tradeVO.getGifts().forEach(tradeItemVO -> {
                                    ShippingItemVO shippingItemVO = KsBeanUtil.convert(tradeItemVO, ShippingItemVO.class);
                                    shippingItemVO.setItemName(tradeItemVO.getSkuName());
                                    shippingItemVO.setItemNum(tradeItemVO.getNum());
                                    giftItems.add(shippingItemVO);
                                    // 同步已发货数量、发货状态
                                    tradeItemVO.setDeliveredNum(tradeItemVO.getNum());
                                    tradeItemVO.setDeliverStatus(DeliverStatus.SHIPPED);
                                    giftList.put(tradeItemVO.getSkuId(),tradeItemVO);
                                });

                                List<ShippingItemVO> preferentialItems = new ArrayList<>();
                                tradeVO.getPreferential().forEach(tradeItemVO -> {
                                    ShippingItemVO shippingItemVO = KsBeanUtil.convert(tradeItemVO, ShippingItemVO.class);
                                    shippingItemVO.setItemName(tradeItemVO.getSkuName());
                                    shippingItemVO.setItemNum(tradeItemVO.getNum());
                                    preferentialItems.add(shippingItemVO);
                                    // 同步已发货数量、发货状态
                                    tradeItemVO.setDeliveredNum(tradeItemVO.getNum());
                                    tradeItemVO.setDeliverStatus(DeliverStatus.SHIPPED);
                                    Map<String,TradeItemVO> map =
                                            preferentialMap.get(tradeItemVO.getMarketingIds().get(0));
                                    if (MapUtils.isNotEmpty(map)){
                                        map.put(tradeItemVO.getSkuId(),tradeItemVO);
                                    } else {
                                        Map<String,TradeItemVO> mapItem = new HashMap<>();
                                        mapItem.put(tradeItemVO.getSkuId(),tradeItemVO);
                                        preferentialMap.put(tradeItemVO.getMarketingIds().get(0), mapItem);
                                    }
                                });


                                // 同步发货时间,大于两条记录时取菜鸟裹裹的倒数第二条记录的时间作为发货时间
                                LocalDateTime deliverTime = null;
                                if (CollectionUtils.isNotEmpty(dataItem.getLogisticsDetailList())) {
                                    String ocurrTimeStr =
                                            dataItem.getLogisticsDetailList().get(dataItem.getLogisticsDetailList().size() - 1).getOcurrTimeStr();
                                    if (dataItem.getLogisticsDetailList().size() > 1) {
                                        ocurrTimeStr = dataItem.getLogisticsDetailList().get(dataItem.getLogisticsDetailList().size() - 2).getOcurrTimeStr();
                                    }
                                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    deliverTime = LocalDateTime.parse(ocurrTimeStr, df);
                                }

                                // 物流信息
                                LogisticsVO logistics = LogisticsVO.builder()
                                        .logisticCompanyName(dataItem.getLogisticsCompanyName())
                                        .logisticNo(dataItem.getMailNo())
                                        .logisticStandardCode(dataItem.getLogisticsCompanyCode())
                                        // 设置linkedmall物流的订单号、用户id
                                        .thirdPlatformOrderId(lmOrderIds.get(0))
                                        .buyerId(customerId)
                                        .build();

                                if(CollectionUtils.isNotEmpty(tradeVO.getOutOrderIds())) {
                                    // 设置linkedmall物流的淘宝订单号
                                    logistics.setOutOrderId(tradeVO.getOutOrderIds().get(0));
                                }

                                // 生成新的发货清单
                                TradeDeliverVO tradeDeliverVO = TradeDeliverVO.builder()
                                        .tradeId(tradeVO.getId())
                                        .deliverId(generatorService.generate("TD"))
                                        .deliverTime(deliverTime)
                                        .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                                        .shippingItems(shippingItems)
                                        .giftItemList(giftItems)
                                        .preferentialItemList(preferentialItems)
                                        .logistics(logistics)
                                        .shipperType(ShipperType.PROVIDER)
                                        .status(DeliverStatus.SHIPPED)
                                        .providerName(tradeVO.getSupplier().getSupplierName())
                                        .build();

                                tradeDeliverVOList.add(tradeDeliverVO);
                            }
                        });
                // 填充并更新 linkedmall订单信息
                if(CollectionUtils.isNotEmpty(tradeDeliverVOList)) {
                    tradeVO.getTradeDelivers().addAll(tradeDeliverVOList);
                    tradeDeliverVOs.addAll(tradeDeliverVOList);
                    ThirdPlatformTrade thirdPlatformTrade = KsBeanUtil.convert(tradeVO, ThirdPlatformTrade.class);
                    // 添加日志
                    tradeEventLog.setEventDetail(String.format("同步linkedmall订单%s发货清单", thirdPlatformTrade.getId()));
                    thirdPlatformTrade.appendTradeEventLog(tradeEventLog);
                    thirdPlatformTradeRepository.save(thirdPlatformTrade);
                }
            }
        });

        // 更新父订单、主订单配送信息
        if(CollectionUtils.isNotEmpty(tradeDeliverVOs)) {
            // 同步provideTrade商品/赠品对应发货数和发货状态,发货清单
            List<TradeDeliverVO> providerDelivers = this.syncTradeDelivers(providerTrade, tradeDeliverVOs,
                    tradeItemList,giftList, preferentialMap);

            providerTrade.getTradeDelivers().addAll(providerDelivers);
            ProviderTrade providerTrade1 = KsBeanUtil.convert(providerTrade, ProviderTrade.class);
            // 添加日志
            tradeEventLog.setEventDetail(String.format("同步linkedmall订单%s发货清单", providerTrade1.getId()));
            providerTrade1.appendTradeEventLog(tradeEventLog);
            providerTradeService.updateProviderTrade(providerTrade1);

            // 同步Trade商品对应发货数和发货状态,发货清单
            List<TradeDeliverVO> tradeDelivers = this.syncTradeDelivers(trade, providerDelivers, tradeItemList,
                    giftList, preferentialMap);
            trade.getTradeDelivers().addAll(tradeDelivers);

            Trade trade1 = KsBeanUtil.convert(trade, Trade.class);
            // 添加日志
            tradeEventLog.setEventDetail(String.format("同步linkedmall订单%s发货清单", trade1.getId()));
            trade1.appendTradeEventLog(tradeEventLog);
            tradeService.updateTrade(trade1);
        }
    }

    // 同步发货清单
    private List<TradeDeliverVO> syncTradeDelivers(TradeVO trade, List<TradeDeliverVO> tradeDeliverVOs,
                                                   Map<String, TradeItemVO> tradeItemList,
                                                   Map<String, TradeItemVO> giftList,
                                                   Map<Long,Map<String,TradeItemVO>> preferentialMap) {

        List<TradeDeliverVO> providerDelivers = tradeDeliverVOs.stream().map(tradeDeliverVO -> {
            TradeDeliverVO deliverVO = KsBeanUtil.convert(tradeDeliverVO, TradeDeliverVO.class);
            deliverVO.setTradeId(trade.getId());
            deliverVO.setSunDeliverId(tradeDeliverVO.getDeliverId());
            deliverVO.setDeliverId(generatorService.generate("TD"));
            deliverVO.setShipperType(ShipperType.PROVIDER);
            return deliverVO;
        }).collect(Collectors.toList());

        // 同步商品对应发货数和发货状态
        trade.getTradeItems().stream()
                .filter(tradeItemVO -> tradeItemList.containsKey(tradeItemVO.getSkuId()))
                .forEach(tradeItemVO -> {
                    TradeItemVO vo = tradeItemList.get(tradeItemVO.getSkuId());
                    tradeItemVO.setDeliveredNum(tradeItemVO.getDeliveredNum() + vo.getDeliveredNum());
                    tradeItemVO.setDeliverStatus(DeliverStatus.SHIPPED);
                });
        // 同步赠品对应发货数和发货状态
        trade.getGifts().stream()
                .filter(tradeItemVO -> giftList.containsKey(tradeItemVO.getSkuId()))
                .forEach(tradeItemVO -> {
            TradeItemVO vo = giftList.get(tradeItemVO.getSkuId());
            tradeItemVO.setDeliveredNum(tradeItemVO.getDeliveredNum() + vo.getDeliveredNum());
            tradeItemVO.setDeliverStatus(DeliverStatus.SHIPPED);
        });

        // 加价购
        preferentialMap.forEach((marketingId, data) -> {
            trade.getPreferential().stream()
                    .filter(tradeItemVO -> marketingId.equals(tradeItemVO.getMarketingIds().get(0)) && data.containsKey(tradeItemVO.getSkuId()))
                    .forEach(tradeItemVO -> {
                        TradeItemVO vo = data.get(tradeItemVO.getSkuId());
                        tradeItemVO.setDeliveredNum(tradeItemVO.getDeliveredNum() + vo.getDeliveredNum());
                        tradeItemVO.setDeliverStatus(DeliverStatus.SHIPPED);
                    });
        });

        return providerDelivers;
    }

    // linkedmall 物流信息入库
    private void saveLinkedMallLogictics(LinkedMallLogisticsQueryResponse.DataItem dataItem, String tradeId, String lmOrderId, String customerId) {
        LinkedMallTradeLogistics logistics = new LinkedMallTradeLogistics();
        logistics.setTradeId(tradeId);
        logistics.setCustomerId(customerId);
        logistics.setLmOrderId(lmOrderId);

        logistics.setMailNo(dataItem.getMailNo());
        logistics.setDataProvider(dataItem.getDataProvider());
        logistics.setDataProviderTitle(dataItem.getDataProviderTitle());
        logistics.setLogisticsCompanyName(dataItem.getLogisticsCompanyName());
        logistics.setLogisticsCompanyCode(dataItem.getLogisticsCompanyCode());

        dataItem.getLogisticsDetailList().forEach(v -> {
            LinkedMallLogisticsDetail logisticsDetail = LinkedMallLogisticsDetail.builder()
                    .standerdDesc(v.getStanderdDesc())
                    .ocurrTimeStr(v.getOcurrTimeStr())
                    .build();
            logistics.getLogisticsDetailList().add(logisticsDetail);

        });

        dataItem.getGoods().forEach(v -> {
            LinkedMallGoods goods = LinkedMallGoods.builder()
                    .goodName(v.getGoodName())
                    .quantity(v.getQuantity())
                    .itemId(String.valueOf(v.getItemId()))
                    .build();
            logistics.getGoods().add(goods);
        });

        linkedMallTradeLogisticsService.updateLinkedMallLogistics(logistics);
    }

}
