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
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingBuyoutPriceLevelVO;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.countPrice.CountMarketingPriceService;
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
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一口价插件
 *
 * @author zhanggaolei
 * @className MarketingBuyoutPricePlugin
 * @description
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.BUYOUT_PRICE)
public class MarketingBuyoutPricePlugin
        implements MarketingPluginInterface, CountMarketingPriceService {

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
        return this.setLabel(request, MarketingPluginLabelDetailVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        return this.setLabel(request, MarketingPluginLabelDetailVO.class);
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
                                            marketing.getBuyoutPriceLevelList())) {
                                List<String> descs =
                                        marketing.getBuyoutPriceLevelList().stream()
                                                .map(
                                                        level ->
                                                                String.format(
                                                                        "%s件%s元",
                                                                        level.getChoiceCount(),
                                                                        fmt.format(
                                                                                level
                                                                                        .getFullAmount())))
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
                                            MarketingType.BUYOUT_PRICE
                                                    == marketing.getMarketingType())
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingList)) {
                MarketingResponse marketingObj = marketingList.get(0);
                // 填充营销描述<营销编号,描述>
                Map<Long, String> labelMap = this.getLabelMap(marketingList);
                // 填充营销描述<营销编号,描述>
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.BUYOUT_PRICE.getId());
                label.setMarketingDesc(labelMap.get(marketingObj.getMarketingId()));
                label.setMarketingId(marketingObj.getMarketingId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                if (c.equals(MarketingPluginLabelVO.class)) {

                    return (T) label;
                }
                if (c.equals(MarketingPluginLabelDetailVO.class)) {

                    label.setDetail(marketingObj.getBuyoutPriceLevelList());
                    return (T) label;
                } else {

                    String des = label.getMarketingDesc();
                    if (StringUtils.isNotBlank(des)) {
                        int index = des.indexOf('，');
                        if (index <= 0) {
                            index = des.length();
                        }
                        des = des.substring(0, index);
                    }
                    // 处理完在做一次判断，防止为空，兜底打包一口价
                    if (StringUtils.isBlank(des)) {
                        des = "一口价优惠";
                    }
                    label.setMarketingDesc(des);
                    return (T) label;
                }
            }
        }
        return null;
    }

    @Override
    public CountPriceItemVO countMarketingPrice(CountPriceItemVO countPriceItemVO, CountPriceMarketingDTO countPriceMarketingDTO) {
        log.info("打包一口价格-算价服务 Begin； goods：{}， marketing:{}", JSONObject.toJSONString(countPriceItemVO), JSONObject.toJSONString(countPriceMarketingDTO));
        //选择目标商品
        List<CountPriceItemGoodsInfoVO> itemVOList = countPriceItemVO.getGoodsInfoList().stream()
                .filter(itemGoodsInfoVO -> countPriceMarketingDTO.getSkuIds().contains(itemGoodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(itemVOList)) {
            log.warn("打包一口价格-算价服务，目标商品信息有误:{}", countPriceMarketingDTO.getSkuIds());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<MarketingBuyoutPriceLevelVO> levelList = JSONArray.parseArray(JSONObject.toJSONString(Optional.ofNullable(countPriceMarketingDTO.getDetail())), MarketingBuyoutPriceLevelVO.class);
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(levelList)) {
            log.warn("打包一口价格-算价服务，活动规则查询有误:{}", countPriceMarketingDTO.getMarketingId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        //按照金额排序
        levelList.sort(Comparator.comparing(MarketingBuyoutPriceLevelVO::getFullAmount).reversed());
        //合计数量
        BigDecimal countPrice = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        Long count = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getNum).reduce(0L, Long::sum);
        Optional<MarketingBuyoutPriceLevelVO> inviter = levelList.stream().filter(leve -> countPrice.compareTo(leve.getFullAmount()) >= 0 && count.compareTo(leve.getChoiceCount()) >=0).findFirst();
        MarketingBuyoutPriceLevelVO level = inviter.orElse(null);
        if (Objects.isNull(level)) {
            log.warn("打包一口价格-算价服务，用户选中的活动规则有误:{}", countPriceMarketingDTO.getMarketingId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        itemVOList =
                itemVOList.stream().sorted((v1, v2) -> {
                    // 优先使用市场价排序，与前端保持一致
                    if (Objects.nonNull(v2.getPrice()) && Objects.nonNull(v1.getPrice())) {
                        return v2.getPrice().compareTo(v1.getPrice());
                    }
                    return v2.getSplitPrice().compareTo(v1.getSplitPrice());
                }).collect(Collectors.toList());
        Map<String, BigDecimal> beforePriceMap = itemVOList.stream().collect(Collectors.toMap(CountPriceItemGoodsInfoVO::getGoodsInfoId, CountPriceItemGoodsInfoVO::getSplitPrice));

        //未满足活动的金额
        BigDecimal noDiscountPrice = BigDecimal.ZERO;
        Long mod = NumberUtils.LONG_ZERO;
        //满足活动的金额
        BigDecimal fullAmount = BigDecimal.ZERO;
        //一口价的数量
        Long levelCount = level.getChoiceCount();
        // *** 多等级：按照前端传入的等级仅优惠一次    单等级使用：每达到一次条件生效一次优惠  ***
        if (levelList.size() > 1) {
            boolean isDiscounts = false;
            for (CountPriceItemGoodsInfoVO info : itemVOList) {
                mod = mod + info.getNum();
                if (!isDiscounts && mod.compareTo(levelCount) < 0) {
                    noDiscountPrice = noDiscountPrice.add(info.getSplitPrice());
                    continue;
                }
                if (!isDiscounts) {
                    noDiscountPrice = BigDecimal.ZERO;
                    fullAmount = level.getFullAmount();
                    isDiscounts = true;
                    if(mod.compareTo(levelCount) == 0) {
                        continue;
                    }
                    if(mod.compareTo(levelCount) > 0) {
                        long outNum = mod-levelCount;
                        //计算超出 数量实付金额
                        BigDecimal ratio = BigDecimal.valueOf(outNum).divide(BigDecimal.valueOf(info.getNum()), 8,
                                RoundingMode.HALF_UP);
                        BigDecimal price = ratio.multiply(info.getSplitPrice()).setScale(2, RoundingMode.HALF_UP);
                        noDiscountPrice = noDiscountPrice.add(price);
                        continue;
                    }
                }
                noDiscountPrice = noDiscountPrice.add(info.getSplitPrice());
            }
        } else {
            for (CountPriceItemGoodsInfoVO info : itemVOList) {
                mod = mod + info.getNum();
                if (mod.compareTo(levelCount) < 0) {
                    noDiscountPrice = noDiscountPrice.add(info.getSplitPrice());
                    continue;
                }
                noDiscountPrice = BigDecimal.ZERO;
                if (mod.compareTo(levelCount) == 0) {
                    fullAmount = fullAmount.add(level.getFullAmount());
                    mod = NumberUtils.LONG_ZERO;
                }

                if (mod.compareTo(levelCount) > 0){
                    fullAmount = fullAmount.add(BigDecimal.valueOf(mod / levelCount).multiply(level.getFullAmount()));
                    mod =  mod % levelCount;
                    BigDecimal ratio = BigDecimal.valueOf(mod).divide(BigDecimal.valueOf(info.getNum()), 8, RoundingMode.HALF_UP);
                    BigDecimal price = ratio.multiply(info.getSplitPrice()).setScale(2, RoundingMode.HALF_UP);
                    noDiscountPrice = noDiscountPrice.add(price);
                }
            }
        }

        //一口价+部分未优惠的单价
        BigDecimal newPrice = fullAmount.add(noDiscountPrice);

        //商品总价
        BigDecimal price = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discountsAmount = newPrice.compareTo(price) > 0 ? BigDecimal.ZERO : price.subtract(newPrice);

        //计算优惠均摊
        discountsAmount = divideAmount(itemVOList, discountsAmount);
        //封装返回数据
        wrapperItem(itemVOList,level.getMarketingId(), level.getReductionLevelId(), MarketingType.BUYOUT_PRICE, beforePriceMap);
        countPriceItemVO.getTradeMarketings().add(wrapperTradeMarketingVO(itemVOList, level, discountsAmount));

        //根据活动ID查询规格
        log.info("打包一口价格-算价服务 End：" + JSONObject.toJSONString(countPriceItemVO));
        return countPriceItemVO;
    }

    private TradeMarketingVO wrapperTradeMarketingVO(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList, MarketingBuyoutPriceLevelVO level, BigDecimal discountsAmount) {
        List<String> goodsInfoIdList = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        BigDecimal amount = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        return TradeMarketingVO.builder()
                .marketingId(level.getMarketingId())
                .marketingType(MarketingType.BUYOUT_PRICE)
                .marketingLevelId(level.getReductionLevelId())
                .skuIds(goodsInfoIdList)
                .discountsAmount(discountsAmount)
                .realPayAmount(amount)
                .buyoutPriceLevel(level)
                .build();
    }
}
