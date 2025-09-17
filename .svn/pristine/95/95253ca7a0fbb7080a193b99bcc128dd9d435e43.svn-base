package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.marketing.api.provider.countPrice.TradeCountMarketingPriceProvider;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountMarketingPriceRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountPricePluginResponse;
import com.wanmi.sbc.marketing.bean.dto.CountPriceDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyMarketingInterface;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className TradeMarketingService
 * @description TODO
 * @date 2022/4/6 16:02
 */
@Service
public class TradeBuyMarketingService implements TradeBuyMarketingInterface {

    @Autowired TradeCountMarketingPriceProvider tradeCountMarketingPriceProvider;

    @Override
    public boolean multiTypeMapHandle(
            String skuId,
            MarketingPluginLabelDetailVO marketingPluginLabelDetailVO,
            Map<String, TradeItemDTO> skuTradeItemDTOMap,
            MarketingPluginType marketingPluginType,
            Map<String, CountPriceMarketingDTO> marketingIdCountPriceMarketingDTOMap,
            Long assignMarketingId) {
        boolean flag = true;
        Object object = marketingPluginLabelDetailVO.getDetail();
        String str = JSONObject.toJSONString(object);
        TradeItemDTO tradeItemDTO = skuTradeItemDTOMap.get(skuId);
        switch (marketingPluginType) {
            case BUYOUT_PRICE:
                List<MarketingBuyoutPriceLevelVO> marketingBuyoutPriceLevelVOS =
                        JSONArray.parseArray(str, MarketingBuyoutPriceLevelVO.class);
                if (Objects.nonNull(assignMarketingId)) {
                    marketingBuyoutPriceLevelVOS = marketingBuyoutPriceLevelVOS.stream()
                            .filter(m -> assignMarketingId.equals(m.getMarketingId())).collect(Collectors.toList());
                }
                for (MarketingBuyoutPriceLevelVO marketingBuyoutPriceLevelVO :
                        marketingBuyoutPriceLevelVOS) {
                    if (marketingBuyoutPriceLevelVO.getFullAmount() != null && marketingBuyoutPriceLevelVO.getChoiceCount() != null) {
                        BigDecimal fullAmount = marketingBuyoutPriceLevelVO.getFullAmount();
                        BigDecimal splitPrice = tradeItemDTO.getSplitPrice();
                        if (splitPrice.compareTo(fullAmount) > -1 && tradeItemDTO.getNum() >= marketingBuyoutPriceLevelVO.getChoiceCount()) {
                            flag = false;
                        }
                    }
                }
                break;
            case REDUCTION:
                List<MarketingFullReductionLevelVO> marketingFullReductionLevelVOS =
                        JSONArray.parseArray(str, MarketingFullReductionLevelVO.class);
                if (Objects.nonNull(assignMarketingId)) {
                    marketingFullReductionLevelVOS = marketingFullReductionLevelVOS.stream()
                            .filter(m -> assignMarketingId.equals(m.getMarketingId())).collect(Collectors.toList());
                }
                for (MarketingFullReductionLevelVO marketingFullReductionLevelVO :
                        marketingFullReductionLevelVOS) {
                    if (marketingFullReductionLevelVO.getFullAmount() != null) {
                        BigDecimal fullAmount = marketingFullReductionLevelVO.getFullAmount();
                        BigDecimal splitPrice = tradeItemDTO.getSplitPrice();
                        if (splitPrice.compareTo(fullAmount) > -1) {
                            flag = false;
                        }
                    } else if (marketingFullReductionLevelVO.getFullCount() != 0
                            && marketingFullReductionLevelVO.getFullCount() != null) {
                        Long num = tradeItemDTO.getBuyCycleNum() != null
                                ? tradeItemDTO.getNum() * tradeItemDTO.getBuyCycleNum()
                                : tradeItemDTO.getNum();
                        Long fullCount = marketingFullReductionLevelVO.getFullCount();
                        if (num >= fullCount) {
                            flag = false;
                        }
                    }
                }
                break;
            case DISCOUNT:
                List<MarketingFullDiscountLevelVO> marketingFullDiscountLevelVOS =
                        JSONArray.parseArray(str, MarketingFullDiscountLevelVO.class);
                if (Objects.nonNull(assignMarketingId)) {
                    marketingFullDiscountLevelVOS = marketingFullDiscountLevelVOS.stream()
                            .filter(m -> assignMarketingId.equals(m.getMarketingId())).collect(Collectors.toList());
                }
                for (MarketingFullDiscountLevelVO marketingFullDiscountLevelVO :
                        marketingFullDiscountLevelVOS) {
                    if (marketingFullDiscountLevelVO.getFullAmount() != null) {
                        BigDecimal fullAmount = marketingFullDiscountLevelVO.getFullAmount();
                        BigDecimal splitPrice = tradeItemDTO.getSplitPrice();
                        if (splitPrice.compareTo(fullAmount) > -1) {
                            flag = false;
                        }
                    } else if (marketingFullDiscountLevelVO.getFullCount() != 0
                            && marketingFullDiscountLevelVO.getFullCount() != null) {
                        Long num = tradeItemDTO.getBuyCycleNum() != null
                                ? tradeItemDTO.getNum() * tradeItemDTO.getBuyCycleNum()
                                : tradeItemDTO.getNum();
                        Long fullCount = marketingFullDiscountLevelVO.getFullCount();
                        if (num >= fullCount) {
                            flag = false;
                        }
                    }
                }
                break;
            case GIFT:
                List<MarketingFullGiftLevelVO> marketingFullGiftLevelVOS =
                        JSONArray.parseArray(str, MarketingFullGiftLevelVO.class);
                if (Objects.nonNull(assignMarketingId)) {
                    marketingFullGiftLevelVOS = marketingFullGiftLevelVOS.stream()
                            .filter(m -> assignMarketingId.equals(m.getMarketingId())).collect(Collectors.toList());
                }
                for (MarketingFullGiftLevelVO marketingFullGiftLevelVO :
                        marketingFullGiftLevelVOS) {
                    if (marketingFullGiftLevelVO.getFullAmount() != null) {
                        BigDecimal fullAmount = marketingFullGiftLevelVO.getFullAmount();
                        BigDecimal splitPrice = tradeItemDTO.getSplitPrice();
                        if (splitPrice.compareTo(fullAmount) > -1) {
                            flag = false;
                        }
                    } else if (marketingFullGiftLevelVO.getFullCount() != 0
                            && marketingFullGiftLevelVO.getFullCount() != null) {
                        Long num = tradeItemDTO.getNum();
                        Long fullCount = marketingFullGiftLevelVO.getFullCount();
                        if (num >= fullCount) {
                            flag = false;
                        }
                    }
                }
                break;
            case HALF_PRICE_SECOND_PIECE:
                List<MarketingHalfPriceSecondPieceLevelVO> marketingHalfPriceSecondPieceLevelVOS =
                        JSONArray.parseArray(str, MarketingHalfPriceSecondPieceLevelVO.class);
                if (Objects.nonNull(assignMarketingId)) {
                    marketingHalfPriceSecondPieceLevelVOS = marketingHalfPriceSecondPieceLevelVOS.stream()
                            .filter(m -> assignMarketingId.equals(m.getMarketingId())).collect(Collectors.toList());
                }
                for (MarketingHalfPriceSecondPieceLevelVO marketingHalfPriceSecondPieceLevelVO :
                        marketingHalfPriceSecondPieceLevelVOS) {
                    if (marketingHalfPriceSecondPieceLevelVO.getNumber() != 0
                            && marketingHalfPriceSecondPieceLevelVO.getNumber() != null) {
                        Long num = tradeItemDTO.getNum();
                        Long fullCount = marketingHalfPriceSecondPieceLevelVO.getNumber();
                        if (num >= fullCount) {
                            flag = false;
                        }
                    }
                }
                break;
            case RETURN:
                List<MarketingFullReturnLevelVO> marketingFullReturnLevelVOS =
                        JSONArray.parseArray(str, MarketingFullReturnLevelVO.class);
                for (MarketingFullReturnLevelVO marketingFullReturnLevelVO :
                        marketingFullReturnLevelVOS) {
                    if (marketingFullReturnLevelVO.getFullAmount() != null) {
                        BigDecimal fullAmount = marketingFullReturnLevelVO.getFullAmount();
                        BigDecimal splitPrice = tradeItemDTO.getSplitPrice();
                        if (splitPrice.compareTo(fullAmount) > -1) {
                            flag = false;
                        }
                    }
                }
                break;
            default:
                break;
        }

        if (!flag) {
            String id = marketingPluginLabelDetailVO.getMarketingId().toString();
            Long marketingId = Long.parseLong(id);
            String mapKey = marketingPluginType.getServiceType().concat(marketingId.toString());
            CountPriceMarketingDTO countPriceMarketingDTO =
                    marketingIdCountPriceMarketingDTOMap.get(mapKey);
            if (countPriceMarketingDTO == null) {
                countPriceMarketingDTO = new CountPriceMarketingDTO();
                countPriceMarketingDTO.setMarketingPluginType(marketingPluginType);
                countPriceMarketingDTO.setMarketingId(marketingId);
                List<String> skuIdList = new ArrayList<>();
                skuIdList.add(skuId);
                countPriceMarketingDTO.setSkuIds(skuIdList);
                countPriceMarketingDTO.setDetail(object);
                marketingIdCountPriceMarketingDTOMap.put(mapKey, countPriceMarketingDTO);
            } else {
                countPriceMarketingDTO.getSkuIds().add(skuId);
            }
        }
        return flag;
    }

