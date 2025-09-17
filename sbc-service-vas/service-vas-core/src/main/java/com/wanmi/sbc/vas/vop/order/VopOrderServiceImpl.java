package com.wanmi.sbc.vas.vop.order;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PayState;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelOrderProvider;
import com.wanmi.sbc.empower.api.provider.channel.vop.order.VopOrderProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderPayRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.VopOrderFreightQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.order.VopQueryOrderDetailRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.order.VopQueryOrderDetailResponse;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelTradeDTO;
import com.wanmi.sbc.empower.bean.dto.channel.vop.VopFreightSkuDTO;
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
import com.wanmi.sbc.vas.vop.address.VopAddressService;
import com.wanmi.sbc.vas.vop.goods.VopGoodsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description VOP订单服务
 * @author daiyitian
 * @date 2021/5/12 18:09
 */
@Service
@Slf4j
public class VopOrderServiceImpl implements ChannelOrderService {

    @Autowired private GeneratorService generatorService;

    @Autowired private VopAddressService vopAddressService;

    @Autowired private ChannelOrderProvider channelOrderProvider;

    @Autowired private VopOrderProvider vopOrderProvider;

    @Autowired private VopGoodsServiceImpl vopGoodsServiceImpl;

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
        // 压缩vop商品， 主要赠品和商品可能存在同一种商品
        List<ChannelGoodsInfoVO> skuList = this.zipItem(items, gifts, preferentialList, ThirdPlatformType.VOP);

        if (CollectionUtils.isEmpty(skuList)) {
            return response;
        }
        // 提取第三方地址
        PlatformAddress platformAddress = this.getThirdAddressByTrade(trades.get(0));
        ChannelGoodsVerifyResponse getResponse =
                vopGoodsServiceImpl.verifyGoods(skuList, platformAddress);
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
        // 不是VOP的订单不处理
        if (!ThirdPlatformType.VOP.equals(trade.getThirdPlatformType())) {
            return response;
        }
        // 提取第三方地址
        PlatformAddress platformAddress = this.getThirdAddressByTrade(trade);
        ThirdAddress thirdAddress = vopAddressService.convertPlatformToThird(platformAddress);
        ChannelOrderVO tradeVO = KsBeanUtil.convert(trade, ChannelOrderVO.class);
        // 用渠道订单parentId作为供应商订单的父id
        tradeVO.setParentId(trade.getId());
        // 主订单号
        tradeVO.setTradeId(trade.getParentId());
        tradeVO.setId(generatorService.generateProviderThirdTid());
        // 拆单后，重新计算价格信息
        ChannelOrderPriceVO tradePrice = tradeVO.getTradePrice();
        // 商品总价
        BigDecimal goodsPrice = BigDecimal.ZERO;

        Long goodsPoint = 0L;

        // 订单总价:实付金额
        BigDecimal orderPrice = BigDecimal.ZERO;
        // 订单供货价总额
        BigDecimal orderSupplyPrice = BigDecimal.ZERO;
        for (ChannelOrderItemVO item : tradeVO.getTradeItems()) {
            // 商品总价
            goodsPrice =
                    goodsPrice.add(
                            Objects.isNull(item.getPrice())
                                    ? BigDecimal.ZERO
                                    : item.getPrice().multiply(BigDecimal.valueOf(item.getNum())));
            goodsPoint += (Objects.isNull(item.getPoints()) ? 0 : item.getPoints()) * item.getNum();
            // 商品分摊价格
            BigDecimal splitPrice =
                    Objects.isNull(item.getSplitPrice()) ? BigDecimal.ZERO : item.getSplitPrice();
            orderPrice = orderPrice.add(splitPrice);
            // 订单供货价总额
            orderSupplyPrice = orderSupplyPrice.add(item.getTotalSupplyPrice());
        }

