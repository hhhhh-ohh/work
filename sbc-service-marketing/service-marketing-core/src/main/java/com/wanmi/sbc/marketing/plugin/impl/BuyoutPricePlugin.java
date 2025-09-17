package com.wanmi.sbc.marketing.plugin.impl;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.MarketingLabelVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.MarketingBuyoutPriceLevelVO;
import com.wanmi.sbc.marketing.buyoutprice.model.entry.MarketingBuyoutPriceLevel;
import com.wanmi.sbc.marketing.buyoutprice.repository.MarketingBuyoutPriceLevelRepository;
import com.wanmi.sbc.marketing.common.model.entity.TradeMarketing;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.repository.MarketingRepository;
import com.wanmi.sbc.marketing.common.request.TradeItemInfo;
import com.wanmi.sbc.marketing.common.request.TradeMarketingPluginRequest;
import com.wanmi.sbc.marketing.common.request.TradeMarketingRequest;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.common.response.TradeMarketingResponse;
import com.wanmi.sbc.marketing.plugin.IGoodsDetailPlugin;
import com.wanmi.sbc.marketing.plugin.IGoodsListPlugin;
import com.wanmi.sbc.marketing.plugin.ITradeCommitPlugin;
import com.wanmi.sbc.marketing.request.MarketingPluginRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 营销打包一口价插件
 * Created by dyt on 2020/04/14.
 */
@Repository("buyoutPricePlugin")
public class BuyoutPricePlugin implements IGoodsListPlugin, IGoodsDetailPlugin, ITradeCommitPlugin {


    @Autowired
    private MarketingBuyoutPriceLevelRepository buyoutPriceLevelRepository;

    @Autowired
    private MarketingRepository marketingRepository;

    /**
     * 商品列表处理
     *
     * @param goodsInfos 商品数据
     * @param request    参数
     */
    @Override
    public void goodsListFilter(List<GoodsInfoVO> goodsInfos, MarketingPluginRequest request) {
        if (request.getCommitFlag()){
            return;
        }
        List<MarketingResponse> marketingList = request.getMarketingMap().values().stream()
                .flatMap(Collection::stream)
                .filter(marketing -> MarketingType.BUYOUT_PRICE == marketing.getMarketingType())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(marketingList)) {
            return;
        }