    @Override
    public void bookingSaleHandle(
            ParamsDataVO paramsDataVO,
            String skuId,
            Map<String, TradeItemDTO> skuTradeItemDTOMap,
            MarketingPluginLabelDetailVO marketingPluginLabelDetailVO) {
        Long bookingSaleId =
                Long.parseLong(marketingPluginLabelDetailVO.getMarketingId().toString());
        Map<Long, BookingSaleVO> idBookingSaleVOMap =
                paramsDataVO.getBookingSaleVOS().stream()
                        .collect(Collectors.toMap(BookingSaleVO::getId, Function.identity()));
        Map<String, BigDecimal> skuGoodsInfoMap =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .collect(
                                Collectors.toMap(
                                        GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getMarketPrice));
        // 从库中查出的预售信息
        BookingSaleVO bookingSaleVO = idBookingSaleVOMap.get(bookingSaleId);
        // 获取sku对应的TradeItemDTO。填充预售专属信息
        TradeItemDTO tradeItemDTO = skuTradeItemDTOMap.get(skuId);
        tradeItemDTO.setIsBookingSaleGoods(Boolean.TRUE);
        tradeItemDTO.setBookingSaleId(bookingSaleId);
        tradeItemDTO.setPrice(
                Objects.isNull(bookingSaleVO.getBookingSaleGoods().getBookingPrice())
                        ? skuGoodsInfoMap.get(skuId)
                        : bookingSaleVO.getBookingSaleGoods().getBookingPrice());
        // 回填均摊价，用于给优惠券判断使用门槛
        tradeItemDTO.setSplitPrice(
                tradeItemDTO
                        .getPrice()
                        .multiply(new BigDecimal(tradeItemDTO.getNum()))
                        .setScale(2, RoundingMode.HALF_UP));
        tradeItemDTO.setBookingType(BookingType.FULL_MONEY);
        if (bookingSaleVO.getBookingType().equals(NumberUtils.INTEGER_ONE)) {
            BigDecimal handSelPrice = bookingSaleVO.getBookingSaleGoods().getHandSelPrice();
            BigDecimal inflationPrice = bookingSaleVO.getBookingSaleGoods().getInflationPrice();
            tradeItemDTO.setBookingType(BookingType.EARNEST_MONEY);
            tradeItemDTO.setEarnestPrice(
                    handSelPrice.multiply(BigDecimal.valueOf(tradeItemDTO.getNum())));
            if (Objects.nonNull(inflationPrice)) {
                tradeItemDTO.setSwellPrice(
                        inflationPrice.multiply(BigDecimal.valueOf(tradeItemDTO.getNum())));
            } else {
                tradeItemDTO.setSwellPrice(tradeItemDTO.getEarnestPrice());
            }
            tradeItemDTO.setHandSelStartTime(bookingSaleVO.getHandSelStartTime());
            tradeItemDTO.setHandSelEndTime(bookingSaleVO.getHandSelEndTime());
            tradeItemDTO.setTailStartTime(bookingSaleVO.getTailStartTime());
            tradeItemDTO.setTailEndTime(bookingSaleVO.getTailEndTime());
            tradeItemDTO.setPrice(skuGoodsInfoMap.get(skuId));
        }
    }

