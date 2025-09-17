package com.wanmi.sbc.order.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeliverStatus;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.request.quicklogin.ThirdLoginRelationByCustomerRequest;
import com.wanmi.sbc.customer.api.response.quicklogin.ThirdLoginRelationResponse;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.trade.model.entity.TradeDeliver;
import com.wanmi.sbc.order.trade.model.mapper.TradeMapper;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.repository.TradeRepository;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformOrderProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformAddOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformDeliverySendRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformOrderRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformOrderResponse;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformDeliveryGoodsVO;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformDeliveryVO;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformTradeVO;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className SellPlatformOrderService
 * @description 代销平台订单处理
 * @date 2022/4/19 18:24
 **/
@Service
public class SellPlatformTradeService {

    @Autowired private SellPlatformOrderProvider sellPlatformOrderProvider;

    @Autowired private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;

    @Autowired private TradeRepository tradeRepository;

    @Autowired private TradeMapper tradeMapper;

    @Autowired private OrderProducerService orderProducerService;

    /**
     * @description
     * @author  wur
     * @date: 2022/4/20 17:19
     * @param trade
     * @return
     **/
    public void addOrder(Trade trade) {
        //查询用户关联的微信openId
        BaseResponse<ThirdLoginRelationResponse> baseResponse = thirdLoginRelationQueryProvider
                .listThirdLoginRelationByCustomer(
                ThirdLoginRelationByCustomerRequest.builder()
                        .customerId(trade.getBuyer().getId())
                        .thirdLoginType(ThirdLoginType.WECHAT).build());
        if ( Objects.isNull(baseResponse.getContext()) || Objects.isNull(baseResponse.getContext().getThirdLoginRelation())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050154);
        }
        String thirdLoginOpenId = baseResponse.getContext().getThirdLoginRelation().getThirdLoginOpenId();

        //处理订单支付超时时间
        if (Objects.isNull(trade.getOrderTimeOut())) {
            trade.setOrderTimeOut(LocalDateTime.now().plusHours(1));
            orderProducerService.cancelOrder(
                    trade.getId(), 1 * 60 * 60 * 1000L);
        }
        //调用下单接口
        TradeVO tradeVO = tradeMapper.tradeToTradeVo(trade);
        SellPlatformAddOrderRequest request = new SellPlatformAddOrderRequest();
        request.setThirdOpenId(thirdLoginOpenId);
        request.setTradeVO(KsBeanUtil.convert(tradeVO, SellPlatformTradeVO.class));
        BaseResponse<SellPlatformOrderResponse> sellResponse = sellPlatformOrderProvider.addOrder(request);

