package com.wanmi.sbc.vas.linkedmall.order;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PayState;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelOrderProvider;
import com.wanmi.sbc.empower.api.provider.channel.linkedmall.order.LinkedMallOrderProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderPayRequest;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallOrderListQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallRenderOrderRequest;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallOrderListQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallRenderOrderResponse;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelGoodsBuyNumDTO;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelTradeDTO;
import com.wanmi.sbc.empower.bean.vo.channel.linkedmall.LinkedMallSkuVO;
import com.wanmi.sbc.setting.bean.dto.ThirdAddress;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderCompensateResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderSplitResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderVerifyResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelOrderDTO;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelOrderItemDTO;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelOrderStateDTO;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelOrderItemVO;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelOrderPriceVO;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelOrderVO;
import com.wanmi.sbc.vas.channel.order.ChannelOrderService;
import com.wanmi.sbc.vas.linkedmall.address.LinkedMallAddressService;
import com.wanmi.sbc.vas.linkedmall.goods.LinkedMallGoodsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description LinkedMall订单服务
 * @author hanwei
 * @date 2021/5/27
 */
@Service
@Slf4j
public class LinkedMallOrderServiceImpl implements ChannelOrderService {

    @Autowired private GeneratorService generatorService;

    @Autowired private LinkedMallAddressService linkedMallAddressService;

    @Autowired private LinkedMallGoodsServiceImpl linkedMallGoodsService;

    @Autowired private ChannelOrderProvider channelOrderProvider;

    @Autowired private LinkedMallOrderProvider linkedMallOrderProvider;

    /**
     * @description 订单验证
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param trades 商品列表
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelOrderVerifyResponse
     */
    @Override
    public ChannelOrderVerifyResponse verifyOrder(List<ChannelOrderDTO> trades) {
        ChannelOrderVerifyResponse response = new ChannelOrderVerifyResponse();
        List<ChannelOrderItemDTO> items =
                trades.stream()
                        .filter(t -> CollectionUtils.isNotEmpty(t.getTradeItems()))
                        .flatMap(t -> t.getTradeItems().stream())
                        .collect(Collectors.toList());
        List<ChannelOrderItemDTO> gifts =
                trades.stream()
                        .filter(t -> CollectionUtils.isNotEmpty(t.getGifts()))
                        .flatMap(t -> t.getGifts().stream())
                        .collect(Collectors.toList());
        List<ChannelOrderItemDTO> preferentialList =
                trades.stream()
                        .filter(t -> CollectionUtils.isNotEmpty(t.getPreferential()))
                        .flatMap(t -> t.getPreferential().stream())
                        .collect(Collectors.toList());
        // 压缩商品， 主要赠品和商品可能存在同一种商品
        List<ChannelGoodsInfoVO> skuList = this.zipItem(items, gifts, preferentialList, ThirdPlatformType.LINKED_MALL);
        if (CollectionUtils.isEmpty(skuList)) {
            return response;
        }
        // 提取第三方地址
        PlatformAddress platformAddress = this.getThirdAddressByTrade(trades.get(0));
        ChannelGoodsVerifyResponse getResponse =
                linkedMallGoodsService.verifyGoods(skuList, platformAddress);
        response.setOffAddedSkuId(getResponse.getOffAddedSkuId());
        response.setInvalidGoods(getResponse.getInvalidGoods());
        response.setNoAuthGoods(getResponse.getNoAuthGoods());
        response.setNoOutStockGoods(getResponse.getNoOutStockGoods());
        return response;
    }