        Map<Long, MarketingResponse> params = new HashMap<>();
        marketingList.forEach(marketingResponse -> params.put(marketingResponse.getMarketingId(), marketingResponse));
        //填充营销描述<营销编号,描述>
        Map<Long, String> labelMap = this.getLabelMap(marketingList);
        goodsInfos.stream()
                .filter(goodsInfo -> request.getMarketingMap().containsKey(goodsInfo.getGoodsInfoId()))
                .forEach(goodsInfo -> {
                    request.getMarketingMap().get(goodsInfo.getGoodsInfoId()).stream()
                            .filter(marketing -> MarketingType.BUYOUT_PRICE == marketing.getMarketingType())
                            .forEach(marketing -> {
                                MarketingLabelVO label = new MarketingLabelVO();
                                label.setMarketingId(marketing.getMarketingId());
                                label.setMarketingType(marketing.getMarketingType().toValue());
                                label.setMarketingDesc(labelMap.get(marketing.getMarketingId()));
                                label.setJoinLevel(marketing.getJoinLevel());
                                goodsInfo.getMarketingLabels().add(label);
                            });
                });
    }

    /**
     * 商品详情处理
     *
     * @param detailResponse 商品详情数据
     * @param request        参数
     */
    @Override
    public void goodsDetailFilter(GoodsInfoDetailByGoodsInfoResponse detailResponse, MarketingPluginRequest request) {
        List<MarketingResponse> marketingList = request.getMarketingMap().get(detailResponse.getGoodsInfo().getGoodsInfoId());
        if (CollectionUtils.isEmpty(marketingList)
                || marketingList.stream().filter(marketing -> MarketingType.BUYOUT_PRICE == marketing.getMarketingType()).count() < 1) {
            return;
        }
        MarketingResponse marketingObj = marketingList.stream().filter(marketing -> MarketingType.BUYOUT_PRICE == marketing.getMarketingType()).findFirst().orElse(new MarketingResponse());
        //填充营销描述<营销编号,描述>
        Map<Long, String> labelMap = this.getLabelMap(marketingList);
        MarketingLabelVO label = new MarketingLabelVO();
        label.setMarketingId(marketingObj.getMarketingId());
        label.setMarketingType(marketingObj.getMarketingType().toValue());
        label.setMarketingDesc(labelMap.get(marketingObj.getMarketingId()));
        detailResponse.getGoodsInfo().getMarketingLabels().add(label);
    }

    /**
     *  多级规则：按照前端传入的等级仅优惠一次    单级规则：每达到一次条件生效一次优惠
     * @param request
     * @return
     */
    @Override
    public TradeMarketingResponse wraperMarketingFullInfo(TradeMarketingPluginRequest request) {
        TradeMarketingRequest tradeMarketingDTO = request.getTradeMarketingDTO();
        if (tradeMarketingDTO == null) {
            return null;
        }
        Marketing marketing = marketingRepository.findById(tradeMarketingDTO.getMarketingId()).orElse(null);
        if (marketing.getMarketingType() != MarketingType.BUYOUT_PRICE) {
            return null;
        }
        List<TradeItemInfo> tradeItems = request.getTradeItems();
        // 校验营销关联商品中，是否存在分销商品
        List<String> distriSkuIds = tradeItems.stream()
                .filter(item -> item.getDistributionGoodsAudit() == DistributionGoodsAudit.CHECKED)
                .map(TradeItemInfo::getSkuId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(distriSkuIds)) {
            tradeMarketingDTO.getSkuIds().forEach(skuId -> {
                if (distriSkuIds.contains(skuId)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            });
        }

//        MarketingBuyoutPriceLevel level = buyoutPriceLevelRepository.findById(tradeMarketingDTO.getMarketingLevelId())
//                .orElseThrow(() -> new SbcRuntimeException(OrderErrorCodeEnum.K050084));
        //
        List<MarketingBuyoutPriceLevel> levelList = buyoutPriceLevelRepository.findByMarketingId(marketing.getMarketingId());
        Assert.assertNotEmpty(levelList, OrderErrorCodeEnum.K050084);

        MarketingBuyoutPriceLevel level = levelList.stream()
                .filter(levels->levels.getReductionLevelId().equals(tradeMarketingDTO.getMarketingLevelId()))
                .findFirst().orElseThrow(()-> new SbcRuntimeException(OrderErrorCodeEnum.K050084));

        // 合计数量
        Long count = tradeItems.stream().map(TradeItemInfo::getNum).reduce(0L, (a, b) -> a + b);
        if (count < level.getChoiceCount()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        // 按价格倒序,以便优先以价格高的商品凑单计算优惠金额
//        Long discountCount = 0L;
//        BigDecimal noDiscountPrice = BigDecimal.ZERO;
        List<TradeItemInfo> itemInfoList = tradeItems.stream().sorted((v1, v2) -> v2.getPrice().compareTo(v1.getPrice())).collect(Collectors.toList());
//        for (TradeItemInfo info : itemInfoList) {
//            if (discountCount >= level.getChoiceCount()) {
//                noDiscountPrice = noDiscountPrice.add(info.getPrice().multiply(BigDecimal.valueOf(info.getNum())));
//            } else {
//                discountCount += info.getNum();
//                if(discountCount > level.getChoiceCount()){
//                    noDiscountPrice = noDiscountPrice.add(info.getPrice().multiply(BigDecimal.valueOf((discountCount-level.getChoiceCount()))));
//                }
//            }
//        }

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
            for (TradeItemInfo info : itemInfoList) {
                mod = mod + info.getNum();
                if (!isDiscounts && mod.compareTo(levelCount) < 0) {
                    noDiscountPrice = noDiscountPrice.add(info.getPrice().multiply(BigDecimal.valueOf(info.getNum())));
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
                        Long outNum = mod-levelCount;
                        noDiscountPrice = noDiscountPrice.add(info.getPrice().multiply(BigDecimal.valueOf((outNum))));
                        continue;
                    }
                }
                noDiscountPrice = noDiscountPrice.add(info.getPrice().multiply(BigDecimal.valueOf((info.getNum()))));
            }
        } else {
            for (TradeItemInfo info : itemInfoList) {
                mod = mod + info.getNum();
                if (mod.compareTo(levelCount) < 0) {
                    noDiscountPrice = noDiscountPrice.add(info.getPrice().multiply(BigDecimal.valueOf(info.getNum())));
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
                    noDiscountPrice = noDiscountPrice.add(info.getPrice().multiply(BigDecimal.valueOf((mod))));
                }
            }
        }

        //一口价+部分未优惠的单价
        BigDecimal newPrice = fullAmount.add(noDiscountPrice);

        //商品总价
        BigDecimal price = tradeItems.stream().map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getNum()))).reduce(
                BigDecimal.ZERO, BigDecimal::add);
        //一口价+部分未优惠的单价
      //  BigDecimal newPrice = level.getFullAmount().add(noDiscountPrice);

        //如果一口价>总价就以总价为准
        BigDecimal amount = newPrice.compareTo(price) > 0 ? price : newPrice;

        TradeMarketingResponse response = new TradeMarketingResponse();
        response.setTradeMarketing(TradeMarketing.builder()
                .buyoutPriceLevel(KsBeanUtil.copyPropertiesThird(level, MarketingBuyoutPriceLevelVO.class))
                .discountsAmount(price.subtract(amount))
                .realPayAmount(amount)
                .marketingId(marketing.getMarketingId())
                .marketingName(marketing.getMarketingName())
                .marketingType(marketing.getMarketingType())
                .skuIds(tradeMarketingDTO.getSkuIds())
                .subType(marketing.getSubType())
                .build());
        return response;
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
        marketingList.forEach(marketing ->{
            if (Objects.nonNull(marketing)&&CollectionUtils.isNotEmpty(marketing.getBuyoutPriceLevelList())){
                List<String> descs = marketing.getBuyoutPriceLevelList().stream()
                        .map(level -> String.format("%s件%s元", level.getChoiceCount(), fmt.format(level.getFullAmount())))
                        .collect(Collectors.toList());
                labelMap.put(marketing.getMarketingId(), StringUtils.join(descs, "，"));
            }
        });
        return labelMap;
    }
}