        if (CollectionUtils.isNotEmpty(tradeVO.getPreferential())){
            for (ChannelOrderItemVO item : tradeVO.getPreferential()) {
                // 商品总价
                goodsPrice =
                        goodsPrice.add(
                                Objects.isNull(item.getPrice())
                                        ? BigDecimal.ZERO
                                        : item.getPrice().multiply(BigDecimal.valueOf(item.getNum())));
                goodsPoint += (Objects.isNull(item.getPoints()) ? 0 : item.getPoints()) * item.getNum();
                // 商品分摊价格
                BigDecimal splitPrice =
                        Objects.isNull(item.getSplitPrice()) ? BigDecimal.ZERO : item.getSplitPrice();
                orderPrice = orderPrice.add(splitPrice);
                // 订单供货价总额
                orderSupplyPrice = orderSupplyPrice.add(item.getTotalSupplyPrice());
            }
        }

        if (CollectionUtils.isNotEmpty(tradeVO.getGifts())) {
            for (ChannelOrderItemVO item : tradeVO.getGifts()) {
                // 订单供货价总额
                orderSupplyPrice = orderSupplyPrice.add(item.getTotalSupplyPrice());
            }
        }
        // 计算VOP运费
        List<VopFreightSkuDTO> freightSkuList =
                this.zipItem(trade.getTradeItems(), trade.getGifts(), trade.getPreferential(), ThirdPlatformType.VOP).stream()
                        .map(
                                i -> {
                                    VopFreightSkuDTO skuDTO = new VopFreightSkuDTO();
                                    skuDTO.setSkuId(i.getThirdPlatformSkuId());
                                    skuDTO.setNum(i.getBuyCount().intValue());
                                    return skuDTO;
                                })
                        .collect(Collectors.toList());
        VopOrderFreightQueryRequest request =
                VopOrderFreightQueryRequest.builder()
                        .sku(freightSkuList)
                        .province(NumberUtils.toInt(thirdAddress.getProvinceId()))
                        .city(NumberUtils.toInt(thirdAddress.getCityId()))
                        .county(NumberUtils.toInt(thirdAddress.getAreaId()))
                        .town(NumberUtils.toInt(thirdAddress.getStreetId()))
                        .build();
        BigDecimal freight = vopOrderProvider.queryFreight(request).getContext().getFreight();