    /**
     * @description 拆分订单
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param trade 供应商订单信息 是供应商订单providerOrder
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelOrderSplitResponse
     */
    @Override
    public ChannelOrderSplitResponse splitOrder(ChannelOrderDTO trade) {
        ChannelOrderSplitResponse response = new ChannelOrderSplitResponse();
        // 不是LinkedMall的订单不处理
        if (!ThirdPlatformType.LINKED_MALL.equals(trade.getThirdPlatformType())) {
            return response;
        }
        // 提取第三方地址
        PlatformAddress platformAddress = this.getThirdAddressByTrade(trade);
        ThirdAddress thirdAddress =
                linkedMallAddressService.convertPlatformToThird(platformAddress);

        LinkedMallRenderOrderRequest request = new LinkedMallRenderOrderRequest();
        // 获取区县编号
        String divisionCode = thirdAddress.getStreetId();
        if (StringUtils.isBlank(divisionCode)) {
            divisionCode = thirdAddress.getAreaId();
        }
        if (StringUtils.isBlank(divisionCode)) {
            divisionCode = thirdAddress.getCityId();
        }
        if (StringUtils.isBlank(divisionCode)) {
            divisionCode = thirdAddress.getProvinceId();
        }
        request.setDivisionCode(divisionCode);
        request.setBizUid(trade.getBuyer().getId());
        request.setAddressDetail(trade.getConsignee().getDetailAddress());
        request.setMobile(trade.getConsignee().getPhone());
        request.setFullName(trade.getConsignee().getName());
        List<ChannelGoodsInfoVO> zipItems = this.zipItem(trade.getTradeItems(), trade.getGifts(),
         trade.getPreferential(), ThirdPlatformType.LINKED_MALL);
        request.setLmGoodsItems(
                zipItems.stream()
                        .map(
                                channelOrderItemDTO ->
                                        ChannelGoodsBuyNumDTO.builder()
                                                .thirdSkuId(
                                                        channelOrderItemDTO.getThirdPlatformSkuId())
                                                .thirdSpuId(
                                                        channelOrderItemDTO.getThirdPlatformSpuId())
                                                .buyNum(
                                                        channelOrderItemDTO
                                                                .getBuyCount()
                                                                .intValue())
                                                .build())
                        .collect(Collectors.toList()));
        LinkedMallRenderOrderResponse renderOrderResponse =
                linkedMallOrderProvider.renderOrder(request).getContext();
        List<ChannelOrderVO> channelTradeList =
                renderOrderResponse.getRenderOrderInfos().stream()
                        .map(
                                renderOrder -> {
                                    ChannelOrderVO channelOrderVO =
                                            KsBeanUtil.convert(trade, ChannelOrderVO.class);
                                    // 用渠道订单parentId作为供应商订单的父id
                                    channelOrderVO.setParentId(trade.getId());
                                    channelOrderVO.setTradeId(trade.getParentId()); // 主订单号
                                    channelOrderVO.setId(
                                            generatorService.generateProviderThirdTid());
                                    // 筛选当前供应商的订单商品信息
                                    Map<String, LinkedMallSkuVO> itemMap =
                                            renderOrder.getLmItemInfos().stream()
                                                    .collect(
                                                            Collectors.toMap(
                                                                    i ->
                                                                            String.valueOf(
                                                                                    i.getSkuId()),
                                                                    i -> i));
                                    List<ChannelOrderItemDTO> providerTradeItems =
                                            trade.getTradeItems().stream()
                                                    .filter(
                                                            i ->
                                                                    itemMap.containsKey(
                                                                            i
                                                                                    .getThirdPlatformSkuId()))
                                                    .collect(Collectors.toList());

                                    // 筛选当前LM店铺的赠品信息
                                    List<ChannelOrderItemDTO> providerTradeGifts =
                                            new ArrayList<>();
                                    if (CollectionUtils.isNotEmpty(trade.getGifts())) {
                                        providerTradeGifts.addAll(
                                                trade.getGifts().stream()
                                                        .filter(
                                                                i ->
                                                                        itemMap.containsKey(
                                                                                i
                                                                                        .getThirdPlatformSkuId()))
                                                        .collect(Collectors.toList()));
                                    }

                                    // 筛选当前LM店铺的加价购商品
                                    List<ChannelOrderItemDTO> providerTradePreferentialList =
                                            new ArrayList<>();
                                    if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                                        providerTradePreferentialList.addAll(
                                                trade.getPreferential().stream()
                                                        .filter(
                                                                i ->
                                                                        itemMap.containsKey(
                                                                                i
                                                                                        .getThirdPlatformSkuId()))
                                                        .collect(Collectors.toList()));
                                    }

                                    // 拆单后，重新计算价格信息
                                    ChannelOrderPriceVO tradePrice = channelOrderVO.getTradePrice();
                                    // 商品总价
                                    BigDecimal goodsPrice = BigDecimal.ZERO;

                                    Long goodsPoint = 0L;

                                    // 订单总价:实付金额
                                    BigDecimal orderPrice = BigDecimal.ZERO;
                                    // 订单供货价总额
                                    BigDecimal orderSupplyPrice = BigDecimal.ZERO;
                                    for (ChannelOrderItemDTO item : providerTradeItems) {
                                        // 商品总价
                                        goodsPrice =
                                                goodsPrice.add(
                                                        Objects.isNull(item.getPrice())
                                                                ? BigDecimal.ZERO
                                                                : item.getPrice()
                                                                        .multiply(
                                                                                BigDecimal.valueOf(
                                                                                        item
                                                                                                .getNum())));
                                        goodsPoint +=
                                                (Objects.isNull(item.getPoints())
                                                                ? 0
                                                                : item.getPoints())
                                                        * item.getNum();
                                        // 商品分摊价格
                                        BigDecimal splitPrice =
                                                Objects.isNull(item.getSplitPrice())
                                                        ? BigDecimal.ZERO
                                                        : item.getSplitPrice();
                                        orderPrice = orderPrice.add(splitPrice);
                                        // 订单供货价总额
                                        orderSupplyPrice =
                                                orderSupplyPrice.add(item.getTotalSupplyPrice());
                                    }

                                    for (ChannelOrderItemDTO item : providerTradePreferentialList) {
                                        // 商品总价
                                        goodsPrice =
                                                goodsPrice.add(
                                                        Objects.isNull(item.getPrice())
                                                                ? BigDecimal.ZERO
                                                                : item.getPrice()
                                                                .multiply(
                                                                        BigDecimal.valueOf(
                                                                                item
                                                                                        .getNum())));
                                        goodsPoint +=
                                                (Objects.isNull(item.getPoints())
                                                        ? 0
                                                        : item.getPoints())
                                                        * item.getNum();
                                        // 商品分摊价格
                                        BigDecimal splitPrice =
                                                Objects.isNull(item.getSplitPrice())
                                                        ? BigDecimal.ZERO
                                                        : item.getSplitPrice();
                                        orderPrice = orderPrice.add(splitPrice);
                                        // 订单供货价总额
                                        orderSupplyPrice =
                                                orderSupplyPrice.add(item.getTotalSupplyPrice());
                                    }

                                    for (ChannelOrderItemDTO item : providerTradeGifts) {
                                        // 订单供货价总额
                                        orderSupplyPrice =
                                                orderSupplyPrice.add(item.getTotalSupplyPrice());
                                    }

                                    channelOrderVO.setThirdSellerName(
                                            itemMap.get(
                                                            providerTradeItems
                                                                    .get(0)
                                                                    .getThirdPlatformSkuId())
                                                    .getSellerNick());
                                    channelOrderVO.setThirdSellerId(
                                            String.valueOf(
                                                    itemMap.get(
                                                                    providerTradeItems
                                                                            .get(0)
                                                                            .getThirdPlatformSkuId())
                                                            .getSellerId()));
                                    channelOrderVO.setThirdDivisionCode(request.getDivisionCode());

                                    channelOrderVO.setTradeItems(
                                            KsBeanUtil.convert(
                                                    providerTradeItems, ChannelOrderItemVO.class));
                                    channelOrderVO.setGifts(
                                            KsBeanUtil.convert(
                                                    providerTradeGifts, ChannelOrderItemVO.class));
                                    channelOrderVO.setPreferential(
                                            KsBeanUtil.convert(
                                                    providerTradePreferentialList, ChannelOrderItemVO.class));
                                    // 商品总价
                                    tradePrice.setGoodsPrice(goodsPrice);
                                    tradePrice.setOriginPrice(goodsPrice);
                                    if (goodsPoint > 0) {
                                        tradePrice.setPoints(goodsPoint);
                                    }
                                    // 订单总价
                                    tradePrice.setTotalPrice(orderPrice);
                                    tradePrice.setTotalPayCash(orderPrice);
                                    // 订单供货价总额
                                    tradePrice.setOrderSupplyPrice(orderSupplyPrice);
                                    channelOrderVO.setTradePrice(tradePrice);
                                    return channelOrderVO;
                                })
                        .collect(Collectors.toList());
        response.setChannelTradeList(channelTradeList);
        return response;
    }

    @Override
    public BigDecimal queryFreight(List<ChannelGoodsInfoDTO> channelGoodsInfoDTOList, PlatformAddress address) {
        return BigDecimal.ZERO;
    }

    /**
     * @description 补偿订单
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param trades 第三方平台订单列表
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelOrderCompensateResponse
     */
    @Override
    public ChannelOrderCompensateResponse compensateOrder(List<ChannelOrderDTO> trades) {
        ChannelOrderCompensateResponse compensateResponse = new ChannelOrderCompensateResponse();
        List<ChannelOrderDTO> thirdTrades =
                trades.stream()
                        .filter(
                                t ->
                                        ThirdPlatformType.LINKED_MALL.equals(
                                                        t.getThirdPlatformType())
                                                && CollectionUtils.isNotEmpty(
                                                        t.getThirdPlatformOrderIds()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(thirdTrades)) {
            return compensateResponse;
        }

        List<String> tradeIds =
                thirdTrades.stream().map(ChannelOrderDTO::getParentId).collect(Collectors.toList());
        List<String> ids =
                thirdTrades.stream()
                        .flatMap(t -> t.getThirdPlatformOrderIds().stream())
                        .distinct()
                        .collect(Collectors.toList());
        String bizUid = thirdTrades.get(0).getBuyer().getId();

        LinkedMallOrderListQueryRequest queryRequest = new LinkedMallOrderListQueryRequest();
        queryRequest.setPageNum(1L);
        queryRequest.setPageSize(20L);
        queryRequest.setLmOrderList(ids);
        queryRequest.setBizUid(bizUid);
        queryRequest.setAllFlag(Boolean.TRUE);
        queryRequest.setEnableStatus(-1);
        LinkedMallOrderListQueryResponse response = null;

        try {
            response = linkedMallOrderProvider.queryOrderDetail(queryRequest).getContext();
        } catch (Exception e) { // 内网网络问题
            compensateResponse.getUnconfirmedThirdTradeId().addAll(ids);
            compensateResponse.getUnconfirmedProviderTradeIds().addAll(tradeIds);
            log.error("第三方子订单，查询异常! 第三方订单id={}", JSON.toJSONString(ids), e);
            return compensateResponse;
        }

        if (response == null || CollectionUtils.isEmpty(response.getLmOrderList())) {
            compensateResponse.getUnconfirmedThirdTradeId().addAll(ids);
            compensateResponse.getUnconfirmedProviderTradeIds().addAll(tradeIds);
            log.error("订单ids：{}，第三方平台订单不存在!", ids);
            return compensateResponse;
        }

        List<LinkedMallOrderListQueryResponse.LmOrderListItem> items = response.getLmOrderList();

        Map<String, LinkedMallOrderListQueryResponse.LmOrderListItem> itemMap =
                items.stream()
                        .collect(
                                Collectors.toMap(
                                        i -> Objects.toString(i.getLmOrderId()),
                                        Function.identity()));

        for (ChannelOrderDTO thirdTrade : thirdTrades) {
            if (thirdTrade.getTradeState() == null) {
                thirdTrade.setTradeState(new ChannelOrderStateDTO());
            }
            String tradeId = thirdTrade.getTradeId();
            // 未付款或待确认
            if (PayState.UNCONFIRMED.equals(thirdTrade.getTradeState().getPayState())
                    || PayState.NOT_PAID.equals(thirdTrade.getTradeState().getPayState())) {

                LinkedMallOrderListQueryResponse.LmOrderListItem item =
                        itemMap.get(thirdTrade.getThirdPlatformOrderIds().get(0));
                if (item != null) {
                    Integer orderStatus = item.getOrderStatus();
                    // 支付成功
                    if (Integer.valueOf(2).equals(orderStatus)
                            || Integer.valueOf(6).equals(orderStatus)) {
                        thirdTrade.getTradeState().setPayState(PayState.PAID);
                        compensateResponse.getPaySuccessThirdTradeId().add(thirdTrade.getId());
                    } else if (Integer.valueOf(12).equals(orderStatus)) {
                        // 待支付
                        ChannelOrderPayRequest payRequest = new ChannelOrderPayRequest();
                        payRequest.setChannelTrade(
                                KsBeanUtil.convert(thirdTrade, ChannelTradeDTO.class));
                        try {
                            channelOrderProvider.pay(payRequest);
                            compensateResponse.getPaySuccessThirdTradeId().add(thirdTrade.getId());
                        } catch (SbcRuntimeException e) {
                            // 业务性问题
                            if (CommonErrorCodeEnum.K999999.getCode().equals(e.getErrorCode())) {
                                this.errorTrade(thirdTrade, compensateResponse);
                            } else { // 外网网络性问题
                                this.unconfirmedTrade(thirdTrade, compensateResponse);
                            }
                            log.error(
                                    "linkedMall子订单id={}，linkedMall订单支付异常! tradeId={}，error=",
                                    thirdTrade.getId(),
                                    tradeId,
                                    e);
                        } catch (Exception e) { // 内网网络问题
                            this.unconfirmedTrade(thirdTrade, compensateResponse);
                            log.error(
                                    "linkedMall子订单id={}，linkedMall订单支付其他异常! tradeId={}，error=",
                                    thirdTrade.getId(),
                                    tradeId,
                                    e);
                        }
                    } else {
                        this.errorTrade(thirdTrade, compensateResponse);
                        log.error(
                                "linkedMall平台订单id：{}，linkedMall订单状态不符合，标记支付失败! --> tradeId：{}",
                                thirdTrade.getId(),
                                tradeId);
                    }
                } else {
                    this.errorTrade(thirdTrade, compensateResponse);
                    log.error(
                            "linkedMall平台订单id：{}，linkedMall订单不存在，标记支付失败! --> tradeId：{}",
                            thirdTrade.getId(),
                            tradeId);
                }
            }
        }
        return compensateResponse;
    }
}
