package com.wanmi.sbc.marketing.newplugin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.countPrice.CountMarketingPriceService;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.common.MarketingPluginLabelBuild;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.reduction.repository.MarketingFullReductionLevelRepository;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 满减插件
 *
 * @author zhanggaolei
 * @className MarketingReductionPlugin
 * @description TODO
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.REDUCTION)
public class MarketingReductionPlugin implements MarketingPluginInterface, CountMarketingPriceService {

    @Autowired MarketingFullReductionLevelRepository marketingFullReductionLevelRepository;

    @Autowired private MarketingMapper marketingMapper;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {

        return MarketingPluginLabelBuild.getDetailResponse(
                this.setLabel(request, MarketingPluginLabelVO.class));
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {

        return this.setLabel(request,MarketingPluginSimpleLabelVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request, MarketingPluginLabelDetailVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request, MarketingPluginLabelDetailVO.class);
    }


    /**
     * 获取营销描述<营销编号,描述>
     *
     * @param marketingList 营销列表
     * @return
     */
    private Map<Long, String> getLabelMap(List<MarketingResponse> marketingList) {
        Map<Long, String> labelMap = new HashMap<>();
        DecimalFormat fmt = new DecimalFormat("#.##");
        marketingList.stream()
                .forEach(
                        marketing -> {
                            if (Objects.nonNull(marketing)
                                    && CollectionUtils.isNotEmpty(
                                            marketing.getFullReductionLevelList())) {
                                List<String> descs =
                                        marketing.getFullReductionLevelList().stream()
                                                .map(
                                                        level -> {
                                                            if (MarketingSubType
                                                                            .REDUCTION_FULL_AMOUNT
                                                                    == marketing.getSubType()) {
                                                                return String.format(
                                                                        "满%s元减%s元",
                                                                        fmt.format(
                                                                                level
                                                                                        .getFullAmount()),
                                                                        fmt.format(
                                                                                level
                                                                                        .getReduction()));
                                                            }
                                                            return String.format(
                                                                    "满%s件减%s元",
                                                                    level.getFullCount(),
                                                                    fmt.format(
                                                                            level.getReduction()));
                                                        })
                                                .collect(Collectors.toList());
                                labelMap.put(
                                        marketing.getMarketingId(), StringUtils.join(descs, "，"));
                            }
                        });
        return labelMap;
    }

    private <T extends MarketingPluginSimpleLabelVO> T setLabel(
            GoodsInfoPluginRequest request, Class<T> c) {
        if (MarketingContext.isNotNullMultiMarketingMap(
                request.getGoodsInfoPluginRequest().getGoodsInfoId())) {
            List<MarketingResponse> marketingList =
                    MarketingContext.getBaseRequest()
                            .getMultiTypeMarketingMap()
                            .get(request.getGoodsInfoPluginRequest().getGoodsInfoId())
                            .stream()
                            .filter(
                                    marketing ->
                                            MarketingType.REDUCTION == marketing.getMarketingType())
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingList)) {
                MarketingResponse marketingObj = marketingList.get(0);
                // 填充营销描述<营销编号,描述>
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.REDUCTION.getId());
                label.setMarketingDesc("满减");
                label.setMarketingId(marketingObj.getMarketingId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                if (Objects.nonNull(marketingObj.getSubType())) {
                    label.setSubType(MarketingSubType.REDUCTION_FULL_AMOUNT.equals(marketingObj.getSubType()) ? 0 : 1);
                }
                if (c.equals(MarketingPluginSimpleLabelVO.class)) {
                    return (T) label;
                } else {
                    String desc =
                            this.getLabelMap(marketingList).get(marketingObj.getMarketingId());
                    label.setMarketingDesc(desc);
                    if (c.equals(MarketingPluginLabelVO.class)) {

                        return (T) label;
                    }
                    if (c.equals(MarketingPluginLabelDetailVO.class)) {

                        label.setDetail(marketingObj.getFullReductionLevelList());
                        return (T) label;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public CountPriceItemVO countMarketingPrice(CountPriceItemVO countPriceItemVO, CountPriceMarketingDTO countPriceMarketingDTO) {
        log.info("满减-算价服务 Item：{}, Marketing: {}" + JSONObject.toJSONString(countPriceItemVO), JSONObject.toJSONString(countPriceMarketingDTO));

        //选择目标商品
        List<CountPriceItemGoodsInfoVO> goodsInfoList = countPriceItemVO.getGoodsInfoList().stream()
                .filter(itemGoodsInfoVO -> countPriceMarketingDTO.getSkuIds().contains(itemGoodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(goodsInfoList)) {
            log.warn("满减-算价服务, 订单商品信息有误：{}", JSONObject.toJSONString(countPriceMarketingDTO.getSkuIds()));
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //根据规则Id查询规格信息
        List<MarketingFullReductionLevelVO> levelList = JSONArray.parseArray(JSONObject.toJSONString(Optional.ofNullable(countPriceMarketingDTO.getDetail())), MarketingFullReductionLevelVO.class);
        if (org.springframework.util.CollectionUtils.isEmpty(levelList)) {
            log.warn("满减-算价服务，用户选中的活动规则有误：{}", countPriceMarketingDTO.getMarketingLevelId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        Map<String, BigDecimal> beforePriceMap = goodsInfoList.stream().collect(Collectors.toMap(CountPriceItemGoodsInfoVO::getGoodsInfoId, CountPriceItemGoodsInfoVO::getSplitPrice));
        //目标商品总金额
        BigDecimal price = goodsInfoList.stream().map(i -> i.getSplitPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        //目标商品总数量
        Long count = goodsInfoList.stream().map(CountPriceItemGoodsInfoVO::getNum).reduce(0L, (a, b) -> a + b);
        //优惠金额
        BigDecimal discountsAmount = BigDecimal.ZERO;
        MarketingFullReductionLevelVO level = levelList.get(0);
        MarketingSubType subType = MarketingSubType.REDUCTION_FULL_AMOUNT;
        if (Objects.nonNull(level.getFullAmount())) {
            //满金额
            levelList.sort(Comparator.comparing(MarketingFullReductionLevelVO :: getFullAmount).reversed());
            Optional<MarketingFullReductionLevelVO> inviter = levelList.stream().filter(levelVO -> price.compareTo(levelVO.getFullAmount())>=0).findFirst();
            level = inviter.isPresent() ? inviter.get() : null;
        } else {
            //满数量
            subType = MarketingSubType.REDUCTION_FULL_COUNT;
            levelList.sort(Comparator.comparing(MarketingFullReductionLevelVO :: getFullCount).reversed());
            Optional<MarketingFullReductionLevelVO> inviter = levelList.stream().filter(levelVO -> count.compareTo(levelVO.getFullCount())>=0).findFirst();
            level = inviter.isPresent() ? inviter.get() : null;
        }


        if(Objects.isNull(level)){
            log.warn("满减-算价服务，用户选中的活动规则有误：{}", countPriceMarketingDTO.getMarketingLevelId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        discountsAmount = level.getReduction().compareTo(price) > 0 ? price : level.getReduction();
        //计算优惠均摊
        discountsAmount = divideAmount(goodsInfoList, discountsAmount);
        //封装返回数据
        wrapperItem(goodsInfoList, level.getMarketingId(), level.getReductionLevelId(), MarketingType.REDUCTION,
                beforePriceMap);
        countPriceItemVO.getTradeMarketings().add(wrapperTradeMarketingVO(goodsInfoList, level, discountsAmount, subType));

        log.info("满减-算价服务 End：{}" + JSONObject.toJSONString(countPriceItemVO));
        return countPriceItemVO;
    }

    private TradeMarketingVO wrapperTradeMarketingVO(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList, MarketingFullReductionLevelVO level, BigDecimal discountsAmount, MarketingSubType subType) {
        List<String> goodsInfoIdList = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        BigDecimal amount = itemGoodsInfoVOList.stream().map(i -> i.getSplitPrice()).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        TradeMarketingVO tradeMarketingVO = TradeMarketingVO.builder()
                .marketingId(level.getMarketingId())
                .marketingType(MarketingType.REDUCTION)
                .marketingLevelId(level.getReductionLevelId())
                .subType(subType)
                .skuIds(goodsInfoIdList)
                .discountsAmount(discountsAmount)
                .realPayAmount(amount)
                .reductionLevel(level)
                .build();
        return tradeMarketingVO;
    }
}