        if (!sellResponse.isSuccess()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050154);
        }
        //验证支付金额是否一致
        int totalPrice = trade.getTradePrice().getTotalPrice().multiply(new BigDecimal(100)).setScale(2, RoundingMode.DOWN).intValue();
        if (Objects.nonNull(sellResponse.getContext().getFinalPrice()) && sellResponse.getContext().getFinalPrice() != totalPrice) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050154);
        }
        // 封装订单数据
        trade.getBuyer().setThirdLoginOpenId(thirdLoginOpenId);
        trade.setSellPlatformOrderId(sellResponse.getContext().getSellPlatformOrderId());
        tradeRepository.save(trade);
    }

    /**
     * @description    批量下单处理
     * @author  wur
     * @date: 2022/4/22 17:35
     * @param tradeList
     * @return
     **/
    public void batchAddOrder(List<Trade> tradeList) {
        //查询用户关联的微信openId
        BaseResponse<ThirdLoginRelationResponse> baseResponse = thirdLoginRelationQueryProvider
                .listThirdLoginRelationByCustomer(
                        ThirdLoginRelationByCustomerRequest.builder()
                                .customerId(tradeList.get(0).getBuyer().getId())
                                .thirdLoginType(ThirdLoginType.WECHAT).build());
        if ( Objects.isNull(baseResponse.getContext())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050154);
        }
        String thirdLoginOpenId = baseResponse.getContext().getThirdLoginRelation().getThirdLoginOpenId();

        // 调用下单接口
        tradeList.forEach(
                trade -> {
                    //处理订单支付超时时间
                    if (Objects.isNull(trade.getOrderTimeOut())) {
                        trade.setOrderTimeOut(LocalDateTime.now().plusHours(1));
                        orderProducerService.cancelOrder(
                                trade.getId(), 1 * 60 * 60 * 1000L);
                    }
                    TradeVO tradeVO = tradeMapper.tradeToTradeVo(trade);
                    BaseResponse<SellPlatformOrderResponse> sellResponse =
                            sellPlatformOrderProvider.addOrder(SellPlatformAddOrderRequest.builder()
                                    .tradeVO(KsBeanUtil.convert(tradeVO, SellPlatformTradeVO.class))
                                    .thirdOpenId(thirdLoginOpenId)
                                    .build());

                    if (!sellResponse.isSuccess()) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050154);
                    }
                    // 验证支付金额是否一致
                    int totalPrice =
                            trade.getTradePrice()
                                    .getTotalPrice()
                                    .multiply(new BigDecimal(100))
                                    .setScale(2, RoundingMode.DOWN)
                                    .intValue();
                    if (Objects.nonNull(sellResponse.getContext().getFinalPrice())
                            && sellResponse.getContext().getFinalPrice() != totalPrice) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050154);
                    }
                    // 封装订单数据
                    trade.getBuyer().setThirdLoginOpenId(thirdLoginOpenId);
                    trade.setSellPlatformOrderId(
                            sellResponse.getContext().getSellPlatformOrderId());
                });
        tradeRepository.saveAll(tradeList);
    }

    /**
     * @description    订单取消
     * @author  wur
     * @date: 2022/4/21 10:09
     * @param trade
     * @return
     **/
    public void cancelOrder(Trade trade) {
        //验证订单是否是视频号订单
        if ( Strings.isBlank(trade.getSellPlatformOrderId())
                || Objects.isNull(trade.getSellPlatformType())
                || SellPlatformType.NOT_SELL.toValue() == trade.getSellPlatformType().toValue()) {
            return;
        }
        SellPlatformOrderRequest request = SellPlatformOrderRequest
                .builder()
                .orderId(trade.getId())
                .sellOrderId(trade.getSellPlatformOrderId())
                .thirdOpenId(trade.getBuyer().getThirdLoginOpenId())
                .build();
        request.setSellPlatformType(trade.getSellPlatformType());
        sellPlatformOrderProvider.cancelOrder(request);
    }

    /**
     * @description   代销单发货同步
     * @author  wur
     * @date: 2022/4/22 11:41
     * @param trade
     * @param tradeDeliver
     * @return
     **/
    public void deliverySend(Trade trade, TradeDeliver tradeDeliver) {
        //验证订单是否是视频号订单
        if (Objects.isNull(trade) || Strings.isBlank(trade.getSellPlatformOrderId())
                || Objects.isNull(trade.getSellPlatformType())
                || SellPlatformType.NOT_SELL.toValue() == trade.getSellPlatformType().toValue()) {
            return;
        }
        if (CollectionUtils.isEmpty(tradeDeliver.getShippingItems())) {
            return;
        }
        SellPlatformDeliverySendRequest deliverySendRequest = new SellPlatformDeliverySendRequest();
        deliverySendRequest.setThirdOpenId(trade.getBuyer().getThirdLoginOpenId());
        deliverySendRequest.setSellOrderId(trade.getSellPlatformOrderId());
        deliverySendRequest.setOrderId(trade.getId());
        deliverySendRequest.setFinishAll(
                DeliverStatus.SHIPPED.getStatusId().equals(tradeDeliver.getStatus().getStatusId()) ? 1 : 0);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        deliverySendRequest.setDeliveryTime(dateTimeFormatter.format(tradeDeliver.getDeliverTime()));
        List<SellPlatformDeliveryVO> deliveryVOList = new ArrayList<>();
        SellPlatformDeliveryVO deliveryVO = SellPlatformDeliveryVO.builder()
                .deliveryId(tradeDeliver.getLogistics().getLogisticCompanyId())
                .waybillId(tradeDeliver.getLogistics().getLogisticNo())
                .build();
        List<SellPlatformDeliveryGoodsVO> goodsList = tradeDeliver.getShippingItems().stream().map(item->{
            return SellPlatformDeliveryGoodsVO.builder().goodsId(item.getSpuId()).goodsInfoId(item.getSkuId()).build();
        }).collect(Collectors.toList());
        deliveryVO.setGoodsList(goodsList);
        deliveryVOList.add(deliveryVO);
        deliverySendRequest.setDeliveryVOList(deliveryVOList);
        deliverySendRequest.setSellPlatformType(trade.getSellPlatformType());
        sellPlatformOrderProvider.deliverySend(deliverySendRequest);
    }

    /**
     * @description      确认收货
     * @author  wur
     * @date: 2022/4/22 13:36
     * @param trade
     * @return
     **/
    public void deliveryReceive(Trade trade) {
        //验证订单是否是视频号订单
        if (Objects.isNull(trade) || Strings.isBlank(trade.getSellPlatformOrderId())
                || Objects.isNull(trade.getSellPlatformType())
                || SellPlatformType.NOT_SELL.toValue() == trade.getSellPlatformType().toValue()) {
            return;
        }
        SellPlatformOrderRequest request = SellPlatformOrderRequest
                .builder()
                .orderId(trade.getId())
                .sellOrderId(trade.getSellPlatformOrderId())
                .thirdOpenId(trade.getBuyer().getThirdLoginOpenId())
                .build();
        request.setSellPlatformType(trade.getSellPlatformType());
        sellPlatformOrderProvider.deliveryReceive(request);
    }

}