        tradeVO.setThirdProvinceCode(thirdAddress.getProvinceId());
        tradeVO.setThirdCityCode(thirdAddress.getCityId());
        tradeVO.setThirdDivisionCode(thirdAddress.getAreaId());
        tradeVO.setThirdStreetCode(thirdAddress.getStreetId());
        tradeVO.setThirdPlatFormFreight(freight);
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
        tradeVO.setTradePrice(tradePrice);
        response.setChannelTradeList(Collections.singletonList(tradeVO));
        return response;
    }

    @Override
    public BigDecimal queryFreight(List<ChannelGoodsInfoDTO> channelGoodsInfoDTOList, PlatformAddress address) {

        //过滤商品
        List<ChannelGoodsInfoDTO> skuList =
                channelGoodsInfoDTOList.stream()
                        .filter(i ->ThirdPlatformType.VOP.equals(i.getThirdPlatformType())
                                                && StringUtils.isNotBlank(i.getThirdPlatformSkuId()))
                        .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(skuList)) {
            return BigDecimal.ZERO;
        }
        ThirdAddress thirdAddress = vopAddressService.convertPlatformToThird(address);
        List<VopFreightSkuDTO> freightSkuList = skuList.stream()
                        .map(
                                i -> {
                                    VopFreightSkuDTO skuDTO = new VopFreightSkuDTO();
                                    skuDTO.setSkuId(i.getThirdPlatformSkuId());
                                    skuDTO.setNum(i.getNum().intValue());
                                    return skuDTO;
                                })
                        .collect(Collectors.toList());
        VopOrderFreightQueryRequest request =
                VopOrderFreightQueryRequest.builder()
                        .sku(freightSkuList)
                        .province(NumberUtils.toInt(thirdAddress.getProvinceId()))
                        .city(NumberUtils.toInt(thirdAddress.getCityId()))
                        .county(NumberUtils.toInt(thirdAddress.getAreaId()))
                        .town(NumberUtils.toInt(thirdAddress.getStreetId()))
                        .build();
        BigDecimal freight = vopOrderProvider.queryFreight(request).getContext().getFreight();
        return freight;
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
                                        ThirdPlatformType.VOP.equals(t.getThirdPlatformType())
                                                && CollectionUtils.isNotEmpty(
                                                        t.getThirdPlatformOrderIds()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(thirdTrades)) {
            return compensateResponse;
        }
        for (ChannelOrderDTO thirdTrade : thirdTrades) {
            if (thirdTrade.getTradeState() == null) {
                thirdTrade.setTradeState(new ChannelOrderStateDTO());
            }
            // 未付款或待确认
            if (PayState.UNCONFIRMED.equals(thirdTrade.getTradeState().getPayState())
                    || PayState.NOT_PAID.equals(thirdTrade.getTradeState().getPayState())) {
                VopQueryOrderDetailRequest request = new VopQueryOrderDetailRequest();
                request.setJdOrderId(
                        NumberUtils.toLong(thirdTrade.getThirdPlatformOrderIds().get(0)));
                request.setQueryExts("jdOrderState");
                VopQueryOrderDetailResponse response = null;
                try {
                    response = vopOrderProvider.queryOrderDetail(request).getContext();
                } catch (SbcRuntimeException e) {
                    log.error(
                            "VOP子订单id={}，VOP订单业务异常! tradeId={}，error=",
                            thirdTrade.getId(),
                            thirdTrade.getTradeId(),
                            e);
                    // 业务性问题
                    if (CommonErrorCodeEnum.K999999.getCode().equals(e.getErrorCode())) {
                        this.errorTrade(thirdTrade, compensateResponse);
                    } else { // 外网网络性问题
                        this.unconfirmedTrade(thirdTrade, compensateResponse);
                    }
                } catch (Exception e) { // 内网网络问题
                    log.error(
                            "VOP子订单id={}，VOP订单业务其他异常! tradeId={}，error=",
                            thirdTrade.getId(),
                            thirdTrade.getTradeId(),
                            e);
                    this.unconfirmedTrade(thirdTrade, compensateResponse);
                }

                if (response != null) {
                    // 有效，但未确认库存
                    if (Integer.valueOf(1).equals(response.getOrderState())
                            && Integer.valueOf(0).equals(response.getSubmitState())) {
                        ChannelOrderPayRequest payRequest = new ChannelOrderPayRequest();
                        payRequest.setChannelTrade(
                                KsBeanUtil.convert(thirdTrade, ChannelTradeDTO.class));
                        try {
                            channelOrderProvider.pay(payRequest);
                        } catch (SbcRuntimeException e) {
                            log.error(
                                    "VOP子订单id={}，VOP订单预占库存业务异常! tradeId={}，error=",
                                    thirdTrade.getId(),
                                    thirdTrade.getTradeId(),
                                    e);
                            // 业务性问题
                            if (CommonErrorCodeEnum.K999999.getCode().equals(e.getErrorCode())) {
                                this.errorTrade(thirdTrade, compensateResponse);
                            } else { // 外网网络性问题
                                this.unconfirmedTrade(thirdTrade, compensateResponse);
                            }
                        } catch (Exception e) { // 内网网络问题
                            this.unconfirmedTrade(thirdTrade, compensateResponse);
                            log.error(
                                    "VOP子订单id={}，VOP订单预占库存网络异常! tradeId={}，error=",
                                    thirdTrade.getId(),
                                    thirdTrade.getTradeId(),
                                    e);
                        }

                    } else if (Integer.valueOf(1).equals(response.getOrderState())
                            && Integer.valueOf(1).equals(response.getSubmitState())
                            && Objects.nonNull(response.getJdOrderState())
                            && response.getJdOrderState() >= 5) {
                        //1.新单、2.等待支付、3.等待支付确认、4.延迟付款确认、>=5表示进入支付成功后环节
                        compensateResponse.getPaySuccessThirdTradeId().add(thirdTrade.getId());
                    } else if (Integer.valueOf(0).equals(response.getOrderState())) {
                        //取消订单
                        this.errorTrade(thirdTrade, compensateResponse);
                    }
                }
            }
        }
        return compensateResponse;
    }
}