    @Override
    public void otherMarketingHandle(
            String skuId,
            Map<String, TradeItemDTO> skuTradeItemDTOMap,
            MarketingPluginLabelDetailVO marketingPluginLabelDetailVO,
            MarketingPluginType marketingPluginType) {
        TradeItemDTO tradeItemDTO = skuTradeItemDTOMap.get(skuId);
        if (MarketingPluginType.APPOINTMENT_SALE.equals(marketingPluginType)) {
            Long appointmentSaleId =
                    Long.parseLong(marketingPluginLabelDetailVO.getMarketingId().toString());
            tradeItemDTO.setAppointmentSaleId(appointmentSaleId);
            tradeItemDTO.setIsAppointmentSaleGoods(Boolean.TRUE);
        } else if (MarketingPluginType.SUITS.equals(marketingPluginType)){
            tradeItemDTO.setMarketingIds(Collections.singletonList(marketingPluginLabelDetailVO.getMarketingId()));
        } else if (MarketingPluginType.DISTRIBUTION.equals(marketingPluginType)){
            tradeItemDTO.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED);
            tradeItemDTO.setDistributionCommission(marketingPluginLabelDetailVO.getPluginPrice());
            return;
        }
        // 这里的营销是不需要再次计算的。直接取pluginPrice就是计算好的营销价格
        tradeItemDTO.setPrice(marketingPluginLabelDetailVO.getPluginPrice());
        tradeItemDTO.setLevelPrice(marketingPluginLabelDetailVO.getPluginPrice());
        BigDecimal splitPrice = marketingPluginLabelDetailVO.getPluginPrice()
                .multiply(new BigDecimal(tradeItemDTO.getNum()));
        if (tradeItemDTO.getBuyCycleNum() != null) {
            splitPrice = splitPrice.multiply(new BigDecimal(tradeItemDTO.getBuyCycleNum()));
        }
        tradeItemDTO.setSplitPrice(splitPrice.setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public void marketingHandle(
            ParamsDataVO paramsDataVO,
            Map<String, TradeItemDTO> skuTradeItemDTOMap,
            Map<String, CountPriceMarketingDTO> marketingIdCountPriceMarketingDTOMap) {
        // 活动集合为空，直接退出
        if (marketingIdCountPriceMarketingDTOMap.size() != 0) {
            List<CountPriceMarketingDTO> countPriceMarketingDTOList =
                    new ArrayList<>(marketingIdCountPriceMarketingDTOMap.values());
            TradeCountMarketingPriceRequest tradeCountMarketingPriceRequest =
                    new TradeCountMarketingPriceRequest();

            // 构建请求参数
            tradeCountMarketingPriceRequest.setCustomerId(
                    paramsDataVO.getCustomerVO().getCustomerId());
            tradeCountMarketingPriceRequest.setForceCommit(Boolean.FALSE);

            CountPriceDTO countPriceDTO = new CountPriceDTO();
            countPriceDTO.setStoreId(paramsDataVO.getStoreVO().getStoreId());

            List<CountPriceGoodsInfoDTO> goodsInfoVOList = new ArrayList<>();
            List<GoodsInfoVO> goodsInfoVOS = paramsDataVO.getGoodsInfoResponse().getGoodsInfos();
            goodsInfoVOS.forEach(
                    goodsInfoVO -> {
                        CountPriceGoodsInfoDTO countPriceGoodsInfoDTO =
                                new CountPriceGoodsInfoDTO();
                        countPriceGoodsInfoDTO.setGoodsInfoId(goodsInfoVO.getGoodsInfoId());
                        countPriceGoodsInfoDTO.setPrice(goodsInfoVO.getMarketPrice());
                        TradeItemDTO tradeItemDTO =
                                skuTradeItemDTOMap.get(goodsInfoVO.getGoodsInfoId());
                        if (Boolean.TRUE.equals(paramsDataVO.getOrderTag().getBuyCycleFlag())) {
                            countPriceGoodsInfoDTO.setNum(tradeItemDTO.getNum() * paramsDataVO.getRequest().getDeliveryCycleNum());
                        } else {
                            countPriceGoodsInfoDTO.setNum(tradeItemDTO.getNum());
                        }
                        countPriceGoodsInfoDTO.setBrandId(goodsInfoVO.getBrandId());
                        countPriceGoodsInfoDTO.setCateId(goodsInfoVO.getCateId());
                        countPriceGoodsInfoDTO.setStoreCateIds(goodsInfoVO.getStoreCateIds());
                        countPriceGoodsInfoDTO.setSplitPrice(tradeItemDTO.getSplitPrice());
                        goodsInfoVOList.add(countPriceGoodsInfoDTO);
                    });
            countPriceDTO.setGoodsInfoVOList(goodsInfoVOList);

            countPriceDTO.setMarketingVOList(countPriceMarketingDTOList);

            tradeCountMarketingPriceRequest.setCountPriceVOList(
                    Collections.singletonList(countPriceDTO));
            // 营销计算
            TradeCountPricePluginResponse tradeCountPricePluginResponse =
                    tradeCountMarketingPriceProvider
                            .tradeCountMarketingPrice(tradeCountMarketingPriceRequest)
                            .getContext();
            Map<Long, CountPriceItemVO> countPriceItemVOMap =
                    tradeCountPricePluginResponse.getCountPriceVOList().stream()
                            .collect(
                                    Collectors.toMap(
                                            CountPriceItemVO::getStoreId, Function.identity(), (v1, v2) -> v1));
            CountPriceItemVO countPriceItemVO =
                    countPriceItemVOMap.get(paramsDataVO.getStoreVO().getStoreId());
            List<TradeMarketingDTO> tradeMarketingDTOS =
                    KsBeanUtil.convert(
                            countPriceItemVO.getTradeMarketings(), TradeMarketingDTO.class);
            if (CollectionUtils.isNotEmpty(tradeMarketingDTOS)) {
                tradeMarketingDTOS.forEach(
                        tradeMarketingDTO -> {
                            if (CollectionUtils.isEmpty(tradeMarketingDTO.getGiftSkuIds())) {
                                tradeMarketingDTO.setGiftSkuIds(new ArrayList<>());
                            }
                            if (CollectionUtils.isEmpty(tradeMarketingDTO.getPreferentialSkuIds())) {
                                tradeMarketingDTO.setPreferentialSkuIds(new ArrayList<>());
                            }
                        });
            }
            paramsDataVO.setTradeMarketingList(tradeMarketingDTOS);
            Map<String, CountPriceItemGoodsInfoVO> countPriceItemGoodsInfoVOMap =
                    countPriceItemVO.getGoodsInfoList().stream()
                            .collect(
                                    Collectors.toMap(
                                            CountPriceItemGoodsInfoVO::getGoodsInfoId,
                                            Function.identity()));
            List<TradeItemDTO> tradeItems = paramsDataVO.getTradeItemDTOS();
            // 填充tradeItems中的营销价格
            tradeItems.forEach(
                    tradeItemDTO -> {
                        CountPriceItemGoodsInfoVO countPriceItemGoodsInfoVO =
                                countPriceItemGoodsInfoVOMap.get(tradeItemDTO.getSkuId());
                        if (countPriceItemGoodsInfoVO != null) {
                            tradeItemDTO.setSplitPrice(countPriceItemGoodsInfoVO.getSplitPrice());
                            if (CollectionUtils.isNotEmpty(countPriceItemGoodsInfoVO.getMarketingList())) {
                                List<Long> marketingIds =
                                        countPriceItemGoodsInfoVO.getMarketingList().stream()
                                                .map(CountPriceItemMarketingVO::getMarketingId)
                                                .collect(Collectors.toList());
                                List<Long> marketingLevelIds =
                                        countPriceItemGoodsInfoVO.getMarketingList().stream()
                                                .map(CountPriceItemMarketingVO::getMarketingLevelId)
                                                .collect(Collectors.toList());
                                tradeItemDTO.setMarketingIds(marketingIds);
                                tradeItemDTO.setMarketingLevelIds(marketingLevelIds);
                            }
                        }
                    });
        }

        if (paramsDataVO.getMarketingResponse() != null) {
            List<String> skuIds =
                    paramsDataVO.getMarketingResponse().getMarketingSuitsSkuVOList().stream()
                            .map(MarketingSuitsSkuVO::getSkuId)
                            .collect(Collectors.toList());
            TradeMarketingDTO tradeMarketingDTO = new TradeMarketingDTO();
            tradeMarketingDTO.setMarketingId(
                    paramsDataVO.getMarketingResponse().getMarketingVO().getMarketingId());
            tradeMarketingDTO.setSkuIds(skuIds);
            tradeMarketingDTO.setGiftSkuIds(new ArrayList<>());
            paramsDataVO.setTradeMarketingList(Collections.singletonList(tradeMarketingDTO));
        }
    }
}
