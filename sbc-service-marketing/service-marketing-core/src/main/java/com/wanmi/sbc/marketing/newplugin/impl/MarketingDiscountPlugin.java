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
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullDiscountLevelVO;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.countPrice.CountMarketingPriceService;
import com.wanmi.sbc.marketing.discount.repository.MarketingFullDiscountLevelRepository;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.common.MarketingPluginLabelBuild;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 满折插件
 *
 * @author zhanggaolei
 * @className MarketingCouponPlugin
 * @description TODO
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.DISCOUNT)
public class MarketingDiscountPlugin implements MarketingPluginInterface, CountMarketingPriceService {

    @Autowired MarketingFullDiscountLevelRepository marketingFullDiscountLevelRepository;

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
                                            marketing.getFullDiscountLevelList())) {
                                List<String> descs =
                                        marketing.getFullDiscountLevelList().stream()
                                                .map(
                                                        level -> {
                                                            if (MarketingSubType
                                                                            .DISCOUNT_FULL_AMOUNT
                                                                    == marketing.getSubType()) {
                                                                return String.format(
                                                                        "满%s元享%s折",
                                                                        fmt.format(
                                                                                level
                                                                                        .getFullAmount()),
                                                                        fmt.format(
                                                                                level.getDiscount()
                                                                                        .multiply(
                                                                                                new BigDecimal(
                                                                                                        10))));
                                                            }
                                                            return String.format(
                                                                    "满%s件享%s折",
                                                                    level.getFullCount(),
                                                                    fmt.format(
                                                                            level.getDiscount()
                                                                                    .multiply(
                                                                                            new BigDecimal(
                                                                                                    10))));
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
                                            MarketingType.DISCOUNT == marketing.getMarketingType())
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingList)) {
                MarketingResponse marketingObj = marketingList.get(0);
                // 填充营销描述<营销编号,描述>
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.DISCOUNT.getId());
                label.setMarketingId(marketingObj.getMarketingId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                label.setMarketingDesc("满折");
                if (Objects.nonNull(marketingObj.getSubType())) {
                    label.setSubType(MarketingSubType.DISCOUNT_FULL_AMOUNT.equals(marketingObj.getSubType()) ? 0 : 1);
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

                        label.setDetail(marketingObj.getFullDiscountLevelList());
                        return (T) label;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public CountPriceItemVO countMarketingPrice(CountPriceItemVO countPriceItemVO, CountPriceMarketingDTO countPriceMarketingDTO) {
        log.info("满折-算价服务 Begin； goods：{}， marketing:{}", JSONObject.toJSONString(countPriceItemVO), JSONObject.toJSONString(countPriceMarketingDTO));

        //选择目标商品
        List<CountPriceItemGoodsInfoVO> itemVOList = countPriceItemVO.getGoodsInfoList().stream()
                .filter(itemGoodsInfoVO -> countPriceMarketingDTO.getSkuIds().contains(itemGoodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(itemVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<MarketingFullDiscountLevelVO> levelList = JSONArray.parseArray(JSONObject.toJSONString(Optional.ofNullable(countPriceMarketingDTO.getDetail())), MarketingFullDiscountLevelVO.class);
        if (org.springframework.util.CollectionUtils.isEmpty(levelList)) {
            log.warn("满折-算价服务，用户选中的活动规则有误：{}", countPriceMarketingDTO.getMarketingLevelId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        //统计目标商品总金额
        BigDecimal price = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        //统计目标商品数量
        Long count = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getNum).reduce(0L, Long::sum);

        //获取满足优惠等级
        MarketingFullDiscountLevelVO level = levelList.get(0);
        MarketingSubType subType = null;
        //满金额
        if (Objects.nonNull(level.getFullAmount())) {
            subType = MarketingSubType.DISCOUNT_FULL_AMOUNT;
            levelList.sort(Comparator.comparing(MarketingFullDiscountLevelVO::getFullAmount).reversed());
            Optional<MarketingFullDiscountLevelVO> inviter = levelList.stream().filter(levelVo -> price.compareTo(levelVo.getFullAmount()) >= 0).findFirst();
            level = inviter.orElse(null);
        } else { //满数量
            subType = MarketingSubType.DISCOUNT_FULL_COUNT;
            levelList.sort(Comparator.comparing(MarketingFullDiscountLevelVO::getFullCount).reversed());
            Optional<MarketingFullDiscountLevelVO> inviter = levelList.stream().filter(levelVo -> count.compareTo(levelVo.getFullCount()) >= 0).findFirst();
            level = inviter.orElse(null);
        }

        if (Objects.isNull(level)) {
            log.warn("满折-算价服务，用户选中的活动规则有误：{}", countPriceMarketingDTO.getMarketingLevelId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        Map<String, BigDecimal> beforePriceMap = itemVOList.stream().collect(Collectors.toMap(CountPriceItemGoodsInfoVO::getGoodsInfoId, CountPriceItemGoodsInfoVO::getSplitPrice));
        //计算优惠金额
        BigDecimal discountsAmount = price.subtract(price.multiply(level.getDiscount()).setScale(2, RoundingMode.HALF_UP));

        //计算优惠均摊
        discountsAmount = divideAmount(itemVOList, discountsAmount);
        // 封装返回数据
        wrapperItem(itemVOList, level.getMarketingId(), level.getDiscountLevelId(), MarketingType.DISCOUNT, beforePriceMap);
        countPriceItemVO.getTradeMarketings().add(wrapperTradeMarketingVO(itemVOList, level, discountsAmount, subType));
        log.info("满折-算价服务，End：{}", JSONObject.toJSONString(countPriceItemVO));
        return countPriceItemVO;
    }

    private TradeMarketingVO wrapperTradeMarketingVO(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList, MarketingFullDiscountLevelVO level, BigDecimal discountsAmount, MarketingSubType subType) {
        List<String> goodsInfoIdList = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        BigDecimal amount = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        return TradeMarketingVO.builder()
                .marketingId(level.getMarketingId())
                .marketingType(MarketingType.DISCOUNT)
                .marketingLevelId(level.getDiscountLevelId())
                .subType(subType)
                .skuIds(goodsInfoIdList)
                .discountsAmount(discountsAmount)
                .realPayAmount(amount)
                .fullDiscountLevel(level)
                .build();
    }
}
